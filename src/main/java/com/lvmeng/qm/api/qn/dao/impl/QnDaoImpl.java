package com.lvmeng.qm.api.qn.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmeng.qm.api.qn.dao.IQnDao;
import com.lvmeng.qm.base.datasource.BaseDao;
import com.lvmeng.qm.base.vo.questionnaire.Questionnaire;
@Repository("qnDao")
public class QnDaoImpl extends BaseDao implements IQnDao {

	@Override
	public int saveQn(List<Questionnaire> list) throws Exception {
		System.out.println(jdbcTemplate);
		return 0;
	}

}
