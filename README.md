# Botan - Java SDK
[![Build Status](https://travis-ci.org/shibme/botan.svg)](https://travis-ci.org/shibme/botan)
[![Dependency Status](https://www.versioneye.com/user/projects/56c60bb918b271002c69b073/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56c60bb918b271002c69b073)
[![Download](https://api.bintray.com/packages/shibme/maven/botan/images/download.svg)](https://bintray.com/shibme/maven/botan/_latestVersion)
[![Percentage of issues still open](http://isitmaintained.com/badge/open/shibme/botan.svg)](http://isitmaintained.com/project/shibme/botan "Percentage of issues still open")

Java SDK for [Botan analytics](http://botan.io)

### How to?
Add to your `pom.xml`
```xml
<dependency>
	<groupId>me.shib.java.lib</groupId>
	<artifactId>botan</artifactId>
	<version>1.0</version>
</dependency>
```

Below is a sample java code containing methods written with the Botan SDK
```java
import me.shib.java.lib.botan.Botan;
import java.io.IOException;
import java.util.Map;

public class TestBotan {
    private static final String botanToken = "YourBotanTokenGoesHere";
    private Botan botan;

    public TestBotan() {
        botan = new Botan(botanToken);
    }

    //Tracks any sent or received messages
    public void trackMessage(long user_id, String messageType, Message message) {
        try {
            botan.track(user_id, messageType, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Tracks any received update
    public void trackUpdate(long user_id, Update update) {
        try {
            botan.track(user_id, "update", update);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Tracks anything else, even a custom map
    public void trackAnyOtherMap(long user_id, String nameOrContext, Map<String, Object> xyzMap) {
        try {
            botan.track(user_id, nameOrContext, xyzMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Shorten a URL and also track any visits to the shortened URL
    public void shortenURL(long user_id, String url) {
        try {
            System.out.println(botan.shortenURL(user_id, url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```