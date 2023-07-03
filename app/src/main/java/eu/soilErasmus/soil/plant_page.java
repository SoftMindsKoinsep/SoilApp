package eu.soilErasmus.soil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class plant_page extends AppCompatActivity {

    private List<Plant> plantList;
    private RecyclerView plantRecyclerView;
    private Button backButton;

    int [] resourcesList = {
            R.raw.tomato_plant, R.raw.pine, R.raw.pepper, R.raw.watermelon, R.raw.kolokithia,

            R.raw.apple_tree, R.raw.pumpkin, R.raw.carrot, R.raw.clean_shovel, R.raw.clean_shovel,//R.raw.strawberry,

            R.raw.cherry_tree, R.raw.fir,
            //R.raw.peach,
            R.raw.fir2, R.raw.pine, R.raw.nettle,

            R.raw.clean_shovel, R.raw.clean_shovel, R.raw.clean_shovel,R.raw.onion, R.raw.clean_shovel,

            R.raw.clean_shovel, R.raw.clean_shovel, R.raw.radishes, R.raw.broccoli, R.raw.clean_shovel
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_page);

        plantRecyclerView = findViewById(R.id.plant_recycler_view);
        backButton = findViewById(R.id.backButton);

        plantList = new ArrayList<>();
        plantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        plantRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getData();
        hideSystemUI();
    }

    private void getData() {
        String[] plantName = getResources().getStringArray(R.array.plant_name_array);
        String[] plantDescription = getResources().getStringArray(R.array.plant_description_array);

        for (int i=0; i<plantName.length; i++){
            Plant plant = new Plant(plantName[i],plantDescription[i],resourcesList[i]);
            plantList.add(plant);
        }

        PlantRecyclerViewAdapter adapter = new PlantRecyclerViewAdapter(this,plantList);
        plantRecyclerView.setAdapter(adapter);
        plantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void hideSystemUI(){
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(),this.getWindow().getDecorView().findViewById(android.R.id.content));
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }
}
