package com.lvmeng.qm.api.qn.dao;

import java.util.List;

import com.lvmeng.qm.base.vo.BaseQn;
import com.lvmeng.qm.base.vo.questionnaire.Questionnaire;


public interface IQnDao {
	public int saveQn(List<Questionnaire> list, String createTime) throws Exception;
	
	public int saveBaseQn(List<? extends BaseQn> list, String createTime, Integer type) throws Exception;
}
