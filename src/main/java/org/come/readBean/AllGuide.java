package org.come.readBean;

import java.util.HashMap;
import java.util.Map;

public class AllGuide {
	
	//所有新手引导信息
	private Map<Integer, RookieGuideBean> rookieguide=new HashMap<>();

	public Map<Integer, RookieGuideBean> getRookieguide() {
		return rookieguide;
	}

	public void setRookieguide(Map<Integer, RookieGuideBean> rookieguide) {
		this.rookieguide = rookieguide;
	}
	
}
