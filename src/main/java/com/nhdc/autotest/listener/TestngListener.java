package com.nhdc.autotest.listener;

import com.nhdc.autotest.testcase.CaseBase;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestngListener extends TestListenerAdapter{

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		// 对于dataProvider的用例，每次成功后，重置Retry次数
		try {
			Retry retry = (Retry)tr.getMethod().getRetryAnalyzer();
			retry.resetRetryCnt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);

		CaseBase caseBase = (CaseBase)tr.getInstance();
		// 对于dataProvider的用例，每次失败后，重置Retry次数
		caseBase.takeScreenShot(tr.getName());
		Retry retry = (Retry)tr.getMethod().getRetryAnalyzer();
		retry.resetRetryCnt();
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
//		理论上每个用例执行后，均需重置重试RetryCnt，跳过情况暂时不管
//		Retry retry = (Retry)tr.getMethod().getRetryAnalyzer();
//		retry.resetRetryCnt();
	}

	@Override
	public void onConfigurationFailure(ITestResult itr) {
		super.onConfigurationFailure(itr);
	}

	@Override
	public void onConfigurationSkip(ITestResult itr) {
		super.onConfigurationSkip(itr);
	}

}
