package foodget.ihm.foodget.activities;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import foodget.ihm.foodget.R;

public class CameraActivity extends AppCompatActivity {

    static final int REQUEST_ID_IMAGE_CAPTURE = 100;
    static final int PERMISSIONS_REQUEST_READ_MEDIA = 1000;
    String pictureName = "_test.jpg";
    ImageView imageView;
    Button buttonSave;
    Bitmap picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        //getwidgets
        Button takeImage = findViewById(R.id.button_image_capture);
        buttonSave = findViewById(R.id.button_image_save);
        Button buttonLoad = findViewById(R.id.button_image_load);
        imageView = findViewById(R.id.takePicture);

        buttonSave.setAlpha(0.5f);

        //setListeners
        takeImage.setOnClickListener((v) -> {
            //create an implicit intent, for image capture.
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            //start camera and wait for results
            startActivityForResult(intent, REQUEST_ID_IMAGE_CAPTURE);
        });

        buttonSave.setOnClickListener((v) -> {
            if (picture != null) {
                //manage authorizations
                int permissionCheck = ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_MEDIA);
                } else {
                    saveToInternalStorage(picture);
                }
            }
        });

        buttonLoad.setOnClickListener((v) -> {
            if (picture != null) {
                //manage authorizations
                int permissionCheck = ContextCompat.checkSelfPermission(CameraActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_MEDIA);
                } else {
                    System.out.println("on rentre ici");
                    loadImageFromStorage("data/user/0/foodget.ihm.foodget/app_imageDir");
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_MEDIA:
                if ((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveToInternalStorage(picture);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == REQUEST_ID_IMAGE_CAPTURE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                picture = (Bitmap) data.getExtras().get("data/user/0/foodget.ihm.foodget/app_imageDir" + pictureName);
                imageView = setImageBitmap(picture);
                buttonSave.setAlpha(1f);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "action cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "action false", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        //path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("app_imageDir", Context.MODE_PRIVATE);
        Log.d("DEBUG_FR", "directory=" + directory);
        //Create imageDir

        File file = new File(directory, pictureName);
        Log.d("DEBUG_FR", "file=" + file);

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            Log.d("DEBUG_FR", "fos=" + fos);
            // Use the compress method on the Bitmap object to write
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getPath(), pictureName, "");
            Toast.makeText(getApplicationContext(), "Pic saved", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Log.d("DEBUG_FR", "ERROR: path not found");
            e.printStackTrace();
        }

    }

    private void loadImageFromStorage(String path) {
        ImageView imageView = this.findViewById(R.id.takePicture);
        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
    }

    private ImageView setImageBitmap(Bitmap bitmap) {
        ImageView imageView = this.findViewById(R.id.takePicture);
        imageView.setImageBitmap(bitmap);
        return imageView;
    }
}
