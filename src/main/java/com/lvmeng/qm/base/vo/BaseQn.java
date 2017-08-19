package com.lvmeng.qm.base.vo;

import java.util.HashSet;
import java.util.Set;

public class BaseQn {
	protected Integer panelId;
	protected Set<SetString> questionnaire;
	
	public Integer getPanelId() {
		return panelId;
	}
	public void setPanelId(Integer panelId) {
		this.panelId = panelId;
	}
	public Set<SetString> getQuestionnaire() {
		if (questionnaire == null){
			questionnaire = new HashSet<>();
		}
		return questionnaire;
	}
	public void setQuestionnaire(Set<SetString> questionnaire) {
		this.questionnaire = questionnaire;
	}
}
