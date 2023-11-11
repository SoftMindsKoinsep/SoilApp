package eu.soilErasmus.soil;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class plant_page extends AppCompatActivity {

    List<Plant> plantList;
    RecyclerView plantRecyclerView;
    Button backButton;
    int [] resourcesList = {
            R.raw.pumpkin, R.raw.radishes, R.raw.carrot, R.raw.broccoli,

            R.raw.onion ,R.raw.cauliflower, R.raw.fir, R.raw.fir2,

            R.raw.nettle ,R.raw.kolokithia, R.raw.pepper, R.raw.tomato_plant
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_page);

//analoga me ta insets ton status bars, allazei to UI
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
        plantRecyclerView = findViewById(R.id.plant_recycler_view);
        backButton = findViewById(R.id.backButton);

        plantList = new ArrayList<>();
        plantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        plantRecyclerView.setHasFixedSize(true);
        createPlantItems();

        backButton.setOnClickListener(view -> finish());

    }
//ena recycler view gia ta plant items

    private void createPlantItems() {
        String[] plantName = getResources().getStringArray(R.array.plant_name_array);
        for (int i=0; i<plantName.length; i++){
            Plant plant = new Plant(plantName[i],resourcesList[i]);
            plantList.add(plant);
        }
        PlantRecyclerViewAdapter adapter = new PlantRecyclerViewAdapter(this,plantList);
        plantRecyclerView.setAdapter(adapter);
    }
}
