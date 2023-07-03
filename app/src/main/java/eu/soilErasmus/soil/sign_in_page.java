package eu.soilErasmus.soil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import eu.soilErasmus.soil.databinding.ActivitySignInPageBinding;

public class sign_in_page extends AppCompatActivity {
    ActivitySignInPageBinding binding;
    Button backButton,settingsButton;
    DatabaseHelper databaseHelper;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Boolean loginFlag;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignInPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backButton = findViewById(R.id.backButton);
        settingsButton = findViewById(R.id.settingsButtonSignIn);

        pref = getSharedPreferences("Data",MODE_PRIVATE);
        editor = pref.edit();
        loginFlag = pref.getBoolean("ISLOGGEDIN",false);

        databaseHelper = new DatabaseHelper(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (v, insets) -> {
            boolean imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime());

            if(imeVisible){
                WindowCompat.setDecorFitsSystemWindows(getWindow(),true);
                WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(),getWindow().getDecorView().findViewById(android.R.id.content));
                controller.show(WindowInsetsCompat.Type.navigationBars());
            }

            else{
                WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
                WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(),getWindow().getDecorView().findViewById(android.R.id.content));
                controller.hide(WindowInsetsCompat.Type.systemBars());
                controller.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
            }
            return insets;
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettings();
            }
        });

        if(loginFlag){
            Intent intent = new Intent(this, navigation_page.class);
            startActivity(intent);
        }


        binding.secondLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                if(email.equals("") || password.equals(""))
                    Toast.makeText(sign_in_page.this, "Please fill in your credentials", Toast.LENGTH_SHORT).show();
                else if(email.equals("guest") && password.equals("guest")){
                    Intent intent = new Intent(getApplicationContext(), navigation_page.class);
                    startActivity(intent);
                }
                else{
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email,password);

                    if(checkCredentials){
                        if(binding.checkBox.isChecked()){
                            editor.putString("email",email);
                            editor.putString("password",password);
                            editor.putBoolean("ISLOGGEDIN", true);
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), navigation_page.class);
                            startActivity(intent);
                        } else{
                            Intent intent = new Intent(getApplicationContext(), navigation_page.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(sign_in_page.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void openSettings() {
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseHelper.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}