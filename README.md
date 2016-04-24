[![Build Status](https://travis-ci.org/BuieConnect/BuieConnect-Android.svg?branch=master)](https://travis-ci.org/BuieConnect/BuieConnect-Android)

Android app for Buie Connect


## Build Requirements
### Variables:
#### App level build.gradle
   - **SERVER_CLIENT_ID** = \<google sign in client id\>
   - **FIREBASE_URL** = \<Firebase app url\>
   - **APP_SERVER_URL** = \<App server url with api path\> (e.g. http://buieconnect.arkanayan.me/api/v1/ )

```java
android {
    resValue "string", "server_client_id", SERVER_CLIENT_ID
    resValue "string", "firebase_url", FIREBASE_URL
    resValue "string", "app_server_url", APP_SERVER_URL
}
```


### Files:
- google-services.json ( in app root ) [Get file google-services.json](https://developers.google.com/mobile/add)
    
    