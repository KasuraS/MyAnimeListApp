# MyAnimeListApp

An Android application using API Jikan (used by MyAnimeList plateform) that has +10K animes & mangas.

## 0 - Pre-requisites

I used Android Studio 3.6.1 (I highly recommand you to update the latest version and SDK tools are up-to-date too). 
You may need an Android phone that supports API Level 26 or higher for testing, but you can also use the emulator of Android Studio 
if you have a good PC.

## 1 - How to launch the app

- Open project file
- Select the application from the run configurations
- Select the target device (either run the emulator or use your own phone)
- For virtual devices, you can choose a compatible version of Google Pixel or Samsung Galaxy
- For physical devices, if the detection isn't working, go find your Android Build number and enable Developer options 
(it all depends on your phone model, I suggest you enter the keywords "enable developer options" + your phone model in the browser)
- Build and run the app
- Wait for the launch (it's normal if it takes a couple of minutes for your first build)

## 2 - Core functionalities

- Authentification of a user registered in MAL plateform
- Check one's own anime & manga lists
- Search by anime or manga
- Get details of a selected item
- Save user session during the first login
- Restore user session when leaving the application
