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

        WindowCompat.setDecorFitsSystemWindows(getWindow(),false);

        ViewCompat.setOnApplyWindowInsetsListener(getWindow().getDecorView(), (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars());
            backButton = view.findViewById(R.id.backButton);

            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) backButton.getLayoutParams();
            marginLayoutParams.topMargin = insets.top;
            backButton.setLayoutParams(marginLayoutParams);

            return WindowInsetsCompat.CONSUMED;
        });
        backButton = findViewById(R.id.backButton);
        erasmusImage = findViewById(R.id.erasmusImage);
        fundedByImage = findViewById(R.id.fundedByImage);

        erasmusImage.setBackgroundColor(Color.TRANSPARENT);
        fundedByImage.setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        backButton.setOnClickListener(view -> finish());
    }
}