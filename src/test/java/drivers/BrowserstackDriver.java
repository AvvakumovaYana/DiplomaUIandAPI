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

public class BrowserstackDriver implements WebDriverProvider {
    private static final DeviceConfig deviceConfig = ConfigFactory.create(DeviceConfig.class);

    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        MutableCapabilities caps = new MutableCapabilities();

        caps.setCapability("browserstack.user", deviceConfig.username());
        caps.setCapability("browserstack.key", deviceConfig.password());

        caps.setCapability("app", deviceConfig.app());

        caps.setCapability("device", deviceConfig.deviceName());
        caps.setCapability("os_version", deviceConfig.platformVersion());

        caps.setCapability("project", deviceConfig.projectName());
        caps.setCapability("build", deviceConfig.buildName());
        caps.setCapability("name", deviceConfig.name());
        caps.setCapability("url", deviceConfig.url());

        System.out.println("APP: " + deviceConfig.app());

        try {
            return new RemoteWebDriver(new URL(deviceConfig.url()), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
