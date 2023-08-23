package eu.soilErasmus.soil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.LinearLayoutManager;

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
    ExoPlayerRecyclerView videoRecyclerView;
    ViewGroup.LayoutParams thisLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

        videoRecyclerView = findViewById(R.id.video_recycler_view);
        soilLogo = findViewById(R.id.soilLogo);
        backButton = findViewById(R.id.backButton);
        thisLayout = videoRecyclerView.getLayoutParams();
        exoPlayerList = new ArrayList<ExoPlayer>();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            videoRecyclerView.setLayoutParams(layoutParams);
            soilLogo.setVisibility(View.GONE);
            backButton.setVisibility(View.GONE);
        } else {

            controlSystemUI();
            soilLogo.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            videoRecyclerView.setLayoutParams(thisLayout);
        }
        
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onResume(){
        super.onResume();
        backButton.setOnClickListener(view -> finish());
        Intent mIntent = getIntent();
        int intValue = mIntent.getIntExtra("videoCategoryClicked", 0);
        createPlaylist(intValue);

    }
    private void createPlaylist(int intValue) {
        String[] videoNames,videoUri;

        switch (intValue){

            default:
                videoNames = getResources().getStringArray(R.array.irrigation_names);
                videoUri = getResources().getStringArray(R.array.irrigation_uri);
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
                videoNames = getResources().getStringArray(R.array.test_name);
                videoUri = getResources().getStringArray(R.array.test);
                break;

            case 5:
                videoNames = getResources().getStringArray(R.array.test_name);
                videoUri = getResources().getStringArray(R.array.test);
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
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT);
            videoRecyclerView.setLayoutParams(layoutParams);
            soilLogo.setVisibility(View.GONE);
            backButton.setVisibility(View.GONE);
        } else {
            controlSystemUI();
            soilLogo.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            videoRecyclerView.setLayoutParams(thisLayout);
        }
    }

    public void controlSystemUI(){
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            Button backButton = view.findViewById(R.id.backButton);
            ImageView soilLogo = view.findViewById(R.id.soilLogo);

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
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) finish();
        else finish();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        for (ExoPlayer video : exoPlayerList){
            video.release();
        }
    }

}