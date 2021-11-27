package com.example.echo.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.echo.LoginActivity;
import com.example.echo.Post;
//import com.example.echo.PostsAdapter;
import com.example.echo.PostsAdapter;
import com.example.echo.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    private TextView username;
    private Button btnLogout;
    SwipeRefreshLayout swipeContainer;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public void clear() {
        allPosts.clear();
        adapter.notifyDataSetChanged();
    }

    // Add a list of posts
    public void addAll(List<Post> postsList) {
        allPosts.addAll(postsList);
        adapter.notifyDataSetChanged();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "fetching new data");
                queryPosts();
            }
        });

        username = view.findViewById(R.id.tvPfpUsername);
        username.setText(ParseUser.getCurrentUser().getUsername());
        rvPosts = view.findViewById(R.id.rvPostsProfile);
        btnLogout = view.findViewById(R.id.btnLogout);

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

        // TODO: Uncomment after adding PostsAdapter
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);


        rvPosts.setAdapter(adapter);

        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
    }

    // TODO: Uncomment and edit after cleaning up Posts and making PostsAdapter
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATEDAT);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts");
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getKeyDescription() + ", username: " + post.getUser().getUsername());
                }
                adapter.clear();
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }
        });
    }


}