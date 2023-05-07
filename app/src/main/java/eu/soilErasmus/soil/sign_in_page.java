package eu.soilErasmus.soil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import eu.soilErasmus.soil.databinding.ActivitySignInPageBinding;

public class sign_in_page extends AppCompatActivity {
    ActivitySignInPageBinding binding;
    Button backButton;
    DatabaseHelper databaseHelper;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Boolean loginFlag;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivitySignInPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backButton = findViewById(R.id.backButton);

        pref = getSharedPreferences("Data",MODE_PRIVATE);
        editor = pref.edit();
        loginFlag = pref.getBoolean("ISLOGGEDIN",false);

        databaseHelper = new DatabaseHelper(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });

        if(loginFlag){
            Intent intent = new Intent(this, youtubePlayer.class);
            startActivity(intent);
        }


        binding.secondLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                if(email.equals("") || password.equals(""))
                    Toast.makeText(sign_in_page.this, "Please fill in your credentials", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email,password);

                    if(checkCredentials){
                        if(binding.checkBox.isChecked()){
                            editor.putString("email",email);
                            editor.putString("password",password);
                            editor.putBoolean("ISLOGGEDIN", true);
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(),youtubePlayer.class);
                            startActivity(intent);
                        } else{
                            Intent intent = new Intent(getApplicationContext(),youtubePlayer.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(sign_in_page.this, "Wrong credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseHelper.close();
    }

}