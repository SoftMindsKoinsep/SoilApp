package eu.soilErasmus.soil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Dilono pos tha xeiristo manually το UI (Navigation bar, Status bar)
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

//Analoga me ta insets ton status bars, allazei to UI
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {

            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            Button settingsButton = view.findViewById(R.id.settingsButton);

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) settingsButton.getLayoutParams();
            marginLayoutParams.topMargin = insets.top + 10;
            settingsButton.setLayoutParams(marginLayoutParams);

            return WindowInsetsCompat.CONSUMED;
        });

        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupButton);

    }

    @Override
    protected void onResume() {
        super.onResume();
        login.setOnClickListener(view -> openLogin());
        signup.setOnClickListener(view -> openSignup());
    }

    public void openLogin(){
        Intent intent = new Intent(this, log_in_page.class);
        startActivity(intent);
    }

    public void openSignup(){
        Intent intent = new Intent(this, sign_up_page.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }

//otan patietai "piso", den termatizei h efarmogh alla metaferese sto home page
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}