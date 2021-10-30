package com.example.echo;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        //Register parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("801k2jvUlNkfWH9hBfAp9MCKEAJDiJMqK4nY3wbf")
                .clientKey("ZM2CX5H1Sl9J0qpKasFzSv4pTF8QYXQUj85EfEfW")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}
