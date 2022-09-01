package com.example.covax.Activities;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.covax.Models.Post;
import com.example.covax.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    Dialog popUpPost;
    ImageView postIcon, postImage, postButton;
    TextView postTitle, postAddress, postDescription;
    ProgressBar progressBar;
    static final int REQUEST_GALLERY = 1;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //user wants to create a post
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
                popUpPost.show();
            }
        });

        //user navigation
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //passing each menu ID as a set of Ids because each
        //menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navHome, R.id.navMaps, R.id.navSignOut)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        updateNavHeader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sidebar, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void updateNavHeader() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUserName = headerView.findViewById(R.id.navUserName);
        TextView navUserEmail = headerView.findViewById(R.id.navUserEmail);
        ImageView navUserIcon = headerView.findViewById(R.id.navUserIcon);

        navUserName.setText(currentUser.getDisplayName());
        navUserEmail.setText(currentUser.getEmail());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserIcon);
    }

    //various fields that go into a post
    private void createPost() {
        popUpPost = new Dialog(this);
        popUpPost.setContentView(R.layout.add_post);
        popUpPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popUpPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        popUpPost.getWindow().getAttributes().gravity = Gravity.TOP;

        postIcon = popUpPost.findViewById(R.id.postIcon);
        postTitle = popUpPost.findViewById(R.id.postTitle);
        postAddress = popUpPost.findViewById(R.id.postAddress);
        postDescription = popUpPost.findViewById(R.id.postDescription);
        postImage = popUpPost.findViewById(R.id.postImage);
        postButton = popUpPost.findViewById(R.id.postButton);
        progressBar = popUpPost.findViewById(R.id.progressBar);

        Glide.with(this).load(currentUser.getPhotoUrl()).into(postIcon);

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGetPictureIntent();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //every post must at least have a title and description
                if (postTitle.getText().toString().isEmpty() || postDescription.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in *required fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //if there is an associating image
                    if (selectedImage != null) {
                        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("posts_images");
                        StorageReference imageFilePath = storageReference.child(selectedImage.getLastPathSegment());
                        imageFilePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Post post = new Post(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getPhotoUrl().toString(), postTitle.getText().toString(), postDescription.getText().toString(), uri.toString());

                                        //if there is an associating addresss
                                        if (!postAddress.getText().toString().isEmpty())
                                            post.setAddress(postAddress.getText().toString());

                                        addPost(post);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "ERROR attaching image!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                    else {
                        //regular post
                        Post post = new Post(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getPhotoUrl().toString(), postTitle.getText().toString(), postDescription.getText().toString());

                        //if there is an associating addresss
                        if (!postAddress.getText().toString().isEmpty())
                            post.setAddress(postAddress.getText().toString());

                        addPost(post);
                    }
                    postButton.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //user wants to upload their post
    private void addPost(Post post) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Posts").push();

        //unique post ID and key
        String key = myRef.getKey();
        post.setKey(key);

        //stores post in firebase database
        myRef.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Post uploaded successfully!", Toast.LENGTH_SHORT).show();
                popUpPost.dismiss();
            }
        });
    }

    //user selects a photo
    private void dispatchGetPictureIntent() {
        try {
            Intent getPictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(getPictureIntent, REQUEST_GALLERY);
        }
        catch (ActivityNotFoundException e) {
            e.printStackTrace();;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //accesses gallery app on android
        if (resultCode == RESULT_OK && requestCode == REQUEST_GALLERY) {
            try {
                selectedImage = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                postImage.setImageBitmap(imageBitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();;
            }
        }
    }
}