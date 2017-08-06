package com.lvmeng.qm.api.qn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lvmeng.qm.api.qn.dao.IQnDao;
import com.lvmeng.qm.api.qn.service.IQnService;
import com.lvmeng.qm.base.commons.util.ExcelUtil;
import com.lvmeng.qm.base.vo.BaseResult;
import com.lvmeng.qm.base.vo.questionnaire.Questionnaire;
@Service("qnService")
public class QnServiceImpl implements IQnService {
	@Resource
	private IQnDao qnDao;

	@Override
	public BaseResult dealWithQn(MultipartFile file) {
		BaseResult result = new BaseResult();
		try {
			List<Questionnaire> list = ExcelUtil.toVO(file.getInputStream());
			int rows = qnDao.saveQn(list);
			if (rows != list.size()) {
				result.failure("问卷存储失败条数" + (list.size() - rows));
			}
		}  catch (Exception e) {
			result.failure(e.getMessage());
		}
		return result;
	}

}