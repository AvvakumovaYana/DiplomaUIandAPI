package drivers;

import com.codeborne.selenide.WebDriverProvider;
import config.mobile.DeviceConfig;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class BrowserstackDriver implements WebDriverProvider {
    private static final DeviceConfig deviceConfig = ConfigFactory.create(DeviceConfig.class);

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        MutableCapabilities caps = new MutableCapabilities();

        HashMap<String, Object> bstackOptions = new HashMap<>();
        bstackOptions.put("userName", deviceConfig.username());
        bstackOptions.put("accessKey", deviceConfig.password());
        bstackOptions.put("projectName", deviceConfig.projectName());
        bstackOptions.put("buildName", deviceConfig.buildName());
        bstackOptions.put("sessionName", deviceConfig.name());
        caps.setCapability("bstack:options", bstackOptions);

        caps.setCapability("appium:app", deviceConfig.app());

        caps.setCapability("appium:deviceName", deviceConfig.deviceName());
        caps.setCapability("appium:platformVersion", deviceConfig.platformVersion());
        caps.setCapability("appium:adbExecTimeout", 120000);
        //caps.setCapability("url", deviceConfig.url());

        System.out.println("APP: " + deviceConfig.app());

        try {
            return new RemoteWebDriver(new URL(deviceConfig.url()), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
