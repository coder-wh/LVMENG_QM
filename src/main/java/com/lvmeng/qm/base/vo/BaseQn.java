package com.lvmeng.qm.base.vo;

import java.util.ArrayList;
import java.util.List;

public class BaseQn extends AbstractModel {
	protected List<String> questionnaire;
	public List<String> getQuestionnaire() {
		if (questionnaire == null){
			questionnaire = new ArrayList<>(15);
		}
		return questionnaire;
	}
	public void setQuestionnaire(List<String> questionnaire) {
		this.questionnaire = questionnaire;
	}
}
