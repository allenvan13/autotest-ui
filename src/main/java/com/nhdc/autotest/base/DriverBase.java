package com.nhdc.autotest.base;

import com.nhdc.autotest.utils.GetProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

//浏览器基类
@Slf4j
public class DriverBase { 

	public WebDriver driver;
	
	public DriverBase(String browser) {
		SelectDriver sd = new SelectDriver();
		this.driver = sd.driverName(browser);
		log.info("打开[{}]浏览器",browser);
	}

	/**
	 * 定位获取1个WebElement
	 * @param By
	 * @return WebElement
	 */
	public WebElement getElement(By by) {
		WebElement element = driver.findElement(by);
		if (element != null) {
			log.info("P:定位元素，通过[{}]",by);
			return element;
		}else {
			log.error("P:定位元素失败。[{}]",by);
			Reporter.log("P:定位元素失败。[{}]");
			return null;
		}
	}



	/**
	 * 鼠标悬停在元素上
	 * @param WebElement
	 * */
	public void moveOnWebElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		log.info("P:鼠标悬停至元素[{}]",element);
	}

	/**
	 * 定位获取1组Elements
	 * @param By
	 * @return List<WebElement>
	 * */
	public List<WebElement> getElements(By by){
		List<WebElement> elements = driver.findElements(by);
		if (elements != null) {
			log.info("P:定位组元素,通过[{}]",by);
			return elements;
		}else {
			log.error("P:定位组元素失败。[{}]",by);
			Reporter.log("P:定位组元素失败。");
			return null;
		}
	}

	/**
	 * click（点击）元素
	 * @param WebElement
	 * */
	public void click(WebElement element) {
		if(element != null) {
			log.info("点击元素成功,元素标签[{}]",element.getTagName());
			element.click();
		}else {
			log.error("点击元素失败,未定位到元素[{}]",element.getTagName());
			Reporter.log("P:点击元素失败。");
		}
	}

	/**
	 * 点击元素hold并水平拖动x,垂直拖动y，再释放
	 * @param (WebElement element,int x,int y)
	 * */
	public void clickAndMove(WebElement element,int x,int y) {
		Actions action = new Actions(driver);
//    	action.moveToElement(element).clickAndHold(element).perform();
		action.dragAndDropBy(element, x, y).perform();
		action.release(element);
	}

	/**
	 * actionClick 点击元素
	 * @param WebElement
	 * */
	public void actionClick(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();

//     JavascriptExecutor js = (JavascriptExecutor)driver;
//     js.executeScript("arguments[0].click();", element);
//     element.click();
	}

	/**
	 * 清除元素已输入的文本
	 * @param (WebElement element) 要清除文本的元素
	 * */
	public boolean clearText(WebElement element) {
		if (element.isEnabled()) {
			element.clear();
			log.info("清除元素文本成功");
			return true;
		}else {
			log.error("清除元素文本失败，元素不可编辑。[{}]",element);
			Reporter.log("P:清除元素文本失败。");
			return false;
		}
	}

	/**
	 * sendKeys 向元素输入文本
	 * @param (WebElement element,String text)
	 * */
	public void sendKeys(WebElement element,String text) {
		if(element.isEnabled()) {
			element.sendKeys(text);
			log.info("向元素输入文本[{}]成功，元素{}",text,element);
		}else {
			log.error("向元素输入文本失败，元素不可编辑。[{}]",element);
			Reporter.log("P:向元素输入文本失败。");
		}
	}

	/**
	 * 获取浏览器驱动driver
	 * */
	public WebDriver getDriver() {
		log.info("获取driver");
		return driver;
	}
	
	/**
	 * 关闭浏览器
	 * */
	public void stopDriver() {
		log.info("关闭浏览器");
		driver.quit();		
	}
	
	/**
	 * 打开网址 get(url)
	 * */
	public void get(String url) {
		log.info("打开网址[{}]",url);
		driver.get(url);
	}
	
	/**
	  * 窗口最大化
	 * */
	public void setWindowMax() {
		log.info("最大化窗口");
		driver.manage().window().maximize();	
	}
	
	/**
	  * 窗口自定义大小
	 * */
	public void setWindowSize(int x,int y) {
		log.info("设置窗口分辨率[{},{}]",x,y);
		driver.manage().window().setSize(new Dimension(x, y));
	}
	
	/**
	  * 窗口返回 <- 功能
	 * */
	public void back() {
		log.info("页面返回");
		driver.navigate().back();
	}
	
	/**
	  * 窗口刷新
	 * */
	public void refresh() {
		log.info("页面刷新");
		driver.navigate().refresh();		
	}
	
	/**
	 * 获取当前URL
	 * */
	public String getUrl() {
//		log.info("获取页面url");
		return driver.getCurrentUrl();
	}

	/**
	 * 获取当前Title
	 * */
	public String getTitle() {
//		log.info("获取页面title");
		return driver.getTitle();	
	}

	/**
	 * getText 获取元素文本
	 * @param WebElement
	 * */
	public String getText(WebElement element) {
		log.info("P:获取元素文本,元素{}",element);
		return element.getText();
	}

	/**
	 * 获取当前窗口handle
	 * */
	public String getWindowHandle() {
		log.info("获取当前窗口handle");
		return driver.getWindowHandle();	
	}
	
	/**
	 * 获取多个窗口handles
	 * */
	public List<String> getWindowHandles() {
		log.info("获取多个窗口handles");
		Set<String> winHandels = driver.getWindowHandles();
        List<String> handles = new ArrayList<String>(winHandels);
        return handles;
	}

	/**
	 * 根据title切换新窗口
	 * @param (String windowTitle)
	 * */
	public boolean switchToWindow_Title(String windowTitle) {
		boolean flag = false;
		try {
			String currentHandle = this.getWindowHandle();
			List<String> handles = this.getWindowHandles();
			for (String s : handles) {
				if (s.equals(currentHandle))
					continue;
				else {
					this.switchToWindow(s);
					if (this.getTitle().equalsIgnoreCase(windowTitle)) {
						flag = true;
						log.info("P:成功切换页面至[{}]",windowTitle);
						break;
					} else
						continue;
				}
			}
		} catch (NoSuchWindowException e) {
			log.error("P:切换页面失败,日志：[{}]",e.toString());
			Reporter.log("P:切换页面失败。");
			System.out.println();
			flag = false;
		}
		return flag;
	}

	/**
	  * 切换至Alert
	 * */
	public void switchToAlert() {
		log.info("切换至弹窗");
		driver.switchTo().alert();
	}
    
	/**
	  * 切换至iframe
	 * */
	public void switchToIframe(WebElement frameElement) {
		log.info("切换至IFrame窗{}",frameElement);
		driver.switchTo().frame(frameElement);
	}

	/**
	 * 切换回默认iframe
	 * */
	public void switchToDefaultIframe() {
		log.info("P:切换回默认IFrame窗");
		driver.switchTo().defaultContent();
	}

	/**
	  * 切换至窗口 
	 * */
	public void switchToWindow(String handle) {
		log.info("切换至窗口[{}]",handle);
		driver.switchTo().window(handle);
	}
	
    /*
     * 休眠，单位为秒
     * */
    public void sleep(int second) {
        try {
        	log.info("进程休眠[{}]秒",second);
        	Thread.sleep(second*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
	/**
	 * setCookies
	 * */
	public void setCookies(Cookie cookie) {
		driver.manage().addCookie(cookie);
		log.info("添加cookies");
	}

	/**
	 * deleteCookies
	 * */
	public void deleteCookies() {
		driver.manage().deleteAllCookies();
		log.info("删除cookies");
	}

	/**
	 * getCookies
	 * */
	public Set<Cookie> getCookies() {
		Set<Cookie> cookies = driver.manage().getCookies();
		log.info("获取cookies");
		return cookies;
	}

	/**
	 * 鼠标焦点于某元素,输入F12
	 * @param element
	 */
	public void pagePressF12(WebElement element) {
        element.click();
    	Actions action = new Actions(driver);
        action.sendKeys(Keys.F12).perform();
        log.info("输入F12键");
    }
    
    /**
     * PAGE DOWN
     * @param (WebElement element,int num) 输入次数
     * */
    public void pageDown(WebElement element,int num) {
    	element.click();
    	Actions action = new Actions(driver);
        while (num>0) {
        	action.sendKeys(Keys.PAGE_DOWN).perform();
        	num--;
		}    
    }
    	
    /**
     * PAGE DOWN 1次 
     * @param (WebElement element) 需要先点击不跳转或无效果的某元素
     * */
    public void pageOneDown(WebElement element) {
    	element.click();
    	Actions action = new Actions(driver);
        action.sendKeys(Keys.PAGE_DOWN).perform();
    }
    
    /**
     * PAGE UP 1次 
     * @param (WebElement element) 需要先点击不跳转或无效果的某元素
     * */
    public void pageOneUp(WebElement element) {
    	element.click();
    	Actions action = new Actions(driver);
        action.sendKeys(Keys.PAGE_UP).perform();
    }
    
    /**
     * BACK SPACE 1次 
     * @param (WebElement element) 
     * */
    public boolean backOneSpace(WebElement element) {
    	boolean isSucess = false;
    	Actions action = new Actions(driver);
    	if (element != null) {
    		element.click();
            action.sendKeys(Keys.BACK_SPACE).perform();
            isSucess = true;
            return isSucess;
		}else {
			log.error("BackSpace ERROR！");
			Reporter.log("出错啦！ BackSpace ERROR！");
		}
    	return isSucess;
    	
    }
       
    /**
	 * 输入回车键
	 * */
	public void pagePressEnter() {
		Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).perform();
        log.info("输入回车键->提交");
	}
    
	/**
     * 传入参数截图
     * @param
     */
    public void takeScreenShot(TakesScreenshot drivername, String path) {
        String currentPath = System.getProperty("user.dir")+"\\test-output\\screenshots\\";
        File screenFile = drivername.getScreenshotAs(OutputType.FILE);

        try {
        	FileUtils.copyFile(screenFile, new File(currentPath+"\\"+path));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            log.info("已成功截图至路径[{}]",path);
        }
    }

    /**
     * 自动截图
     */
    public void takeScreenShot() {
    	String currentPath = System.getProperty("user.dir")+"\\test-output\\screenshots\\";
        SimpleDateFormat simpleDF = new SimpleDateFormat("yyyyMMdd_HH-mm-ss");

        String path_temp = simpleDF.format(new Date());
        String path = currentPath+this.getClass().getSimpleName() + "_" + path_temp + ".png";
        takeScreenShot((TakesScreenshot) driver, path);
        //takeScreenShot((TakesScreenshot) driver, path);
        File screenFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
			FileUtils.copyFile(screenFile, new File(currentPath+"\\"+path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            log.info("已成功截图至路径[{}]",currentPath);
        }
    }
    
    /**
     * @discription: 隐式等待 全局
     * @param 等待时间second秒
     */
    public void implicitlyWait(int second) {
    	log.info("设置全局元素等待[{}]秒",second);
    	driver.manage().timeouts().implicitlyWait(second, TimeUnit.SECONDS);	
    }
    
    /**
     * @Decription: 判断元素是否存在，忽略异常
     * @return 存在为true ，不存在为false
     */
    public boolean isElementPresent(By locatorKey) {
        try {
            driver.findElement(locatorKey);
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    
    /**
     * @Decription: 判断元素是否可见（有些元素存在DOM中，但不可见）
     */
    public boolean isElementVisible(String cssLocator){
        return driver.findElement(By.cssSelector(cssLocator)).isDisplayed();
    }

	/**
	 * JS执行器方法，此部分方法需自定义进行适配
	 *
	 * ================分割线==================
	 */

	/**
	 * 执行JS语句
	 * @parm:  js语句
	 */
	public void executeJs(String javaScript) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript(javaScript);
	}


	/**
	 * @discription: 只要某元素上文本未出现就一致下滚到页面最底部
	 * @param (int timeout,By by,String text)
	 */
	public void pageDownByJs_BASE(int timeout,By by,String text) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
//    	while(!waitForElementPresent(1, By.className("loadMore"),"我是有底线的"))
		do {
			js.executeScript("$('#main').scrollTop(100000);");
		} while (!waitForElementPresent(timeout, by,text));
	}

	/**
	 * @discription: 只要某元素上文本未出现就一致下滚到页面最底部
	 * @param (int timeout,By by,String text)
	 */
	public void pageDownByJs(int timeout,By by,String text) {
//    	while(!waitForElementPresent(1, By.className("loadMore"),"我是有底线的"))
		System.out.println(waitForElementPresent(timeout, by,text));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		do {
//    		System.out.println(waitForElementPresent(timeout, by,text));
			js.executeScript("$(window).scrollTop(1000000);");
		} while (!waitForElementPresent(timeout, by,text));
		System.out.println(waitForElementPresent(timeout, by,text));
	}

	/**
	 * @discription:　下滚到页面最底部
	 * @param 循环-下滚次数
	 */
	public void pageOneDownByJs(int num) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		do {
			js.executeScript("$('#main').scrollTop(100000);");
			num --;
		} while (num == 0);
	}

	/**
	 * @Description: 通过输入按键Keys.PAGE_DOWN 下滑至页面底部
	 * @param (WebElement element) 需要点击一个元素（点击后无跳转，目的，使光标在页面上）
	 */
	public void pageDownByKey(WebElement element) {
		element.click();
		Actions action = new Actions(driver);
		do {
			action.sendKeys(Keys.PAGE_DOWN).perform();
		} while (!waitForElementPresent(1, GetProfile.getBy("S_searchResultAssertDiv"),"我是有底线的~")
				|| !waitForElementPresent(1, GetProfile.getBy("S_searchNoResultAssertDiv"),"无搜索结果，换个词试试吧~"));
	}

	/**
	 * @Description: 通过输入按键Keys.PAGE_DOWN 下滑至页面底部
	 * @param
	 */
	public void pageDownByKey(WebElement element,By byYes,String textYes) {
		element.click();
		Actions action = new Actions(driver);
		do {
			action.sendKeys(Keys.PAGE_DOWN).perform();
		} while (!waitForElementPresent(1, byYes,textYes));
//    	System.out.println(waitForElementPresent(1, byYes,textYes));
	}

	/**
	 * @Description: 通过输入按键Keys.PAGE_DOWN 下滑至页面底部
	 */
	public void pageDownByKey() {
		Actions action = new Actions(driver);
		do {
			action.sendKeys(Keys.PAGE_DOWN).perform();
		} while (!waitForElementPresent(1, GetProfile.getBy("S_searchResultAssertDiv"),"我是有底线的~"));
	}

	/**
	 * @Description: 通过输入按键Keys.PAGE_DOWN 下滑至页面底部,直至by定位的元素上出现text
	 */
	public void pageDownByKey(By by, String text) {
		Actions action = new Actions(driver);
		do {
			action.sendKeys(Keys.PAGE_DOWN).perform();
		} while (!waitForElementPresent(1, by, text));
	}

	/**
	 * @function: 视角返回需要点击的商品
	 */
	public void pageMoveToElement_BASE(int index) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		if (index == 0) {
			js.executeScript("function moveToElement(index) {var elements = $('.list');var elementsId = $('.list').eq(index).attr('id'); window.location.href = '#' + elementsId;}"+"moveToElement("+index+");");
			js.executeScript("$('#main').scrollTop(-100);");
//    		System.out.println("=========视角返回第["+(category_ID+1)+"]个分类["+category_name+"]第["+(index+1)+"]个商品元素，该分类共["+productMax+"]个商品========");
		}else {
			index = index - 1;
			js.executeScript("function moveToElement(index) {var elements = $('.list');var elementsId = $('.list').eq(index).attr('id'); window.location.href = '#' + elementsId;}"+"moveToElement("+index+");");
//			System.out.println("=========视角返回第["+(category_ID+1)+"]个分类["+category_name+"]第["+(index+2)+"]个商品元素，该分类共["+productMax+"]个商品========");
		}
	}

	/**
	 * @function: 视角返回需要点击的商品
	 */
	public void pageMoveToElement(int index) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("function moveToElement(index) {var elements = $(\'.list\');var elementsId = $(\'.list\').eq(index).attr(\'id\');window.location.href = \'#\' + elementsId;var scrollTop = $(window).scrollTop();$(window).scrollTop(scrollTop - 150);}moveToElement("+index+")");
	}

	/**
	  * @Description：等待直到某元素上的文本出现
	 * @param (int timeout 等待时间,By by 元素定位方式,String text 元素上出现的文本)
	 */
	public boolean waitForElementPresent(int timeout,By by,String text) {
		boolean isPresent = false;
		try {
			isPresent = new WebDriverWait(driver, timeout)
					.ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
					.until(ExpectedConditions.textToBePresentInElementLocated(by,text));
//			System.out.println(isPresent+"++++++++");
//			isPresent = true;
		} catch (Exception e) {
//			isPresent = false;
			e.printStackTrace();
//			return isPresent;
		}
		return isPresent;
	}

    
}
