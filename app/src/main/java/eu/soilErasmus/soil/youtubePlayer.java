package eu.soilErasmus.soil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class youtubePlayer extends AppCompatActivity {

    private List<String> lista;
    private YouTubePlayerView youTubePlayerView,youTubePlayerView2,youTubePlayerView3,youTubePlayerView4,youTubePlayerView5;

    private ImageView settings,camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_youtube_player);

        settings = findViewById(R.id.settingsButton3);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });


        camera = findViewById(R.id.artificial);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        lista = new ArrayList<String>();
        lista.add("PLgh0UxNx43uBCvdlEq1brjMVrNF6M9WRl");
        lista.add("PLgh0UxNx43uBqUHgjVipN0vXp9pHR9Pkq");
        lista.add("PLgh0UxNx43uDnAx7H6QaEfVnKi7aiYUNL");
        lista.add("PLgh0UxNx43uAx43WBIU1V6fjZ28nynlUj");
        lista.add("PLgh0UxNx43uDE9s1SoSZdctfApwbx7j0t");

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView2 = findViewById(R.id.youtube_player_view2);
        youTubePlayerView3 = findViewById(R.id.youtube_player_view3);
        youTubePlayerView4 = findViewById(R.id.youtube_player_view4);
        youTubePlayerView5 = findViewById(R.id.youtube_player_view5);

        youTubePlayerView.setEnableAutomaticInitialization(false);
        youTubePlayerView2.setEnableAutomaticInitialization(false);
        youTubePlayerView3.setEnableAutomaticInitialization(false);
        youTubePlayerView4.setEnableAutomaticInitialization(false);
        youTubePlayerView5.setEnableAutomaticInitialization(false);
        getLifecycle().addObserver(youTubePlayerView);
        getLifecycle().addObserver(youTubePlayerView2);
        getLifecycle().addObserver(youTubePlayerView3);
        getLifecycle().addObserver(youTubePlayerView4);
        getLifecycle().addObserver(youTubePlayerView5);


        for (int i=0; i< lista.size(); i++){
            initYouTubePlayerView(i);

        }

    }

    private void initYouTubePlayerView(int i) {

            IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                    .controls(1)
                    .listType("playlist")
                    .list((lista.get(i)))
                    .fullscreen(1)
                    .build();


            switch (i){
                case 0 :
                    getLifecycle().addObserver(youTubePlayerView);
                    youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                    }, true, iFramePlayerOptions);
                    break;
                case 1 :
                    getLifecycle().addObserver(youTubePlayerView2);
                    youTubePlayerView2.initialize(new AbstractYouTubePlayerListener() {
                    }, true, iFramePlayerOptions);
                    break;
                case 2 :
                    getLifecycle().addObserver(youTubePlayerView3);
                    youTubePlayerView3.initialize(new AbstractYouTubePlayerListener() {
                    }, true, iFramePlayerOptions);
                    break;
                case 3 :
                    getLifecycle().addObserver(youTubePlayerView4);
                    youTubePlayerView4.initialize(new AbstractYouTubePlayerListener() {
                    }, true, iFramePlayerOptions);
                    break;
                case 4 :
                    getLifecycle().addObserver(youTubePlayerView5);
                    youTubePlayerView5.initialize(new AbstractYouTubePlayerListener() {
                    }, true, iFramePlayerOptions);
                    break;
            }



    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            this.youTubePlayerView.matchParent();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.youTubePlayerView.wrapContent();
        }
    }


    private void openCamera() {
        Intent intent = new Intent(this,artificial_shovel.class);
        startActivity(intent);
    }

    public void openSettings(){
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }
}
        /*webView=(WebView) findViewById(R.id.webView);
        webView.loadUrl("https://www.youtube.com/@soil-erasmus9571");
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        youtubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youtubePlayerView);
        initYouTubePlayerView();

    }
    private void initYouTubePlayerView() {
        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                .listType("playlist")
                .list("PLgh0UxNx43uBCvdlEq1brjMVrNF6M9WRl")
                .controls(1)
                .rel(0)
                .ivLoadPolicy(1)
                .ccLoadPolicy(1)
                .build();


        getLifecycle().addObserver(youtubePlayerView);

            }

            @Override
            public void onConfigurationChanged(@NonNull Configuration newConfig) {
                super.onConfigurationChanged(newConfig);

                // Checks the orientation of the screen
                if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    youtubePlayerView.matchParent();
                } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    youtubePlayerView.wrapContent();
                }
            }




    public void onBackPressed(){
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


    public void onDestroy() {
        super.onDestroy();
        youtubePlayerView.release();
    }
}



package com.pierfrancescosoffritti.androidyoutubeplayer.core.sampleapp.examples.playlistExample;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.aytplayersample.R;


*/



