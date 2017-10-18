package com.lvmeng.qm.api.qn.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lvmeng.qm.api.qn.dao.IQnDao;
import com.lvmeng.qm.api.qn.service.IQnService;
import com.lvmeng.qm.base.commons.CodeTable;
import com.lvmeng.qm.base.commons.filetype.FileType;
import com.lvmeng.qm.base.commons.filetype.FileTypeJudge;
import com.lvmeng.qm.base.commons.util.DownloadUtil;
import com.lvmeng.qm.base.commons.util.ExcelUtil;
import com.lvmeng.qm.base.commons.util.StringUtil;
import com.lvmeng.qm.base.vo.BaseQn;
import com.lvmeng.qm.base.vo.BaseResult;
import com.lvmeng.qm.base.vo.Pattern;
import com.lvmeng.qm.base.vo.SetString;
import com.lvmeng.qm.base.vo.contactQn.ContactQn;
import com.lvmeng.qm.base.vo.funcQn.FuncQn;
import com.lvmeng.qm.base.vo.proQn.ProQn;
import com.lvmeng.qm.base.vo.questionnaire.Questionnaire;
import com.lvmeng.qm.base.vo.saleQn.SaleQn;
@Service("qnService")
public class QnServiceImpl implements IQnService {
	@Resource
	private IQnDao qnDao;

	@Override
	public BaseResult dealWithQn(MultipartFile file,HttpServletResponse response) {
		BaseResult result = new BaseResult();
		HSSFWorkbook workbook = new HSSFWorkbook();
		OutputStream out = null;
		try {
			//校验文件格式
			if (file == null || file.getInputStream().available() == 0){
				return result.failure("请选择文件");
			}
			FileType type = FileTypeJudge.getType(file.getInputStream());
			if (FileType.XLSX_DOCX.equals(type)){
				return result.failure("该excel版本过高,请上传07版以下的excel");
			} else if (FileType.XLS_DOC.equals(type)){
			} else {
				return result.failure("该文件不是excel");
			}
			
			//读取Excel数据封装为对象
			List<Questionnaire> list = ExcelUtil.toVO(file.getInputStream());
			//对问卷列表依据规则做处理
			Map<String, List<? extends BaseQn>> map = dealWithQn(list);
//			String createTime = LocalDateTime.now().toString();
//			Set<String> keySet = map.keySet();
//			for (String key : keySet) {
//				List<? extends BaseQn> baseQnList = map.get(key);
//				qnDao.saveBaseQn(baseQnList, createTime, CodeTable.codeMap.get(key));
//			}
//			int rows = qnDao.saveQn(list,createTime);
//			if (rows != list.size()) {
//				result.failure("问卷存储失败条数" + (list.size() - rows));
//			}
			
			//遍历处理好的map  分别插入不同的list到不同的sheet中
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				List<? extends BaseQn> baseQnList = map.get(key);
				switch (key) {
				case CodeTable.QN_pro:
					ExcelUtil.toSheet(workbook, "工程", CodeTable.proHeader, baseQnList, ProQn.class);
					break;
				case CodeTable.QN_sale:
					ExcelUtil.toSheet(workbook, "销售", CodeTable.saleHeader, baseQnList, SaleQn.class);
					break;
				case CodeTable.QN_func:
					ExcelUtil.toSheet(workbook, "功能", CodeTable.funcHeader, baseQnList, FuncQn.class);
					break;
				case CodeTable.QN_contact:
					ExcelUtil.toSheet(workbook, "联系人", CodeTable.contactHeader, baseQnList, ContactQn.class);
					break;
				case CodeTable.QN_willOrNot:
					ExcelUtil.toSheet(workbook, "愿意不愿意", CodeTable.willOrNotHeader, baseQnList, SaleQn.class);
					break;
				default:
					break;
				}
			}
//			out = new FileOutputStream("C:/Users/wanghui/Desktop/梅姐/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")) + ".xls");
//			workbook.write(out);
			//将生成好的Excel以流的形式输出
			DownloadUtil.downloadExcel(response, workbook, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))+"处理.xls");
		}  catch (Exception e) {
			e.printStackTrace();
			result.failure(e.getMessage());
		} finally {
			try {
				if (out != null){
					out.close();
				}
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 处理问卷
	 * @param list
	 * @return
	 */
	private Map<String,List<? extends BaseQn>> dealWithQn(List<Questionnaire> list) {
		Map<String,List<? extends BaseQn>> map = new HashMap<>();
		//初始化五类结果相应的list
		List<ProQn> proQnList = new ArrayList<>();
		List<SaleQn> saleQnList = new ArrayList<>();
		List<SaleQn> willOrNotQnList = new ArrayList<>();
		List<FuncQn> funcQnList = new ArrayList<>();
		List<ContactQn> contactQnList = new ArrayList<>();
		for (Questionnaire qn : list) {
			List<String> l = qn.getQuestionnaire();
			//问卷从下标42开始
			int d = 42;
			//初始化五类结果对应的对象
			ProQn proQn = new ProQn();
			SaleQn saleQn = new SaleQn();
			FuncQn funcQn = new FuncQn();
			ContactQn contactQn = new ContactQn();
			SaleQn willOrNotQn = new SaleQn();
			for (int i = 0; i < l.size(); i++) {
				String res = l.get(i);
				if (StringUtils.isNotBlank(res)){
					//依次判断每个答案是否符合五类结果取值逻辑
					if (CodeTable.proPattern.containsKey(i+d)){
						Pattern pattern = CodeTable.proPattern.get(i+d);
						dealWithAnswer(proQn, res, pattern, qn);
					}
					if (CodeTable.salePattern.containsKey(i+d)){
						Pattern pattern = CodeTable.salePattern.get(i+d);
						dealWithAnswer(saleQn, res, pattern, qn);
					}
					if (CodeTable.funcPattern.containsKey(i+d)){
						Pattern pattern = CodeTable.funcPattern.get(i+d);
						dealWithAnswer(funcQn, res, pattern, qn);
					}
					if (CodeTable.contactPattern.containsKey(i+d)){
						Pattern pattern = CodeTable.contactPattern.get(i+d);
						dealWithAnswer(contactQn, res, pattern, qn);
					}
					if (CodeTable.willOrNotPattern.containsKey(i+d)) {
						Pattern pattern = CodeTable.willOrNotPattern.get(i+d);
						dealWithAnswer(willOrNotQn, res, pattern, qn);
					}
				}
			}
			//将基本属性的值拷贝至新对象
			BeanUtils.copyProperties(qn, proQn);
			BeanUtils.copyProperties(qn, saleQn);
			BeanUtils.copyProperties(qn, funcQn);
			BeanUtils.copyProperties(qn, contactQn);
			BeanUtils.copyProperties(qn, willOrNotQn);
			
			//被注释掉代码需求为    Q1 与绿盟科技的合作过程中您的主要角色是属于？【合作伙伴】→【绿盟】的【技术】、【伙伴】的【技术】销售sheet中。
//			if (proQn.getQuestionnaire().contains(new SetString(1,""))){
//				Set<SetString> set = proQn.getQuestionnaire();
//				boolean ifAddToProList = true;
//				for (SetString setString : set) {
//					if (setString.getIndex() == 0 && "合作伙伴".equals(setString.getStr())){
//						SaleQn sqn = new SaleQn();
//						BeanUtils.copyProperties(qn, sqn);
//						sqn.setQuestionnaire(proQn.getQuestionnaire());
//						saleQnList.add(sqn);
//						ifAddToProList = false;
//						break;
//					}
//				}
//				if (ifAddToProList){
//					proQnList.add(proQn);
//				}
//			}
//			//忘记此处代码的对应的需求了
//			if (saleQn.getQuestionnaire().contains(new SetString(1,""))){
//				saleQnList.add(saleQn);
//			}
			
			proQnList.add(proQn);
			saleQnList.add(saleQn);
			//将对应对象添加至list中
			willOrNotQnList.add(willOrNotQn);
			funcQnList.add(funcQn);
			contactQnList.add(contactQn);
		}
		
		//将五类结果集封装为map返回   并以key来区分
		map.put(CodeTable.QN_pro, proQnList);
		map.put(CodeTable.QN_sale, saleQnList);
		map.put(CodeTable.QN_func, funcQnList);
		map.put(CodeTable.QN_contact, contactQnList);
		map.put(CodeTable.QN_willOrNot, willOrNotQnList);
		return map;
	}

	/**
	 * 每个结果有四种取值逻辑  选择题   分数题    取全部值题(eg:文本题)  比较题(比较是否相同)
	 * @param qn
	 * @param res
	 * @param pattern
	 * @param sourceQn
	 */
	private void dealWithAnswer(BaseQn qn, String res, Pattern pattern, Questionnaire sourceQn) {
		switch (pattern.getType()) {
		case SELECT:
			if (StringUtil.isContains(pattern.getList(), res)){
				add(qn, res, pattern);
			}
			break;
		case LESS:
			if (StringUtil.subNumber(res) < pattern.getScore()){
				add(qn, res, pattern);
			}
			break;
		case ALL:
			if (StringUtils.isNotBlank(res)){
				add(qn, res, pattern);
			}
			break;
		case COMPARE:
			double score = 0.9;
			if (StringUtils.isNotBlank(res)){
				if (StringUtils.isNotBlank(sourceQn.getName())) {
					if ((StringUtils.isNotBlank(sourceQn.getName()) && (StringUtil.SimilarDegree(sourceQn.getName(), res) >= score || sourceQn.getName().contains(res)))
						|| (StringUtils.isNotBlank(sourceQn.getCustomerName()) && (StringUtil.SimilarDegree(sourceQn.getCustomerName(), res) >= score || sourceQn.getCustomerName().contains(res)))
						|| (StringUtils.isNotBlank(sourceQn.getEmail()) && (StringUtil.SimilarDegree(sourceQn.getEmail(), res) >= score || sourceQn.getEmail().contains(res)))
						|| (StringUtils.isNotBlank(sourceQn.getPhone()) && (StringUtil.SimilarDegree(sourceQn.getPhone(), res) >= score || sourceQn.getPhone().contains(res)))
						|| (StringUtils.isNotBlank(sourceQn.getTelephone()) && (StringUtil.SimilarDegree(sourceQn.getTelephone(), res) >= score || sourceQn.getTelephone().contains(res)))
						|| (StringUtils.isNotBlank(sourceQn.getAddress()) && (StringUtil.SimilarDegree(sourceQn.getAddress(), res) >= score || sourceQn.getAddress().contains(res)))) {
					}else {
						add(qn, res, pattern);
					}
				}
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 将符合逻辑的答案添加至对应答案集中
	 * 答案集为Set   以SetString中的index值来排序      好让答案填到对应的问题那一列中
	 * @param qn
	 * @param res
	 * @param pattern
	 */
	private void add(BaseQn qn, String res, Pattern pattern) {
		SetString setString = new SetString(pattern.getIndex(), res);
		Set<SetString> set = qn.getQuestionnaire();
		if (set.contains(setString)){
			SetString ss = get(set, setString);
			ss.setStr(ss.getStr() + CodeTable.separator + setString.getStr());
			set.add(ss);
		}else {
			set.add(setString);
		}
	}

	private SetString get(Set<SetString> set, SetString setString){
		for (SetString ss : set) {
			if (ss.equals(setString)){
				return ss;
			}
		}
		return null;
	}
}