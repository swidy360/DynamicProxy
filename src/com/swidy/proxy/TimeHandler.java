package com.swidy.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler{

	//被代理的目标对象
	private Object target;
	
	public TimeHandler(Object target) {
		super();
		this.target = target;
	}

	@Override
	public void invoke(Object o, Method m) {
		long start = System.currentTimeMillis();
		System.out.println("startTime:" + start);
		System.out.println(o.getClass().getName());
		try {
			m.invoke(target, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("endtime:" + end);
	}

}
