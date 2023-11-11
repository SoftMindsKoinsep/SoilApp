package eu.soilErasmus.soil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class settings_page extends AppCompatActivity {

    private RecyclerView itemRecyclerView;
    private List<SettingsItem> itemList;
    Button backButton;

    int[] itemImages = {R.drawable.baseline_person_24,R.drawable.baseline_lock_24
            ,R.drawable.baseline_delete_24,R.drawable.baseline_logout_24};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

//analoga me ta insets ton status bars, allazei to UI
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            backButton = view.findViewById(R.id.backButton);
            ImageView soilLogo = view.findViewById(R.id.soilLogo);

            ViewGroup.MarginLayoutParams backLayoutParams = (ViewGroup.MarginLayoutParams) backButton.getLayoutParams();
            ViewGroup.MarginLayoutParams imageLayoutParams = (ViewGroup.MarginLayoutParams) soilLogo.getLayoutParams();
            backLayoutParams.topMargin = insets.top + 10;
            imageLayoutParams.topMargin = insets.top;

            backButton.setLayoutParams(backLayoutParams);
            soilLogo.setLayoutParams(imageLayoutParams);

            return WindowInsetsCompat.CONSUMED;
        });

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        itemRecyclerView = findViewById(R.id.settings_recycler_view);

        itemList = new ArrayList<>();
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemRecyclerView.setHasFixedSize(true);

        createSettingsItems();
    }

//ena recycler view gia ta settings items
    private void createSettingsItems() {
        String[] itemNames = getResources().getStringArray(R.array.settings_items);

        for( int i=0; i<itemImages.length; i++){
            SettingsItem item = new SettingsItem(itemImages[i],itemNames[i]);
            itemList.add(item);

        }

        SettingsRecyclerViewAdapter adapter = new SettingsRecyclerViewAdapter(this,itemList);
        itemRecyclerView.setAdapter(adapter);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}