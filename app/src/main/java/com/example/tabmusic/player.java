package com.example.tabmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class player extends AppCompatActivity {
    Bundle songExtraData;
    ArrayList<File> songFileList;
    SeekBar seekBar;
    TextView songtitle;
    ImageView playbtn;
    ImageView nextbtn;
    ImageView prevbtn;
    static MediaPlayer mMediaplayer;
    int position;
    TextView currenttime;
    TextView totaltime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        seekBar=findViewById(R.id.seekBar);
        songtitle=findViewById(R.id.songtitle);
        playbtn=findViewById(R.id.play);
        nextbtn=findViewById(R.id.next);
        prevbtn=findViewById(R.id.previous);
        currenttime=findViewById(R.id.timerstart);
        totaltime=findViewById(R.id.timerend);

        if(mMediaplayer!=null){
            mMediaplayer.stop();
        }

        Intent songData=getIntent();
        songExtraData=songData.getExtras();

        songFileList=(ArrayList)songExtraData.getParcelableArrayList("songFileList");
        position=songExtraData.getInt("position",0);
        initMusicPlayer(position);

        //setup play pause button
        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play();
            }
        });

        //setup nxtbtn
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position<songFileList.size()-1){
                    //check if the current position of the song in the list is less than the total song present in the list
                    //increase the position by one to play next song in the list
                    position++;
                }else{
                    //if the position is greater than or equal to the number of songs on the list
                    //set the position to zero
                    position=0;
                }
                //play the songs in the list with position
                initMusicPlayer(position);
            }
        });

        //setup previous play
        prevbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position<=0){
                    //if the position of the song on the list is less or equal to 0
                    position=songFileList.size()-1;

                }else {
                    position--;
                }
                initMusicPlayer(position);
            }
        });



        //setup seekbar to change with songs duration
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaplayer!=null){
                    try{
                        if(mMediaplayer.isPlaying()){
                            Message message=new Message();
                            message.what=mMediaplayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).run();

    }

    //create handler to set progress
    @SuppressLint("HandlerLeak")
    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            currenttime.setText(createtimerlable(msg.what));
            seekBar.setProgress(msg.what);
        }
    };

    private void initMusicPlayer(int position){
        if(mMediaplayer!=null && mMediaplayer.isPlaying()){
            mMediaplayer.reset();
;        }
        String name=songFileList.get(position).getName();
        songtitle.setText(name);

        Uri songResouuri= Uri.parse(songFileList.get(position).toString());

        mMediaplayer=MediaPlayer.create(getApplicationContext(),songResouuri);
        mMediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {


                seekBar.setMax(mMediaplayer.getDuration());



                String toTime=createtimerlable(mediaPlayer.getDuration());
                totaltime.setText((toTime));

                mMediaplayer.start();

                playbtn.setImageResource(R.drawable.pause);

            }
        });


        mMediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

            //do sth when the is finished playing now change the play icon

                playbtn.setImageResource(R.drawable.paly);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mMediaplayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




    }

    private void play(){
        if(mMediaplayer!=null && mMediaplayer.isPlaying()){
            mMediaplayer.pause();
            playbtn.setImageResource(R.drawable.paly);

        }else{
            mMediaplayer.start();
            playbtn.setImageResource(R.drawable.pause);
        }

    }
    private String createtimerlable(int duration){
        String timerlable="";
        int min=duration / 1000 / 60;
        int sec=duration / 1000 % 60;
        timerlable +=min + ":";
        if(sec<10)timerlable +="0";
        timerlable += sec;
        return timerlable;
    }

}
