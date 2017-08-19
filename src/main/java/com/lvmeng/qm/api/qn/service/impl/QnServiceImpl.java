package com.lvmeng.qm.api.qn.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lvmeng.qm.api.qn.dao.IQnDao;
import com.lvmeng.qm.api.qn.service.IQnService;
import com.lvmeng.qm.base.commons.CodeTable;
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
		try {
			List<Questionnaire> list = ExcelUtil.toVO(file.getInputStream());
			Map<String, List<? extends BaseQn>> map = dealWithQn(list, response);
			String createTime = LocalDateTime.now().toString();
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				List<? extends BaseQn> baseQnList = map.get(key);
				qnDao.saveBaseQn(baseQnList, createTime, CodeTable.codeMap.get(key));
			}
			int rows = qnDao.saveQn(list,createTime);
			if (rows != list.size()) {
				result.failure("问卷存储失败条数" + (list.size() - rows));
			}
		}  catch (Exception e) {
			e.printStackTrace();
			result.failure(e.getMessage());
		}
		return result;
	}
	
	private Map<String,List<? extends BaseQn>> dealWithQn(List<Questionnaire> list,HttpServletResponse response) throws IOException{
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
//		ServletOutputStream out = response.getOutputStream();
		OutputStream proout = new FileOutputStream("D:/proQn.xls");
		ExcelUtil.toExcel(proout, CodeTable.proHeader, proQnList, ProQn.class);
		OutputStream saleout = new FileOutputStream("D:/saleQn.xls");
		ExcelUtil.toExcel(saleout, CodeTable.saleHeader, saleQnList, SaleQn.class);
		OutputStream funcout = new FileOutputStream("D:/funcQn.xls");
		ExcelUtil.toExcel(funcout, CodeTable.funcHeader, funcQnList, FuncQn.class);
		OutputStream contactout = new FileOutputStream("D:/contactQn.xls");
		ExcelUtil.toExcel(contactout, CodeTable.contactHeader, contactQnList, ContactQn.class);
		
		return map;
	}

	private void dealWithAnswer(BaseQn qn, String res, Pattern pattern) {
		switch (pattern.getType()) {
		case SELECT:
			if (StringUtil.isContains(pattern.getList(), res)){
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
			break;
		case LESS:
			if (StringUtil.subNumber(res) < pattern.getScore()){
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
			break;
		case ALL:
			if (StringUtils.isNotBlank(res)){
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
			break;
		case COMPARE:
			if (StringUtils.isNotBlank(res)){
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
			break;
		default:
			break;
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