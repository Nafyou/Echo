package com.example.echo.fragments;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.echo.MainActivity;
import com.example.echo.fragments.ComposeFragment;
import com.example.echo.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RecordFragment extends Fragment {

    private Button btnStartRecord;
    private Button btnPlayBack;
    private Button btnFinish;
    protected MediaRecorder recorder;
    private Chronometer timer;
    File fileRecording;
    String recordFile = "";
    String TAG = "RecordFragment";

    public RecordFragment(){
        //empty constructor
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

        btnStartRecord = view.findViewById(R.id.btnStartRecord);
        btnPlayBack = view.findViewById(R.id.btnPlayBack);
        btnFinish = view.findViewById(R.id.btnFinish);

        //TODO: user can start recording audio file (max 30sec), saves file when finished
        //if user starts recording again after stopping, the original file is not saved
//        btnStartRecord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        //TODO: user can play back saved audio file
        //btnPlayBack.setOnClickListener();

        // TODO: save audio file and upload it to Back4APP, then return to ComposeFragment
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the Uri
                fileRecording = getAudioFileUri(recordFile);
                Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider",fileRecording);
                //Bundle stuff
                Bundle result = new Bundle();
                //use Uri.parse(string) in compose to convert back to Uri
                result.putString("bundleKey", String.valueOf(fileProvider));
                getParentFragmentManager().setFragmentResult("requestKey", result);
                //returnToCompose();
            }
        });

    }

    //----------------------RECORDING THE AUDIO AND SAVING FILE------------------------

    //recording audio (btnStartRecording)
    private void startRecording(String fileName) {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

        recordFile = "filename.mp3";

        // initialize and configure MediaRecorder
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFile(fileName);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            recorder.prepare();
        }
        catch (IOException e) {
            e.printStackTrace();
            // handle error
        }
        catch (IllegalStateException e) {
            // handle error
        }
        recorder.start();
    }

    private void stopRecording() {
        // stop recording and free up resources
        recorder.stop();
        recorder.release();

        recorder = null;
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getAudioFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.

        //TODO: We might need to fix the directory_music part
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    //----------------------NEW SECTION------------------------

    //playing the recording (btnPlayBack)
    protected MediaPlayer player;
    private void startPlaying(String filePath) {
        player = new MediaPlayer();
        try {
            player.setDataSource(filePath); // pass reference to file to be played
            player.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()); // optional step
            player.prepare(); // may take a while depending on the media, consider using .prepareAsync() for streaming
        }
        catch (IOException e) { // we need to catch both errors in case of invalid or inaccessible resources
            // handle error
        }
        catch (IllegalArgumentException e) {
            // handle error
        }
        player.start();
    }

    private void stopPlaying() {
        player.stop();
        player.release(); // free up resources
        player = null;
    }

    private void returnToCompose() {
        FragmentTransaction fr = getFragmentManager().beginTransaction();
        //fr.addToBackStack(recordFile);
        fr.replace(R.id.flContainer,new ComposeFragment());
        fr.commit();
    }
}
