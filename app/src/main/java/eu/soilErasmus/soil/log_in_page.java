package eu.soilErasmus.soil;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class log_in_page extends AppCompatActivity {

    EditText loginEmail,loginPassword;
    Button backButton,loginButton,forgotPassword,cancelDialog,acceptDialog;

    FirebaseAuth firebaseAuth;
    ImageView soilLogo,toggle;
    boolean deleteFlag = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

        backButton = findViewById(R.id.backButton);
        soilLogo = findViewById(R.id.soilLogo);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginButton);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        toggle = findViewById(R.id.passwordToggle);
        forgotPassword = findViewById(R.id.forgotPassword);

        if(getIntent().getExtras() != null) {
            Bundle deleteIntent = getIntent().getExtras();
            deleteFlag = deleteIntent.getBoolean("Account Delete");
            loginButton.setText(R.string.delete);
        }

        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());

            ViewGroup.MarginLayoutParams buttonLayoutParams = (ViewGroup.MarginLayoutParams) backButton.getLayoutParams();
            ViewGroup.MarginLayoutParams imageLayoutParams = (ViewGroup.MarginLayoutParams) soilLogo.getLayoutParams();
            buttonLayoutParams.topMargin = insets.top;
            imageLayoutParams.topMargin = insets.top;

            backButton.setLayoutParams(buttonLayoutParams);
            soilLogo.setLayoutParams(imageLayoutParams);

            return WindowInsetsCompat.CONSUMED;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, navigation_page.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        forgotPassword.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(log_in_page.this);
            View customLayout = getLayoutInflater().inflate(R.layout.dialog_forgot_password, null);
            builder.setView(customLayout);

            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

            acceptDialog = dialog.findViewById(R.id.accept_forgot_password);
            cancelDialog = dialog.findViewById(R.id.cancel_forgot_password);
            acceptDialog.setOnClickListener(view1 -> {
                EditText emailAddress = customLayout.findViewById(R.id.emailText);
                checkEmailAddress(emailAddress.getText().toString());
                dialog.dismiss();
            });

            cancelDialog.setOnClickListener(view2 -> dialog.dismiss());

        });

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(log_in_page.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        toggle.setImageResource(R.drawable.baseline_radio_button_unchecked_24);
        toggle.setOnClickListener(view -> {

            if (loginPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){

                loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                loginPassword.setSelection(loginPassword.getText().length());
                toggle.setImageResource(R.drawable.baseline_radio_button_unchecked_24);
            } else {

                loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                loginPassword.setSelection(loginPassword.getText().length());
                toggle.setImageResource(R.drawable.baseline_radio_button_checked_24);

            }
        });

        loginButton.setOnClickListener(view -> {

            String email = loginEmail.getText().toString();
            String password = loginPassword.getText().toString();

            if(email.equals("") || password.equals(""))
                Toast.makeText(this, "Please fill in your credentials", Toast.LENGTH_SHORT).show();

            else if(email.equals("guest") && password.equals("guest")){
                Intent intent = new Intent(log_in_page.this, navigation_page.class);
                startActivity(intent);

            }else{
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        if (deleteFlag){
                            deleteUserData(firebaseAuth, firebaseAuth.getCurrentUser());

                        }else{
                            Intent intent = new Intent(log_in_page.this, navigation_page.class);
                            startActivity(intent);
                            finish();
                        }
                    } else Toast.makeText(this, "Your email or password is wrong.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void checkEmailAddress(String emailAddress) {
        if (emailAddress.isEmpty()) return;
        firebaseAuth.sendPasswordResetEmail(emailAddress).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                Toast.makeText(log_in_page.this, "Please check your Email.", Toast.LENGTH_SHORT).show();
            else {
                try {throw (Objects.requireNonNull(task.getException()));}
                catch (Exception e) {Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();}
            }
        });
    }


    private void deleteUserData(FirebaseAuth firebaseAuth,FirebaseUser firebaseUser){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                deleteUser(firebaseAuth,firebaseUser);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,e.getMessage());
                Toast.makeText(log_in_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteUser(FirebaseAuth firebaseAuth,FirebaseUser firebaseUser) {

        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseAuth.signOut();
                    Toast.makeText(log_in_page.this, "Account deleted successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(log_in_page.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    log_in_page.this.startActivity(intent);

                } else {
                    try {
                        throw task.getException();
                    } catch (Exception e){
                        Toast.makeText(log_in_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(log_in_page.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}