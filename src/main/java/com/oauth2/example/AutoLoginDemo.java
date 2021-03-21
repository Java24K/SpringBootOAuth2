package com.oauth2.example;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
/**
 * @Description: 自动登录
 * @Author zhoufeng21@guazi.com
 * @Date 2021/1/7 4:18 下午
 * @version: V1.0
 */
public class AutoLoginDemo {

    public static void main(String[] args) throws InterruptedException {
        // 获取浏览器，窗口最大化，隐式等待，打开网址
        System.setProperty("webdriver.chrome.driver","/Users/zhoufeng/Downloads/chromedriver1");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("http://tms-test.guazi-corp.com/portal/bottom_new.do");

        Cookie c1 = new Cookie("GUAZISSO", "2006905659qhqrnxxeqswq5220143118");
        driver.manage().addCookie(c1);

        driver.get("http://tms-test.guazi-corp.com/portal/bottom_new.do");

        Thread.sleep(1000);
        // 登录后台输入账号密码及验证码点击登录
        driver.findElement(By.id("userno")).sendKeys("admin1");
        WebElement element = driver.findElement(By.id("password"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = 'block'", element);
        element.sendKeys("11111111");
        //driver.findElement(By.id("password")).sendKeys("11111111");
        Thread.sleep(2000);
        driver.findElement(By.id("submitA")).click();
        Thread.sleep(2000);
        driver.get("http://tms-test.guazi-corp.com/portal/main_new.do");
        Thread.sleep(10000);

    }


}
