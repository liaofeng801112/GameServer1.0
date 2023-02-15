package org.come.redis;

public class RedisObject<T> {

	private T t;
	private Boolean is;//是否被调用
	public RedisObject(T t) {
		super();
		this.t = t;
		this.is = Boolean.TRUE;
	}
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	public Boolean getIs() {
		return is;
	}
	public void setIs(Boolean is) {
		this.is = is;
	}
}
