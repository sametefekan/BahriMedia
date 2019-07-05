package com.bahricorp.stumarkt.Activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bahricorp.stumarkt.R;
import com.bahricorp.stumarkt.models.BlogPost;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VideoActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener
{
    public TextView textView, nameTextView, emailTextView, descTextView, priceTextView;
    VideoView vidView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    BlogPost model;

    String vidAddress;

    private MediaPlayer mediaPlayer;
    private SurfaceHolder vidHolder;
    private SurfaceView vidSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Action bar
        ActionBar actionBar = getSupportActionBar();

        // set back button in action bar
        // actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Text Views find
        textView = findViewById(R.id.blog_title);
        descTextView = findViewById(R.id.blog_desc);
        nameTextView = findViewById(R.id.user_name);
        emailTextView = findViewById(R.id.user_email);

        // Video View find by id
        vidView = (VideoView)findViewById(R.id.myVideo);

        // player surface view
        // layout must be added
        // vidSurface = (SurfaceView) findViewById(R.id.surfView);

        // video view holder
        vidHolder = vidSurface.getHolder();
        vidHolder.addCallback(this);

        // get data from fragment
        String mTitle = getIntent().getStringExtra("title");
        String mDesc = getIntent().getStringExtra("desc");

        vidAddress = "https://mega.nz/#!bG5AXS5C!SdF9cYecc3f5E_LeyMRQZae24spclHeO1UZe0IBG-gY";
        Uri vidUri = Uri.parse(vidAddress);

        // media controller
        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(vidView);

        vidView.setMediaController(vidControl);

        textView.setText(mTitle);
        descTextView.setText(mDesc);
        vidView.setVideoURI(vidUri);

        vidView.start();
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {
        mediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        try
        {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(vidHolder);
            mediaPlayer.setDataSource(vidAddress);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }
}
