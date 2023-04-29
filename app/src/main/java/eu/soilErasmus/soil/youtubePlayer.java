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
import android.os.Build;
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
        settings = findViewById(R.id.settingsButton3);
        camera = findViewById(R.id.artificial);

        lista = new ArrayList<String>();
        lista.add("PLgh0UxNx43uBqUHgjVipN0vXp9pHR9Pkq");
        lista.add("PLgh0UxNx43uBCvdlEq1brjMVrNF6M9WRl");
        lista.add("PLgh0UxNx43uDE9s1SoSZdctfApwbx7j0t");
        lista.add("PLgh0UxNx43uAx43WBIU1V6fjZ28nynlUj");
        lista.add("PLgh0UxNx43uDnAx7H6QaEfVnKi7aiYUNL");

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

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });


    }
    private void openCamera() {
        Intent intent = new Intent(this,artificial_shovel.class);
        startActivity(intent);
    }

    public void openSettings(){
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

/*

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.YouTubePlayerViewHolder> {

    private final List<String> playlistIds;
    private final Lifecycle lifecycle ;

    @NonNull
    public YouTubePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.youtube_player_item,viewGroup,false);
        return new YouTubePlayerViewHolder(lifecycle, itemView);
    }


    public void onBindViewHolder( YouTubePlayerViewHolder holder, int position) {
        holder.cueVideo(playlistIds.get(position));
        //
    }

    public int getItemCount() {
        return playlistIds.size();
    }

    public RecyclerViewAdapter( List<String> playlistIds,  Lifecycle lifecycle) {
        super();
        this.playlistIds = playlistIds;
        this.lifecycle = lifecycle;
    }
    public static final class YouTubePlayerViewHolder extends RecyclerView.ViewHolder {

        private String currentId = null;
        private YouTubePlayer youTubePlayer = null;

        public void cueVideo(String playlistId){
            currentId = playlistId;
            youTubePlayer.cueVideo(playlistId,0);

        }

        public YouTubePlayerViewHolder( Lifecycle lifecycle, View itemView) {
            super(itemView);

            YouTubePlayerView youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            lifecycle.addObserver(youTubePlayerView);



            View overlayView = itemView.findViewById(R.id.overlay_view);
            overlayView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    YouTubePlayer player = YouTubePlayerViewHolder.this.youTubePlayer;
                    player.play();

                }
            });
            lifecycle.addObserver(youTubePlayerView);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    YouTubePlayerViewHolder.this.youTubePlayer = youTubePlayer;
                    String playerId= YouTubePlayerViewHolder.this.currentId;
                    if(playerId != null){
                        youTubePlayer.cueVideo(playerId,0);

                    }
                }
            });

        }




    }
}






        lista = new ArrayList<String>();
        lista.add("PLgh0UxNx43uBCvdlEq1brjMVrNF6M9WRl");
        lista.add("PLgh0UxNx43uBqUHgjVipN0vXp9pHR9Pkq");
        lista.add("PLgh0UxNx43uDnAx7H6QaEfVnKi7aiYUNL");
        lista.add("PLgh0UxNx43uAx43WBIU1V6fjZ28nynlUj");
        lista.add("PLgh0UxNx43uDE9s1SoSZdctfApwbx7j0t");


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
