package eu.soilErasmus.soil;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class settings_page extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    RelativeLayout accountData,logOut;
    Button backButton;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings_page);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        accountData = findViewById(R.id.accountButton);
        accountData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountData();
            }
        });

        pref = getSharedPreferences("Data",MODE_PRIVATE);
        editor = pref.edit();
        logOut = findViewById(R.id.logout_button);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        databaseHelper = new DatabaseHelper(this);
        databaseHelper.deleteData();
        Intent intent = new Intent(this, MainActivity.class);
        editor.clear();
        editor.apply();
        startActivity(intent);
    }


    private void accountData() {
        databaseHelper = new DatabaseHelper(this);
        Cursor result = databaseHelper.getData();
        if (result.getCount()== 0) {
            Toast.makeText(this, "Please create an account firstly", Toast.LENGTH_SHORT).show();
            return;
        }
            StringBuffer buffer = new StringBuffer();
            while (result.moveToNext()){
                buffer.append("Name :" + result.getString(0) + "\n");
                buffer.append("LastName :" + result.getString(1) + "\n");
                buffer.append("Email :" + result.getString(2) + "\n");
                buffer.append("Phone :" + result.getString(3) + "\n");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(settings_page.this);
        builder.setCancelable(true);
        builder.setTitle("User Data");
        builder.setMessage(buffer.toString());
        builder.show();
    }

}