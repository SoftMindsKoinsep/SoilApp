package eu.soilErasmus.soil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Mia arxiki selida me 3 koumpia

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupButton);
    }

    @Override
    protected void onResume() {
        super.onResume();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }

        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignup();
            }

        });

        //krivo to SystemUI
        hideSystemUI();
    }

    public void openLogin(){
        Intent intent = new Intent(this, sign_in_page.class);
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


    public void hideSystemUI(){
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(),getWindow().getDecorView().findViewById(android.R.id.content));
        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }


    //otan patas "piso", den termatizei h efarmogh alla metaferese sto home page
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}