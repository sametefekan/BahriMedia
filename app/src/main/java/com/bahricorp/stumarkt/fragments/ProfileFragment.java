package com.bahricorp.stumarkt.fragments;

// import android.app.Fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bahricorp.stumarkt.Activity.ProfileActivity;
import com.bahricorp.stumarkt.R;
import com.bahricorp.stumarkt.Activity.RegisterActivity;
import com.bahricorp.stumarkt.models.UserModel;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{
    private Bundle savedInstanceState;

    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button menuButton;
    private CircleImageView profilePhoto;

    Uri imgUri, ImageUri;
    private ProgressDialog progressDialog;

    //Firebase
    private Firebase mRef;
    private StorageReference PostImagesRefrence;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase, mDatabase1;
    StorageReference filePath2, filePath3;

    private ArrayList<UserModel> mUsers;

    private FirebaseFirestore firebaseFirestore;

    public String image;

    FirebaseUser fuser;

    public ProfileFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        assert user != null;
        mUsers = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());

        assert user != null;

        textViewUserEmail = (TextView) view.findViewById(R.id.textViewEmail);
        buttonLogout = (Button) view.findViewById(R.id.buttonLogout01);
        menuButton = (Button) view.findViewById(R.id.buttonHome);
        profilePhoto = (CircleImageView) view.findViewById(R.id.profilePhoto);

        textViewUserEmail.setText("Hey  " + user.getEmail());

        // Firebase Storage
        PostImagesRefrence = FirebaseStorage.getInstance().getReference();

        // Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance();

        readUser();

        profilePhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Sign Out
                firebaseAuth.signOut();

                // Start Activity
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();

        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, 1);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            ImageUri = data.getData();
            profilePhoto.setImageURI(ImageUri);

            imgUri = ImageUri;

            // for Crop Image
            // start picker to get image for cropping and then use the image in cropping activity
            // CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);

            // start cropping activity for pre-acquired image saved on the device
            CropImage.activity(ImageUri).setCropShape(CropImageView.CropShape.OVAL).setBorderLineColor(Color.RED).start(getActivity());
            //.setCropShape(CropImageView.CropShape.RECTANGLE)
            //.setAspectRatio(4, 4)
            //TODO: action
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == getActivity().RESULT_OK)
            {
                // imgUri = result.getUri();
                ImageUri = result.getUri();
                profilePhoto.setImageURI(ImageUri);

                progressDialog.setMessage("Uploading, please wait...");
                progressDialog.show();

                StoringImageToFireBaseStorage();
            }

            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                Exception error = result.getError();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void StoringImageToFireBaseStorage()
    {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // Storage location
        final StorageReference filePath = PostImagesRefrence.child(ImageUri.getLastPathSegment() + ".jpg"); // final StorageReference filePath = PostImagesRefrence.child("Post images").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");
        mDatabase = firebaseDatabase.getReference().child("Users").child(user.getUid());

        // add file on Firebase and got Download Link
        filePath.putFile(ImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
        {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
            {
                if(!task.isSuccessful())
                {
                    throw task.getException();
                }

                return filePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>()
        {
            @Override
            public void onComplete(@NonNull Task<Uri> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(getActivity(), "post uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    Uri downUri = task.getResult();

                    mDatabase.child("imageURL").setValue(downUri.toString());
                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(getActivity(), "Error" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void readUser()
    {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // old for users activity
                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    // it's for users activity
                    UserModel user = snapshot.getValue(UserModel.class);

                    assert user != null;

                    if(user.getEmail().equals(firebaseUser.getEmail()))
                    {
                        String imageUrl = user.getImageURL();
                        Picasso.get().load(imageUrl).into(profilePhoto);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
