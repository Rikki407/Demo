package com.kirayepay.kirayepay101.Fab.ADs;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kirayepay.kirayepay101.R;
import com.kirayepay.kirayepay101.RikkiClasses.Acquire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by rikki on 10/7/17.
 */

public class GetImagesActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    ImageView main_image, other_image_1, other_image_2, other_image_3, other_image_4;
    CardView main_image_plus, main_image_cross, other_image_1_plus, other_image_1_cross, other_image_2_plus, other_image_2_cross,
            other_image_3_plus, other_image_3_cross, other_image_4_plus, other_image_4_cross;
    TextView next_activity;

    private static final int REQUEST_CAMERA_CODE_0 = 8370;
    private static final int REQUEST_CAMERA_CODE_1 = 8371;
    private static final int REQUEST_CAMERA_CODE_2 = 8372;
    private static final int REQUEST_CAMERA_CODE_3 = 8373;
    private static final int REQUEST_CAMERA_CODE_4 = 8374;
    private static final int REQUEST_GALLERY_CODE_0 = 200;
    private static final int REQUEST_GALLERY_CODE_1 = 201;
    private static final int REQUEST_GALLERY_CODE_2 = 202;
    private static final int REQUEST_GALLERY_CODE_3 = 203;
    private static final int REQUEST_GALLERY_CODE_4 = 204;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private Uri imageUri, main_img_uri, other_img_uri_1, other_img_uri_2, other_img_uri_3, other_img_uri_4;
    private ContentValues values;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_images);
        values = new ContentValues();
        mContext = this;
        checkPermissionREAD_EXTERNAL_STORAGE(mContext);
        checkPermissionForCamera(mContext,124);
        main_image = (ImageView) findViewById(R.id.main_image_prev);
        main_image_plus = (CardView) findViewById(R.id.main_image_plus);
        main_image_cross = (CardView) findViewById(R.id.main_image_cross);
        other_image_1 = (ImageView) findViewById(R.id.other_image_1);
        other_image_1_plus = (CardView) findViewById(R.id.other_image_1_plus);
        other_image_1_cross = (CardView) findViewById(R.id.other_image_1_cross);
        other_image_2 = (ImageView) findViewById(R.id.other_image_2);
        other_image_2_plus = (CardView) findViewById(R.id.other_image_2_plus);
        other_image_2_cross = (CardView) findViewById(R.id.other_image_2_cross);
        other_image_3 = (ImageView) findViewById(R.id.other_image_3);
        other_image_3_plus = (CardView) findViewById(R.id.other_image_3_plus);
        other_image_3_cross = (CardView) findViewById(R.id.other_image_3_cross);
        other_image_4 = (ImageView) findViewById(R.id.other_image_4);
        other_image_4_plus = (CardView) findViewById(R.id.other_image_4_plus);
        other_image_4_cross = (CardView) findViewById(R.id.other_image_4_cross);
        next_activity = (TextView) findViewById(R.id.next_activity);

        main_image_plus.setOnClickListener(this);
        main_image_cross.setOnClickListener(this);
        other_image_1_plus.setOnClickListener(this);
        other_image_1_cross.setOnClickListener(this);
        other_image_2_plus.setOnClickListener(this);
        other_image_2_cross.setOnClickListener(this);
        other_image_3_plus.setOnClickListener(this);
        other_image_3_cross.setOnClickListener(this);
        other_image_4_plus.setOnClickListener(this);
        other_image_4_cross.setOnClickListener(this);
        next_activity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_activity:
                if(main_img_uri==null&&(other_img_uri_1!=null||other_img_uri_2!=null||other_img_uri_3!=null||other_img_uri_4!=null)){
                    Toast.makeText(mContext,"Main image is required with other images ",Toast.LENGTH_LONG).show();
                    return;
                }
                Acquire.CALL_WITH_IMAGES = main_img_uri != null;
                Intent intent = new Intent(mContext, GetAdsDetails.class);
                if(main_img_uri!=null)
                intent.putExtra("main_img_uri", main_img_uri.toString());
                if(other_img_uri_1!=null)
                intent.putExtra("other_img_uri_1",other_img_uri_1.toString());
                if(other_img_uri_2!=null)
                intent.putExtra("other_img_uri_2",other_img_uri_2.toString());
                if(other_img_uri_3!=null)
                intent.putExtra("other_img_uri_3",other_img_uri_3.toString());
                if(other_img_uri_4!=null)
                intent.putExtra("other_img_uri_4",other_img_uri_4.toString());
                startActivity(intent);
                break;
            case R.id.main_image_plus:
            case R.id.other_image_1_plus:
            case R.id.other_image_2_plus:
            case R.id.other_image_3_plus:
            case R.id.other_image_4_plus:
                startChooser(v.getId());
                break;
            case R.id.main_image_cross:
            case R.id.other_image_1_cross:
            case R.id.other_image_2_cross:
            case R.id.other_image_3_cross:
            case R.id.other_image_4_cross:
                removeImage(v.getId());
                break;

        }
    }

    private void removeImage(int id) {
        switch (id) {
            case R.id.main_image_cross:
                main_image.setImageResource(R.drawable.photo_preview);
                main_img_uri = null;
                break;
            case R.id.other_image_1_cross:
                other_image_1.setImageResource(R.drawable.photo_preview);
                other_img_uri_1 = null;
                break;
            case R.id.other_image_2_cross:
                other_image_2.setImageResource(R.drawable.photo_preview);
                other_img_uri_2 = null;
                break;
            case R.id.other_image_3_cross:
                other_image_3.setImageResource(R.drawable.photo_preview);
                other_img_uri_3 = null;
                break;
            case R.id.other_image_4_cross:
                other_image_4.setImageResource(R.drawable.photo_preview);
                other_img_uri_4 = null;
                break;
        }

    }

    private void startChooser(int image_id) {
        ChooserDialog cd = new ChooserDialog(this, image_id);
        cd.show();
    }

    public void takeImageFromCamera(int request_code) {
        Log.e("camera", "camera_start");
        if (checkPermissionForCamera(mContext, request_code)) {
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, request_code);
        }
    }

    public void getImageFromGallery(int request_code) {
        if (checkPermissionREAD_EXTERNAL_STORAGE(mContext)) {
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            openGalleryIntent.setType("image/*");
            startActivityForResult(openGalleryIntent, request_code);
        }
    }


    public boolean checkPermissionForCamera(final Context context, final int MY_PERMISSIONS_REQUESTS) {
        int currentAPIVersion = Build.VERSION.SDK_INT;

        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {

                    showDialog("Camera", context, Manifest.permission.CAMERA, MY_PERMISSIONS_REQUESTS);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUESTS);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }


    }


    public boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context, Manifest.permission.READ_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                }
                else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    checkPermissionForCamera(mContext,124);
                }
                checkPermissionForCamera(mContext,124);

                return false;
            }
            else {
                checkPermissionForCamera(mContext,124);

                return true;
            }

        }
        else {
            checkPermissionForCamera(mContext,124);

            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission, final int myPermissionsRequestCamera) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                myPermissionsRequestCamera);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE_0:
                    try {
                        main_img_uri = imageUri;
                        main_image.setImageURI(main_img_uri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_GALLERY_CODE_0:
                    main_img_uri = data.getData();
                    Log.e("photo_uri ", "" + main_img_uri);
                    main_image.setImageURI(main_img_uri);
                    break;
                case REQUEST_CAMERA_CODE_1:
                    try {
                        other_img_uri_1 = imageUri;
                        other_image_1.setImageURI(other_img_uri_1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_GALLERY_CODE_1:
                    other_img_uri_1 = data.getData();
                    other_image_1.setImageURI(other_img_uri_1);
                    break;
                case REQUEST_CAMERA_CODE_2:
                    try {
                        other_img_uri_2 = imageUri;
                        other_image_2.setImageURI(other_img_uri_2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_GALLERY_CODE_2:
                    other_img_uri_2 = data.getData();
                    other_image_2.setImageURI(other_img_uri_2);
                    break;
                case REQUEST_CAMERA_CODE_3:
                    try {
                        other_img_uri_3 = imageUri;
                        other_image_3.setImageURI(other_img_uri_3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_GALLERY_CODE_3:
                    other_img_uri_3 = data.getData();
                    other_image_3.setImageURI(other_img_uri_3);
                    break;
                case REQUEST_CAMERA_CODE_4:
                    try {
                        other_img_uri_4 = imageUri;
                        other_image_4.setImageURI(other_img_uri_4);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case REQUEST_GALLERY_CODE_4:
                    other_img_uri_4 = data.getData();
                    other_image_4.setImageURI(other_img_uri_4);
                    break;

            }
        }

    }


}
