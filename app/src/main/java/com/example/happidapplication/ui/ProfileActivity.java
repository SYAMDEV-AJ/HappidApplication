package com.example.happidapplication.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.happidapplication.R;
import com.example.happidapplication.databinding.ActivityprofileBinding;
import com.example.happidapplication.modelclass.SubmitRequest;
import com.example.happidapplication.modelclass.SubmitResponse;
import com.example.happidapplication.network.APIInterface;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileActivity extends AppCompatActivity {

    ActivityprofileBinding binding;

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    private static final int REQUEST_CAPTURE_IMAGE = 1;

    SubmitRequest submitRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activityprofile);
        btnsubmit();
        locationgetbtn();
        profileclick();

        binding.backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.this.finish();
            }
        });


    }

    private void profileclick() {
        binding.profileclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    //14 code
                    camerpermissionforupsidedowncake();
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    //13 code
                    camerpermissionforupsidedowncake();
                } else {
                    if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestforMediaPermission();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        proofPhotoUpload.launch(camera_intent);
                    }
                }
            }

        });


    }

    private void camerpermissionforupsidedowncake() {
        // Check if the camera permission is granted
        if (ContextCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with camera operations
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            proofPhotoUpload.launch(camera_intent);
        }

    }

    private void locationgetbtn() {

        binding.btnGeolocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchLastLocation();

                Toast.makeText(ProfileActivity.this, "Please Wait a Moment", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void btnsubmit() {

        binding.submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.firstname.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please Enter First Name", Toast.LENGTH_SHORT).show();
                } else if (binding.lastname.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please Enter Last Name", Toast.LENGTH_SHORT).show();
                } else if (binding.mobnumber.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (binding.Postalcode.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please Select Postal Code", Toast.LENGTH_SHORT).show();
                } else {
                    submitRequest = new SubmitRequest();

                    submitRequest.setFirstname(binding.firstname.getText().toString());
                    submitRequest.setLastname(binding.lastname.getText().toString());
                    submitRequest.setPhone(binding.mobnumber.getText().toString());
                    submitRequest.setPostalcode(binding.Postalcode.getText().toString());

                    retrofitcall();

                }
            }
        });
    }

    private void fetchLastLocation() {
        LocationManager locationManager;
        locationManager = (LocationManager) ProfileActivity.this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestforGpsPermission();

        } else {


            boolean gps_enabled = false;

            gps_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!gps_enabled) {
                new android.app.AlertDialog.Builder(ProfileActivity.this).setMessage("To continue, let your device turn on location using Google\\'s location Service").setNegativeButton("Cancel", null).setPositiveButton("Turn on", (paramDialogInterface, paramInt) -> ProfileActivity.this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))).show();
            } else {

                getCurrentLocation();

                //Toast.makeText(MapsActivity.this, "latitude = " + mCurrentLocation.getLatitude() + "longtitude = " + mCurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void requestforGpsPermission() {
        Dexter.withActivity(ProfileActivity.this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    fetchLastLocation();

                } else if (report.isAnyPermissionPermanentlyDenied()) {

                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).onSameThread().check();
    }


    private boolean checkAndRequestPermissions() {

        if (EasyPermissions.hasPermissions(ProfileActivity.this, perms)) {
            return true;
        } else {
            requestforGpsPermission();
            return false;
        }

    }

    private void getCurrentLocation() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ProfileActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkAndRequestPermissions();
        }
        LocationServices.getFusedLocationProviderClient(ProfileActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(ProfileActivity.this).removeLocationUpdates(this);

                if (locationResult != null && locationResult.getLocations().size() > 0) {


                    int lastLocationIndex = locationResult.getLocations().size() - 1;
                    double currentLatitude = locationResult.getLocations().get(lastLocationIndex).getLatitude();
                    double currentLongitude = locationResult.getLocations().get(lastLocationIndex).getLongitude();


                    try {
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(ProfileActivity.this, Locale.getDefault());
                        addresses = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String currentCity = addresses.get(0).getLocality();
//                                binding.location.setText(address);
                        if (currentCity == null) {
                            String[] location_name = address.split(",");
                            String loc_name1 = location_name[1];
                            String loc_name2 = location_name[2];
                            currentCity = loc_name2;


                        }
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName();

                        if (!postalCode.isEmpty()) {
                            binding.Postalcode.setText(postalCode);

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, Looper.getMainLooper());
    }

    public void requestforMediaPermission() {
        Dexter.withActivity(this).withPermissions(perms).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    proofPhotoUpload.launch(camera_intent);
                } else if (report.isAnyPermissionPermanentlyDenied()) {

                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }

        }).onSameThread().check();
    }

    ActivityResultLauncher<Intent> proofPhotoUpload = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onActivityResult(ActivityResult result) {
            // Do your code from onActivityResult
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                Bundle extras = data.getExtras();
                Bitmap photo = extras.getParcelable("data");
                assert data != null;
                Uri selectedFileUri = getImageUri(ProfileActivity.this.getApplicationContext(), photo);
                UploadMedia(selectedFileUri);


            }

        }
    });

    private void UploadMedia(Uri businesspicUri) {
        Bitmap originalBitmap = null;
        long filesize;
        ByteArrayOutputStream bytes = null;
        try {
            originalBitmap = MediaStore.Images.Media.getBitmap(ProfileActivity.this.getContentResolver(), businesspicUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = new ByteArrayOutputStream();
        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        originalBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        byte[] imageInByte = bytes.toByteArray();
        filesize = (long) (imageInByte.length / 1024.0);//KB

        if (filesize > 2048) {
            originalBitmap = resize(originalBitmap, originalBitmap.getWidth() / 2, originalBitmap.getHeight() / 6);
            bytes = new ByteArrayOutputStream();
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            originalBitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
            imageInByte = bytes.toByteArray();
            filesize = (long) (imageInByte.length / 1024.0);//KB

        } else {
            Log.e("TAG", "UploadMedia: " + filesize);
        }


        Bitmap bitmap = originalBitmap;
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);

        binding.profileclick.setImageBitmap(circleBitmap);

        // binding.profileclick.setImageBitmap(originalBitmap);


    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float) maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float) maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = "";

        String currentTime = new SimpleDateFormat("ddmmyyyyHHmmss", Locale.getDefault()).format(new Date());
        try {
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + currentTime, null);

        } catch (Exception e) {
            Log.e("Media", String.valueOf(e));
        }
        return Uri.parse(path);
    }

    private void retrofitcall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://uatonpay.manappuram.com/mobess/ESSWebService.asmx/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIInterface apiService = retrofit.create(APIInterface.class);


        Call<SubmitResponse> call = apiService.submit(submitRequest);
        call.enqueue(new Callback<SubmitResponse>() {
            @Override
            public void onResponse(Call<SubmitResponse> call, Response<SubmitResponse> response) {
                if (response.isSuccessful()) {
                    SubmitResponse data = response.body();
                    // Handle the data response here
                } else {
                    SubmitResponse data = response.body();

                    // Handle unsuccessful response
                    Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmitResponse> call, Throwable t) {
                // Handle API call failure
                Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
