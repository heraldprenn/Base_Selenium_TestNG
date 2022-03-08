package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Keywords {
	WebDriver webDriver;

	public Keywords(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	// ==================================================|Do|==================================================
	/**
	 * Converts the color RGBA to hexadecimal value.
	 * 
	 * @param strColor - the colors RGBA value to be converted.
	 * @return Returns the Hexadecimal value of the color.
	 */
	public String convertColorToHexadecimal(String strColor) {
		String strHex = Color.fromString(strColor).asHex();

		return strHex;
	}

	/**
	 * Executes the JavaScript code then returns a value.
	 * 
	 * @param strJavaScript - the javascript to execute.
	 * @return Returns the result of the executed JavaScript code.
	 */
	public String javaScriptExecutorWithReturn(String strJavaScript) {
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) webDriver;

		return (String) javaScriptExecutor.executeScript(strJavaScript);
	}

	/**
	 * Send keyboard inputs on the focused element or frame.
	 * 
	 * @param strKeys - keys/characters to be sent.
	 */
	public void actionSendKeys(String strKeys) {
		Actions actions = new Actions(webDriver);
		actions.sendKeys(strKeys);
	}

	/**
	 * Accepts the browser alert popup.
	 */
	public void alertAccept() {
		webDriver.switchTo().alert().accept();
	}

	/**
	 * Dismisses the browser alert popup.
	 */
	public void alertDismiss() {
		webDriver.switchTo().alert().dismiss();
	}

	/**
	 * Sends the keys/characters to the browser alert popup.
	 * 
	 * @param strKeys - keys/characters to be sent.
	 */
	public void alertSendKeys(String strKeys) {
		webDriver.switchTo().alert().sendKeys(strKeys);
	}

	/**
	 * Executes the JavaScript code.
	 * 
	 * @param strJavaScript - the javascript to execute.
	 */
	public void javaScriptExecutor(String strJavaScript) {
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) webDriver;

		javaScriptExecutor.executeScript(strJavaScript);
	}

	/**
	 * Navigate back to the last accessed page before the current one if available.
	 */
	public void navigateBack() {
		webDriver.navigate().back();
	}

	/**
	 * Navigate forward to the next accessed page before the current one if
	 * available.
	 */
	public void navigateForward() {
		webDriver.navigate().forward();
	}

	/**
	 * Navigates to the provided URL.
	 * 
	 * @param strURL - web page URL to navigate to.
	 */
	public void navigateToUrl(String strURL) {
		webDriver.navigate().to(strURL);
	}

	/**
	 * Refreshes the current page.
	 */
	public void refreshWebPage() {
		webDriver.navigate().refresh();
	}

	/**
	 * Scrolls to the bottom of the current frame.
	 */
	public void scrollToBottom() {
		javaScriptExecutor("window.scrollTo(0, document.body.scrollHeight)");
	}

	/**
	 * Scrolls to the given coordinates of the current frame.
	 * 
	 * @param intX - X coordinate.
	 * @param intY - Y coordinate.
	 */
	public void scrollToCoordinates(int intX, int intY) {
		javaScriptExecutor("window.scrollBy(" + intX + "," + intY + ")");
	}

	/**
	 * Scrolls to the top of the current frame.
	 */
	public void scrollToTop() {
		javaScriptExecutor("window.scrollTo(0, 0)");
	}

	/**
	 * Scrolls to the web element into view of the current frame.
	 * 
	 * @param webElement - web element to perform the action to.
	 */
	public void scrollToWebElement(WebElement webElement) {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;

		javascriptExecutor.executeScript("arguments[0].scrollIntoView();", webElement);
	}

	/**
	 * Switch frame by web element.
	 * 
	 * @param webElement - web element to find.
	 */
	public void switchFrameByWebElement(WebElement webElement) throws Exception {
		Integer intNumberOfFrames = webDriver.findElements(By.tagName("iframe")).size();

		for (int i = 0; i <= intNumberOfFrames; i++) {
			webDriver.switchTo().frame(i);

			if (isWebElementDisplayed(webElement))
				break;

			webDriver.switchTo().defaultContent();
		}
	}

	/**
	 * Switches the focus to the parent frame.
	 */
	public void switchToParentFrame() {
		webDriver.switchTo().parentFrame();
	}

	/**
	 * Take screenshot.
	 * 
	 * @param strFilePath - string where the image is saved.
	 */
	public void takeScreenshot(String strFilePath) throws Exception {
		TakesScreenshot screenshot = ((TakesScreenshot) webDriver);
		File fileSource = screenshot.getScreenshotAs(OutputType.FILE);
		File fileDestination = new File(strFilePath);

		FileUtils.copyFile(fileSource, fileDestination);
	}

	/**
	 * Take screenshot of web element. Width and Height percentage optional.
	 * 
	 * @param webElement          - web element to screenshot.
	 * @param intWidthPercentage  - width percentage of web element.
	 * @param intHeightPercentage - height percentage of web element.
	 * @param strFilePath         - string where the image is saved.
	 */
	public void takeScreenshotOfWebElement(WebElement webElement, int intWidthPercentage, int intHeightPercentage,
			String strFilePath) throws Exception {
		File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImage = ImageIO.read(screenshot);
		Point point = webElement.getLocation();
		int intWebElementWidth = webElement.getSize().getWidth();
		int intWebElementHeight = webElement.getSize().getHeight();

		if (intWidthPercentage != 0) {
			intWebElementWidth = intWebElementWidth * (intWidthPercentage / 100);
		}

		if (intHeightPercentage != 0) {
			intWebElementHeight = intWebElementHeight * (intHeightPercentage / 100);
		}

		BufferedImage webElementScreenshot = fullImage.getSubimage(point.getX(), point.getY(), intWebElementWidth,
				intWebElementHeight);

		ImageIO.write(webElementScreenshot, "png", screenshot);

		File screenshotLocation = new File(strFilePath);

		FileUtils.copyFile(screenshot, screenshotLocation);
	}

	/**
	 * Waits for the web element to be visible then clears the value in the web
	 * element.
	 * 
	 * @param webElement - web element to perform the action to.
	 */
	public void webElementClear(WebElement webElement) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			webElement.clear();
		}
	}

	/**
	 * Waits for the web element to be visible then clicks the web element.
	 * 
	 * @param webElement - web element to perform the action to.
	 */
	public void webElementClick(WebElement webElement) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			webElement.click();
		}
	}

	/**
	 * Waits for the web element to be visible then clicks the web element.
	 * Alternative (Actions).
	 * 
	 * @param webElement - web element to perform the action to.
	 */
	public void webElementActionClick(WebElement webElement) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			Actions actions = new Actions(webDriver);

			actions.click(webElement).build().perform();
		}
	}

	/**
	 * Waits for the web element to be visible then double clicks the web element.
	 * 
	 * @param webElement - web element to perform the action to.
	 */
	public void webElementDoubleClick(WebElement webElement) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			Actions actions = new Actions(webDriver);

			actions.doubleClick(webElement).build().perform();
		}
	}

	/**
	 * Waits for the web elements to be visible then click-and-hold the source web
	 * element and then moves to the target web element then releases the mouse
	 * click.
	 * 
	 * @param sourceWebElement - web element to drag from.
	 * @param targetWebElement - web element to drag to.
	 */
	public void webElementDragAndDrop(WebElement sourceWebElement, WebElement targetWebElement) throws Exception {
		waitUntilWebElementVisible(sourceWebElement);
		waitUntilWebElementVisible(targetWebElement);

		if ((isWebElementDisplayed(sourceWebElement) && isWebElementEnabled(sourceWebElement))
				&& (isWebElementDisplayed(targetWebElement) && isWebElementEnabled(targetWebElement))) {
			Actions actions = new Actions(webDriver);

			actions.dragAndDrop(sourceWebElement, targetWebElement).build().perform();
		}
	}

	/**
	 * Waits for the web element to be visible then selects a value in the given web
	 * element dropdown. Web element dropdown must be a SELECT tag.
	 * 
	 * @param webElement - web element with SELECT tag.
	 * @param strOption  - value, index, visibletext
	 * @param strValue   - value to select.
	 */
	public void webElementDropdownSelect(WebElement webElement, String strOption, String strValue) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			Select select = new Select(webElement);

			if (strOption.equals("value")) {
				select.selectByValue(strValue);
			} else if (strOption.equals("index")) {
				select.selectByIndex(Integer.parseInt(strValue));
			} else if (strOption.equals("visibletext")) {
				select.selectByVisibleText(strValue);
			}
		}
	}

	/**
	 * Waits for the web element to be visible then hovers over the web element.
	 * 
	 * @param webElement - web element to perform the action to.
	 * @implSpec Waits for the web element to be visible.
	 */
	public void webElementMouseHover(WebElement webElement) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			Actions actions = new Actions(webDriver);

			actions.moveToElement(webElement).build().perform();
		}
	}

	/**
	 * Waits for the web element to be visible then right clicks the web element.
	 * 
	 * @param webElement - web element to perform the action to.
	 */
	public void webElementRightClick(WebElement webElement) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			Actions actions = new Actions(webDriver);

			actions.contextClick(webElement).build().perform();
		}
	}

	/**
	 * Waits for the web element to be visible then sends the keys/characters to the
	 * web element.
	 * 
	 * @param webElement - web element to perform the action to.
	 * @param strKeys    - keys/characters to be sent.
	 */
	public void webElementSendKeys(WebElement webElement, String strKeys) throws Exception {
		waitUntilWebElementVisible(webElement);

		if (isWebElementDisplayed(webElement) && isWebElementEnabled(webElement)) {
			// webElement.sendKeys(strKeys);

			for (int i = 0; i < strKeys.length(); i++) {
				webElement.sendKeys(strKeys.substring(i, i + 1));
				// wait(50, false);
			}
		}
	}

	// ==================================================|See|==================================================

	/**
	 * Checks the web element dropdown options.Web element dropdown must be a SELECT
	 * tag.
	 * 
	 * @param webElement         - web element to check. Web element dropdown must
	 *                           be a SELECT tag.
	 * @param strExpectedOptions - array of string expected in the dropdown option.
	 * @return True if the options are displayed, false otherwise.
	 * @implNote Mostly used in Asserts.assertTrue, or in IF ELSE conditions.
	 */
	public boolean checkWebElementDropdownOptions(WebElement webElement, String[] strExpectedOptions) {
		int intOptionsFound = 0;
		Select select = new Select(webElement);
		List<WebElement> options = select.getOptions();

		for (WebElement we : options) {
			for (int i = 0; i < strExpectedOptions.length; i++) {
				if (we.getText().equals(strExpectedOptions[i])) {
				}
			}
		}
		if (intOptionsFound == strExpectedOptions.length) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if the browser alert popup is present.
	 * 
	 * @return True if the options are displayed, false otherwise.
	 * @implNote Mostly used in Asserts.assertTrue, or in IF ELSE conditions.
	 */
	public boolean isAlertPresent() {
		try {
			webDriver.switchTo().alert();

			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	/**
	 * Checks if the web element is displayed or not.
	 * 
	 * @param webElement - web element to check.
	 * @return True if the options are displayed, false otherwise.
	 * @implNote Mostly used in Asserts.assertTrue, or in IF ELSE conditions.
	 */
	public boolean isWebElementDisplayed(WebElement webElement) throws Exception {
		try {
			return webElement.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks if the web element is enabled or not.
	 * 
	 * @param webElement - web element to check.
	 * @return True if the options are displayed, false otherwise.
	 * @implNote Mostly used in Asserts.assertTrue, or in IF ELSE conditions.
	 */
	public boolean isWebElementEnabled(WebElement webElement) throws Exception {
		try {
			return webElement.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks if the web element is focused or not.
	 * 
	 * @param webElement - web element to check.
	 * @return True if the options are displayed, false otherwise.
	 * @implNote Mostly used in Asserts.assertTrue, or in IF ELSE conditions.
	 */
	public boolean isWebElementFocused(WebElement webElement) throws Exception {
		try {
			return webElement.equals(webDriver.switchTo().activeElement());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Checks if the web element is selected/checked or not.
	 * 
	 * @param webElement - web element to check.
	 * @return True if the options are displayed, false otherwise.
	 * @implNote Mostly used in Asserts.assertTrue, or in IF ELSE conditions.
	 */
	public boolean isWebElementSelected(WebElement webElement) throws Exception {
		try {
			return webElement.isSelected();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Gets the current page URL.
	 * 
	 * @return Returns the current page URL.
	 * @implNote Mostly used in Asserts.assertEquals, or use in IF ELSE conditions.
	 */
	public String getCurrentPageUrl() {
		return webDriver.getCurrentUrl();
	}

	/**
	 * Gets the current page title.
	 * 
	 * @return Returns the current page title.
	 * @implNote Mostly used in Asserts.assertEquals, or use in IF ELSE conditions.
	 */
	public String getPageTitle() {
		return webDriver.getTitle();
	}

	/**
	 * Gets the pseudo element value.
	 * 
	 * @param strSelector      - example is "label.MandatoryLabel".
	 * @param strPseudoElement - example is ":after".
	 * @param strCSSProperty   - example is "content".
	 * @return Returns the value of the pseudo element.
	 * @implNote Mostly used in Asserts.assertEquals, or use in IF ELSE conditions.
	 */
	public String getPseudoElementValue(String strSelector, String strPseudoElement, String strCSSProperty) {
		String strScript = "return window.getComputedStyle(document.querySelector('" + strSelector + "'),'"
				+ strPseudoElement + "').getPropertyValue('" + strCSSProperty + "')";
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		String strContent = (String) js.executeScript(strScript);

		return strContent;
	}

	/**
	 * Gets the current date and time and returns it depending on the given format.
	 * 
	 * @param strFormat - the format of the date and time string. Example is
	 *                  "_ddMMyy_HHmm".
	 * @return Returns the formatted current date and time.
	 */
	public String getStringDateTimeNow(String strFormat) {
		String strFormattedDateTimeNow = "";
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strFormat);
		strFormattedDateTimeNow = simpleDateFormat.format(calendar.getTime());

		return strFormattedDateTimeNow;
	}

	/**
	 * Gets the web element attribute value.
	 * 
	 * @param webElement   - web element to check.
	 * @param strAttribute - attribute of the web element to get the value from.
	 * @return Returns the value of the web element attribute.
	 * @implNote Mostly used in Asserts.assertEquals, or use in IF ELSE conditions.
	 */
	public String getWebElementAttributeValue(WebElement webElement, String strAttribute) {
		return webElement.getAttribute(strAttribute).toString();
	}

	/**
	 * Gets the web element CSS value.
	 * 
	 * @param webElement - web element to check.
	 * @param strCSS     - CSS of the web element to get the value from.
	 * @return Returns the value of the web element CSS.
	 * @implNote Mostly used in Asserts.assertEquals, or use in IF ELSE conditions.
	 */
	public String getWebElementCssValue(WebElement webElement, String strCSS) {
		return webElement.getCssValue(strCSS).toString();
	}

	/**
	 * Gets the web element dropdown selected value.
	 * 
	 * @param webElement - web element to check.
	 * @return Returns the selected value of the web element dropdown.
	 * @implNote Mostly used in Asserts.assertEquals, or use in IF ELSE conditions.
	 */
	public String getWebElementDropdownSelectedValue(WebElement webElement) {
		Select select = new Select(webElement);
		WebElement webElementSelectedValue = select.getFirstSelectedOption();

		return webElementSelectedValue.getText();
	}

	/**
	 * Gets the web element text.
	 * 
	 * @param webElement - web element to check.
	 * @return Returns the text of the web element.
	 * @implNote Mostly used in Asserts, or use in IF ELSE conditions.
	 */
	public String getWebElementText(WebElement webElement) {
		return webElement.getText();
	}

	/**
	 * Gets the web element xpath.
	 * 
	 * @param webElement - web element to check.
	 * @return Returns the text of the web element.
	 * @implNote Mostly used in Asserts, or use in IF ELSE conditions.
	 */
	public String getWebElementXPath(WebElement webElement) {
		return webElement.toString().substring(webElement.toString().indexOf("/"),
				webElement.toString().lastIndexOf("'"));
	}

	// ==================================================|Wait|==================================================
	/**
	 * Wait until the time duration runs out.
	 * 
	 * @param intDuration  - number of minutes or milliseconds.
	 * @param blnIsMinutes - flag if duration is in minutes or in milliseconds.
	 * @implNote Mostly used in special conditions. Avoid using at all cost.
	 */
	public void wait(Integer intDuration, Boolean blnIsMinutes) throws Exception {
		if (blnIsMinutes) {
			intDuration = intDuration * 60000;
		}

		Thread.sleep(intDuration);
	}

	/**
	 * Wait until a browser alert popup is present.
	 */
	public void waitUntilAlertPresent() {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.alertIsPresent());
	}

	/**
	 * Wait until text is present in the web element.
	 * 
	 * @param webElement - web element to check.
	 * @param strText    - text to expect in the web element.
	 */
	public void waitUntilTextPresentInWebElement(WebElement webElement, String strText) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.textToBePresentInElement(webElement, strText));
	}

	/**
	 * Wait until web browser title contains the expected string.
	 * 
	 * @param strTitle - string to be expected.
	 */
	public void waitUntilWebBrowserTitleContains(String strTitle) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.titleContains(strTitle));
	}

	/**
	 * Wait until web element attribute value contains the expected value.
	 * 
	 * @param webElement   - web element to check.
	 * @param strAttribute - attribute of the web element to check.
	 * @param strValue     - expected attribute value.
	 */
	public void waitUntilWebElementAttributeValueContains(WebElement webElement, String strAttribute, String strValue) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.attributeContains(webElement, strAttribute, strValue));
	}

	/**
	 * Wait until web element attribute value does not contain the expected value.
	 * 
	 * @param webElement   - web element to check.
	 * @param strAttribute - attribute of the web element to check.
	 * @param strValue     - value to expect not in the attribute.
	 */
	public void waitUntilWebElementAttributeValueDoesNotContains(WebElement webElement, String strAttribute,
			String strValue) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(
				ExpectedConditions.not(ExpectedConditions.attributeContains(webElement, strAttribute, strValue)));
	}

	/**
	 * Wait until web element can be clicked.
	 * 
	 * @param webElement - web element to check.
	 */
	public void waitUntilWebElementClickable(WebElement webElement) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
	}

	/**
	 * Wait until web element is invisible.
	 * 
	 * @param webElement - web element to check.
	 */
	public void waitUntilWebElementInvisible(WebElement webElement) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.invisibilityOf(webElement));
	}

	/**
	 * Wait until web element is not existing in the page.
	 * 
	 * @param strWebElementXPath - web element XPath to check.
	 */
	public void waitUntilWebElementXPathNotExisting(String strWebElementXPath) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(strWebElementXPath)));
	}

	/**
	 * Wait until web element is selected or not.
	 * 
	 * @param webElement    - web element to check.
	 * @param blnIsSelected - selection state of the web element.
	 */
	public void waitUntilWebElementSelectionStateToBe(WebElement webElement, Boolean blnIsSelected) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.elementSelectionStateToBe(webElement, blnIsSelected));
	}

	/**
	 * Wait until web element is visible.
	 * 
	 * @param webElement - web element to check.
	 */
	public void waitUntilWebElementVisible(WebElement webElement) {
		WebDriverWait webDriverWait = new WebDriverWait(webDriver,
				Long.parseLong(Base.configurationVariables("waitTimeout")));

		webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
	}

	// ==================================================|FileManagement|==================================================

	/**
	 * Create or replace a single cell data of the Excel file.
	 * 
	 * @param strFilePath     - Excel file path.
	 * @param intSheetNumber  - Excel sheet number starts from 0.
	 * @param intRowNumber    - Excel row number starts from 0.
	 * @param intColumnNumber - Excel cell number starts from 0.
	 */
	public synchronized void setExcelCellData(String strFilePath, int intSheetNumber, int intRowNumber,
			int intColumnNumber, String strValue) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(strFilePath);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workBook.getSheetAt(intSheetNumber);
		Row row = sheet.getRow(intRowNumber);

		if (row == null)
			row = sheet.createRow(intRowNumber);

		if (row.getCell(intColumnNumber) != null)
			row.getCell(intColumnNumber);
		else
			row.createCell(intColumnNumber);

		sheet.getRow(intRowNumber).getCell(intColumnNumber).setCellValue(strValue);

		FileOutputStream fileOutputStream = new FileOutputStream(strFilePath);

		workBook.write(fileOutputStream);
		workBook.close();
		fileInputStream.close();
		fileOutputStream.close();
	}

	/**
	 * Get single cell data from Excel file and return the value as String.
	 * 
	 * @param strFilePath     - Excel file path.
	 * @param intSheetNumber  - Excel sheet number starts from 0.
	 * @param intRowNumber    - Excel row number starts from 0.
	 * @param intColumnNumber - Excel cell number starts from 0.
	 * @return Returns a string output.
	 */
	public synchronized String getExcelCellData(String strFilePath, int intSheetNumber, int intRowNumber,
			int intColumnNumber) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(strFilePath);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workBook.getSheetAt(intSheetNumber);
		Row row = sheet.getRow(intRowNumber);
		Cell cell = row.getCell(intColumnNumber);
		DataFormatter dataFormatter = new DataFormatter();
		String strCellData = dataFormatter.formatCellValue(cell);

		workBook.close();
		fileInputStream.close();

		return strCellData;
	}

	/**
	 * Get all data from the specified row in the Excel file and return a array of
	 * String.
	 * 
	 * @param strFilePath    - Excel file path.
	 * @param intSheetNumber - Excel sheet number starts from 0.
	 * @param intRowNumber   - Excel row number starts from 0.
	 * @return Returns a array list of String.
	 */
	public synchronized ArrayList<String> getExcelRowData(String strFilePath, int intSheetNumber, int intRowNumber)
			throws Exception {
		FileInputStream fileInputStream = new FileInputStream(strFilePath);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workBook.getSheetAt(intSheetNumber);
		Row row = sheet.getRow(intRowNumber);
		ArrayList<String> arrayListStrRowData = new ArrayList<String>();

		for (Cell cell : row) {
			DataFormatter dataFormatter = new DataFormatter();
			String strCellData = dataFormatter.formatCellValue(cell);

			arrayListStrRowData.add(strCellData);
		}

		workBook.close();
		fileInputStream.close();

		return arrayListStrRowData;
	}

	/**
	 * Get all data from the specified excel sheet and return a list of array
	 * string.
	 * 
	 * @param strFilePath    - Excel file path.
	 * @param intSheetNumber - Excel sheet number starts from 0.
	 * @return Returns a array list of String.
	 */
	public synchronized List<ArrayList<String>> getExcelSheetData(String strFilePath, int intSheetNumber)
			throws Exception {
		DataFormatter dataFormatter = new DataFormatter();
		FileInputStream fileInputStream = new FileInputStream(strFilePath);
		XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);

		Sheet sheet = workBook.getSheetAt(intSheetNumber);
		Row row = null;
		Cell cell = null;

		ArrayList<String> tempArray = new ArrayList<String>();
		List<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();

		for (int y = 0; y <= sheet.getLastRowNum(); y++) {
			row = sheet.getRow(y);
			for (int x = 0; x < row.getLastCellNum(); x++) {
				cell = row.getCell(x);
				tempArray.add(dataFormatter.formatCellValue(cell));
			}
			excelData.add(tempArray);
			tempArray = new ArrayList<String>();
		}

		workBook.close();
		fileInputStream.close();

		return excelData;
	}

	/**
	 * Create a copy of a file. 
	 * Sample usage: Creating a copy of test data before modifying it to preserve the base test data.
	 * 
	 * @param fileFrom - Path of the file to copy.
	 * @param fileTo   - Path of the copied file including the new file name.
	 */
	public synchronized void fileCopy(String fileFrom, String fileTo) throws IOException {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(fileFrom);
			fos = new FileOutputStream(fileTo);
			int c;
			while ((c = fis.read()) != -1) {
				fos.write(c);
			}
		} finally {
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}
	}

	/**
	 * Check if the file exist.
	 * Sample usage: Validating downloaded file or finding if any file exist in the given path.
	 * 
	 * @param path     - Path of the directory to look for.
	 * @param fileName - Filename including the file extension to look for.
	 * @return Returns true or false
	 */
	public synchronized boolean fileExists(String path, String fileName) {
		File dir = new File(path);
		WebDriverWait wait = new WebDriverWait(webDriver, Long.parseLong(Base.configurationVariables("waitTimeout")));
		return wait.until(arg0 -> {
			File[] filesInDir = dir.listFiles();
			for (File fileInDir : filesInDir) {
				if (fileInDir.getName().equals(fileName)) {
					return true;
				}
			}
			return false;
		});
	}

	/**
	 * Delete a file using the given full file path
	 * 
	 * @param filePath - Path of the file to delete.
	 * @return Returns true if the file is successfully deleted, and false if not
	 */
	public synchronized boolean fileDelete(String filePath) {
		File fileToDelete = new File(filePath);
		try {
			FileUtils.forceDelete(fileToDelete);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}