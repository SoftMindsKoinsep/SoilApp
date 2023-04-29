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
        setContentView(binding.getRoot());

        backButton = findViewById(R.id.backButton);
        databaseHelper = new DatabaseHelper(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                    Toast.makeText(sign_up_page.this, "Please fill in your information", Toast.LENGTH_SHORT).show();
                else{
                    if (password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if (!checkUserEmail){
                            Boolean insert = databaseHelper.insertData(name,lastname,email,phone,password);

                            if (insert){
                                Toast.makeText(sign_up_page.this, "Signed up Succesfully!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(view.getContext(),sign_in_page.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(sign_up_page.this, "Failure!", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(sign_up_page.this, "There is already a user with this email", Toast.LENGTH_SHORT).show();
                        }
                    } else{
                        Toast.makeText(sign_up_page.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        databaseHelper.close();
    }
}