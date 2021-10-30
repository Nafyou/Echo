package com.example.echo;

import android.content.Context;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            evView = itemView.findViewById(R.id.evView);
            etLanguage = itemView.findViewById(R.id.etLanguage);
            etTranslation = itemView.findViewById(R.id.etTranslation);
            etCategory = itemView.findViewById(R.id.etCategory);
            swipeContainer = itemView.findViewById(R.id.swipeContainer);
        }

        public void bind(Post post) {
            // Bind the post data to the view elements

            etTranslation.setText(post.getKeyTranslation());
            etLanguage.setText(post.getKeyLanguage());
            etCategory.setText(post.getKeyCategory());

            //TODO: Fix everything after writing code for respective functions (getUsername() and audio files)
//            tvUsername.setText(post.getKeyUser().getUsername());
//            ParseFile image = post.getImage();
//            if (image != null) {
//                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
//            }
        }
    }
}
