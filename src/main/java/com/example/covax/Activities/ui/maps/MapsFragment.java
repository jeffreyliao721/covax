package com.example.covax.Activities.ui.maps;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.covax.Activities.Login;
import com.example.covax.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MapsFragment extends Fragment {

    Button mapsButton;

    //Google maps cannot extend from fragment so there is this extra page
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_maps, container, false);

        mapsButton = fragmentView.findViewById(R.id.mapsButton);

        //brings user to the in app map
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsActivity = new Intent(getActivity(), Maps.class);
                startActivity(mapsActivity);
            }
        });

        return fragmentView;
    }
}