package com.studiorg.qa.selenium.config;

import static com.studiorg.qa.selenium.executionEngine.DriverScript.OR;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;
import com.studiorg.qa.selenium.executionEngine.DriverScript;
import com.studiorg.qa.selenium.utility.ExcelUtils;
import com.studiorg.qa.selenium.utility.ExtentLog;
import com.studiorg.qa.selenium.utility.Log;
import com.studiorg.qa.selenium.utility.RespositoryParser;

public class ActionKeywords {
	public static WebDriver driver;
	static String orderNumber = "";
	static int i = 1;

	// ******************************************* GENERIC METHODS
	// ***********************************************************//

	public static void openBrowser(String object, String browserType) {
		Log.info("Opening Browser");
		ExtentLog.test.log(LogStatus.INFO, "Opening Browser");
		try {
			switch (browserType) {

			case "Chrome":
				// System.out.println("Coming here as well");
				Log.info("Opening Chrome Browser");
				ExtentLog.test.log(LogStatus.INFO, "Opening Chrome Browser");
				// System.setProperty("webdriver.chrome.driver","C:\\Drivers\\chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", Constants.PATH_RESOURCES + "chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				// options.addArguments("--start-maximized");

				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();

				ChromeDriverService service = new ChromeDriverService.Builder().usingAnyFreePort().build();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				options.merge(capabilities);
				driver = new ChromeDriver(service, options);
				driver.manage().deleteAllCookies();
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				break;

			case "IE":
				Log.info("Opening Internet Explorer Browser");
				ExtentLog.test.log(LogStatus.INFO, "Opening IE Browser");
				System.setProperty("webdriver.ie.driver", Constants.PATH_RESOURCES + "IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				driver.manage().deleteAllCookies();
				break;

			case "Firefox":
				// Log.info("Opening Mozilla Firefox Browser");
				// ExtentLog.test.log(LogStatus.INFO, "Opening Mozilla
				// Browser");
				System.setProperty("webdriver.gecko.driver", Constants.PATH_RESOURCES + "geckodriver.exe");
				// DesiredCapabilities ffcapabilities =
				// DesiredCapabilities.firefox();
				// ffcapabilities.setCapability("marionette", true);
				driver = new FirefoxDriver();
				driver.manage().deleteAllCookies();
				break;

			default:
				// Log.info("Opening Chrome Browser");
				// ExtentLog.test.log(LogStatus.INFO, "Opening Chrome Browser");
				System.setProperty("webdriver.chrome.driver", Constants.PATH_RESOURCES + "chromedriver.exe");
				driver = new ChromeDriver();
				break;
			}
		} catch (Exception e) {

			Log.error("Not able to open Browser --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Not able to open Browser ---" + e.getMessage());
			DriverScript.bResult = false;
		}

	}

	public static void selectEnvironmenment(String object, String URL) {
		Log.info("Environment is " + URL);
		// ExtentLog.test.log(LogStatus.INFO, "Environment is " +data);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		try {
			switch (URL) {
			case "Prod":
			Constants.PROD = "http://studiorguat.azurewebsites.net/PartsTown";
			break;

			case "QA1":
			Constants.QA1 = "http://studiorgqa.azurewebsites.net/PartsTown";
			break;
			
			case "UAT":
			Constants.UAT = "https://uat.mystudiorg.com/PartsTown";
			break;
			
			default:
				Constants.UAT = "https://uat.mystudiorg.com/PartsTown";
			break;
			
	//-----------Below cases for OTC Project ------------
			/*case "Prod":
				Constants.OTC = "http://www.orientaltrading.com/";
				Constants.Learn = "http://www.learn365.orientaltrading.com/";
				Constants.Mindware = "http://www.mindware.orientaltrading.com/";
				Constants.Marryme = "http://www.marryme.orientaltrading.com/";
				Constants.F365 = "http://www.fun365.orientaltrading.com/";
				Constants.CF365 = "http://www.customfun365.orientaltrading.com/";
				break;

			case "QA1":
				Constants.OTC = "http://dev.orientaltrading.com/";
				Constants.Learn = "http://dev.learn365.orientaltrading.com/";
				Constants.Mindware = "http://dev.mindware.orientaltrading.com/";
				Constants.Marryme = "http://dev.marryme.orientaltrading.com/";
				Constants.F365 = "http://dev.fun365.orientaltrading.com/";
				Constants.CF365 = "http://dev.customfun365.orientaltrading.com/";
				break;

			case "QA2":
				Constants.OTC = "http://test.orientaltrading.com/";
				Constants.Learn = "http://test.learn365.orientaltrading.com/";
				Constants.Mindware = "http://test.mindware.orientaltrading.com/";
				Constants.Marryme = "http://test.marryme.orientaltrading.com/";
				Constants.F365 = "http://test.fun365.orientaltrading.com/";
				Constants.CF365 = "http://test.customfun365.orientaltrading.com/";
				break;

			case "QA3":
				Constants.OTC = "http://stage.orientaltrading.com/";
				Constants.Learn = "http://stage.learn365.orientaltrading.com/";
				Constants.Mindware = "http://stage.mindware.orientaltrading.com/";
				Constants.Marryme = "http://stage.marryme.orientaltrading.com/";
				Constants.F365 = "http://stage.fun365.orientaltrading.com/";
				Constants.CF365 = "http://stage.customfun365.orientaltrading.com/";
				break;

			case "QA4":
				Constants.OTC = "http://qa4.orientaltrading.com/";
				Constants.Learn = "http://qa4.learn365.orientaltrading.com/";
				Constants.Mindware = "http://qa4.mindware.orientaltrading.com/";
				Constants.Marryme = "http://qa4.marryme.orientaltrading.com/";
				Constants.F365 = "http://qa4.fun365.orientaltrading.com/";
				Constants.CF365 = "http://qa4.customfun365.orientaltrading.com/";
				break;

			case "QA5":
				Constants.OTC = "http://qa5.orientaltrading.com/";
				Constants.Learn = "http://qa5.learn365.orientaltrading.com/";
				Constants.Mindware = "http://qa5.mindware.orientaltrading.com/";
				Constants.Marryme = "http://qa5.marryme.orientaltrading.com/";
				Constants.F365 = "http://qa5.fun365.orientaltrading.com/";
				Constants.CF365 = "http://qa5.customfun365.orientaltrading.com/";
				break;

			case "Cell D":
				Constants.OTC = "http://perf.orientaltrading.com/";
				Constants.Learn = "http://perf.learn365.orientaltrading.com/";
				Constants.Mindware = "http://perf.mindware.orientaltrading.com/";
				Constants.Marryme = "http://perf.marryme.orientaltrading.com/";
				Constants.F365 = "http://perf.fun365.orientaltrading.com/";
				Constants.CF365 = "http://perf.customfun365.orientaltrading.com/";
				break;

			default:
				Constants.OTC = "http://www.orientaltrading.com/";
				Constants.Learn = "http://www.learn365.orientaltrading.com/";
				Constants.Mindware = "http://www.mindware.orientaltrading.com/";
				Constants.Marryme = "http://www.marryme.orientaltrading.com/";
				Constants.F365 = "http://www.fun365.orientaltrading.com/";
				Constants.CF365 = "http://www.customfun365.orientaltrading.com/";
				break;*/
			}
		} catch (Exception e) {
			Log.error("Unable to Set Environment --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Set Environment--- " + e.getMessage());

			DriverScript.bResult = false;
		}
	}

	public static void navigate(String object, String URL) {
		Log.info("Navigating to " + URL);
		ExtentLog.test.log(LogStatus.INFO, "Navigating to " + URL);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		try {
			switch (URL) {
			case "PROD":
				driver.get(Constants.PROD);
				driver.manage().window().maximize();
				break;

			case "QA1":
				driver.get(Constants.QA1);
				driver.manage().window().maximize();
				break;
			
			case "UAT":
				driver.get(Constants.UAT);
				driver.manage().window().maximize();
				break;
			
			default:
				driver.get(Constants.UAT);
				driver.manage().window().maximize();
				break;
			
			
			
	//-----------Below cases for OTC Project ------------	
			/*case "OTC":
				driver.get(Constants.OTC);
				driver.manage().window().maximize();
				break;

			case "Learn":
				driver.get(Constants.Learn);
				driver.manage().window().maximize();
				break;

			case "Mindware":
				driver.get(Constants.Mindware);
				driver.manage().window().maximize();
				break;

			case "Marryme":
				driver.get(Constants.Marryme);
				driver.manage().window().maximize();
				break;

			case "F365":
				driver.get(Constants.F365);
				driver.manage().window().maximize();
				break;

			case "CF365":
				driver.get(Constants.CF365);
				driver.manage().window().maximize();
				break;

			default:
				driver.get(Constants.OTC);
				driver.manage().window().maximize();
				break;*/
			}

		} catch (Exception e) {
			Log.error("Unable to Navigate --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Navigate --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void click(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Clicking on Webelement " + object);
			ExtentLog.test.log(LogStatus.INFO, "Clicking on Webelement " + object);
			driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).click();
		} catch (Exception e) {
			Log.error("Unable to click --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to click --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void clickButton(String object, String data) {
		// This Method can be best used if the element is not at all clickable
		// by general click
		try {
			verifyFeedbackPage("", "");
			Log.info("Clicking on Webelement " + object);
			ExtentLog.test.log(LogStatus.INFO, "Clicking on Webelement " + object);
			WebElement element = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
		} catch (Exception exc) {
			Log.error("Unable to click --- " + exc.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to click --- " + exc.getMessage());
			DriverScript.bResult = false;
		}
		
	}

	
	public static void hover(String object, String data) {
		// Hover over to Login
		try {
			verifyFeedbackPage("", "");
			Log.info("Hovering on Webelement " + object);
			ExtentLog.test.log(LogStatus.INFO, "Hovering on Webelement " + object);
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)))).build()
					.perform();
		} catch (Exception e) {
			Log.error("Unable to Hover --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Hover --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void input(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Entering the text '" + data + "' in "+object + " field");
			ExtentLog.test.log(LogStatus.INFO, "Entering the text '" + data + "' in "+object + " field");
			verifyFeedbackPage("", "");
			driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).clear();
			driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).sendKeys(data);
			// driver.findElement(By.id("zip")).sendKeys(data);
		} catch (Exception e) {
			Log.error("Unable to Enter " + data + " in "+object + " field " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR,"Unable to Enter "  + data + " in "+object + " field" + "----- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	
	
	public static void inputQTY(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Entering the text '" + data + "' in "+object + " field");
			ExtentLog.test.log(LogStatus.INFO, "Entering the text '" + data + "' in "+object + " field");
			verifyFeedbackPage("", "");
			driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).click();
			driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).sendKeys(data);
			// driver.findElement(By.id("zip")).sendKeys(data);
		} catch (Exception e) {
			Log.error("Unable to Enter " + data + " in "+object + " field " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR,"Unable to Enter "  + data + " in "+object + " field" + "----- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	public static void catalogueOrder(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Selecting Catalogue Quick Order Option " + object);
			ExtentLog.test.log(LogStatus.INFO, "Selecting Catalogue Quick Order Option " + object);
			driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).sendKeys(Keys.RETURN);
			// driver.findElement(By.id("zip")).sendKeys(data);
		} catch (Exception e) {
			Log.error("Unable to Click " + object + "----- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Click --- " + object + "----- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void switchFrame(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			if (data == "") {
				Log.info("Switching to default Frame");
				ExtentLog.test.log(LogStatus.INFO, "Switching to default Frame");
				driver.switchTo().defaultContent();
			} else {
				Log.info("Switching to " + data + " Frame");
				ExtentLog.test.log(LogStatus.INFO, "Switching to " + data + " Frame");
				driver.switchTo().frame(data);
			}
		} catch (Exception e) {
			Log.error("Unable to Switch " + "----- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Switch Frame --- " + "----- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void dropdown(String object, String data) {
		// Hover over to Login
		try {
			verifyFeedbackPage("", "");
			Log.info("Selecting DropDown " + object);
			ExtentLog.test.log(LogStatus.INFO, "Hovering on Webelement " + object);
			Select dropdown = new Select(driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))));
			dropdown.selectByVisibleText(data);
		} catch (Exception e) {
			Log.error("Unable to Select Dopdown Text --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to select Dropdown text --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void captureOrder(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Capturing the Order Number");
			ExtentLog.test.log(LogStatus.INFO, "Capturing the Order Number");
			orderNumber = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).getText();
			ExcelUtils.setCellData(orderNumber, DriverScript.iTestStep, Constants.Col_TestStepResult - 1,
					Constants.Sheet_TestSteps, DriverScript.sPath);
			// JavascriptExecutor executor = (JavascriptExecutor)driver;
			// executor.executeScript("window.scrollBy(0,550)", "");
			Log.info("Order Number is " + orderNumber);
			ExtentLog.test.log(LogStatus.PASS, "Order Number is " + orderNumber);
		} catch (Exception e) {
			Log.error("Unable to Capture Order Number " + object + "----- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Capture Order Number " + object + "----- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	

	
	public static void submit(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Clicking Submit Button");
			ExtentLog.test.log(LogStatus.INFO, "Clicking Submit Button");
			driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).submit();
		} catch (Exception e) {
			Log.error("Unable to Submit --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Enter --- " + object + "----- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void verifyText(String object, String expText) {
		try {
			verifyFeedbackPage("", "");
			String actText = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).getText().toLowerCase();
			if (actText.contains(expText.toLowerCase())) {
				Log.info("Verifying Text" + expText);
				ExtentLog.test.log(LogStatus.INFO, "Verifying Text " + expText);
				System.out.println("Text Verified");
			} else {
				System.out.println("Text Not Verified");
				Log.error("Unable to Verify Text Message --- " + "Actual Text is " + actText + " Expected Text is "
						+ expText);
				ExtentLog.test.log(LogStatus.ERROR, "Unable to Verify Text Message --- " + "Actual Text is " + actText
						+ " Expected Text is " + expText);
				DriverScript.bStep = false;
			}

		} catch (Exception e) {
			Log.error("Unable to Verify Text Message --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Verify Text Message --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	

	public static void assertText(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			data.toLowerCase();
			String actText = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).getText().toLowerCase();
			if (actText.contains(data)) {
				Log.info("Verifying Text" + data);
				ExtentLog.test.log(LogStatus.INFO, "Verifying Text " + data);
				System.out.println("Text Verified");
			} else {
				System.out.println("Text Not Verified");
				Log.error(
						"Unable to Verify Text Message --- " + "Actual Text is " + actText + " Expected Text is " + data);
				ExtentLog.test.log(LogStatus.ERROR,
						"Unable to Verify Text Message --- " + "Actual Text is " + actText + " Expected Text is " + data);
				DriverScript.bResult = false;
			}

		} catch (Exception e) {
			Log.error("Unable to Verify Text Message --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Verify Text Message --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void wait(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Waiting for the next element to load");
			ExtentLog.test.log(LogStatus.INFO, "Waiting for the next element to load");
			int waitTime = 15;
			if (data != "")
				waitTime = Integer.parseInt(data);
			WebDriverWait wait = new WebDriverWait(driver, waitTime);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(RespositoryParser.getbjectLocator(OR.getProperty(object))));
		} catch (Exception e) {
			Log.error("Unable to Load/Find the next object " + object + "-------" + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR,
					"Unable to Load/Find the next object " + object + "-------" + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void uploadFile(String object,String data)
	{
		try{
			
			if(data!=""){
				Log.info("Uploading File");
				ExtentLog.test.log(LogStatus.INFO, "Uploading File");
				driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).sendKeys(data);
			}			
		}catch(Exception e){
			Log.error("Unable to Upload File " +"----- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Upload File --- " +"----- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void externalWait(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Waiting for the next element to load");
			ExtentLog.test.log(LogStatus.INFO, "Waiting for the next element to load");
			int waitTime = Integer.parseInt(data);
			Thread.sleep(waitTime * 1000);
		} catch (Exception e) {
			Log.error("Unable to Load/Find the next object " + object + "-------" + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR,
					"Unable to Load/Find the next object " + object + "-------" + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void takeScreenshot(String object, String data) {
		verifyFeedbackPage("", "");
		// Create reference of TakesScreenshot
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Copy files to specific location here it will save all screenshot in
		// our project home directory and
		// result.getName() will return name of test case so that screenshot
		// name will be same
		try {
			Log.info("Capturing Screenshot");
			ExtentLog.test.log(LogStatus.INFO, "Taking Screenshot");
			FileUtils.copyFile(source, new File(
					Constants.Path_TestResults + "\\" + DriverScript.sTestCaseID + "\\TestResult" + i + ".png"));
			String image = ExtentLog.test.addScreenCapture(
					Constants.Path_TestResults + "\\" + DriverScript.sTestCaseID + "\\TestResult" + i + ".png");
			ExtentLog.test.log(LogStatus.INFO, "Title verification", image);
			System.out.println("Screenshot Captured");
			i++;
		} catch (Exception e) {
			Log.error("Unable to Capture Screenshot --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Capture Screenshot --- " + e.getMessage());
			DriverScript.bResult = false;
		}

	}

	public static void closeBrowser(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Closing the browser");
			ExtentLog.test.log(LogStatus.INFO, "Closing the browser");
			driver.close();
		} catch (Exception e) {
			Log.error("Unable to Close the Browser --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Close the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	
	public static void linkVerify(String object, String data) {
	
		String object1 = null; String data1 = null;
		String object2 = null; String data2 = null;
		String[] Obj= object.split(",");
		String[] Dat= data.split(",");
		object1 =Obj[0];data1 = Dat[0];
		object2 = Obj[1];data2 = Dat[1];
		try{ 
			navigate("", data1);
			closePopup("close_PopUp","");
			hover(object1, "");
			click(object1, "");
			wait(object2,"");
			verifyText(object2, data2);
			ExtentLog.test.log(LogStatus.ERROR, "Verified link text: " + data2);
			Log.info("Verified link text: " + data2);
		}catch (Exception e) {
			Log.error("Not Verified the link --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Not Verified the link --- " + e.getMessage());
		}
	}
	
	
	
	public static void verifyURL(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Capturing URL");
			ExtentLog.test.log(LogStatus.INFO, "Capturing URL");
			String URL = driver.getCurrentUrl();
			if (URL.contains(data)) {
				ExtentLog.test.log(LogStatus.PASS, "URL " + URL + " has been verified successfully");
				Log.info("URL " + URL + " has been verified successfully");
				takeScreenshot("", "");
			} else {
				ExtentLog.test.log(LogStatus.ERROR, "Expected URL is " + data + " ------ actual URL is " + URL);
				Log.info("URL " + URL + " verification FAILED");
				takeScreenshot("", "");
			}
		} catch (Exception e) {
			Log.error("Unable to Get the URL --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Get the URL --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void verifyCell(String object, String cellName) {
		try {
			verifyFeedbackPage("", "");
			Log.info("verifying off cell Name " + object);
			ExtentLog.test.log(LogStatus.INFO, "verifying off cell Name " + object);
			// String source=driver.getPageSource();
			if (driver.getPageSource().contains(cellName)) {
				System.out.println(cellName);
				Log.info("Running on " + cellName);
				ExtentLog.test.log(LogStatus.INFO, "Running on " + cellName);
			} else {
				System.out.println("Can not find Off cell Name " + cellName);
				Log.error("Can not find Off cell Name --- ");
				ExtentLog.test.log(LogStatus.ERROR, "Unable to verify off cell Name --- ");
				DriverScript.bResult = false;
			}

		} catch (Exception e) {
			Log.error("Unable to verify off cell Name --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to verify off cell Name --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	

	// ****************************** CUSTOM Functions   ********************************************************************//
	
	// Edit Quantity in Cart Summary Page
	public static void editQuantity(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Editing quantity of Item Order " + object);
			ExtentLog.test.log(LogStatus.INFO, "Editing quantity of Item Order " + object);
			int itemOrder = Integer.parseInt(object);
			driver.findElement(By.xpath("//tbody/tr[" + (itemOrder + 1) + "]/td[3]/input")).clear();
			driver.findElement(By.xpath("//tbody/tr[" + (itemOrder + 1) + "]/td[3]/input")).sendKeys(data);
			driver.findElement(By.xpath("//tbody/tr[" + (itemOrder + 1) + "]/td[3]/a")).click();
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//tbody/tr[" + (itemOrder + 1) + "]/td[3]/p")));
		} catch (Exception e) {
			Log.error("Unable to find Item Order --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to find Item Order --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void closePopup(String object, String cellName) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Closing Pop Up" + object);
			ExtentLog.test.log(LogStatus.INFO, "Closing Pop Up " + object);
			// String source=driver.getPageSource();
			driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).click();
		} catch (Exception e) {
			Log.error("Could not find pop Up --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Could not find pop Up --- " + e.getMessage());

		}
	}	
	
	//Fill Customer Information
	public static void fill_CustInfoPage(String object, String data) {
		
		String firstName = null; 
		String lastName = null;
		String street = null; 
		String zipcode= null;
		String phone = null;
		String[] address = data.split(",");
		firstName = address[0];	
		lastName = address[1];
		street = address[2];  
		zipcode = address[3];
		phone = address[4];
		try {
			verifyFeedbackPage("", "");
			if(driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).isDisplayed()){
				driver.findElement(By.id("firstName")).sendKeys(firstName);
				Log.info("Customer Details: "+firstName);
				driver.findElement(By.id("lastName")).sendKeys(lastName);
				Log.info(lastName);
				driver.findElement(By.id("address1")).sendKeys(street);
				Log.info(street);
				driver.findElement(By.id("zip")).sendKeys(zipcode);
				Log.info(zipcode);
				driver.findElement(By.id("phone")).sendKeys(phone);
				Log.info(phone);
				wait("state","30");
				Log.info("Customer info filled ---");
				ExtentLog.test.log(LogStatus.INFO, "Customer info filled ---");
			}
			else{
				Log.info("Customer information is already saved !");
				ExtentLog.test.log(LogStatus.INFO, "Customer information is already saved !");
			}
			
		} catch (Exception e) {
			Log.error("Could not find  --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Could not find --- " + e.getMessage());

		}
	}
	
	//Paypal login details submit
	public static void paypal_Login(String object, String data) {
		String emailid = null;
		String password = null;
		String[] logincredential = data.split(",");
		emailid = logincredential[0];
		password = logincredential[1];
		Log.info(emailid);
		Log.info(password);
		try {		
			if(driver.findElement(By.cssSelector("#password")).isDisplayed()){
				//driver.switchTo().frame("injectedUl");
				//wait("payEmail", "");
				driver.findElement(By.cssSelector("#email")).sendKeys(emailid);
				driver.findElement(By.cssSelector("#password")).sendKeys(password);
				driver.findElement(By.cssSelector("label.checkboxLabel")).click();
				driver.findElement(By.cssSelector("#btnLogin")).click();
				
			}else{
				driver.findElement(By.cssSelector("#email")).sendKeys(emailid);
				driver.findElement(By.cssSelector("#btnNext")).click();
				wait("payPassword", "");
				driver.findElement(By.cssSelector("#password")).sendKeys(password);
				driver.findElement(By.cssSelector("label.checkboxLabel")).click();
				driver.findElement(By.cssSelector("#btnLogin")).click();	
			}
			Log.info("Successfully Logged In using paypal account"+object);
			ExtentLog.test.log(LogStatus.INFO, "Successfully Logged In using paypal account" + object);
		} catch (Exception e) {
			Log.error("Unable to Login with paypal & paypalCreadit account --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Login with paypal & paypalCreadit account --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	
	public static void MovetoElement(String object, String data) {
		try{
			Actions action = new Actions(driver);
			//object "helloIcon"
			WebElement element = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)));
			action.moveToElement(element).build().perform();
		
		}catch(Exception e){
			Log.error("Unable to perform action --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to perform action --- " + e.getMessage());
			DriverScript.bResult = false;	
		}
	}
	
	//Login Event
	public static void loginEvent(String object, String data) {
		// Pattern pattern ;
		String emailid = null;
		String password = null;
		String[] logincredential = data.split(",");
		emailid = logincredential[0];
		password = logincredential[1];
		Log.info(emailid);
		Log.info(password);
		try{
			verifyFeedbackPage("", "");
			Actions action = new Actions(driver);
			//object "helloIcon"
			WebElement element = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)));
			action.moveToElement(element).build().perform();
			driver.findElement(By.linkText("Log In")).click();
			wait("user_Name", "5");
			driver.findElement(By.id("userId")).sendKeys(emailid);
			driver.findElement(By.id("userPassword")).sendKeys(password);
			driver.findElement(By.cssSelector("button.btn.btn_primary.hideError")).click();
			Log.info("User logged In --- ");
			ExtentLog.test.log(LogStatus.INFO, "User logged In --- ");
		}catch(Exception e){
			Log.error("Unable to Login --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Login --- " + e.getMessage());
			DriverScript.bResult = false;	
		}
	}
	
	//LogOut Event
	public static void logoutEvent(String object, String data){
		try{
			verifyFeedbackPage("", "");
			Actions action = new Actions(driver);
			//object "helloIcon"
			WebElement element = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)));
			action.moveToElement(element).build().perform();
			wait("logout_Link", "2");
			driver.findElement(By.linkText("Log Out")).click();
			Log.info("User log out successfully--- ");
			ExtentLog.test.log(LogStatus.INFO, "User log out successfully---  ");		
		}catch(Exception e){
			Log.error("Unable to Logout --- "+e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to Logout --- "+e.getMessage());
			DriverScript.bResult = false;	
		}
	} 
	
	
	
	// Edit Items
	public static void removeCartItems(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			if (driver.findElement(By.cssSelector(".article-header>p")).getText()
					.contains("Items remain in your shopping cart")) {
				Log.info("Removing Items from Cart");
				ExtentLog.test.log(LogStatus.INFO, "Removing Items from Cart");
				int totalItems = Integer
						.parseInt(driver.findElement(By.cssSelector(".cart-total-item>.cart-size")).getText());
				int i = totalItems;
				while (i > 0) {
					WebDriverWait wait = new WebDriverWait(driver, 10);
					wait.until(ExpectedConditions
							.elementToBeClickable(RespositoryParser.getbjectLocator(OR.getProperty(object))));
					int pos1Items = Integer
							.parseInt(driver.findElement(By.xpath("//*[@id='cart-items']/tbody/tr[2]/td[3]/input"))
									.getAttribute("value"));
					i = i - pos1Items;
					WebElement element = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)));
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", element);
					System.out.println(i);
					// driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).click();
				}
			} else
				ExtentLog.test.log(LogStatus.INFO, "No Items Found in Cart");
		} catch (Exception e) {
			Log.error("Unable to remove Items --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to remove Items --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void verifyFeedbackPage(String object, String data) {
		try {
			if (driver.getPageSource().contains("We'd welcome your feedback!")) {
				Log.info("Closing Feedback page");
				ExtentLog.test.log(LogStatus.INFO, "Closing Feedback page ");
				driver.findElement(By.xpath("//a[@title='Click to close.']")).click();
			}
			else if (driver.getPageSource().contains("We'd love to hear from you!")) {
				Log.info("Closing Feedback page");
				ExtentLog.test.log(LogStatus.INFO, "Closing Feedback page ");
				driver.findElement(By.cssSelector(".QSIPopOver>div:nth-of-type(3)>div>img")).click(); // html/body/div[16]/div[6]/div
			}
		} catch (Exception e) {
			Log.error("Null pointer --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Null pointer --- " + e.getMessage());
			// DriverScript.bResult = false;
		}
	}

	//Manage FreeShipping popup
	public static void freeShipping(String object, String data) {
		try {
				driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).click();
				Log.info("Closing Freeshipping (baloon )popup");
				ExtentLog.test.log(LogStatus.INFO, "Closing Freeshipping (baloon )popup");
		} catch (Exception e) {
			Log.info("No Freeshipping popup is observed on the page--- ");
			ExtentLog.test.log(LogStatus.INFO, "No Freeshipping popup is observed on the page--- ");
		}
	}
	
	//Manage referFriend popup
		public static void referFriend_Popup(String object, String data) {
			try {
					driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object))).click();
					Log.info("Closing referFriend popup");
					ExtentLog.test.log(LogStatus.INFO, "Closing referFriend popup");
			} catch (Exception e) {
				Log.info("No referFriend popup is observed on the page--- ");
				ExtentLog.test.log(LogStatus.INFO, "No referFriend popup is observed on the page--- ");
			}
		}
	
	public static int itemCount() {
		int second_itemCount=0;
		try{
			second_itemCount=Integer.parseInt(driver.findElement(By.cssSelector("span.cart-total-item>span.cart-size")).getText());
			Log.info("Showing the Item Count ---"+second_itemCount);
			ExtentLog.test.log(LogStatus.INFO, "Showing the Item Count ---"+second_itemCount);
		}catch (Exception e) {
			Log.info("Item total Count is not Found");
			ExtentLog.test.log(LogStatus.INFO, "Item total Count is not Found");
		}
		return second_itemCount;
	}
		
	public static void yes_MoveToWL(String object, String data) {
		try{
			WebElement element = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			Log.info("Yes, Confirm Move to Wishlist Item");
			ExtentLog.test.log(LogStatus.INFO, "Yes, Confirm Move to Wishlist Item");
		}catch (Exception e) {
			Log.error("Confirm Move to Wishlist Item --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Confirm Move to Wishlist Item --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	
	}
	
	public static void addCart_FromWL(String object, String data) {
		try{
			WebElement element = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)));
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			Log.info("Yes, Confirm Move to Wishlist Item");
			ExtentLog.test.log(LogStatus.INFO, "Yes, Confirm Move to Wishlist Item");
		}catch (Exception e) {
			Log.error("Confirm Move to Wishlist Item --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Confirm Move to Wishlist Item --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	
	}
	
	public static void verifyLinkActive(String linkUrl) {
		try {
			verifyFeedbackPage("", "");
			URL url = new URL(linkUrl);
			HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();
			httpURLConnect.setConnectTimeout(3000);
			httpURLConnect.connect();

			if (httpURLConnect.getResponseCode() == 200) {
				System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
				ExtentLog.test.log(LogStatus.INFO, linkUrl + " - " + httpURLConnect.getResponseMessage());
			}
			if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
				System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
						+ HttpURLConnection.HTTP_NOT_FOUND);
				Log.info(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
						+ HttpURLConnection.HTTP_NOT_FOUND);
				ExtentLog.test.log(LogStatus.INFO, linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
						+ HttpURLConnection.HTTP_NOT_FOUND);
			}
			httpURLConnect.disconnect();

		} catch (Exception e) {
			Log.info("Unable to verify Link " + linkUrl + "-----" + e.getMessage());
			ExtentLog.test.log(LogStatus.INFO, "Unable to verify Link " + linkUrl + " ----- " + e.getMessage());
		}
	}
	
	public static void verifySalesTax(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			double subTotal = 0.0;
			double shipTotal = 0.0;
			double actSalesTax = 0.0;
			double actOrderTotal = 0.0;
			double expSalesTax = 0.0;
			double expOrderTotal = 0.0;
			double shipDiscount = 0.0;
			String state = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)))
					.getAttribute("value");
			Log.info("Verifyng Sales Tax for " + state + " " + object);
			ExtentLog.test.log(LogStatus.INFO, "Verifyng Sales Tax for " + state + " " + object);
			// Get Items sub Total
			subTotal = Double.parseDouble(driver.findElement(By.cssSelector(".amt-subtotal")).getText());
			shipTotal = Double.parseDouble(driver.findElement(By.cssSelector(".amt-ship-and-handle")).getText());
			actSalesTax = Double.parseDouble(driver.findElement(By.cssSelector(".amt-tax")).getText());
			actOrderTotal = Double.parseDouble(driver.findElement(By.cssSelector(".amt-grand-total")).getText());
			try {
				shipDiscount = Double.parseDouble(driver.findElement(By.cssSelector(".amt-ship-discount")).getText());
			} catch (Exception e) {
				Log.info("Unable to find shipping discount --- " + e.getMessage());
				ExtentLog.test.log(LogStatus.INFO, "Unable to find shipping discount --- " + e.getMessage());
			}

			Log.info("Items Subtotal=" + subTotal + " shipTotal=" + shipTotal + " salesTax= " + actSalesTax
					+ " Actual orderTotal=" + actOrderTotal);
			ExtentLog.test.log(LogStatus.INFO, "Items Subtotal=" + subTotal + " shipTotal=" + shipTotal
					+ " Actual salesTax= " + actSalesTax + " Actual orderTotal=" + actOrderTotal);

			switch (state) {
			case "NE":
				expSalesTax = (subTotal + shipTotal - shipDiscount) * 0.07;
				expSalesTax = Math.round(expSalesTax * 100d) / 100d;
				break;

			case "SC":
				expSalesTax = (subTotal + shipTotal - shipDiscount) * 0.06;
				expSalesTax = Math.round(expSalesTax * 100d) / 100d;
				break;

			case "MN":
				expSalesTax = (subTotal + shipTotal - shipDiscount) * 0.0688;
				expSalesTax = Math.round(expSalesTax * 100d) / 100d;
				break;

			default:
				expSalesTax = (subTotal + shipTotal - shipDiscount) * 0.00;
				expSalesTax = Math.round(expSalesTax * 100d) / 100d;
				break;
			}
			Log.info("Expected salesTax= " + expSalesTax);
			ExtentLog.test.log(LogStatus.INFO, " Expected salesTax= " + expSalesTax);
			expOrderTotal = expSalesTax + subTotal + shipTotal - shipDiscount;
			expOrderTotal = Math.round(expOrderTotal * 100d) / 100d;
			if (expOrderTotal == actOrderTotal) {
				Log.info("Expected Total: " + expOrderTotal + " = " + actOrderTotal + ":" + "Actual Order Total");
				ExtentLog.test.log(LogStatus.PASS,
						"Expected Total: " + expOrderTotal + " = " + actOrderTotal + ":" + "Actual Order Total");
			} else {
				Log.info("Expected Total: " + expOrderTotal + " Not Equal to " + actOrderTotal + ":"
						+ "Actual Order Total");
				ExtentLog.test.log(LogStatus.ERROR, "Expected Total: " + expOrderTotal + " Not Equal to "
						+ actOrderTotal + ":" + "Actual Order Total");
				DriverScript.bStep = false;
			}
		} catch (Exception e) {
			Log.error("Unable to click --- " + e.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to click --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}

	public static void f365_clickLink(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			int i = driver.findElements(RespositoryParser.getbjectLocator(OR.getProperty(object))).size();
			i = (int) (i * Math.random()) + 1;
			// i=10;
			Log.info("Clicking on " + i + "th Random Item on the list");
			ExtentLog.test.log(LogStatus.INFO, "Clicking on " + i + "th Random Item on the list");
			// Clicking on i th Item
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(
					driver.findElement(By.cssSelector(".card-list>div:nth-of-type(" + i + ") .card-title>a"))));
			driver.findElement(By.cssSelector(".card-list>div:nth-of-type(" + i + ") .card-title>a")).click();
		} catch (Exception e) {
			Log.info("Unable to click Cards in F365 Page " + e.getMessage());
			ExtentLog.test.log(LogStatus.INFO, "Unable to click Cards in F365 Page " + e.getMessage());
		}
	}
	
	
	public static void cf3_qtyIncrease(String object, String data) {
		// This Method can be best used if the element is not at all clickable
		// by general click
		try {
			verifyFeedbackPage("", "");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			int totalItems = driver.findElements(RespositoryParser.getbjectLocator(OR.getProperty("qtyOptions"))).size();
			System.out.println("Total Items in Card = " + totalItems);
			int iRandom = (int) (totalItems * Math.random()) + 1;

			System.out.println("Random Item Selected = " + iRandom);
			for (int cnt = 1; cnt <= totalItems; cnt++) {
				int cmm =11;
				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By
						.cssSelector(".quantity-container > div:nth-child("+cmm+") >div.col-qty>div>button.btn.btn_primary.desigStudioPage.plus-icon.js-qty-increment"))));
				driver.findElement(
						By.cssSelector(".quantity-container > div:nth-child("+cmm+") >div.col-qty>div>button.btn.btn_primary.desigStudioPage.plus-icon.js-qty-increment"))
						.click();
				cmm = cmm+2;
		}
		}catch (Exception exc) {
			Log.error("Unable to click --- " + exc.getMessage());
			ExtentLog.test.log(LogStatus.ERROR, "Unable to click --- " + exc.getMessage());
			DriverScript.bResult = false;}
		}

	public static void f365_addCart(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(
					ExpectedConditions.elementToBeClickable(RespositoryParser.getbjectLocator(OR.getProperty(object))));
			int totalItems = driver.findElements(RespositoryParser.getbjectLocator(OR.getProperty(object))).size();
			System.out.println("Total Items in Card = " + totalItems);
			int iRandom = (int) (totalItems * Math.random()) + 1;

			System.out.println("Random Item Selected = " + iRandom);
			for (int cnt = 1; cnt <= totalItems; cnt++) {
				wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By
						.cssSelector(".materials-list>div:nth-of-type(" + cnt + ") .item-quantity-input-increment"))));
				driver.findElement(
						By.cssSelector(".materials-list>div:nth-of-type(" + cnt + ") .item-quantity-input-increment"))
						.click();
			}
			driver.findElement(By.cssSelector(".btn.add-to-cart")).click();
			// Log.info("Clicking on "+i +"th Random Item on the list" );
			// ExtentLog.test.log(LogStatus.INFO, "Clicking on "+i +"th Random
			// Item on the list");
			// Clicking on i th Item
			// driver.findElement(By.cssSelector(".card-list>div:nth-of-type("+i+")
			// .card-title>a")).click();
		} catch (Exception e) {
			Log.info("Unable to click Cards in F365 Page " + e.getMessage());
			ExtentLog.test.log(LogStatus.INFO, "Unable to click Cards in F365 Page " + e.getMessage());
		}
	}

	public static void verifyAllLinks(String object, String data) {
		try {
			verifyFeedbackPage("", "");
			Log.info("Verify links in the web page");
			ExtentLog.test.log(LogStatus.INFO, "Verify links in the web page ");
			WebElement elements = null;
			List<WebElement> links = null;

			if (object != "") {
				elements = driver.findElement(RespositoryParser.getbjectLocator(OR.getProperty(object)));
				links = elements.findElements(By.tagName("a"));
				// links.addAll(elements.findElements(By.tagName("img")));
			} else {
				links = driver.findElements(By.tagName("a"));
				links.addAll(driver.findElements(By.tagName("img")));
			}
			System.out.println("Total links are " + links.size());

			for (int i = 0; i < links.size(); i++) {
				WebElement element = links.get(i);
				String url = element.getAttribute("href");
				verifyLinkActive(url);
			}
		} catch (Exception e) {
			Log.info("Unable to click Cards in F365 Page " + e.getMessage());
			ExtentLog.test.log(LogStatus.INFO, "Unable to click Cards in F365 Page " + e.getMessage());
		}
	}


}
