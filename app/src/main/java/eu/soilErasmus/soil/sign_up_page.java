package eu.soilErasmus.soil;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import eu.soilErasmus.soil.databinding.ActivitySignUpPageBinding;

public class sign_up_page extends AppCompatActivity {

    Button backButton;
    ActivitySignUpPageBinding binding;
    DatabaseHelper databaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_sign_up_page);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        databaseHelper = new DatabaseHelper(this);

        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.signupName.getText().toString();
                String lastname = binding.signupLastname.getText().toString();
                String email = binding.signupEmail.getText().toString();
                String phone = binding.signupPhone.getText().toString();
                String password = binding.signupPassword .getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();


                if (name.equals("") || lastname.equals("") || email.equals("") || phone.equals("") || password.equals("") || confirmPassword.equals(""))
                    Toast.makeText(sign_up_page.this, "Παρακαλώ συμπληρώστε όλα τα πεδία", Toast.LENGTH_SHORT).show();
                else{
                    if (password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if (!checkUserEmail){
                            Boolean insert = databaseHelper.insertData(name,lastname,email,phone,password);

                            if (insert){
                                Toast.makeText(sign_up_page.this, "Εγραφήκατε Επιτυχώς!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),sign_in_page.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(sign_up_page.this, "Απέτυχε η εγγραφή!", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(sign_up_page.this, "Υπάρχει ήδη χρήστης με αυτό το email", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(sign_up_page.this, "Λάθος κωδικός", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });
    }
}