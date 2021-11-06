package com.example.echo.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import java.io.*;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.echo.MainActivity;
import com.example.echo.Post;
import com.example.echo.R;
import com.parse.ParseException;
import com.parse.ParseFile;
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

        //TODO: Figure out how to navigate to another fragment
        btnCaptureAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction fr = getFragmentManager().beginTransaction();
//                fr.replace(R.id.container,new RecordFragment());
//                fr.commit();

                //openRecordFragment();
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
                savePost(language, word, description, translation, category);
            }
        });
    }

    //TODO: change to fragment_record screen to record audio
    private void launchFileExplorer() {
        Intent i = new Intent(getContext(), RecordFragment.class);
        startActivityForResult(i, 1);

        // create Intent to take a picture and return control to the calling application
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("audio/*");
//        audioFile = getAudioFileUri(audioFileName);
//
//        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.echo.fileprovider", audioFile);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
//
//
//        startActivityForResult(intent, 1);
    }

    // Create launcher variable inside onAttach or onCreate or global
    ActivityResultLauncher<Intent> launchAudioCapture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        // your operation....
                    }
                }
            });

    public void openRecordFragment() {
        Intent intent = new Intent(getContext(), RecordFragment.class);
        launchAudioCapture.launch(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri audioFileUri = data.getData();
                try {
                    InputStream stream = getContext().getContentResolver().openInputStream(data.getData());
                    String FilePath = data.getData().getPath();
                    File original = new File(FilePath);
                    FileOutputStream out = new FileOutputStream(audioFile);
                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = stream.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    out.close();
                    stream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



                /**String pathFromUri = audioFileUri.getPath();
                audioFile = new File(audioFileUri.getPath());
                String envPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                File envPath2 = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), TAG);
                if (!envPath2.exists() && !envPath2.mkdirs()){
                    Log.d(TAG, "failed to create directory");
                }
                Log.d(TAG, "Uri: " + audioFileUri);
                Log.d(TAG, "Uri: " + pathFromUri);
                Log.d(TAG, "env: " + envPath);
                Log.d(TAG, "env: " + envPath2.getPath());**/
            }
            else {
                Toast.makeText(getContext(), "File not chosen", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public File getAudioFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }


    private void savePost(String language, String word, String description, String translation, String category) {
        Post post = new Post();
        post.setKeyLanguage(language);
        post.setKeyPhrase(word);
        post.setKeyDescription(description);
        post.setKeyTranslation(translation);
        post.setKeyCategory(category);
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