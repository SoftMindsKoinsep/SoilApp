package eu.soilErasmus.soil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;
public class youtubePlayer extends AppCompatActivity {

    private ArrayList<String> youtubeList;
    private RecyclerView recyclerView;
    private ImageView settings, camera;

    private static Bundle mBundleRecyclerViewState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);

//Alert box pou enimeronei ton xristi

        AlertDialog.Builder builder = new AlertDialog.Builder(youtubePlayer.this);

        View view = getLayoutInflater().inflate(R.layout.dialog_help, null);
        CheckBox mCheckBox = view.findViewById(R.id.checkBox);
        builder.setTitle("Note");
        builder.setMessage("To see all the playlists, scroll by touching on the background space.");
        builder.setView(view);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog mDialog = builder.create();
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
        camera = findViewById(R.id.plant_button);

//lista me tis playlists
        youtubeList = new ArrayList<>();
        youtubeList.add("PLgh0UxNx43uBqUHgjVipN0vXp9pHR9Pkq");
        youtubeList.add("PLgh0UxNx43uBCvdlEq1brjMVrNF6M9WRl");
        youtubeList.add("PLgh0UxNx43uDnAx7H6QaEfVnKi7aiYUNL");
        youtubeList.add("PLgh0UxNx43uAx43WBIU1V6fjZ28nynlUj");
        youtubeList.add("PLgh0UxNx43uDE9s1SoSZdctfApwbx7j0t");

// ftiaxnoume recyclerView
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Lifecycle lifecycle = this.getLifecycle();

        RecyclerView.Adapter<RecyclerViewAdapter.YouTubePlayerViewHolder> adapter = new RecyclerViewAdapter(youtubeList, lifecycle);
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
                openPlants();
            }
        });
    }


// Otan h efarmogi mpei se Paused state, apothikevi thn katastasi tou recyclerView gia na tin anaktisoume sto Restored state
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onResume() {
        hideSystemUI();
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

    private void openPlants() {
        Intent intent = new Intent(this,plant_page.class);
        startActivity(intent);
    }

    public void openSettings(){
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }


    public void hideSystemUI(){
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(),this.getWindow().getDecorView().findViewById(android.R.id.content));
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
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
        finish();
    }


}

