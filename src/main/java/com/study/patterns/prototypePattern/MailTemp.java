package com.study.patterns.prototypePattern;

public class MailTemp {

	public String subString;// 标题
	public String mainContentString; // 广告内容

	public String getSubString() {
		return "xxxxxxxxxxxxx账单";
	}

	public String getMainContentString() {
		return "xxx" + "(先生/女士)";
	}
}
