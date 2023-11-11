package eu.soilErasmus.soil;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class about_page extends AppCompatActivity {
    Button backButton;
    ImageView erasmusImage,fundedByImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> finish());

        erasmusImage = findViewById(R.id.erasmusImage);
        fundedByImage = findViewById(R.id.fundedByImage);

        erasmusImage.setBackgroundColor(Color.TRANSPARENT);
        fundedByImage.setBackgroundColor(Color.TRANSPARENT);

        //Analoga me ta insets ton status bars, allazei to UI
        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);
        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) backButton.getLayoutParams();
            marginLayoutParams.topMargin = insets.top;
            backButton.setLayoutParams(marginLayoutParams);

            return WindowInsetsCompat.CONSUMED;
        });
    }
}