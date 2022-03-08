package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Page_Login {
	WebDriver webDriver;

	public Page_Login(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	// ==================================================|Page_Objects|==================================================
	@FindBy(xpath = "//div[text() = 'Login']/parent::button")
	public WebElement button_Login;

	@FindBy(xpath = "//input[@id = 'Input_PasswordVal']")
	public WebElement input_Password;

	@FindBy(xpath = "//input[@id = 'Input_UsernameVal']")
	public WebElement input_Username;
	
	@FindBy(xpath = "//div[@class = 'feedback-message-text']")
	public WebElement label_FeedbackMessage;
}