package eu.soilErasmus.soil;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class account_info_page extends AppCompatActivity {
    Button backButton;
    TextView firstNameText,lastNameText,emailText,phoneText;
    String firstName,lastName,email,phone;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(view -> finish());

        firstNameText = findViewById(R.id.accountName);
        lastNameText = findViewById(R.id.accountLastName);
        emailText = findViewById(R.id.accountEmail);
        phoneText = findViewById(R.id.accountPhone);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            showUserInfo();
        } else {
            Toast.makeText(this, "Please create an account firstly.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showUserInfo() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userDetails = snapshot.getValue(User.class);
                if (userDetails!= null){
                    firstName = userDetails.getFirstName();
                    lastName = userDetails.getLastName();
                    email = userDetails.getEmail();
                    phone = userDetails.getPhone();

                    firstNameText.setText(firstName);
                    lastNameText.setText(lastName);
                    emailText.setText(email);
                    phoneText.setText(phone);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(account_info_page.this, "Please create an account firstly.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}