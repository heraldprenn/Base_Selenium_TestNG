package stepDefinitions;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.Pages;
import utilities.Base;
import utilities.Keywords;

public class StepDefinitions {
	Keywords keywords = new Keywords(Base.getWebDriver());
	Pages pages = new Pages(Base.getWebDriver());

	// ==================================================|Given|==================================================
	@Given("the user is on the login page")
	public void the_user_is_on_the_login_page() throws Throwable {
		try {
			goToLoginPage();

			Assert.assertEquals(Base.dataVariables("pageTitleLogin"), keywords.getPageTitle());
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageLogin().input_Username));
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageLogin().input_Password));
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageLogin().button_Login));
		} catch (AssertionError assertionError) {
			throw new Exception(assertionError.getMessage());
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	@Given("the user is logged in")
	public void the_user_is_logged_in() throws Throwable {
		try {
			goToLoginPage();
			loginWithCredentials(Base.dataVariables("username"), Base.dataVariables("password"));

			keywords.waitUntilWebElementVisible(pages.getPageGeneral().label_Username);
			keywords.waitUntilWebElementVisible(pages.getPageGeneral().button_Logout);

			Assert.assertEquals(Base.dataVariables("pageTitleDashboard"), keywords.getPageTitle());
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageGeneral().label_Username));
			Assert.assertEquals(Base.dataVariables("user"),
					keywords.getWebElementText(pages.getPageGeneral().label_Username));
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageGeneral().button_Logout));
		} catch (AssertionError assertionError) {
			throw new Exception(assertionError.getMessage());
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	// ==================================================|When|==================================================
	@When("the user logs in with the credentials (.*) and (.*)$")
	public void the_user_logs_in_with_the_credentials_username_and_password(String strUsername, String strPassword)
			throws Throwable {
		try {
			loginWithCredentials(strUsername, strPassword);
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	@When("the user logs out")
	public void the_user_logs_out() throws Throwable {
		try {
			keywords.webElementClick(pages.getPageGeneral().button_Logout);
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	// ==================================================|Then|==================================================
	@Then("^the user (.*) will be logged in successfully$")
	public void the_user_will_be_logged_in_successfully(String strUser) throws Throwable {
		try {
			keywords.waitUntilWebElementVisible(pages.getPageGeneral().label_Username);
			keywords.waitUntilWebElementVisible(pages.getPageGeneral().button_Logout);

			Assert.assertEquals(Base.dataVariables("pageTitleDashboard"), keywords.getPageTitle());
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageGeneral().label_Username));
			Assert.assertEquals(strUser, keywords.getWebElementText(pages.getPageGeneral().label_Username));
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageGeneral().button_Logout));
		} catch (AssertionError assertionError) {
			throw new Exception(assertionError.getMessage());
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	@Then("the user will be back on the login page")
	public void the_user_will_be_back_on_the_login_page() throws Throwable {
		try {
			keywords.waitUntilWebElementVisible(pages.getPageLogin().input_Username);
			keywords.waitUntilWebElementVisible(pages.getPageLogin().input_Password);
			keywords.waitUntilWebElementVisible(pages.getPageLogin().button_Login);

			Assert.assertEquals(Base.dataVariables("pageTitleLogin"), keywords.getPageTitle());
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageLogin().input_Username));
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageLogin().input_Password));
			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageLogin().button_Login));
		} catch (AssertionError assertionError) {
			throw new Exception(assertionError.getMessage());
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	@Then("the feedback message (.*) will be displayed$")
	public void the_feedback_message_will_be_displayed(String strFeedbackMessage) throws Throwable {
		try {
			strFeedbackMessage = strFeedbackMessage.replace("\"", "");

			keywords.waitUntilWebElementVisible(pages.getPageLogin().label_FeedbackMessage);

			Assert.assertTrue(keywords.isWebElementDisplayed(pages.getPageLogin().label_FeedbackMessage));
			Assert.assertEquals(strFeedbackMessage,
					keywords.getWebElementText(pages.getPageLogin().label_FeedbackMessage));
		} catch (AssertionError assertionError) {
			throw new Exception(assertionError.getMessage());
		} catch (Exception exception) {
			throw new Exception(exception.getMessage());
		}
	}

	// ==================================================|Reusable_Functions|==================================================
	private void goToLoginPage() throws Throwable {
		keywords.navigateToUrl(Base.dataVariables("url"));
		keywords.waitUntilWebElementVisible(pages.getPageLogin().input_Username);
		keywords.waitUntilWebElementVisible(pages.getPageLogin().input_Password);
		keywords.waitUntilWebElementVisible(pages.getPageLogin().button_Login);
	}

	private void loginWithCredentials(String strUsername, String strPassword) throws Throwable {
		if (keywords.getPageTitle().equals(Base.dataVariables("pageTitleLogin"))) {
			keywords.webElementClear(pages.getPageLogin().input_Username);
			keywords.webElementSendKeys(pages.getPageLogin().input_Username, strUsername);
			keywords.webElementClear(pages.getPageLogin().input_Password);
			keywords.webElementSendKeys(pages.getPageLogin().input_Password, strPassword);
			keywords.webElementClick(pages.getPageLogin().button_Login);
		}
	}
}