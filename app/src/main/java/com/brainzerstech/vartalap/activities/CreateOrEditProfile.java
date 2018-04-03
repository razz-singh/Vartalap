package com.brainzerstech.vartalap.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.brainzerstech.vartalap.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateOrEditProfile extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView ivCapture, ivCapturedImgPreview;
    LinearLayout llProfilePic, llCaptureBtnContainer;
    Context context;
    private int MY_PERMISSIONS_REQUEST_CAMERA = 2;
    String mCurrentPhotoPath;
    private Drawable capturedImgDrawable;
    private static Uri photoURI;
    DatabaseReference dbRef;
    Uri downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_profile);
        context=this;
        ivCapture = findViewById(R.id.capture);
        ivCapturedImgPreview = findViewById(R.id.captured_img_preview);
        llProfilePic = findViewById(R.id.ll_profile_pic);
        llCaptureBtnContainer = findViewById(R.id.capture_btn_container);
        ivCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                        // MY_PERMISSIONS_REQUEST_CAMERA is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }
                else{
                    dispatchTakePictureIntent();
                }

            }
        });
    }

        @Override
        public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
            switch (requestCode) {
                case 2: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
//                        dispatchTakePictureIntent();

                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request.
            }
        }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.brainzerstech.vartalap"+".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(photoURI);
                capturedImgDrawable = Drawable.createFromStream(inputStream, photoURI.toString() );
            } catch (FileNotFoundException e) {
                capturedImgDrawable = getResources().getDrawable(R.drawable.profile_img);
            }

//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");


            llCaptureBtnContainer.setVisibility(View.GONE);
            ivCapturedImgPreview.setVisibility(View.VISIBLE);
//            Picasso.get().load(photoURI.toString()).into(ivCapturedImgPreview);
//            ivCapturedImgPreview.setImageDrawable(capturedImgDrawable);
        }

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_edit_profile, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_profile) {
            saveDataToFireDB();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveDataToFireDB() {

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://phoneauth-4fd5e.appspot.com/");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        // Create a child reference
// imagesRef now points to "images"
        StorageReference imagesRef = storageRef.child("PROFILE_PICS");

// Child references can also take paths
// spaceRef now points to "images/space.jpg
// imagesRef still points to "images"
        StorageReference spaceRef = storageRef.child("PROFILE_PICS/"+new File(photoURI.getPath()).getName());
        // Get the data from an ImageView as bytes
        ivCapturedImgPreview.setDrawingCacheEnabled(true);
        ivCapturedImgPreview.buildDrawingCache();
        Bitmap bitmap = ivCapturedImgPreview.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = spaceRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });




        String token = FirebaseInstanceId.getInstance().getToken();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        dbRef= database.getReference("VUsers");
        dbRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("vUserToken").setValue(FirebaseInstanceId.getInstance().getToken());
        dbRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("vAvtarUrl").setValue(downloadUrl);
        dbRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("VUserName").setValue(findViewById(R.id.et_name));
        dbRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("vUserAge").setValue(findViewById(R.id.et_age));
        dbRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("vUserState").setValue(findViewById(R.id.et_state));
        dbRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("vUserCountry").setValue(findViewById(R.id.et_country));
        dbRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("vUserStatus").setValue(findViewById(R.id.et_status));
    }
}
