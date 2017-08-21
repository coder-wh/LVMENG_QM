package com.lvmeng.qm.api.qn.service.impl;

import java.io.FileOutputStream;
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
			
			List<Questionnaire> list = ExcelUtil.toVO(file.getInputStream());
			Map<String, List<? extends BaseQn>> map = dealWithQn(list, response);
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
				default:
					break;
				}
			}
			out = new FileOutputStream("C:/Users/wanghui/Desktop/梅姐/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")) + ".xls");
			
			DownloadUtil.downloadExcel(response, workbook, "测试下载.xls");

			workbook.write(out);
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
	
	private Map<String,List<? extends BaseQn>> dealWithQn(List<Questionnaire> list,HttpServletResponse response) {
		Map<String,List<? extends BaseQn>> map = new HashMap<>();
		List<ProQn> proQnList = new ArrayList<>();
		List<SaleQn> saleQnList = new ArrayList<>();
		List<FuncQn> funcQnList = new ArrayList<>();
		List<ContactQn> contactQnList = new ArrayList<>();
		for (Questionnaire qn : list) {
			List<String> l = qn.getQuestionnaire();
			int d = 42;
			ProQn proQn = new ProQn();
			SaleQn saleQn = new SaleQn();
			FuncQn funcQn = new FuncQn();
			ContactQn contactQn = new ContactQn();
			BeanUtils.copyProperties(qn, proQn);
			BeanUtils.copyProperties(qn, saleQn);
			BeanUtils.copyProperties(qn, funcQn);
			BeanUtils.copyProperties(qn, contactQn);
			for (int i = 0; i < l.size(); i++) {
				String res = l.get(i);
				if (StringUtils.isNotBlank(res)){
					if (CodeTable.proPattern.containsKey(i+d)){
						Pattern pattern = CodeTable.proPattern.get(i+d);
						dealWithAnswer(proQn, res, pattern);
					}
					if (CodeTable.salePattern.containsKey(i+d)){
						Pattern pattern = CodeTable.salePattern.get(i+d);
						dealWithAnswer(saleQn, res, pattern);
					}
					if (CodeTable.funcPattern.containsKey(i+d)){
						Pattern pattern = CodeTable.funcPattern.get(i+d);
						dealWithAnswer(funcQn, res, pattern);
					}
					if (CodeTable.contactPattern.containsKey(i+d)){
						Pattern pattern = CodeTable.contactPattern.get(i+d);
						dealWithAnswer(contactQn, res, pattern);
					}
				}
			}
			proQnList.add(proQn);
			saleQnList.add(saleQn);
			funcQnList.add(funcQn);
			contactQnList.add(contactQn);
		}
		map.put(CodeTable.QN_pro, proQnList);
		map.put(CodeTable.QN_sale, saleQnList);
		map.put(CodeTable.QN_func, funcQnList);
		map.put(CodeTable.QN_contact, contactQnList);
		return map;
	}

	private void dealWithAnswer(BaseQn qn, String res, Pattern pattern) {
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
			if (StringUtils.isNotBlank(res)){
				add(qn, res, pattern);
			}
			break;
		default:
			break;
		}
	}

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