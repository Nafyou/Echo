package com.example.echo.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.echo.MainActivity;
import com.example.echo.LoginActivity;
import com.example.echo.Post;
import com.example.echo.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class ComposeFragment extends Fragment {

    public static final String TAG = "ComposeFragment";
    private EditText etLanguage;
    private EditText etWord;
    private EditText etDescription;
    private EditText etTranslation;
    private EditText etCategory;
    private File audioFile;
    private Button btnCaptureAudio;
    private VideoView evVideo;
    private Button btnSubmit;
    private String audioFileName = "audio.mp4";
    private Button btnLogout;

    public ComposeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etLanguage = view.findViewById(R.id.etLanguage);
        btnCaptureAudio = view.findViewById(R.id.btnCaptureAudio);
        evVideo = view.findViewById(R.id.evView);
        etWord = view.findViewById(R.id.etWord);
        etCategory = view.findViewById(R.id.etCategory);
        etDescription = view.findViewById(R.id.etDescription);
        etTranslation = view.findViewById(R.id.etTranslation);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnLogout = view.findViewById(R.id.btnLogout);

        btnCaptureAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchFileExplorer();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String language = etLanguage.getText().toString();
                String word = etWord.getText().toString();
                String description = etDescription.getText().toString();
                String translation = etTranslation.getText().toString();
                String category = etCategory.getText().toString();
                //Add video view/audio recording, and user object
                savePost(language, word, description, translation, audioFile);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Attempting to logout");
                ParseUser.logOut();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void launchFileExplorer() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");


        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri audioFileUri = data.getData();
                String pathFromUri = audioFileUri.getPath();
                audioFile = new File(audioFileUri.getPath());
                Log.d(TAG, "Uri: " + audioFileUri + pathFromUri);
            }
            else {
                Toast.makeText(getContext(), "File not chosen", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void savePost(String language, String word, String description, String translation, File audioFile) {
        Post post = new Post();
        post.setKeyLanguage(language);
        post.setKeyPhrase(word);
        post.setKeyDescription(description);
        post.setKeyTranslation(translation);
        post.setRecording(new ParseFile(audioFile));
        //post.setRecording();
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "Error saving", Toast.LENGTH_SHORT).show();
                }
                etWord.setText("");
                etDescription.setText("");
                etTranslation.setText("");
            }
        });
    }
}