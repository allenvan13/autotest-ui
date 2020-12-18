package com.nhdc.autotest.listener;

import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer{

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		IRetryAnalyzer retry = annotation.getRetryAnalyzer();
		if (null == retry) {
			annotation.setRetryAnalyzer(com.nhdc.autotest.listener.Retry.class);
		}
	}
	
}
