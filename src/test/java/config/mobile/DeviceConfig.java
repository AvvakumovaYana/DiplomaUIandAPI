package config.mobile;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.FIRST)
@Config.Sources({"classpath:mobile/${device}.properties"})
public interface DeviceConfig extends Config {
    String username();
    String password();

    String url();
    String platformName();
    String platformVersion();
    String deviceName();
    String automationName();
    String app();
    String projectName();
    String buildName();
    String name();
    String appWaitPackage();
    String appWaitActivity();
}
