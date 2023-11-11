package eu.soilErasmus.soil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Pattern;

public class sign_up_page extends AppCompatActivity {

    Button backButton, createAccount;
    EditText signupName, signupLastName, signupEmail, signupPhone, signupPassword, signupConfirmPassword;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        backButton = findViewById(R.id.backButton);

        createAccount = findViewById(R.id.createAccount);
        signupName = findViewById(R.id.signupName);
        signupLastName = findViewById(R.id.signupLastname);
        signupEmail = findViewById(R.id.signupEmail);
        signupPhone = findViewById(R.id.signupPhone);
        signupPassword = findViewById(R.id.signupPassword);
        signupConfirmPassword = findViewById(R.id.signupConfirm);

    }

    @Override
    protected void onResume() {
        super.onResume();

//analoga me ta insets tou navigation bar kai me to state tou keyboard, allazei to UI
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (v, windowInsets) -> {
            boolean imeVisible = windowInsets.isVisible(WindowInsetsCompat.Type.ime());
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars());

            ViewGroup.MarginLayoutParams buttonLayoutParams = (ViewGroup.MarginLayoutParams) backButton.getLayoutParams();

            buttonLayoutParams.bottomMargin = insets.bottom + 50 ;
            WindowCompat.setDecorFitsSystemWindows(getWindow(), imeVisible);

            return windowInsets;
        });

        backButton.setOnClickListener(view -> finish());

        createAccount.setOnClickListener(view -> {
            String name = signupName.getText().toString();
            String lastname = signupLastName.getText().toString();
            String email = signupEmail.getText().toString();
            String phone = signupPhone.getText().toString();
            String password = signupPassword.getText().toString();
            String confirmPassword = signupConfirmPassword.getText().toString();

            if (name.equals("") || lastname.equals("") || email.equals("") || phone.equals("") || password.equals("") || confirmPassword.equals(""))
                Toast.makeText(sign_up_page.this, "Please fill in your information", Toast.LENGTH_SHORT).show();
            else{

                if (isValid(password,confirmPassword)){
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            User user = new User(name, lastname, email, phone);
                            databaseReference = FirebaseDatabase.getInstance("https://soilapp-6e872-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users");
                            databaseReference.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(task1 -> {

                                Toast.makeText(sign_up_page.this, "Signed up Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), navigation_page.class);
                                startActivity(intent);
                                finish();
                            });
                        } else {
                            try {throw (Objects.requireNonNull(task.getException()));}
                            catch (Exception e) {Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();}
                        }
                    });
                }
            }
        });
    }

// elegxos orthotitas tou password pou dothike
    public boolean isValid(String password,String confirmPassword){
        Pattern numberPattern = Pattern.compile("[0-9]");
        Pattern upperCasePattern = Pattern.compile("[A-Z ]");
        Pattern lowerCasePattern = Pattern.compile("[a-z ]");
        boolean flag = true;

        if(password.length()<8){
            Toast.makeText(sign_up_page.this, "Your password must be at least 8 characters long!", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if(password.length()>20){
            Toast.makeText(sign_up_page.this, "Your password must not be longer than 20 characters!", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if(!upperCasePattern.matcher(password).find() && !lowerCasePattern.matcher(password).find()){
            Toast.makeText(sign_up_page.this, "Your password must contain a letter!", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if(!numberPattern.matcher(password).find()){
            Toast.makeText(sign_up_page.this, "Your password must contain a number!", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(sign_up_page.this, "Your password does not match your Confirm Password!", Toast.LENGTH_SHORT).show();
            flag = false;
        }

        return flag;
    }
}