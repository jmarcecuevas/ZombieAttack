# ZombieAttack

##What is it?
**ZombieAttack** is a not finished game project. It's a game designed to allow you to communicate with nearby people within a radius defined for
you in the Settings screen. So, you can say It's a based user location game. 
You can see your real location and the real nearby user's location on an interactive map. So, if you device position or the nearby users position
changes, you will see the "avatars" -which represents the players- moving across the screen.
The idea, behind this, is that you can easily find people who are insterested in to play the game and challenges them to a duel.

##Getting Started
Well, when you open the application you need to sign up, for this, you have two options:
* Sign up with GOOGLE: You must choose a Google account and then you need to provide a valid nickname(existing nicknames or very short nicknames
are not allowed). The application will be in charge to validate the nickname entered. In case you enter an invalid nickname,you will be notified and 
you must enter a new nickname.
* Sign up with a LuckyCode account: If you don't have a LuckyCode account, you must sign up. You will need to provide a name, a nickname, an email and a
password. As in the Google case, the application validates each one of the fields to don't allow invalid data.

##Requisites:
* Android 4.1 JELLY BEAN (API level 16) or higher.
* An Internet connection and GPS signal(You will notified through a SnackBar , in case the application does not find GPS Signal or a Internet connection available)

##How was it done with?
To develop this application I have used:
* **Mapbox Android SDK.**
* **MVP(Model View Presenter)** pattern. 
* **Observer pattern.**
* **Singleton pattern.**
* **Dagger2** to inject dependencies.
* **ButterKnife**: to inject views.
* **Retrofit**: to HTTP requests.
* **MediaPlayer**: to play sounds.
* **MySQL**: I chose this RDBMS to store user information like name,nickname,password and location.
* **PHP**: It was used to create the WebServices to connect the MySql database with the Android application.
* **JSON**: as object notation to transfer data between my PHP WebService and the Android application.
* **GSON**: to converts JSON objects in JAVA objects.
* **BroadCastReceiver**: I used two BroadCastReceiver, one for notify internet connection changes and the other one for inform about GPS signal changes.
* **TextWatcher**
* **SharedPreferences** to store user preferences.
* **ScaleGestureDetector** to zoom and tilt the map simultaneously when the user pinchs to zoom as by default Mapbox only zooms or tilts the map, not both at the same time.
* **Custom Dialogs** : I created a class named "CustomDialog" which extends Dialog to personalize dialogs.
* **Fade in,Fade out,Zoom in and Zoom out** as animations to the activities transitions.
* **Two languages available**: The application is in English and Spanish.





