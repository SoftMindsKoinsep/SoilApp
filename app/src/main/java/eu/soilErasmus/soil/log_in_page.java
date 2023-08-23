package eu.soilErasmus.soil;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class log_in_page extends AppCompatActivity {

    EditText loginEmail,loginPassword;
    Button backButton,loginButton,forgotPassword,cancelDialog,acceptDialog;
    FirebaseAuth firebaseAuth;
    ImageView toggle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            Button backButton = view.findViewById(R.id.backButton);
            ImageView soilLogo = view.findViewById(R.id.soilLogo);

            ViewGroup.MarginLayoutParams backLayoutParams = (ViewGroup.MarginLayoutParams) backButton.getLayoutParams();
            ViewGroup.MarginLayoutParams imageLayoutParams = (ViewGroup.MarginLayoutParams) soilLogo.getLayoutParams();
            backLayoutParams.topMargin = insets.top;
            imageLayoutParams.topMargin = insets.top;

            backButton.setLayoutParams(backLayoutParams);
            soilLogo.setLayoutParams(imageLayoutParams);

            return WindowInsetsCompat.CONSUMED;
        });

        backButton = findViewById(R.id.backButton);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginButton);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        toggle = findViewById(R.id.passwordToggle);
        forgotPassword = findViewById(R.id.forgotPassword);
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

        backButton.setOnClickListener(view -> finish());

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
                Intent intent = new Intent(getApplicationContext(), navigation_page.class);
                startActivity(intent);
            }
            else{
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(), navigation_page.class);
                            startActivity(intent);
                            finish();
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
}