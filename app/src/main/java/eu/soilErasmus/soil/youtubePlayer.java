package eu.soilErasmus.soil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class youtubePlayer extends AppCompatActivity {

    private YouTubePlayerView youtubePlayerView;

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_youtube_player);



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
    public void settings(View view) {
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }


    public void onDestroy() {
        super.onDestroy();
        youtubePlayerView.release();
    }
}

*/



