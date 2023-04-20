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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivitySignInPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());                      //setContentView(R.layout.activity_sign_in_page);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pref = getSharedPreferences("Data",MODE_PRIVATE);
        editor = pref.edit();
        boolean login = pref.getBoolean("ISLOGGEDIN",false);
        if(login){
            Intent intent = new Intent(this, youtubePlayer.class);
            startActivity(intent);
        }

        databaseHelper = new DatabaseHelper(this);
        binding.secondLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();

                if(email.equals("") || password.equals(""))
                    Toast.makeText(sign_in_page.this, "Παρακαλώ συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email,password);

                    if(checkCredentials){
                        if(binding.checkBox.isChecked()){
                            editor.putString("email",email);
                            editor.putString("password",password);
                            editor.putBoolean("ISLOGGEDIN", true);
                            editor.apply();
                            Toast.makeText(sign_in_page.this, "Συνδεθήκατε Επιτυχώς", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),youtubePlayer.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(sign_in_page.this, "Συνδεθήκατε Επιτυχώς", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),youtubePlayer.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(sign_in_page.this, "Λάθος Email ή Κωδικός", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public void settings(View view) {
        Intent intent = new Intent(this,settings_page.class);
        startActivity(intent);
    }
}