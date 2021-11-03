package com.weblieu.findtrue.vendor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.weblieu.findtrue.MainActivity;
import com.weblieu.findtrue.R;
import com.weblieu.findtrue.adapter.CategoryAdapter;
import com.weblieu.findtrue.adapter.GalleryAdapter;
import com.weblieu.findtrue.model.GetAllGallery;
import com.weblieu.findtrue.model.ImageGallery;
import com.weblieu.findtrue.repositry.ApiInterface;
import com.weblieu.findtrue.repositry.ResponseRequest;
import com.weblieu.findtrue.repositry.RetrofitClient;
import com.weblieu.findtrue.service.AppConfig;
import com.weblieu.findtrue.utils.AppConstant;
import com.weblieu.findtrue.utils.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageGalleryActivity extends AppCompatActivity {

    private Context context = ManageGalleryActivity.this;

    private ImageView back;
    private ImageView mimageView;
    private ApiInterface apiInterface;
    String VENDOR_ID;
    private ProgressBar progressBar4, progressBar5;
    private AppCompatButton button4;
    private FloatingActionButton floatingActionButton;
    Uri resultUri;
    private RecyclerView recyclerViewGallery;
    private GalleryAdapter galleryAdapter;
    private List<ImageGallery> imageGalleryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_gallery);

        back = (ImageView)findViewById(R.id.back);
        mimageView = (ImageView)findViewById(R.id.imageView7);
        button4 = (AppCompatButton)findViewById(R.id.button4);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);


        VENDOR_ID = CommonUtils.getPreferencesString(context, AppConstant.VENDOR_ID);
        progressBar4 = (ProgressBar)findViewById(R.id.progressBar4);
        progressBar5 = (ProgressBar)findViewById(R.id.progressBar5);
        recyclerViewGallery = (RecyclerView)findViewById(R.id.recyclerViewGallery);
        imageGalleryList = new ArrayList<>();
        apiInterface = RetrofitClient.getClient().create(ApiInterface.class);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewGallery.setLayoutManager(linearLayoutManager);
        recyclerViewGallery.hasFixedSize();


        getImageGallery();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar4.setVisibility(View.VISIBLE);
                try {
                    InputStream is = getContentResolver().openInputStream(resultUri);
                    uploadFile(VENDOR_ID, getBytes(is));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void onSelectImageClick(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                mimageView.setImageURI(resultUri);
                //System.out.println("resultUri-----------------------------"+resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];
        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
    }

    private void uploadFile(String user_id, byte[] imageBytes){

        RequestBody idBody = RequestBody.create(okhttp3.MultipartBody.FORM, user_id);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", "image.jpg", requestFile);

        System.out.println("================================="+user_id);
        apiInterface.uploadGallery(idBody, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println("----------------------------------------");
                Toast.makeText(context, "Upload Successfully", Toast.LENGTH_LONG).show();
                progressBar4.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Image Updated failed", Toast.LENGTH_LONG).show();
                progressBar4.setVisibility(View.GONE);
            }
        });
    }

    public void getImageGallery(){
        apiInterface.getAllGalery(VENDOR_ID).enqueue(new Callback<GetAllGallery>() {
            @Override
            public void onResponse(Call<GetAllGallery> call, Response<GetAllGallery> response) {
                if (response.isSuccessful()){
                    progressBar5.setVisibility(View.GONE);
                    List<ImageGallery> imageGalleries = response.body().getData();
                    if (imageGalleries!=null){
                        for (ImageGallery imageGallery : imageGalleries){
                            imageGalleryList.add(imageGallery);
                            galleryAdapter = new GalleryAdapter(context, imageGalleryList);
                            recyclerViewGallery.setAdapter(galleryAdapter);

                            String imagePath = imageGalleryList.get(0).getPicPath();
                            System.out.println("imagepath--------------------------"+imagePath);
                            Glide.with(context).load(AppConfig.VENDOR_IMAGE + imagePath).centerCrop().placeholder(R.drawable.unnamed).into(mimageView);

                        }
                    }else {

                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllGallery> call, Throwable t) {
                progressBar5.setVisibility(View.GONE);
            }
        });
    }

}