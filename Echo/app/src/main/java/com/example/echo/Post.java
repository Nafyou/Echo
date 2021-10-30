package com.example.echo;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;

@ParseClassName("Post")
public class Post extends ParseObject {
    // define keys that match columns in parse
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_RECORDING = "recording";
    public static final String KEY_TRANSLATION = "translation";
    //public static final String KEY_USER = "username";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_PHRASE = "phrase";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_CREATEDAT = "createdAt";

    public String getKeyLanguage(){
        return getString(KEY_LANGUAGE);
    }
    public void setKeyLanguage(String language){
        put(KEY_LANGUAGE, language);
    }
// Different TODO:Figure out parseFile syntax for getting recording.
//    public String getKeyRecording(){
//        return getString(KEY_RECORDING);
//    }
// Do something different( parse file)
//    public void setKeyRecording(String recording){
//        put(KEY_LANGUAGE, recording);
//    }

    public String getKeyTranslation(){
        return getString(KEY_TRANSLATION);
    }

    public void setKeyTranslation(String translation){
        put(KEY_LANGUAGE, translation);
    }


    public ParseUser getUser(){
       return getParseUser(KEY_USER);
    }
    public void setKeyUser(String user){
       put(KEY_LANGUAGE, user);
    }


    public String getKeyCategory(){
        return getString(KEY_CATEGORY);
    }
    public void setKeyCategory(String category){
        put(KEY_CATEGORY, category);
    }


    public String getKeyPhrase(){
        return getString(KEY_PHRASE);
    }
    public void setKeyPhrase(String word){
        put(KEY_PHRASE, word);
    }

    public String getKeyDescription(){
        return getString(KEY_DESCRIPTION);
    }
    public void setKeyDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getRecording() {
        return getParseFile(KEY_RECORDING);
    }
    public void setRecording(ParseFile parseFile) {
        put(KEY_RECORDING, parseFile);
    }

}
