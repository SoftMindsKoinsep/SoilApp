package eu.soilErasmus.soil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class videoPlayer extends AppCompatActivity {
    List<ExoPlayer> exoPlayerList;
    Button backButton;
    ImageView soilLogo;
    List<Video> videoPlaylist;
    RecyclerView videoRecyclerView;
    WindowInsetsControllerCompat windowInsetsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoRecyclerView = findViewById(R.id.video_recycler_view);
        soilLogo = findViewById(R.id.soilLogo);
        backButton = findViewById(R.id.backButton);

        exoPlayerList = new ArrayList<ExoPlayer>();
        
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());

            ViewGroup.MarginLayoutParams backLayoutParams = (ViewGroup.MarginLayoutParams) backButton.getLayoutParams();
            ViewGroup.MarginLayoutParams imageLayoutParams = (ViewGroup.MarginLayoutParams) soilLogo.getLayoutParams();
            backLayoutParams.topMargin = insets.top;
            imageLayoutParams.topMargin = insets.top;

            backButton.setLayoutParams(backLayoutParams);
            soilLogo.setLayoutParams(imageLayoutParams);

            return WindowInsetsCompat.CONSUMED;
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());

        controlSystemUI();
        backButton.setOnClickListener(view -> finish());

        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("videoCategoryClicked", 0);
        createPlaylist(intValue);

    }

    private void createPlaylist(int intValue) {
        String[] videoNames,videoUri;

        switch (intValue){

            default:
                videoNames = getResources().getStringArray(R.array.watering_names);
                videoUri = getResources().getStringArray(R.array.watering_uri);
                break;

            case 1:
                videoNames = getResources().getStringArray(R.array.planting_names);
                videoUri = getResources().getStringArray(R.array.planting_uri);
                break;

            case 2:
                videoNames = getResources().getStringArray(R.array.pruning_names);
                videoUri = getResources().getStringArray(R.array.pruning_uri);
                break;

            case 3:
                videoNames = getResources().getStringArray(R.array.mulching_names);
                videoUri = getResources().getStringArray(R.array.mulching_uri);
                break;

            case 4:
                videoNames = getResources().getStringArray(R.array.flower_names);
                videoUri = getResources().getStringArray(R.array.flower_uri);
                break;

            case 5:
                videoNames = getResources().getStringArray(R.array.material_activities_names);
                videoUri = getResources().getStringArray(R.array.material_activities_uri);
                break;

            case 6:
                videoNames = getResources().getStringArray(R.array.harvesting_names);
                videoUri = getResources().getStringArray(R.array.harvesting_uri);
                break;

            case 7:
                videoNames = getResources().getStringArray(R.array.food_names);
                videoUri = getResources().getStringArray(R.array.food_uri);
                break;

            case 8:
                videoNames = getResources().getStringArray(R.array.special_needs_names);
                videoUri = getResources().getStringArray(R.array.special_needs_uri);
                break;

            case 9:
                videoNames = getResources().getStringArray(R.array.sensory_names);
                videoUri = getResources().getStringArray(R.array.sensory_uri);
                break;

            case 10:
                videoNames = getResources().getStringArray(R.array.theme_names);
                videoUri = getResources().getStringArray(R.array.theme_uri);
                break;

            case 11:
                videoNames = getResources().getStringArray(R.array.plant_names);
                videoUri = getResources().getStringArray(R.array.plant_uri);
                break;

            case 12:
                videoNames = getResources().getStringArray(R.array.skills_names);
                videoUri = getResources().getStringArray(R.array.skills_uri);
                break;

            case 13:
                videoNames = getResources().getStringArray(R.array.easy_garden_names);
                videoUri = getResources().getStringArray(R.array.easy_garden_uri);
                break;

            case 14:
                videoNames = getResources().getStringArray(R.array.tools_names);
                videoUri = getResources().getStringArray(R.array.tools_uri);
                break;

            case 15:
                videoNames = getResources().getStringArray(R.array.garden_activities_names);
                videoUri = getResources().getStringArray(R.array.garden_activities_uri);
                break;

            case 16:
                videoNames = getResources().getStringArray(R.array.therapeutic_names);
                videoUri = getResources().getStringArray(R.array.therapeutic_uri);
                break;

            case 17:
                videoNames = getResources().getStringArray(R.array.therapy_names);
                videoUri = getResources().getStringArray(R.array.therapy_uri);
                break;

        }
            playerBuilder(videoNames,videoUri);

    }

    private void playerBuilder(String[] videoNames, String[] videoUri) {
        videoPlaylist = new ArrayList<>();

        for (int i=0; i<videoUri.length; i++) {
            Video video = new Video(videoNames[i],videoUri[i]);
            videoPlaylist.add(video);
        }
        RecyclerViewVideoListPass recyclerViewVideoListPass = new RecyclerViewVideoListPass() {
            @Override
            public void pass(ExoPlayer exoPlayer) {
                exoPlayerList.add(exoPlayer);
            }
        };

        VideoRecyclerViewAdapter adapter = new VideoRecyclerViewAdapter(this,videoPlaylist,recyclerViewVideoListPass);
        videoRecyclerView.setAdapter(adapter);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        controlSystemUI();
    }

    public void controlSystemUI(){

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            soilLogo.setVisibility(View.GONE);
            backButton.setVisibility(View.GONE);
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
            windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        } else {
            soilLogo.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (ExoPlayer video : exoPlayerList) {
            video.stop();

        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        for (ExoPlayer video : exoPlayerList){
            video.release();
        }
    }
}