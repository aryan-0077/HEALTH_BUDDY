package com.example.healthcare.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthcare.R;
import com.example.healthcare.viewmodels.Upload;
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

public class Blog extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mprogressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    // So , as we don't upload the same thing again and again .
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mprogressBar = findViewById(R.id.progress_bar);

        // This means we will store it in a folder called uploads ..
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(Blog.this,"Upload in Progress",Toast.LENGTH_SHORT).show();
                }else{
                    uploadFile();
                }
            }
        });

        mTextViewUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagesActivity();
            }
        });
    }

    // to open the file chooser ..
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    // This method will be called when we pick our file ..
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // filter our image request .. // user actually picked an image
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null){
            // conatians the uri of the image we picked ..
            mImageUri = data.getData();

            // picasso is used for hassle free image loading ..
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    // this is for the uploadFile function .. for getting file extension only ..
    private String getFileExtension(Uri uri){
        // it will retuen the extension of the file we picked .
        // for jpeg it will be jpg ..
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile(){
        if(mImageUri != null){
            // that is we actually picked an image
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            +"."+getFileExtension(mImageUri)); // thus we created a bignumber + jpg

            // mStorageRef points to uploads so child adds some more to the string ..
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // when the uplod is successfull .
                            // It delays the progress bar for 0.5 sec so that user can see 100% for 5 sec
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mprogressBar.setProgress(0);
                                }
                            },500);

                            Toast.makeText(Blog.this,"Upload Successful",Toast.LENGTH_SHORT).show();

                            // we need this upload information for creating new entries in the data base ..
                            // we need database to store the meta data of our image ..

                            Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                            while(!uri.isComplete());
                            Uri url = uri.getResult();

                            Toast.makeText(Blog.this, "Upload Success, download URL " +
                                    url.toString(), Toast.LENGTH_LONG).show();
                            Log.i("FBApp1 URL ", url.toString());

                            Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                    url.toString());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);

                            mImageView.invalidate();
                            mEditTextFileName.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // when the upload is a failur .
                            Toast.makeText(Blog.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            mImageView.setImageBitmap(null);
                            mEditTextFileName.setText("");
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            // when the upload is progressing .
                            // thus updating the progress bar ..
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            // this will simply give us the progress because of comparison between bytes and total bytes .
                            mprogressBar.setProgress((int)progress);
                        }
                    });
        }
        else{
            Toast.makeText(this,"no file Selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagesActivity(){
        Intent intent = new Intent(this,ImagesActivity.class);
        startActivity(intent);
    }
}
