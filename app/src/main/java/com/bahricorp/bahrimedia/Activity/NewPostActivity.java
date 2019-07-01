package com.bahricorp.bahrimedia.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bahricorp.bahrimedia.R;
import com.bahricorp.bahrimedia.UserAdapter;
import com.bahricorp.bahrimedia.models.BlogPost;
import com.bahricorp.bahrimedia.models.UserModel;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewPostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Button buttonShare;
    private EditText postTitle;
    private EditText postDesc;
    private EditText postPrice;
    private ImageButton imageButton;
    private ImageButton imageButton2;
    private ImageButton imageButton3;

    private static final int GalleryPick = 1;

    // img Uri
    private Uri ImageUri;

    //Firebase
    private Firebase mRef;
    private StorageReference PostImagesRefrence;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    DatabaseReference mDatabase1;

    private String saveCurrentDate, saveCurrentTime, postRandomName, generatedFilePath, Title, Description, Price, userName, category_text;

    private ProgressDialog progressDialog;

    private Spinner spinner;
    private static final String[] paths = {
            "Cars",
            "Real Estate",
            "Electronic",
            "House&Garden",
            "Entertainment",
            "Other Vehicles",
            "Clothes&Accessories",
            "Movie, Book&Music",
            "Others"};

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

        buttonShare = (Button) findViewById(R.id.post_btn);

        imageButton = (ImageButton) findViewById(R.id.new_post_image);
        imageButton2 = (ImageButton) findViewById(R.id.new_post_image2);
        imageButton3 = (ImageButton) findViewById(R.id.new_post_image3);

        postTitle = (EditText) findViewById(R.id.new_post_title);
        postDesc = (EditText) findViewById(R.id.new_post_desc);
        postPrice = (EditText) findViewById(R.id.new_post_price);

        spinner = (Spinner)findViewById(R.id.spinner_category);

        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paths);
        //set the spinners adapter to the previously created one.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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

        imageButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Title = postTitle.getText().toString();
                Description = postDesc.getText().toString();

                ValidatePostInfo();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        switch(position)
        {
            case 0:
                category_text = "cars";
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                category_text = "real-estate";
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                category_text = "electronic";
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 3:
                category_text = "house-garden";
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 4:
                category_text = "entertainment";
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 5:
                category_text = "other-vehicles";
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 6:
                category_text = "clothes-accessories";
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 7:
                category_text = "movie-book-music";
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 8:
                category_text = "others";
                // Whatever you want to happen when the thrid item gets selected
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // TODO Auto-generated method stub
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
        if(ImageUri == null)
        {
            Toast.makeText(this, "Please Select Post image", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Title))
        {
            Toast.makeText(this, "Write Something", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Successful Post", Toast.LENGTH_SHORT).show();
            readUser();

            Title = postTitle.getText().toString();
            Description = postDesc.getText().toString();
            Price = postPrice.getText().toString();
            category_text = spinner.getSelectedItem().toString();

            progressDialog.setMessage("Uploading, please wait...");
            progressDialog.show();

            // after
            StoringImageToFireBaseStorage();
        }
    }

    public void readUser()
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                UserModel user = dataSnapshot.getValue(UserModel.class);

                userName = user.getName();

                /*
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    UserModel user = snapshot.getValue(UserModel.class);
                }
                */
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == GalleryPick)
        {
            ImageUri = data.getData();
            imageButton.setImageURI(ImageUri);

            // for Crop Image
            // start picker to get image for cropping and then use the image in cropping activity
            // CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);

            // start cropping activity for pre-acquired image saved on the device
            CropImage.activity(ImageUri).setAspectRatio(16, 9).setBorderLineColor(Color.RED).start(this);
            //.setCropShape(CropImageView.CropShape.RECTANGLE)
            //.setAspectRatio(4, 4)
            //TODO: action
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK)
            {
                ImageUri = result.getUri();
                imageButton.setImageURI(ImageUri);
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
        mDatabase = firebaseDatabase.getReference().child("Post").child(postRandomName); //.child(category_text)

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
                    Toast.makeText(NewPostActivity.this, "post uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    Uri downUri = task.getResult();

                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    mDatabase.child("title").setValue(Title);
                    mDatabase.child("desc").setValue(Description);
                    mDatabase.child("name").setValue(userName);
                    mDatabase.child("price").setValue(Price + "€");

                    mDatabase.child("image").setValue(downUri.toString());
                    mDatabase.child("email").setValue(user.getEmail());
                    mDatabase.child("userId").setValue(user.getUid());
                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(NewPostActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}