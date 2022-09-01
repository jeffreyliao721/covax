package com.example.covax.Activities.ui.signout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.covax.Activities.Login;
import com.example.covax.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignOutFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Button signOutButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_signout, container, false);

        signOutButton = fragmentView.findViewById(R.id.signOutButton);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //properly signs the user out of the database and the app
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(getActivity(), Login.class);
                startActivity(loginActivity);
            }
        });

        return fragmentView;
    }
}