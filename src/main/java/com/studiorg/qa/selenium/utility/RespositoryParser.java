package com.studiorg.qa.selenium.utility;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.LogStatus;
import com.studiorg.qa.selenium.executionEngine.DriverScript;

public class RespositoryParser {
	public static By getbjectLocator(String locatorName) {
		try {
			String locatorProperty[] = locatorName.split("\\|");
			String locatorType = locatorProperty[0];
			String locatorValue = locatorProperty[1];
			By locator = null;
			switch (locatorType) {
			case "Id":
				locator = By.id(locatorValue);
				break;
			case "Name":
				locator = By.name(locatorValue);
				break;
			case "CssSelector":
				locator = By.cssSelector(locatorValue);
				break;
			case "LinkText":
				locator = By.linkText(locatorValue);
				break;
			case "PartialLinkText":
				locator = By.partialLinkText(locatorValue);
				break;
			case "TagName":
				locator = By.tagName(locatorValue);
				break;
			case "Xpath":
				locator = By.xpath(locatorValue);
				break;
			}
			return locator;
		} catch (Exception e) {
			Log.error("Unable to access/Read Page Object " + locatorName + "-----------" + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR,
					"Unable to access/Read Page Object " + locatorName + "-----------" + e.getMessage());
			By locator = null;
			DriverScript.bResult = false;
			return locator;

		}
	}

}
