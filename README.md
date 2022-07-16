# SpaceXClient

SpaceXClient is an application using MVVM architecture. 

## Installation
Clone this repository and import into Android Studio

git clone git@https://github.com/tarikmedjber/SpaceXClient.git

And click run!

## The app
In the app you are able to retrieve SpaceX company info, a list of launches and the functionality
to filter the launches list ascending or descending based on the launch date or whether or not the 
launch was successful. You are also able to click on each Launch to view and choose 3 web links.

## Libraries Used
- Coroutines Library.
- Kotlin coroutine flows.
- Koin dependency injection.
- Retrofit to connect with web based APIs.
- Gson for json conversions.
- Picasso for image retrieval via url.
- Espresso Android UI Testing framework.
- MockK framework for unit testing.

## Project structure
- DI package
- Engine package -> models, network and repositories
- UI -> Fragments and ViewModels
- Utils -> Date and Currency formatting

## API
API used https://api.spacexdata.com/v3/.
