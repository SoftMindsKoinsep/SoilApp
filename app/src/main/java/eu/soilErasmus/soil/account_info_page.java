package eu.soilErasmus.soil;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

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
    ImageView soilLogo;
    String firstName,lastName,email,phone;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        backButton = findViewById(R.id.backButton);
        soilLogo = findViewById(R.id.soilLogo);

        firstNameText = findViewById(R.id.accountName);
        lastNameText = findViewById(R.id.accountLastName);
        emailText = findViewById(R.id.accountEmail);
        phoneText = findViewById(R.id.accountPhone);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) backButton.getLayoutParams();
            ViewGroup.MarginLayoutParams imageLayoutParams = (ViewGroup.MarginLayoutParams) soilLogo.getLayoutParams();

            marginLayoutParams.topMargin = insets.top;
            imageLayoutParams.topMargin = insets.top;

            soilLogo.setLayoutParams(imageLayoutParams);
            backButton.setLayoutParams(marginLayoutParams);

            return WindowInsetsCompat.CONSUMED;
        });


        if(firebaseUser != null){
            showUserInfo();
        } else {
            Toast.makeText(this, "Please create an account firstly.", Toast.LENGTH_SHORT).show();
        }


        backButton.setOnClickListener(view -> finish());

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