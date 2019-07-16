<img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/logo.png?raw=true" alt="React Native Starter Kit" width="200" />

# Stumarkt, BahriMedia 
### By Melih Bahri AKTAN

Second-hand market for students Android App with Firebase.

Download Anroid App from Drive: [Stumarkt](https://drive.google.com/open?id=1wlBplp0FMsA1DU7mkbcvi-3YK1hb8Cty)

### The under construction!
### Documentation coming soon!

### Feature  
1. Signin, signout, register.

Register: allow user register a new account with username and password
Signin: user must signin with username and password to use app.
Edit profile: change avatar image.

2. Home Fragment Recycler View 

3. Category of posts from Firebase database

4. New Post

Three image with crop feature
Post title
Post Desc
Product Price
Category Spinner
User Name, user email
Image Slider

5. Post Detail

6. Chat, Users

send text message.

Image Slider Feature (Library)


<img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/Screenshot_2019-07-13-12-21-53.png?raw=true" width="250"/> <img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/Screenshot_2019-07-13-12-21-50.png?raw=true" width="250"/> <img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/Screenshot_2019-07-13-12-15-34.png?raw=true" width="250"/> 

<img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/Screenshot_2019-07-13-12-15-27.png?raw=true" width="250"/> <img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/Screenshot_2019-07-13-12-15-30.png?raw=true" width="250"/> <img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/Screenshot_2019-07-13-12-15-40.png?raw=true" width="250"/>

<img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/Screenshot_2019-07-13-12-15-45.png?raw=true" width="250"/> <img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/Screenshot_2019-07-13-12-15-59.png?raw=true" width="250"/> <img src="https://github.com/melihbahri/BahriMedia/blob/master/screenshots/Screenshot_2019-07-13-12-16-21.png?raw=true" width="250"/>



### Used `Firebase` documentation coming soon!

### Used Things:
- Min API: 21, 16
- Target&Compile API: 28
- Firebase
- Firebase Database
- Firebase Storage
- Firebase Authentication
- Firebase Analytics
- Firebase UI Database

### Thanks

  * [Firebase](https://github.com/firebase/quickstart-android)
  * [Picasso](https://github.com/square/picasso)
  * [Android-Image-Cropper](https://github.com/ArthurHub/Android-Image-Cropper)
  * [CircleImageView](https://github.com/hdodenhof/CircleImageView)
  * [Banner-Slider](https://github.com/saeedsh92/Banner-Slider)
  * [Android-Image-Slider](https://github.com/smarteist/Android-Image-Slider)

Gradle(App)
-----
```
dependencies 
{
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    implementation 'com.android.support:appcompat-v7:27.1.1'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    implementation 'com.android.support:design:27.1.1'
    implementation 'com.android.support:support-vector-drawable:27.1.1'
    implementation 'com.android.support:support-v4:27.1.1'
    implementation 'com.google.android.gms:play-services-ads:12.0.1'
    implementation 'com.google.firebase:firebase-messaging:12.0.1'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
    implementation 'com.firebase:firebase-client-android:2.5.0'
    implementation 'com.google.firebase:firebase-core:16.0.9'
    implementation 'com.google.firebase:firebase-auth:17.0.0'
    implementation 'com.android.support:recyclerview-v7:27.1.1'
    implementation 'com.android.support:cardview-v7:27.1.1'
    implementation 'com.google.firebase:firebase-database:17.0.0'
    implementation 'com.google.firebase:firebase-storage:17.0.0'
    implementation 'com.google.firebase:firebase-firestore:19.0.2'
    implementation 'com.firebaseui:firebase-ui-database:0.4.0'
    implementation 'com.squareup.picasso:picasso:2.71828'
    implementation 'de.hdodenhof:circleimageview:2.2.0'
    implementation 'com.theartofdev.edmodo:android-image-cropper:2.6.0'
    implementation 'com.github.bumptech.glide:glide:4.7.1'
    implementation 'com.theartofdev.edmodo:android-image-cropper:2.7.+'
    implementation 'com.ss.bannerslider:bannerslider:2.0.0'
    implementation 'com.github.smarteist:autoimageslider:1.3.2'
    implementation 'com.google.android.gms:play-services-auth:11.8.0'
    implementation 'com.uniquestudio:parsingplayer:2.0.6'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.6.1'
}
```
