# Movie bookmarking app

This project is a simple movie browsing and bookmarking application, featuring local bookmarking, searching and browsing through a small dataset of popular movies. It is built with XML views, 
fragments and data-binding for UI, Room database for locally saving data, Retrofit and OkHttp for networking, and Koin for managing dependency injections. The app architecture 
follows [Clean Architecture principles](https://developer.android.com/topic/architecture) and MVVM pattern. You can see functionality overview here: https://www.loom.com/share/9b6c9a18485b4344be3b7ea6645fb38b?sid=08c5a2ee-5ee6-41ae-918b-bb9b3e71aa26


## App structure

This project contains three modules that follow the classic Clean Architecture approach. For simplicity, all the modules are defined as packages under umbrella `:app` module, but for larger projects 
I'd separate them to three full-fledged modules.

- `domain` - corresponds to [Domain layer of Clean Architecture](https://developer.android.com/topic/architecture#domain-layer), contains Room models, repository interfaces, use cases and helper/base classes (namely `BaseUseCase` class used for Koin injections)
- `data` - corresponds to [Data layer of Clean Architecture](https://developer.android.com/topic/architecture#data-layer), contains database configuration, repository implementations, related database DAOs for saving user data locally and API configurations (with appropriate converters and DTOs)
- `presentation` - corresponds to [UI layer of Clean Architecture](https://developer.android.com/topic/architecture#ui-layer), contains various UI implementations and fragments, with each fragment package consisting of its UI implementations, related ViewModel and appropriate states and events

Other than these packages, there is a `di` package, which contains all definitions and structure for Koin dependency injection framework, and a `utils` package with various validators and formatters for 
application data.


## UI

As stated above, UI was built with XML views and fragments, using data-binding library to connect UI components from XML views to appropriate data source. There are four separate fragment packages in 
the application, and each consists of the following: 

- `Fragment` - contains bindings to UI elements from XML, that connect to corresponding data from ViewModel state
- `ViewModel` - contains state and appropriate methods for events. State is implemented with LiveData, and there is an object called `Effect`, which serves for triggering mostly navigational actions between fragments.
- `Contract` - contains definitions for State and related Effects. This is partially borrowed from MVI architecture, as Effects provide a convenient method of triggering navigation events between fragments once an action is completed. There are also appropriate converters for models, so that it's easier to display data in the UI.

All concrete fragments inherit from a base implementation from the `base` package, which contains boilerplate code for defining fragment, viewmodel and contract logic. There are four concrete implementations: 

- `signup` - contains classic functionalities for sign up, with data validators and user registering logic.
- `home` - serves as a dashboard, where users can view their bookmarked movies, simple profile information and a list of movies picked by the staff, and also bookmark any interesting movie.
- `details` - opens as a modal dialog (that cannot be collapsed), whenever user taps on any of the movies from any list, and displays more details about the movie.
- `search` - contains a classic search function, implemented locally, that displays a list of movies where the search query is contained within the movie title.

### Navigation

Navigation is built with `androidx-navigation-fragment`. Navigation paths are modelled in `navigation > movie_nav_graph.xml`, and the resulting actions can be used to easily navigate from one fragment to 
another. Navigation actions are implemented in `Fragment` classes of concrete fragments.


## Domain

Domain package contains all the business logic of application. 

Persistent bookmarking (ability to reflect bookmarking changes across all screens) was implemented using Room database by saving relation data between user and bookmarked movie. That data is then used to 
compare against API response, and if there are any matches, it will reflect in the UI (via the bookmark button). 

There are two Room entities, `Movie` and `User`, and one cross-reference table that houses user favorites. User can have multiple favorited movies, but a movie can also be favorited by another users, so we 
have a many-to-many relationship. In this particular instance, we could've also modelled it as a one-to-many relationship since there is no interaction with other app users, but it's modelled as a standard 
many-to-many relationship in case I decide to build further upon it. There are also two relation tables that model `UserWithMovies` and `MovieWithUsers` relationships defined by the cross-reference table, and 
currently only `UserWithMovies` is used.

Other than the database, there are also repository interfaces and use case interfaces with appropriate implementations. Those implementations would also benefit from separating into different file 
under `data` package, but since it's a simple application, I've decided to keep them in the same file as their interfaces. Both are used to model business logic in application, including registering user, 
checking active user, checking user favorites and adding new favorites to user's list. User authentication is very basic, and it's implemented with `SharedPreferences` under `PreferencesRepository` - it checks 
if user email (and name) is present in it, and then that information is used to verify user is "logged in". User password is also saved in the local database, which would definitely not be the case in a 
real-world app, but if I had to save password data locally, I would use something like `EncryptedSharedPreferences` to store it (although that also doesn't guarantee sensitive data security!).


## Data

Data package contains concrete implementations from domain, which means it houses database implementation, API calls with Retrofit, data converters for API results and repository implementations. For 
database operations there is `UserDao` class that contains some basic methods needed for implementing app functionalities - inserting user in the database, getting user data, getting user favorites and 
updating user favorites. API results are handled with Retrofit and modelled with `MovieDto` class. Due to this class containing some complex data structures for properties, some converters were needed for 
successfully saving data in the local database (namely, lists of strings). There are also some converters that help convert data results to a form that matches the Room models. API is hardcoded as strings 
under `NetworkContract` class, and in real-world scenario I would use something like `BuildConfig`, and define those strings within my build.gradle file for the data module.



## Final remarks

Where I've saved time includes authentication, since this is a pretty simple app, I wanted to employ a very simple way to authenticate the user. If the user registration is successful, some data is saved under
SharedPreferences, and then on each screen these entries are checked to verify the user is active. This also repeats across all screens that are available after user sign up, so what I'd do, I'd just fetch a
token from the backend that would usually handle authentication logic, and then save the token in SharedPreferences (or DataStore). I'd reduce boilerplate code for checking the token by extracting it into 
another use case.

Error handling is also simple - if user is not verified (there is no value under corresponding fields in SharedPreferences), they are navigated back to sign up screen. If they try to sign up with an email that
already exists in the local database, then the behaviour is similar to logging in, as the insert method will just replace values on conflict strategy.

For UI, there are some final touches that I've not added, namely some specific styling on modal dialog (blurred header when scrolling down). I've also not added the "See all" button to the favorites section on the 
home screen, as there is no design provided for displaying a list of all favorited movies. If it were up to me, I'd implement it the same way search screen is implemented, albeit without the search field, and have
the "See more" button navigate to that screen. I'd also limit the amount of visible items as per design. Mostly all list structures in UI are implemented with a recycler view.
