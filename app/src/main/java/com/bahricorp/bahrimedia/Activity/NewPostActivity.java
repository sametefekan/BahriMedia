package com.bahricorp.bahrimedia.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bahricorp.bahrimedia.R;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewPostActivity extends AppCompatActivity {
    private Button buttonShare;
    private EditText postDescription;
    private ImageButton imageButton;

    private static final int GalleryPick = 1;

    // img Uri
    private Uri ImageUri;

    // post desc.
    private String Description;

    //Firebase
    private Firebase mRef;
    private StorageReference PostImagesRefrence;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;

    private String saveCurrentDate, saveCurrentTime, postRandomName, generatedFilePath;

    Uri downloadUri;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // Action bar
        ActionBar actionBar = getSupportActionBar();
        // Action bar title
        actionBar.setTitle("New Post");

        // set back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        imageButton = (ImageButton) findViewById(R.id.new_post_image);
        buttonShare = (Button) findViewById(R.id.post_btn);
        postDescription = (EditText) findViewById(R.id.new_post_desc);

        progressDialog = new ProgressDialog(this);

        // Firebase Storage
        PostImagesRefrence = FirebaseStorage.getInstance().getReference();

        // Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance();

        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenGallery();
            }
        });
        buttonShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ValidatePostInfo();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();

        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, GalleryPick);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void ValidatePostInfo()
    {
        Description = postDescription.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Please Select Post image", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Write Something", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Successful Post", Toast.LENGTH_SHORT).show();
            Description = postDescription.getText().toString();

            progressDialog.setMessage("Uploading, please wait...");
            progressDialog.show();

            // after
            StoringImageToFireBaseStorage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == GalleryPick)
        {
            ImageUri = data.getData();
            imageButton.setImageURI(ImageUri);

            //TODO: action
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void StoringImageToFireBaseStorage()
    {
        /* // Get image Bytes
        Bitmap bitmap = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        final byte[] imageInByte = stream.toByteArray();
        */

        // Get current date
        Calendar calFordData = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordData.getTime());

        // Get current time
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH-mm");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        // Post name with date and time
        postRandomName = saveCurrentDate + saveCurrentTime;

        // Storage location
        final StorageReference filePath = PostImagesRefrence.child(ImageUri.getLastPathSegment() + postRandomName + ".jpg"); // final StorageReference filePath = PostImagesRefrence.child("Post images").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");

        // Database location
        mDatabase = firebaseDatabase.getReference().child("Post").child(postRandomName);

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
                    Toast.makeText(NewPostActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    Uri downUri = task.getResult();
                    //Log.d(TAG, "onComplete: Url: "+ downUri.toString());

                    mDatabase.child("title").setValue(Description);
                    mDatabase.child("desc").setValue("");
                    mDatabase.child("name").setValue("melihbahri");
                    mDatabase.child("email").setValue("melih.bahri@hotmail.com");
                    mDatabase.child("image").setValue(downUri.toString()); //generatedFilePath
                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(NewPostActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
        filePath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() // OnSuccessListener, CompleteListener
        {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(NewPostActivity.this, "image uploaded", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(NewPostActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                }
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@Nullable UploadTask.TaskSnapshot taskSnapshot)
            {
                // 1
                // Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();

                // Uri downloadUrl = downloadUri.getResult();
                // String imgUrl = downloadUrl.toString();

                mDatabase.child("title").setValue(Description);
                mDatabase.child("desc").setValue("");
                mDatabase.child("name").setValue("melihbahri");
                mDatabase.child("email").setValue("melih.bahri@hotmail.com");
                mDatabase.child("image").setValue(generatedFilePath); //generatedFilePath
           }
        });
        */
    }
}