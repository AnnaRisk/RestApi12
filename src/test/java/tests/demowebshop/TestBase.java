package tests.demowebshop;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.AllureAttachments;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import owner.AppConfig;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.restassured.RestAssured.sessionId;

public class TestBase {

    @BeforeAll
    static void setUp() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        AppConfig links = ConfigFactory
                .create(AppConfig.class);
        Configuration.baseUrl = links.webUrl();
        RestAssured.baseURI = links.apiUrl();

        AppConfig remoteConfig = ConfigFactory
                .create(AppConfig.class);
        String remoteLogin = remoteConfig.selenoidLogin();
        String remotePassword = remoteConfig.selenoidPassword();
        String remoteUrl = remoteConfig.remoteUrl();
        Configuration.remote = "https://" + remoteLogin + ":" + remotePassword + "@" + remoteUrl;

        String remoteBrowser = System.getProperty
                ("browser", remoteConfig.remoteBrowser());
        String remoteBrowserVersion = System.getProperty
                ("browserVersion", remoteConfig.remoteBrowserVersion());
        String remoteBrowserSize = System.getProperty
                ("browserSize", remoteConfig.remoteBrowserSize());
        Configuration.browser = remoteBrowser;
        Configuration.browserVersion = remoteBrowserVersion;
        Configuration.browserSize = remoteBrowserSize;


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void afterEach() {
        AllureAttachments.addScreenshotAs("Screen");
        AllureAttachments.addPageSource();
        AllureAttachments.addBrowserConsoleLogs();
        AllureAttachments.addVideo(sessionId);
        closeWebDriver();
    }
}
