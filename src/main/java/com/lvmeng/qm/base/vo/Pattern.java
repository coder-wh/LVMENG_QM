package com.lvmeng.qm.base.vo;

import java.util.ArrayList;
import java.util.List;

import com.lvmeng.qm.base.commons.CodeTable;

public class Pattern {
	private Integer index;//导出时所在列的索引
	private List<String> list;
	private Pattern_Type type;//比较类型 
	private Integer score;//取值分数
	
	public Pattern(Integer index, String str) {
		super();
		this.index = index;
		List<String> list = new ArrayList<>();
		String[] strs = str.split(CodeTable.regex);
		for (int i = 0; i < strs.length; i++) {
			list.add(strs[i]);
		}
		this.list = list;
		this.type = Pattern_Type.SELECT;
	}
	
	public Pattern(Integer index, Integer score) {
		super();
		this.index = index;
		this.score = score;
		this.type = Pattern_Type.LESS;
	}
	
	public Pattern(Integer index, Pattern_Type type) {
		super();
		this.index = index;
		this.type = type;
	}

	public Pattern_Type getType() {
		return type;
	}
	public void setType(Pattern_Type type) {
		this.type = type;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public void setList(String str) {
		List<String> list = new ArrayList<>();
		String[] strs = str.split(CodeTable.regex);
		for (int i = 0; i < strs.length; i++) {
			list.add(strs[i]);
		}
		this.list = list;
	}
	
}
