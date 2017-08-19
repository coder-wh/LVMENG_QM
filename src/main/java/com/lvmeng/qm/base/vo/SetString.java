package com.lvmeng.qm.base.vo;

public class SetString implements Comparable<SetString>{
	private Integer index;
	private String str;
	
	public SetString(Integer index, String str) {
		super();
		this.index = index;
		this.str = str;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetString other = (SetString) obj;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		return true;
	}
	@Override
	public int compareTo(SetString o) {
		return this.getIndex() - o.getIndex();
	}
	@Override
	public String toString() {
		return str;
	}
	

}
