package com.example.echo;

import android.app.Activity;
import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//Not necessary
import com.bumptech.glide.Glide;
import com.parse.Parse;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    String TAG = "PostsAdapter";
    private Context context;
    private List<Post> posts;
    SwipeRefreshLayout swipeContainer;

    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of posts
    public void addAll(List<Post> postsList) {
        posts.addAll(postsList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private VideoView evView;
        private TextView etLanguage;
        private TextView etTranslation;
        private TextView etCategory;
        private MediaPlayer mediaPlayer;
        private Button btnStartAudio;
        private ParseFile recordedFile;
        private ProgressBar progressBar;
        private TextView tvTimeCounter;
        private int currProgress;
        private int totalTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            evView = itemView.findViewById(R.id.evView);
            etLanguage = itemView.findViewById(R.id.etLanguage);
            etTranslation = itemView.findViewById(R.id.etTranslation);
            etCategory = itemView.findViewById(R.id.etCategory);
            swipeContainer = itemView.findViewById(R.id.swipeContainer);
            btnStartAudio = itemView.findViewById(R.id.btnStartAudio);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvTimeCounter = itemView.findViewById(R.id.tvTimeCounter);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements
            try {
                recordedFile = post.getRecording();
                if (recordedFile == null) {
                    tvTimeCounter.setText("No audio");
                }
                tvTimeCounter.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(recordedFile.getFile().getPath());
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(context,uri);
                String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                totalTime = Integer.parseInt(durationStr);
                tvTimeCounter.setText(Integer.toString(0) + "/" + Integer.toString(totalTime/1000));
            } catch (Exception e) {
                e.printStackTrace();
            }

            etTranslation.setText(post.getKeyTranslation());
            etLanguage.setText(post.getKeyLanguage());
            etCategory.setText(post.getKeyCategory());

            btnStartAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    play(view);
                }
            });
//            btnStartAudio.setOnClickListener(post.getRecording());

            //TODO: Fix everything after writing code for respective functions (getUsername() and audio files)
//            tvUsername.setText(post.getKeyUser().getUsername());

//            ParseFile image = post.getImage();
              // mediaPlayer = new MediaPlayer()
//            if (image != null) {
//                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
//            }
        }
        private void play(View view) {
            try {
                tvTimeCounter.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(recordedFile.getFile().getPath());
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(context,uri);
                String durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                int millSecond = Integer.parseInt(durationStr);

                totalTime = millSecond;
                tvTimeCounter.setText(Integer.toString(0) + "/" + Integer.toString(totalTime/1000));
                currProgress = 0;
                progressBar.setProgress(0);
                progressBar.setMax(totalTime);



                final Thread t = new Thread() {
                    @Override
                    public void run() {
                        while (currProgress < totalTime) {
                            ((Activity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (currProgress % 1000 < 50) {
                                        tvTimeCounter.setText(currProgress / 1000 + "/" + totalTime / 1000);
                                    }
                                }
                            });
                            SystemClock.sleep(10);
                            currProgress += 10;
                            progressBar.setProgress(currProgress);
                        }
                    }
                };
                mediaPlayer = new MediaPlayer();       //create the MediaPlayer object
                mediaPlayer.setDataSource(recordedFile.getUrl());
                mediaPlayer.prepare();
                mediaPlayer.start();

                t.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
