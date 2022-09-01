package com.example.covax.Activities.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covax.Adapters.PostAdapter;
import com.example.covax.Models.Post;
import com.example.covax.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView postRecylerView;
    PostAdapter postAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Post> postsList;

    //creates and initializes a ViewHolder to be filled with content
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //organizes posts with recent first
        LinearLayoutManager lin = new LinearLayoutManager(getActivity());
        lin.setStackFromEnd(true);
        lin.setReverseLayout(true);

        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        postRecylerView = fragmentView.findViewById(R.id.postRV);
        postRecylerView.setLayoutManager(lin);
        postRecylerView.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        //gets list of posts from firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postsList = new ArrayList<>();
                for (DataSnapshot postsnap: snapshot.getChildren()) {
                    Post post = postsnap.getValue(Post.class);
                    postsList.add(post);
                }
                postAdapter = new PostAdapter(getActivity(), postsList);
                postRecylerView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}