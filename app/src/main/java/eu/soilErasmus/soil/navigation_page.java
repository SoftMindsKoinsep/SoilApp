package eu.soilErasmus.soil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class navigation_page extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    List<MenuModel> menuModelList;
    Map <MenuModel, String[]> itemCollection;
    NavigationView navigationView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Button navButton;
    ImageView videoButton,ARButton,soilButton,disclaimerButton;
    View headerView;
    TextView welcomeText;
    String message;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    ConstraintLayout swipeableSpace;
    int lastPosition = -1;
    int VIDEOS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_page);

//analoga me ta insets ton status bars, allazei to UI
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            Button navButton = view.findViewById(R.id.openNavButton);
            ImageView soilLogo = view.findViewById(R.id.soilLogo);

            ViewGroup.MarginLayoutParams backLayoutParams = (ViewGroup.MarginLayoutParams) navButton.getLayoutParams();
            ViewGroup.MarginLayoutParams imageLayoutParams = (ViewGroup.MarginLayoutParams) soilLogo.getLayoutParams();
            backLayoutParams.topMargin = insets.top;
            imageLayoutParams.topMargin = insets.top;

            navButton.setLayoutParams(backLayoutParams);
            soilLogo.setLayoutParams(imageLayoutParams);

            return WindowInsetsCompat.CONSUMED;
        });

        navButton = findViewById(R.id.openNavButton);

        videoButton = findViewById(R.id.videoButton);
        ARButton = findViewById(R.id.ARButton);
        soilButton = findViewById(R.id.soilButton);
        disclaimerButton = findViewById(R.id.disclaimerButton);

        swipeableSpace = findViewById(R.id.swipeableSpace);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.navigation_view);
        headerView = navigationView.getHeaderView(0);
        welcomeText = headerView.findViewById(R.id.welcomeText);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            getUserName();

        } else {
            message = getString(R.string.welcome_text ,"");
            welcomeText.setText(message);
        }

        getData();

    }

    @Override
    protected void onResume() {
        super.onResume();

        navButton.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        videoButton.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
            expandableListView.expandGroup(VIDEOS);
        });

        ARButton.setOnClickListener(view -> openPlants());
        soilButton.setOnClickListener(view -> openAbout());
        disclaimerButton.setOnClickListener(view -> openSettings());


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                MenuModel selectedGroup = expandableListAdapter.getGroup(i);

                switch (selectedGroup.getMenuTitle()) {
                    case "About":
                        openAbout();
                        break;

                    case "Settings":
                        openSettings();
                        break;

                    case "Plants":
                        openPlants();
                        break;

                    case "Videos":
                        break;
                }
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                if (lastPosition != -1 && i != lastPosition) {
                    expandableListView.collapseGroup(lastPosition);
                }
                lastPosition = i;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                MenuModel selectedGroup = expandableListAdapter.getGroup(i);
//
                if (selectedGroup.getMenuTitle().equals("Videos")) {
                    Intent intent = new Intent(getApplicationContext(), videoPlayer.class);
                    intent.putExtra("videoCategoryClicked", i1);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    private void getData() {
        menuModelList = new ArrayList<MenuModel>();
        String[] menuItemList = getResources().getStringArray(R.array.menu_items);
        String[] categoryList= getResources().getStringArray(R.array.video_categories);

        for (int i=0; i<menuItemList.length; i++){
            MenuModel menuModel;

            if(i==0){
                menuModel = new MenuModel(menuItemList[i],R.drawable.baseline_keyboard_arrow_right_24);
                menuModelList.add(menuModel);
            } else {
                menuModel = new MenuModel(menuItemList[i]);
                menuModelList.add(menuModel);
            }
        }

        itemCollection = new HashMap<>();
        itemCollection.put(menuModelList.get(0),categoryList);

        expandableListView = findViewById(R.id.expanded_list_view);
        expandableListAdapter = new ExpandableListAdapter(this, menuModelList, itemCollection);
        expandableListView.setAdapter(expandableListAdapter);

    }

    private void openSettings() {
        Intent intent = new Intent(this, settings_page.class);
        startActivity(intent);
    }

    private void openPlants() {
        Intent intent = new Intent(this, plant_page.class);
        startActivity(intent);
    }

    private void openAbout() {
        Intent intent = new Intent(this, about_page.class);
        startActivity(intent);
    }

    public void getUserName(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userDetails = snapshot.getValue(User.class);
                if (userDetails!= null){
                    message = getString(R.string.welcome_text , userDetails.getFirstName());
                    welcomeText.setText(message);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}