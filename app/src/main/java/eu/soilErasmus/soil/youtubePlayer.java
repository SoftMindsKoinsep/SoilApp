package eu.soilErasmus.soil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class youtubePlayer extends AppCompatActivity {

    private List<String> lista;
    private RecyclerView recyclerView;
    private ImageView settings, camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);


        recyclerView = findViewById(R.id.recycler_view);

        lista = new ArrayList<String>();
        lista.add("6JYIGclVQdw");
        lista.add("LvetJ9U_tVY");
        lista.add("6JYIGclVQdw");
        lista.add("LvetJ9U_tVY");
        lista.add("6JYIGclVQdw");


    }


    @Override
    protected void onResume() {
        super.onResume();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Lifecycle lifecycle = getLifecycle();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(lista, lifecycle);
        recyclerView.setAdapter(adapter);

    }

}

/*


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

    private void openCamera() {
        Intent intent = new Intent(this,artificial_shovel.class);
        startActivity(intent);
    }

    public void openSettings(){
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }



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


        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.setEnableAutomaticInitialization(false);
        getLifecycle().addObserver(youTubePlayerView);
        initYouTubePlayerView((2));

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


    public void onBackPressed(){
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }



}


}*/
