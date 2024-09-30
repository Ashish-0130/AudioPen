package com.example.audiopen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotesActivity extends AppCompatActivity {

    private ImageView backgroundImageView;
    private SeekBar blurSeekBar, fontSizeSeekBar, fontColorSeekBar;
    private TextView notesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        backgroundImageView = findViewById(R.id.backgroundImageView);
        blurSeekBar = findViewById(R.id.blur_seekbar);
        fontSizeSeekBar = findViewById(R.id.font_size_seekbar);
        fontColorSeekBar = findViewById(R.id.font_color_seekbar);
        notesTextView = findViewById(R.id.notes_text_view);

        // Load and set background image (example image path, replace with actual)
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.example_background);
        backgroundImageView.setImageBitmap(bitmap);

        // Set up sliders
        blurSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                applyBlurEffect(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                notesTextView.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        fontColorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                notesTextView.setTextColor(Color.argb(255, progress, 0, 0));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void applyBlurEffect(int blurRadius) {
        Bitmap originalBitmap = ((BitmapDrawable) backgroundImageView.getDrawable()).getBitmap();
        Bitmap blurredBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        // Your blur effect implementation
    }
}
