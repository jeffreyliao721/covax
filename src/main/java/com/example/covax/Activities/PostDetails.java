package com.example.covax.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.covax.Activities.ui.maps.Maps;
import com.example.covax.Adapters.CommentAdapter;
import com.example.covax.Models.Comment;
import com.example.covax.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostDetails extends AppCompatActivity {

    ImageView detailsImage, detailsIcon, commentIcon;
    TextView detailsTitle, detailsName, detailsDate, detailsDescription;
    EditText commentText;
    Button commentButton, addressButton;
    String key;

    double latitude, longitude;
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    };

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;

    RecyclerView commentRecyclerView;
    CommentAdapter commentAdapter;
    List<Comment> commentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getSupportActionBar().hide();

        commentRecyclerView = findViewById(R.id.commentRV);

        //blows up all details of a post to user interface
        detailsImage = findViewById(R.id.detailsImage);
        detailsTitle = findViewById(R.id.detailsTitle);
        detailsDate = findViewById(R.id.detailsDate);
        detailsIcon = findViewById(R.id.detailsIcon);
        detailsName = findViewById(R.id.detailsName);
        detailsDescription = findViewById(R.id.detailsDescription);
        commentIcon = findViewById(R.id.commentIcon);
        commentText = findViewById(R.id.commentText);
        commentButton = findViewById(R.id.commentButton);
        addressButton = findViewById(R.id.addressButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        String image = getIntent().getExtras().getString("image");
        Glide.with(this).load(image).into(detailsImage);

        String address = getIntent().getExtras().getString("address");
        if (address == null) {
            addressButton.setVisibility(View.INVISIBLE);
        } else {
            addressButton.setVisibility(View.VISIBLE);

            //user wants to check out the location associating with the post
            addressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if ((ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(LOCATION_PERMS, 100);
                        }
                    }
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

                    String geo = "geo:" + latitude + "," + longitude + "?q=" + address;
                    Uri gmmIntentUri = Uri.parse(geo);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });
        }

        String title = getIntent().getExtras().getString("title");
        detailsTitle.setText(title);

        String date = timeStampToString(getIntent().getExtras().getLong("timeStamp"));
        detailsDate.setText(date);

        String userIcon = getIntent().getExtras().getString("userIcon");
        Glide.with(this).load(userIcon).into(detailsIcon);

        String userName = getIntent().getExtras().getString("userName");
        detailsName.setText(userName);

        String description = getIntent().getExtras().getString("description");
        detailsDescription.setText(description);

        Glide.with(this).load(firebaseUser.getPhotoUrl()).into(commentIcon);

        key = getIntent().getExtras().getString("key");

        //user wants to add a comment
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference commentReference = firebaseDatabase.getReference("Comments").child(key).push();
                String cUserID = firebaseUser.getUid();
                String cUserName = firebaseUser.getDisplayName();
                String cUserIcon = firebaseUser.getPhotoUrl().toString();
                String cText = commentText.getText().toString();

                if (cText.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please write a comment!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Comment comment = new Comment(cUserID, cUserName, cUserIcon, cText);
                    commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Comment added successfully!", Toast.LENGTH_SHORT).show();
                            commentText.setText("");
                            commentButton.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "ERROR adding comment!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        iniCommentRV();
    }

    //gets list of comments from firebase
    private void iniCommentRV() {
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = firebaseDatabase.getReference("Comments").child(key);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentsList = new ArrayList<>();
                for (DataSnapshot commentsnap: snapshot.getChildren()) {
                    Comment comment = commentsnap.getValue(Comment.class);
                    commentsList.add(comment);
                }
                commentAdapter = new CommentAdapter(getApplicationContext(), commentsList);
                commentRecyclerView.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //converts long to a string type
    private String timeStampToString(long time) {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(time);
        String date = DateFormat.format("MM-dd-yyyy", calendar).toString();
        return date;
    }
}