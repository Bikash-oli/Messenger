package com.example.bikash_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikash_messenger.Models.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadImage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button chooseImage, buttonUpload;
    private TextView showuploads;
    private EditText textfilename;
    private ImageView imageView;
    private ProgressBar progressBar;

    private StorageReference mStorageReference;
    private DatabaseReference mDatabaseReference;
    private StorageTask mUploadTask;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        chooseImage = findViewById(R.id.button_choose_image);
        buttonUpload = findViewById(R.id.button_upload);
        showuploads = findViewById(R.id.text_view_show_uploads);
        textfilename = findViewById(R.id.edit_text_file_name);
        imageView = findViewById(R.id.image_view);
        progressBar = findViewById(R.id.progress_bar);
        mStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");


        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfilechooser();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask!=null && mUploadTask.isInProgress()){
                    Toast.makeText(UploadImage.this,"Upload in progress",Toast.LENGTH_SHORT).show();
                }else{
                    uploadFile();
                }

            }
        });

        showuploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagesActivity();
            }
        });
    }

    private void openfilechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(imageView);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver CR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(CR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {

            StorageReference fileReference = mStorageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploadTask=fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },500);
                    Toast.makeText(UploadImage.this,"Upload Sucessful",Toast.LENGTH_LONG).show();

                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();

                    while (!urlTask.isSuccessful());

                    Uri downloadUrl = urlTask.getResult();

                    Upload upload = new Upload(textfilename.getText().toString().trim(),downloadUrl.toString());

                    String uploadId = mDatabaseReference.push().getKey();

                    mDatabaseReference.child(uploadId).setValue(upload);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UploadImage.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress=(100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressBar.setProgress((int) progress);
                }
            });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagesActivity(){
        Intent i=new Intent(UploadImage.this,Home.class);
        startActivity(i);
    }
}