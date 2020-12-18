package com.nhdc.autotest.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * @ClassName:Retry.java
 * @author： Allen Van
 * @date: 2019 上午9:30:45
 * @Description: testng用例失败重跑->重跑规则：
 * @Description: 其中maxRetryCnt是每个用例最多重试的次数（不包括第 1 次执行），retryCnt是已经重跑的次数。retry方法判断如果已经重跑的次数retryCnt小于设定的总次数，则返回true进行重跑，同时retryCnt加 1；否则返回false不再重跑。
 */
public class Retry implements IRetryAnalyzer{

	private int retryCnt = 0;  
    private int maxRetryCnt = 2;	//失败重试最大次数，（不包括第 1 次执行）
    
	@Override
	public boolean retry(ITestResult result) {

		if (retryCnt < maxRetryCnt) {
//			String message = "Retry for [" + result.getName() + "] on class ["
//		+ result.getTestClass().getName() + "] Retry " + (retryCnt+1) + "  times";
//			Reporter.log(message);
			Reporter.log("重试中，运行总次数："+(retryCnt+1));
			retryCnt++;
            return true;
        }
		return false;
	}

	public int getMaxRetryCnt() {
		return maxRetryCnt;
	}

	public void setMaxRetryCnt(int maxRetryCnt) {
		this.maxRetryCnt = maxRetryCnt;
	}
	/**
	 * @Decription: 重置retryCnt 解决重跑次数不对的BUG
	 */
	public void resetRetryCnt() {
		retryCnt = 0;
	}
	
	
}
