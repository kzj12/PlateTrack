//package com.example.america.platetrack2;
//
//import android.content.Intent;
//import android.content.res.AssetManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.support.annotation.NonNull;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.File;
//import java.net.URI;
//
//import android.net.Uri;
//
//import static android.app.Activity.RESULT_OK;
//
///**
// * Created by jjennings on 11/9/17.
// */
//
//
//public class Firebase {
//    private int PICK_IMAGE_REQUEST = 1;
//    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    FirebaseStorage storage = FirebaseStorage.getInstance();
//    StorageReference storageReference = storage.getReferenceFromUrl("gs://platetrack2.appspot.com").child("test.jpeg");
//    private File mFile;
//    private Uri file;
//
//     /*Maybe use this method for frame feed???
//            FirebaseStorage storage=FirebaseStorage.getInstance();
//            StorageReference storageReference=storage.getReferenceFromUrl("gs://platetrack2.appspot.com/UpstreamFrames");
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//            byte[] data = outputStream.toByteArray();
//            UploadTask uploadTask = storageReference.putBytes(data);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//            }
//             }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//            }
//            });*/
//
//    private void uploadImage(String imgName) {
//
//        FirebaseUser user = mAuth.getCurrentUser();
//        //if (user != null) {
//        //FirebaseStorage storage = FirebaseStorage.getInstance();
//        //StorageReference storageReference = storage.getReferenceFromUrl("gs://platetrack2.appspot.com").child("test.jpeg");
//        //AssetManager assetManager = Firebase.this.getAssets();
//        InputStream istr;
//        Bitmap bitmap;
//        try {
//            //get bitmap from PNG file in assets folder
//            //istr = assetManager.open(imgName);
//            bitmap = BitmapFactory.decodeStream(istr);
//
//            //decode to byte output stream
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//            byte[] data = outputStream.toByteArray();
//
//
//
//
//            //Upload to firebase
//            UploadTask uploadTask = storageReference.putBytes(data);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    exception.printStackTrace();
//                    //Toast.makeText(Firebase.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                    //Toast.makeText(Firebase.this, "Upload successful!", Toast.LENGTH_SHORT).show();
//
//                }
//            });
//            //istr.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    } //else {signInAnonymously();}
//
//    //}
//
//
//    //}
//    @Override
//    void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            file = data.getData();
//
//
//            //InputStream stream = getResources().openRawResource(R.raw.test);
//            UploadTask uploadTask = storageReference.putStream(stream);
//            //Getting the Bitmap from Gallery
//            //bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), file);
//            //Setting the Bitmap to ImageView
//            //imageView.setImageBitmap(bitmap);
//            //imageView.setImageURI(file);
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    exception.printStackTrace();
//                    //dismissProgressDialog();
//                    //Toast.makeText(getApplicationContext(), "Upload Failed!", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    //dismissProgressDialog();
//                   // Toast.makeText(getApplicationContext(), "Upload successful!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//}
////root/android/data/
///*
//        //InputStream stream = getResources().openRawResource(R.raw.image);
//
//        //UploadTask uploadTask = storageReference.putStream(stream);
//
//        File file = null;
//        try {
//            file = File.createTempFile("test2", "txt");
//        } catch( IOException e ) {
//
//        }
//        UploadTask uploadTask = storageReference.putFile(Uri.fromFile(file));
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//            }
//        });
//    }*/
//
//
//
//
//
//
///*The simplest way to upload to your storage bucket is by uploading a local file,
//such as photos and videos from the camera, using the putFile() method.
//You can also upload raw data using putBytes() or from an InputStream using putStream() .
//
//The code below downloads a local file using the getFile() method.
//You can also download to device memory using getBytes() .
//
//InputStream stream = new FileInputStream(new File("path/to/images/rivers.jpg"));
//
//uploadTask = mountainsRef.putStream(stream);
//uploadTask.addOnFailureListener(new OnFailureListener() {
//    @Override
//    public void onFailure(@NonNull Exception exception) {
//        // Handle unsuccessful uploads
//    }
//}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//    @Override
//    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
//        Uri downloadUrl = taskSnapshot.getDownloadUrl();
//    }
//});
//
// */
//
////*
////private void signInAnonymously() {
////        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
////@Override
////public void onSuccess(AuthResult authResult) {
////        // do your stuff
////        }
////        })
////        .addOnFailureListener(this, new OnFailureListener() {
////@Override
////public void onFailure(@NonNull Exception exception) {
////        // Log.e(TAG, "signInAnonymously:FAILURE", exception);
////        }
////        });
////        }
////        */
//
