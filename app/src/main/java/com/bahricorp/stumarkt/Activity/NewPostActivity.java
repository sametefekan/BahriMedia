package com.bahricorp.stumarkt.Activity;

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

import com.bahricorp.stumarkt.R;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewPostActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Button buttonShare;
    private EditText postTitle, postDesc, postPrice;
    private ImageButton imageButton, imageButton2, imageButton3;

    private static final int GalleryPick = 1;
    private static final int GalleryPick2 = 2;
    private static final int GalleryPick3 = 3;

    // img Uri
    private Uri ImageUri, ImageUri2, ImageUri3;

    //Firebase
    private Firebase mRef;
    private StorageReference PostImagesRefrence;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase, mDatabase1;
    StorageReference filePath2, filePath3;

    private String saveCurrentDate, saveCurrentTime, postRandomName, generatedFilePath, Title, Description, Price, userName, category_text, userImage;

    private ProgressDialog progressDialog;

    Uri imgUri;
    int imgBtn = 0;

    private Spinner spinner;

    private static final String[] paths = {
            "cars",
            "real-estate",
            "electronic",
            "house&garden",
            "entertainment",
            "other vehicles",
            "clothes&accessories",
            "movie, book&music",
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
                OpenGallery(1);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenGallery(2);
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenGallery(3);
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
                break;
            case 1:
                category_text = "real-estate";
                break;
            case 2:
                category_text = "electronic";
                break;
            case 3:
                category_text = "house&garden";
                break;
            case 4:
                category_text = "entertainment";
                break;
            case 5:
                category_text = "other vehicles";
                break;
            case 6:
                category_text = "clothes&accessories";
                break;
            case 7:
                category_text = "movie, book&music";
                break;
            case 8:
                category_text = "others";
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

    private void OpenGallery(int button)
    {
        imgBtn = button;

        Intent galleryIntent = new Intent();

        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, button);
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

                // for user profile image
                userImage = user.getImageURL();

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

            imgUri = ImageUri;
            imgBtn = 1;

            // for Crop Image
            // start picker to get image for cropping and then use the image in cropping activity
            // CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);

            // start cropping activity for pre-acquired image saved on the device
            CropImage.activity(ImageUri).setAspectRatio(16, 9).setBorderLineColor(Color.RED).start(this);
            //.setCropShape(CropImageView.CropShape.RECTANGLE)
            //.setAspectRatio(4, 4)
            //TODO: action
        }

        if(requestCode == GalleryPick2)
        {
            ImageUri2 = data.getData();
            imageButton2.setImageURI(ImageUri2);

            imgUri = ImageUri2;
            imgBtn = 2;

            CropImage.activity(ImageUri2).setAspectRatio(16, 9).setBorderLineColor(Color.RED).start(this);
        }

        if(requestCode == GalleryPick3)
        {
            ImageUri3 = data.getData();
            imageButton3.setImageURI(ImageUri3);

            imgUri = ImageUri3;
            imgBtn = 3;

            CropImage.activity(ImageUri3).setAspectRatio(16, 9).setBorderLineColor(Color.RED).start(this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK)
            {
                // imgUri = result.getUri();
                /*
                ImageUri = result.getUri();
                imageButton.setImageURI(ImageUri);
                */

                switch(imgBtn)
                {
                    case 1:
                        ImageUri = result.getUri();
                        imageButton.setImageURI(ImageUri);
                        break;
                    case 2:
                        ImageUri2 = result.getUri();
                        imageButton2.setImageURI(ImageUri2);
                        break;
                    case 3:
                        ImageUri3 = result.getUri();
                        imageButton3.setImageURI(ImageUri3);
                        break;
                }
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

        if(ImageUri2 != null)
        {
            filePath2 = PostImagesRefrence.child(ImageUri2.getLastPathSegment() + postRandomName + ".jpg");
        }

        if(ImageUri3 != null)
        {
            filePath3 = PostImagesRefrence.child(ImageUri3.getLastPathSegment() + postRandomName + ".jpg");
        }

        // Database location
        // original *** mDatabase = firebaseDatabase.getReference().child("Post").child(postRandomName);
        mDatabase = firebaseDatabase.getReference().child("Post").child(postRandomName);
        //.child(category_text)

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

                    mDatabase.child("category").setValue(category_text);

                    mDatabase.child("price").setValue(Price + "€");
                    mDatabase.child("image").setValue(downUri.toString());
                    mDatabase.child("sex").setValue("sex");

                    mDatabase.child("name").setValue(userName);
                    mDatabase.child("email").setValue(user.getEmail());
                    mDatabase.child("userId").setValue(user.getUid());

                    // for user image
                    mDatabase.child("imageURL").setValue(userImage);
                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(NewPostActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(filePath2 != null)
        {
            filePath2.putFile(ImageUri2).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return filePath2.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if(task.isSuccessful())
                    {
                        Uri downUri2 = task.getResult();

                        mDatabase.child("image2").setValue(downUri2.toString());
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(NewPostActivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if(filePath3 != null)
        {
            filePath3.putFile(ImageUri3).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if(!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return filePath3.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if(task.isSuccessful())
                    {
                        Uri downUri3 = task.getResult();
                        mDatabase.child("image3").setValue(downUri3.toString());
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
}