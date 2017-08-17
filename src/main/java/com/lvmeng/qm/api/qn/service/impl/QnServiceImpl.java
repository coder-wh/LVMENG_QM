package com.lvmeng.qm.api.qn.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
	public BaseResult dealWithQn(MultipartFile file) {
		BaseResult result = new BaseResult();
		try {
			List<Questionnaire> list = ExcelUtil.toVO(file.getInputStream());
			dealWithQn(list);
			int rows = qnDao.saveQn(list);
			if (rows != list.size()) {
				result.failure("问卷存储失败条数" + (list.size() - rows));
			}
		}  catch (Exception e) {
			e.printStackTrace();
			result.failure(e.getMessage());
		}
		return result;
	}
	
	private void dealWithQn(List<Questionnaire> list){
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
			for (int i = 0; i < l.size(); i++) {
				String res = l.get(i);
				if (StringUtils.isNotBlank(res)){
					if (CodeTable.proPattern.containsKey(i+d)){
						Pattern pattern = CodeTable.proPattern.get(i+d);
						dealWithAnswer(proQn, res, pattern);
					}else if (CodeTable.salePattern.containsKey(i+d)){
						Pattern pattern = CodeTable.salePattern.get(i+d);
						dealWithAnswer(saleQn, res, pattern);
					}else if (CodeTable.funcPattern.containsKey(i+d)){
						Pattern pattern = CodeTable.funcPattern.get(i+d);
						dealWithAnswer(funcQn, res, pattern);
					}else if (CodeTable.contactPattern.containsKey(i+d)){
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
	}

	private void dealWithAnswer(BaseQn qn, String res, Pattern pattern) {
		switch (pattern.getType()) {
		case SELECT:
			if (pattern.getList().contains(res)){
				qn.getQuestionnaire().add(pattern.getIndex(), res);
			}
			break;
		case LESS:
			if (StringUtil.subNumber(res) < pattern.getScore()){
				qn.getQuestionnaire().add(pattern.getIndex(), res);
			}
			break;
		case ALL:
			if (StringUtils.isNotBlank(res)){
				qn.getQuestionnaire().add(pattern.getIndex(), res);
			}
			break;
		case COMPARE:
			if (StringUtils.isNotBlank(res)){
				qn.getQuestionnaire().add(pattern.getIndex(), res);
			}
			break;
		default:
			break;
		}
	}

}