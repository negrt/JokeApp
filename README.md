# JokeApp
A mobile application for Android OS that calls a web service, using AsyncTask, and displays a joke. The web service is called when the button in the activity is pressed.

# What I learned
* Call a webservice
* Parse JSON strings
* Open a URL connection
* Load data using AsyncTask
* Add MutableLiveData members to ViewModel
* Add special get/set methods to help observe data
* Use onPostExecute to set the values of the ViewModel

# Description and Image
When the app starts the screen will just have a button with Call Web Service text. When the button is clicked the web service returns a JSON string. The full JSON string is displayed. The string is parsed and only the joke is displayed below it.

## Image
![Image of Joke App](https://github.com/negrt/cv/blob/master/images/JokeApp.png?raw=true)
