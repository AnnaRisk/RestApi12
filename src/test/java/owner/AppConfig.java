package owner;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/demowebshop/app.properties"
})
public interface AppConfig extends Config {

    String webUrl();
    String apiUrl();
    String selenoidLogin();

    String selenoidPassword();

    String remoteUrl();

    String remoteBrowser();

    String remoteBrowserVersion();

    String remoteBrowserSize();
    String userLogin();

    String userPassword();

    String authCookie();

}
