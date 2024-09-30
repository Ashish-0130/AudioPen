package com.example.audiopen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddBackgroundActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    ImageView selectedImage;
    Button selectImageButton, addBackgroundButton;
    EditText imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_background);

        selectedImage = findViewById(R.id.selected_image_view);
        selectImageButton = findViewById(R.id.select_image_button);
        addBackgroundButton = findViewById(R.id.add_image_button);
        imageName = findViewById(R.id.image_name);

        selectImageButton.setOnClickListener(v -> openGallery());

        addBackgroundButton.setOnClickListener(v -> {
            String name = imageName.getText().toString().trim();
            if (!name.isEmpty()) {
                // Here, add the image to your spinner in MainActivity
                Toast.makeText(AddBackgroundActivity.this, "Image Added: " + name, Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("imageName", name);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(AddBackgroundActivity.this, "Please enter a name for the image.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImage.setImageURI(data.getData());
        }
    }
}
