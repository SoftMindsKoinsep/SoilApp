package eu.soilErasmus.soil;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

    }

    public void openLogin(){
        Intent intent = new Intent(this, artificial_shovel.class);
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

}