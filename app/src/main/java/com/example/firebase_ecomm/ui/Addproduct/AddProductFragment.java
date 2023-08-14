package com.example.firebase_ecomm.ui.Addproduct;

import static android.app.Activity.RESULT_OK;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firebase_ecomm.DataModal;
import com.example.firebase_ecomm.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class AddProductFragment extends Fragment {

    ImageView imageView;
    EditText pname,pprice,pdescription;
    Button submitbutton;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseStorage mainBuket;
    StorageReference imgFolder;
    UploadTask uploadTask;
    String id;
    String imgurl;


    int SELECT_PICTURE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_product,container,false);

        imageView = view.findViewById(R.id.productimage);
        pname=view.findViewById(R.id.productname);
        pprice=view.findViewById(R.id.productprice);
        pdescription=view.findViewById(R.id.productdescription);
        submitbutton = view.findViewById(R.id.submitproduct);
        // Inflate the layout for this fragment

        mainBuket = FirebaseStorage.getInstance();
        String imgName = "Img"+new Random().nextInt(100000)+".jpg";
        imgFolder = mainBuket.getReference().child("Images/"+imgName);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Product").push();
        id = myRef.getKey();



        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImg();
                DataModal modal = new DataModal(id, pname.getText().toString(), pprice.getText().toString(), pdescription.getText().toString(), imgurl);
                myRef.setValue(modal);
                Toast.makeText(getContext(), "Product Add Sucessfully", Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImage();
            }
        });
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    private void getImage()
    {
        CropImage.activity()
                .start(getContext(), this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadImg() {
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imgFolder.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return imgFolder.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            imgurl = String.valueOf(downloadUri);
                        } else {
                            // Handle failures
                            // ...
                        }
                    }
                });
            }
        });
    }
}
