package com.nhdc.autotest.page;

import com.nhdc.autotest.base.DriverBase;
import com.nhdc.autotest.utils.GetProfile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

//页面基类，其他页面继承该类
@Slf4j
public class PageBase {

    public DriverBase driver;

    public PageBase(DriverBase driver) {
        //把初始化传入的DriverBase赋值给当前BasePage类的DriverBase的变量
        this.driver = driver;
    }

    public PageBase() {
	}

    /**
     * 显示等待获取某元素，等待时间10秒
     * @param key
     * @return
     */
	public WebElement getElementWait(String key){
        return explicitlyWait(10,key);
    }

	/**
	 * 层级定位，通过父元素定位一组子元素elements
	 * @param  父元素WebElement 子By
	 * */
	public List<WebElement> getChildrenElements(WebElement element,By childrenBy){
		log.info("P:定位组元素，通过父元素定位一组子元素[{}]",childrenBy);
		return element.findElements(childrenBy);
	}

	/**
	 * 层级定位，通过父元素定位一个子元素
	 * @param 父元素WebElement 子By
	 * */
	public WebElement getChildrenElement(WebElement element,By childrenBy){
		log.info("P:定位子元素，通过父元素定位子元素[{}]",childrenBy);
		return element.findElement(childrenBy);
	}

	/**
	 * 层级定位,通过父节点定位一个子元素
	 * @param 父节点By 子节点By
	 * */
	public WebElement getChildrenElement(By parentBy,By childrenBy) {
		log.info("P:定位子元素，通过父节点[{}]定位子元素[{}]",parentBy,childrenBy);
		WebElement element = driver.getElement(parentBy);
		return element.findElement(childrenBy);
	}

	/**
	 * 层级定位，通过（单个）父元素定位组子元素，获取其中指定index子元素
	 * @param 父元素WebElement 子By
	 * */
	public WebElement getChildrenElement(WebElement element,By childrenBy,int index) {
		log.info("P:定位子元素，通过父元素定位第[{}]个元素[{}]", index + 1, childrenBy);
		List<WebElement> elements = element.findElements(childrenBy);
		int size = elements.size();
		if (index < size && index >= 0) {
			return elements.get(index);
		} else {
			log.error("出错了，索引编号超出边界值");
			return null;
		}
	}

	/**
	 * 层级定位，通过父级组元素，定位需要的index子元素
	 * @parm: (List<WebElement> elements,By by_son,int index) 父元素、子定位、索引
	 * @return: 需要的子元素 WebElement
	 */
	public WebElement getChildrenElement(List<WebElement> elements,By childrenBy,int index) {
		int size = elements.size();
		WebElement element;
		if (index < size && index >= 0) {
			element = elements.get(index);
			log.info("P:定位子元素，通过父元素定位第[{}]个元素[{}]",index+1,element);
			return getChildrenElement(element, childrenBy);
		}else {
			log.error("出错了，索引编号超出边界值");
			return null;
		}
	}

    /**
     * 普通元素-显示等待，默认频率500ms 适用元素实际绝对会出现
     * presenceOfElementLocated：判断该元素是否被加载在DOM中，并不代表该元素一定可见
     * @param 等待时间outTime秒，元素Key
     * @return WebElement
     */
    public WebElement explicitlyWait(int outTime,String key) {
        log.info("P:显式等待页面元素[{}],等待时间[{}]秒",key,outTime);
        WebElement element = new WebDriverWait(driver.getDriver(),outTime)
                .until(ExpectedConditions.presenceOfElementLocated(GetProfile.getBy(key)));
        if (element != null) {
            log.info("P:显示等待定位元素成功");
            return element;
        }else {
            log.error("P:显示等待定位元素失败");
            return null;
        }
    }

    /**
     * @description: 弱警告元素-显示等待-快速版，指定频率100ms 适用元素实际绝对会出现
     * @param 等待时间outTime秒，元素Key
     * @return WebElement
     */
    public WebElement explicitlyFastWait(int outTime,String key) {
        log.info("P:显式等待页面元素[{}],等待时间[{}]秒",key,outTime);
        WebElement element = new WebDriverWait(driver.getDriver(),outTime,100)
                .until(ExpectedConditions.presenceOfElementLocated(GetProfile.getBy(key)));
        if (element != null) {
            log.info("P:Fast显示等待定位元素成功");
            return element;
        }else {
            log.error("P:Fast显示等待定位元素失败");
            return null;
        }
    }

    /**
     * 流畅等待-自定义检查频率sleepInMillisms,且忽略无法定位、超时异常，适用元素实际不一定会出现，可能null
     * @parm: 等待时间outTime秒，元素key,检查频率 sleepInMillis
     * @return: WebElement
     */
    public WebElement fluentWait(int outTime,String key,long sleepInMillis){
        WebElement element = new WebDriverWait(driver.getDriver(),outTime,sleepInMillis)
                .ignoring(NoSuchElementException.class,TimeoutException.class)
                .until(
                        new ExpectedCondition<WebElement>() {
                            @Override
                            public WebElement apply(WebDriver driver) {
                                return driver.findElement(GetProfile.getBy(key));
                            }
                        });
        return element;
    }

    /**
     * @description: 组元素-显示等待，默认检查频率  适用元素实际绝对会出现，获取null或其他视为错误
     * @param 等待时间outTime秒，页面名字page，元素key
     * @return List<WebElement>
     */
    public List<WebElement> explicitlyWaitElements(int outTime,String key) {
        log.info("P:显式等待页面元素[{}],等待时间[{}]秒",key,outTime);
        List<WebElement> elements = new WebDriverWait(driver.getDriver(),outTime)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(GetProfile.getBy(key)));
        if (elements != null) {
            log.info("P:显示等待定位组元素成功");
            return elements;
        }else {
            log.error("P:显示等待定位组元素失败");
            return null;
        }
    }

    /**
     * @Description: 组元素流畅等待-自定义检查频率sleepInMillisms,且忽略无法定位、超时异常，适用组元素实际不一定会出现，可能null
     * @parm: 等待时间outTime秒，页面名字page，元素key,检查频率 sleepInMillis
     * @return: List<WebElement>
     */
    public List<WebElement> fluentWaitElements(int outTime,String key,long sleepInMillis) {
        return new WebDriverWait(driver.getDriver(),outTime,sleepInMillis)
                .ignoring(NoSuchElementException.class,TimeoutException.class)
                .until(
                        new ExpectedCondition<List<WebElement>>() {
                            public List<WebElement> apply(WebDriver driver) {
                                return driver.findElements(GetProfile.getBy(key));
                            }
                        });
    }

    /**
      * @discription: 等待toast提示信息出现某文字，等待时间3秒，检查频率100毫秒
     * @param (By by 元素定位方式,String text 元素上出现的文本)
     */
    public boolean waitForToast(By by,String text) {
        boolean isPresent = true;
        try {
            isPresent =new WebDriverWait(driver.driver, 3, 100)
                    .ignoring(StaleElementReferenceException.class, NoSuchElementException.class)
                    .until(ExpectedConditions.invisibilityOfElementWithText(by,text));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isPresent;
    }

    /**
     * ========================================
     * 修改适配中。。。以下方法需修改适配或弃用,修改后放上面
     * ========================================
     */

    /**
     * 截图  暂时弃用
     */
    public void takeScreenShot() {
//         long date = System.currentTimeMillis();   //获取当前时间（毫秒数）来给文件命名，缺点名字查看不友好无规律
//         String path_temp = String.valueOf(date);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hhmmss");    //SimpleDateFormat类自定义输出日期格式 此处例：2019-10-30-063731
       StringBuilder screenshotPath = new StringBuilder(System.getProperty("user.dir"));
       screenshotPath.append("\\screenshot\\").append(sdf.format(Calendar.getInstance().getTime())).append(".png");
       File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
       try {
             FileUtils.copyFile(screen, new File(screenshotPath.toString()));
       } catch (IOException e) {
             // TODO 自动生成的 catch 块
       e.printStackTrace();
       }
    }

    /**
     * 过滤页面获得的文本元素特殊符号
     */
    public String filterString(String before) {
        return before.replaceAll("[￥¥x月售笔省元]","");
    }

	/**
	 * @function: 等待时间内，目标元素上文本是否出现
	 * @param (int timeout 等待时间,By by 元素定位方式,String text 元素上出现的文本)
	 */
	public boolean IsElementTextPresent(int timeout,By by,String text) {
        return (new WebDriverWait(driver.getDriver(),timeout))
                .until(ExpectedConditions.textToBePresentInElementLocated(by, text));
	}

    /**
     * 判断元素是否显示方法
     * @param WebElement element
     * */
    public boolean assertElementIsDisplay(WebElement element) {
    	log.info("P:判断元素是否显示[{}]",element);
        return element.isDisplayed();
    }

    /**
     * 判断组元素中是否有元素文本信息为text
     * @param elements
     * @param text
     * @return
     */
    public boolean assertElementsHasText(List<WebElement> elements,String text) {
    	boolean result = false;
    	for (WebElement element : elements) {
    		if (element.getText().equals(text)) {
    			result = true;
    			break;
			}
		}
    	return result;
    }

}
