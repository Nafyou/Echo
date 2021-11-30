package com.example.echo;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//Not necessary
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;

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
        private FloatingActionButton btnSave;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            evView = itemView.findViewById(R.id.evView);
            etLanguage = itemView.findViewById(R.id.etLanguage);
            etTranslation = itemView.findViewById(R.id.etTranslation);
            etCategory = itemView.findViewById(R.id.etCategory);
            swipeContainer = itemView.findViewById(R.id.swipeContainer);
            btnStartAudio = itemView.findViewById(R.id.btnStartAudio);
            btnSave = itemView.findViewById(R.id.btnSave);
        }


        public void bind(Post post) {
            // Bind the post data to the view elements

            etTranslation.setText(post.getKeyTranslation());
            etLanguage.setText(post.getKeyLanguage());
            etCategory.setText(post.getKeyCategory());

            //TODO: Create a button save that will add a relation of the post into the savedPosts column of the current user
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), post.getObjectId(), Toast.LENGTH_SHORT).show();
                    ParseRelation relation = post.getRelation("usersLiked");
                    relation.add(ParseUser.getCurrentUser());
                    try {
                        post.save();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //ParseUser.getCurrentUser().put("savedPosts", post.getObjectId());
                    Toast.makeText(view.getContext(), "Post Saved", Toast.LENGTH_SHORT).show();
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
    }
}
