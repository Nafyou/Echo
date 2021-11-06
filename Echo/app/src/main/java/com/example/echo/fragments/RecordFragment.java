package com.example.echo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.echo.MainActivity;
import com.example.echo.R;

public class RecordFragment extends Fragment {

    private Button btnStartRecord;
    private Button btnPlayBack;
    private Button btnFinish;

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
            // if user starts recording again after stopping, the original file is not saved
        //btnStartRecord.setOnClickListener();

        //TODO: user can play back saved audio file
        //btnPlayBack.setOnClickListener();

        // TODO: save audio file and upload it to Back4APP, then return to ComposeFragment
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToCompose();
            }
        });

    }

    private void returnToCompose() {
        Intent i = new Intent(getContext(), ComposeFragment.class);
        startActivity(i);
    }
}
