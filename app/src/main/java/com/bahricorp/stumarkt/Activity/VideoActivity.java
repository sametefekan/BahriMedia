package com.bahricorp.stumarkt.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bahricorp.stumarkt.R;
import com.bahricorp.stumarkt.models.BlogPost;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.media.AudioManager;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

// import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/*
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.Option;
import tcking.github.com.giraffeplayer2.PlayerManager;
import tcking.github.com.giraffeplayer2.VideoView;
import com.github.tcking.viewquery.ViewQuery;
import tcking.github.com.giraffeplayer2.DefaultMediaController;
import tcking.github.com.giraffeplayer2.MediaController;
*/

import com.hustunique.parsingplayer.player.view.ParsingVideoView;

// implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener
// implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener
public class VideoActivity extends AppCompatActivity
{
    public TextView textView, nameTextView, emailTextView, descTextView, priceTextView;

    // old one
    VideoView vidView;

    BlogPost model;
    String vidAddress;

    // giraffeplayer
    // VideoView vidView;

    private ParsingVideoView mVideoView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;

    private MediaPlayer mediaPlayer;

    private SurfaceHolder vidHolder;
    private SurfaceView vidSurface;

    private int position = 0;
    private MediaController mediaController;

    // giraffeplayer
    // private int aspectRatio = VideoInfo.AR_ASPECT_FIT_PARENT;
    // private GiraffePlayer player;

    // for firebase video controller
    private ImageView playButton;
    private TextView tv_time;
    private TextView tv_duration;
    private ProgressBar currentProgress;
    private ProgressBar bufferProgress;

    private boolean isPlaying = false;

    private int current = 0;
    private int duration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // Action bar
        ActionBar actionBar = getSupportActionBar();

        // set back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Text Views find
        textView = findViewById(R.id.blog_title);
        descTextView = findViewById(R.id.blog_desc);
        nameTextView = findViewById(R.id.user_name);
        emailTextView = findViewById(R.id.user_email);

        // ParsingVideoView
        // mVideoView = (ParsingVideoView) findViewById(R.id.video_view);

        // for normal video view
        // Video View find by id
        vidView = (VideoView)findViewById(R.id.myVideo);

        // for firebase video controller
        /*
        playButton = (ImageView) findViewById(R.id.pause);
        tv_time = (TextView)findViewById(R.id.tv_time_sex);
        tv_duration = (TextView)findViewById(R.id.tv_duration);
        currentProgress = (ProgressBar)findViewById(R.id.media_progress);
        currentProgress.setMax(100);
        bufferProgress = (ProgressBar)findViewById(R.id.media_progress);
        */

        // Set the media controller buttons
        if(mediaController == null)
        {
            mediaController = new MediaController(VideoActivity.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(vidView);

            // Set MediaController for VideoView
            vidView.setMediaController(mediaController);
        }
        else
        {
            mediaController = new MediaController(VideoActivity.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(vidView);

            // Set MediaController for VideoView
            vidView.setMediaController(mediaController);
        }

        vidView.requestFocus();

        // giraffeplayer
        // vidView = (VideoView) findViewById(R.id.myVideo);

        // giraffeplayer
        // GiraffePlayer.debug = true;//show java logs
        // GiraffePlayer.nativeDebug = false;//not show native logs

        // giraffeplayer
        // PlayerManager.getInstance().getDefaultVideoInfo().addOption(Option.create(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "multiple_requests", 1L));

        // for normal video view
        // player surface view
        // layout must be added
        // vidSurface = (SurfaceView) findViewById(R.id.surfView);

        // for normal video view
        // video view holder
        // vidHolder = vidSurface.getHolder();
        // vidHolder.addCallback(this);

        // get data from fragment
        String mTitle = getIntent().getStringExtra("title");
        String mDesc = getIntent().getStringExtra("desc");

        vidAddress = "https://dl.dropboxusercontent.com/s/j4u3fze3aocw13c/julia_ann_1.mp4?dl=0";
        // https://dl.dropboxusercontent.com/s/...*** bla bla
        // https://drive.google.com/uc?export=download&id=..*** bla bla
        Uri vidUri = Uri.parse(vidAddress);

        /// for normal video view
        // media controller
        // MediaController vidControl = new MediaController(this);
        // vidControl.setAnchorView(vidView);

        // for normal video view
        // vidView.setMediaController(vidControl);

        textView.setText(mTitle);
        descTextView.setText(mDesc);

        // for normal video view
        vidView.setVideoURI(vidUri);
        // vidView.setVideoPath(vidAddress);

        // for firebase media controller
        /*
        vidView.setOnInfoListener(new MediaPlayer.OnInfoListener()
        {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra)
            {
                // Log.d(TAG, "mainVideoView info what: " + what + " extra: " + extra);
                if(what == mp.MEDIA_INFO_BUFFERING_START)
                {
                    //if (what == mp.MEDIA_INFO_VIDEO_RENDERING_START){
                    bufferProgress.setVisibility(View.VISIBLE);
                }
                else if(what ==  mp.MEDIA_INFO_VIDEO_RENDERING_START)
                {
                    bufferProgress.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });
        */

        // for firebase media controller and Media Player
        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                /*
                duration = mp.getDuration()/1000;  //seconds

                String durationString = String.format("%02d:%02d", duration / 60, duration %60);

                tv_duration.setText(durationString);

                vidView.seekTo(position);
                if(position == 0)
                {
                    vidView.start();
                }
                */

                vidView.seekTo(position);

                if (position == 0)
                {
                    vidView.start();
                }

                // When video Screen change size.
                /*
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener()
                {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
                    {
                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(vidView);
                    }
                });
                */
            }
        });

        vidView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
        {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener()
                {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height)
                    {
                         // add media controller

                        mediaController = new MediaController(VideoActivity.this);
                        vidView.setMediaController(mediaController);
                        // and set its position on screen
                        mediaController.setAnchorView(vidView);
                    }
                });
            }
        });

        // for normal video view
        // vidView.start();

        // giraffeplayer
        // vidView.setVideoPath(vidAddress);
        // vidView.getVideoInfo().setBgColor(Color.TRANSPARENT).setShowTopBar(true).setUri(vidUri).setAspectRatio(aspectRatio);

        // giraffeplayer
        // vidView.getPlayer().start();

        // parsingVideo
        // mVideoView.play(vidAddress);

        // for firebase media controller
        // playButton.setImageResource(R.drawable.ic_pause);
        // isPlaying = true;

        // for firebase media controller
        // new VideoProgress().execute();

        // for firebase media controller
        /*
        playButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(isPlaying)
                {
                    vidView.pause();
                    playButton.setImageResource(R.drawable.ic_play_button);
                    isPlaying = false;
                }
                else
                {
                    vidView.start();
                    playButton.setImageResource(R.drawable.ic_pause);
                    isPlaying = true;
                }
            }
        });
        */
    }

    // for MediaPlayer
    // Find ID corresponding to the name of the resource (in the directory raw).
    public int getRawResIdByName(String resName)
    {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        return resID;
    }

    // for MediaPlayer
    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", vidView.getCurrentPosition());
        vidView.pause();
    }

    // for MediaPlayer
    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        vidView.seekTo(position);
    }

    // for firebase video player
    /*
    public class VideoProgress extends AsyncTask<Void, Integer, Void>
    {

        @Override
        protected Void doInBackground(Void... voids)
        {
            do
            {
                current = vidView.getCurrentPosition()/1000;
                try
                {
                    int currentPercent = current * 100 / duration;
                    publishProgress(currentPercent);
                }
                catch(Exception e)
                {
                }
            }
            while(currentProgress.getProgress() <= 100);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            try
            {
                currentProgress.setProgress(values[0]);

                int current = values[0] * duration / 100 ;
                String currentString = String.format("%02d:%02d", current / 60, current % 60);

                tv_time.setText(currentString);
            }
            catch(Exception e){}
        }
    }
    */

    /*
    @Override
    public void onPrepared(MediaPlayer mp)
    {
        // mediaPlayer.start();
    }
    */

    // for surface
    // @Override
    // public void surfaceCreated(SurfaceHolder holder)
    // {
       // try
        //{
            /*
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(vidHolder);
            mediaPlayer.setDataSource(vidAddress);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            */
        //}

        /*
        catch(Exception e)
        {
            e.printStackTrace();
        }
        */
    //}

    // for surface
    /*
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }
    */

    // for surface
    /*
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }
    */

    /*
    @Override
    protected void onResume()
    {
        super.onResume();
        mVideoView.onResume();
    }
    */

    /*
    @Override
    protected void onPause()
    {
        super.onPause();
        mVideoView.onPause();
    }
    */

    /*
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mVideoView.onDestroy();
    }
    */
}
