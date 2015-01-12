Example of using the Facebook SDK to authenticate on Android. We use activities to
authenticate, not fragments as shown in Facebook's documentation.

### To get it working
 - fillout your Facebook API key in app/src/main/res/values/apikeys.xml
 - provide a debug and release keystore in keystore/ named debug.keystore and release.keystore
 - provide the keystore credentials in gradle.properties

### Remember
Make sure to put your key hashes on the facebook app console.
