package com.example.echo.fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.echo.R;

import java.io.File;
import java.io.IOException;

public class RecordFragment extends Fragment {
    private Button btnRecord;
    private Button btnStop;
    private Button btnPlay;
    private Button btnBack;
    private File recordedFile;
    private static int MICROPHONE_PERMISSION = 200;
    MediaRecorder mediaRecorder;        //API for recording and saving audio
    MediaPlayer mediaPlayer;        //necessary to play audio


    public RecordFragment() {
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
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnRecord = view.findViewById(R.id.btnRecord);
        btnStop = view.findViewById(R.id.btnStop);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnBack = view.findViewById(R.id.btnBack);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(view);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop(view);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play(view);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {  //Function for whenever we click the back button
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();        //create fragment manager
                fragmentManager.popBackStack();              //go back to compose fragment
                Toast.makeText(getContext(), "Audio file saved in Downloads", Toast.LENGTH_LONG).show();
            }
        });


        if (hasMicrophone()) {
            requestMicrophonePermission();
        }
    }

    public void record(View view) {
        try {
            mediaRecorder = new MediaRecorder();    //creating the MediaRecorder object

            ContentValues values = new ContentValues(3);
            values.put(MediaStore.MediaColumns.TITLE, "testRecordingFile.mp3");
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);            //setting microphone as the audio source
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);    //setting audio file format to 3GPP
            mediaRecorder.setOutputFile(getRecordingFilePath());    //Telling MediaRecorder object to save the audio in the testRecordingFile.mp3 file
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);   //encoder necessary to compress the audio data according to the file format
            mediaRecorder.prepare();
            mediaRecorder.start();      //start recording

            Toast.makeText(getContext(), "Recording now", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stop(View view) {
        mediaRecorder.stop();       //Stop the recording
        mediaRecorder.release();
        mediaRecorder = null;       //Delete the MediaRecorder object

        Toast.makeText(getContext(), "Stopped recording", Toast.LENGTH_LONG).show();
    }

    public void play(View view) {
        try {
            mediaPlayer = new MediaPlayer();       //create the MediaPlayer object
            mediaPlayer.setDataSource(recordedFile.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();

            Toast.makeText(getContext(), "Playing recording", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasMicrophone() {        //Check to see if the device has a microphone
        if (getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            return true;
        }
        return false;
    }

    private void requestMicrophonePermission() {    //request microphone permission, if not already allowed
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION);
        }
    }

    private String getRecordingFilePath() {
        ContextWrapper contextWrapper = new ContextWrapper(getContext());
        File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);  //getting directory of the Music file
        recordedFile = new File(downloadsDirectory, "testRecordingFile" + ".mp3");                //Create an empty audio file (for the recording) at that directory.
        return recordedFile.getPath();                  //returning path of the created audio file
    }
}
