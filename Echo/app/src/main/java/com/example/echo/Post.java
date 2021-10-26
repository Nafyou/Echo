package com.example.echo;
import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.File;

@ParseClassName("Post")
public class Post extends ParseObject {
    // define keys that match columns in parse
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_RECORDING = "recording";
    public static final String KEY_TRANSLATION = "translation";
    public static final String KEY_USER = "username";
    public static final String KEY_CATEGORY = "category";

    public String getKeyLanguage(){
        return getString(KEY_LANGUAGE);
    }
    public void String setKeyLanguage(String language){
        put(KEY_LANGUAGE, language);
    }

    public String getKeyRecording(){
        return getString(KEY_RECORDING);
    }

    public String getKeyTranslation(){
        return getString(KEY_TRANSLATION);
    }

    public String getKeyUser(){
        return getString(KEY_USER);
    }
    public String getKeyCategory(){
        return getString(KEY_CATEGORY);
    }



}
