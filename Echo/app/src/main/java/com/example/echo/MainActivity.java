package com.example.echo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.VideoView;

import com.example.echo.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // Create variables that bind to the view(done)
    public static final String TAG = "MainActivity";
    public static final int CAPTURE_AUDIO_ACTIVITY_REQUEST_CODE = 42;
    private EditText etLanguage;

    private Button btnCaptureAudio;
    private VideoView evVideo;
    private Button btnSubmit;
    private File audioFile;
    private String audioFileName = "audio.mp4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etLanguage = findViewById(R.id.etLanguage);
        btnCaptureAudio = findViewById(R.id.btnCaptureAudio);
        evVideo = findViewById(R.id.evView);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}