# ChitChat

Small forum application.

## Project Tools and requirements

+ __AppIntros Library__

    Briefly introduce the user to the application, Will only show once.
     

+ __WaveSwipeRefreshLayout__
    
    Alternative to the native(default) SwipeRefreshLayout
    
+ __Firebase__
    The data in the application is stored on Firebase and will be fetched on App start and stored in SharedPreferences for offline access, of course also an Internal database can be used or a file system of which the app only has access to.
    

### Retrieving data from Firebase

We will need an instance of the database which is initialized in the onCreate method, that is, when the application is first created.



