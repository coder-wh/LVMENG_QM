package com.lvmeng.qm.api.qn.dao;

import java.util.List;

import com.lvmeng.qm.base.vo.questionnaire.Questionnaire;


public interface IQnDao {
	public int saveQn(List<Questionnaire> list) throws Exception;
}
