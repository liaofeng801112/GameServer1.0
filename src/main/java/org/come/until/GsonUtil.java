package org.come.until;

import com.google.gson.Gson;

public class GsonUtil {
	private GsonUtil() {
	}

	private static GsonUtil gsonUtil;
	private Gson gson;

	public static GsonUtil getGsonUtil() {
		if (gsonUtil == null) {
			gsonUtil = new GsonUtil();
		}
		return gsonUtil;
	}

	public Gson getgson() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}
}
