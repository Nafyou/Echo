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
                .applicationId("LtSQuZAIFajCjJpgK73GTHh6eJIZCj9Qs1CPdKoa")
                .clientKey("9e1WTf3qTjn1DAE2H7OCVHLcrWV7hbc7GEo15tjg")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }

}
