package com.swidy.proxy.test;

public class UserMgrImpl implements UserMgr {

	@Override
	public void addUser() {
		System.out.println("1: 插入表数据");
		System.out.println("2: 记录操作日志");
	}
	
}
