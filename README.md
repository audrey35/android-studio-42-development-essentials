# How to Build a Simple Voice Typer App in Android using Java? by Geeks for Geeks
## Demo of registerForActivityResult: Getting a Result the AndroidX Way
Now that you have learned how to startActivityForResult(), take a look at the AndroidX class that wraps around this to help you manage results.  
Whichever approach you use, keep in mind that if the device is low on memory, your Activity might be destroyed after you create a sub-Activity but before your result callback is called, and then your Activity would be re-created.  
Keep a stateless mentality when writing the result callback.
- [Android official documentation](https://developer.android.com/training/basics/intents/result) explaining how to register for activity result rather than simply using startActivityForResult
- Demo of how to use registerForActivityResult: [Voice Typer App - Geeks for Geeks](https://www.geeksforgeeks.org/how-to-build-a-simple-voice-typer-app-in-android-using-java/)
- Another demo of how to use registerForActivityResult: [PDF Picker - Geeks for Geeks](https://www.geeksforgeeks.org/how-to-implement-pdf-picker-in-android/)
