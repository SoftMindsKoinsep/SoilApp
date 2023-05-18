package eu.soilErasmus.soil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class youtubePlayer extends AppCompatActivity {

    private List<String> lista;
    private RecyclerView recyclerView;
    private ImageView settings, camera;

    private static Bundle mBundleRecyclerViewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_youtube_player);

//Alert box pou enimeronei ton xristi

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(youtubePlayer.this);
        @SuppressLint("InflateParams")
        View mView = getLayoutInflater().inflate(R.layout.dialog_help, null);
        CheckBox mCheckBox = mView.findViewById(R.id.checkBox);
        mBuilder.setTitle("Note");
        mBuilder.setMessage("To see all the playlists, scroll by touching on the background space.");
        mBuilder.setView(mView);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

// checkbox gia na min ksanaemfanistei to minima
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    storeDialogStatus(true);
                } else {
                    storeDialogStatus(false);
                }
            }
        });

        if (getDialogStatus()) {
            mDialog.hide();
        } else {
            mDialog.show();
        }


        recyclerView = findViewById(R.id.recycler_view);
        settings = findViewById(R.id.settingsButton3);
        camera = findViewById(R.id.artificial);

//lista me tis playlists
        lista = new ArrayList<String>();
        lista.add("PLgh0UxNx43uBqUHgjVipN0vXp9pHR9Pkq");
        lista.add("PLgh0UxNx43uBCvdlEq1brjMVrNF6M9WRl");
        lista.add("PLgh0UxNx43uDnAx7H6QaEfVnKi7aiYUNL");
        lista.add("PLgh0UxNx43uAx43WBIU1V6fjZ28nynlUj");
        lista.add("PLgh0UxNx43uDE9s1SoSZdctfApwbx7j0t");

// ftiaxnoume recyclerView
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Lifecycle lifecycle = this.getLifecycle();
        RecyclerView.Adapter<RecyclerViewAdapter.YouTubePlayerViewHolder> adapter = new RecyclerViewAdapter(lista, lifecycle);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_DOWN)
                    rv.requestDisallowInterceptTouchEvent(true);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

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


// Otan h efarmogh mpei se Paused state, apothikevei thn katastasi tou recyclerView gia na tin anaktisoume sto Restored state
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        super.onResume();

        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable("recycler_state");
            Objects.requireNonNull(recyclerView.getLayoutManager()).onRestoreInstanceState(listState);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable("recycler_state", listState);
    }

    private void openCamera() {
        Intent intent = new Intent(this,artificial_shovel.class);
        startActivity(intent);
    }

    public void openSettings(){
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }


//Shared Preferences gia to checkbox tou alertBox
    private void storeDialogStatus(boolean isChecked){
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("item", isChecked);
        mEditor.apply();
    }

    private boolean getDialogStatus(){
        SharedPreferences mSharedPreferences = getSharedPreferences("CheckItem", MODE_PRIVATE);
        return mSharedPreferences.getBoolean("item", false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

//Allagi System UI analoga me tin katefthinsi tis othonis
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}

