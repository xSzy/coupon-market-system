package com.tnq.ngocquang.datn.home.tab_info.manage_coupon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.ImageProductAdapter;
import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.interface_.RecyclerViewClickListener;
import com.tnq.ngocquang.datn.model.Category;
import com.tnq.ngocquang.datn.model.Coupon;
import com.tnq.ngocquang.datn.model.ImageUpload;
import com.tnq.ngocquang.datn.model.Product;
import com.tnq.ngocquang.datn.model.ProductImage;
import com.tnq.ngocquang.datn.model.User;
import com.tnq.ngocquang.datn.support.AppHelper;
import com.tnq.ngocquang.datn.support.MultipartRequest;
import com.tnq.ngocquang.datn.support.MyVolley;
import com.tnq.ngocquang.datn.support.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddCouponNew extends AppCompatActivity implements RecyclerViewClickListener, AdapterView.OnItemSelectedListener {

    private static final int GALLERY_REQUEST_CODE = 1;
    private User mUser;
    private Category mCategory;
    private Coupon mCoupon;
    private Product mProduct;
    private ArrayList<ProductImage> mProductImages;
    private int typeCoupon = -1;
    private int valueTypeCoupon = -1;
    private int categoryCoupon = -1;
    private String descriptionCategoryCoupon = "";
    private int mNumberImgUpLoadSuccess = 0;
    private String URL_CREATE_COUPON = Constant.hostname + Constant.createCouponAPI;

    ///////
    private CardView mCardViewValue;
    private CardView mCardViewValueType;
    private EditText mTitle;
    private EditText mValue;
    private EditText mDescription;
    private EditText mNameProduct;
    private EditText mManufacturerProduct;
    private EditText mContactProduct;
    private CardView mCardViewProduct;
    private ImageView mChangeStateProductGroup;
    private RelativeLayout mGroupProduct;
    private RecyclerView mRecyclerViewListImageProduct;
    private ImageProductAdapter mImageProductAdapter;
    private Spinner mTypeCoupon;
    private Spinner mValueTypeCoupon;
    private Spinner mCategoryCoupon;
    private ArrayList<ImageView> mListImageProduct;
    private ArrayList<ImageUpload> mImageUploads;
    private RequestQueue mRequestQueue;
    private final String URL_UPLOAD_IMAGE = Constant.hostname + Constant.uploadImageAPI;
    private FloatingActionButton mDoneAddCoupon;
    private FloatingActionButton mUploadImageAddCoupon;
    private ProgressDialog dialogWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coupon_new);
        anhxa();
        mUser = getIntent().getParcelableExtra("addCouponByUser");
        initDataOfSpinner();
        changeStateCouponGroup();
        RecyclerView.LayoutManager gridLayout = new GridLayoutManager(this, 2);
        mRecyclerViewListImageProduct.setLayoutManager(gridLayout);
        mRecyclerViewListImageProduct.setAdapter(mImageProductAdapter);
    }

    private void initDataOfSpinner() {
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.type_coupon, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterValueType = ArrayAdapter.createFromResource(this, R.array.valuetype_coupon, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this, R.array.category_coupon, android.R.layout.simple_spinner_dropdown_item);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterValueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeCoupon.setAdapter(adapterType);
        mValueTypeCoupon.setAdapter(adapterValueType);
        mCategoryCoupon.setAdapter(adapterCategory);
        mTypeCoupon.setOnItemSelectedListener(this);
        mValueTypeCoupon.setOnItemSelectedListener(this);
        mCategoryCoupon.setOnItemSelectedListener(this);

    }


    private void changeStateCouponGroup() {
        mCardViewProduct.setOnClickListener(changeSCGListener());
        mChangeStateProductGroup.setOnClickListener(changeSCGListener());
    }

    private View.OnClickListener changeSCGListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mGroupProduct.getVisibility() == View.GONE) {
                    mChangeStateProductGroup.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove));
                    mGroupProduct.setVisibility(View.VISIBLE);
                } else {
                    mGroupProduct.setVisibility(View.GONE);
                    mChangeStateProductGroup.setImageDrawable(getResources().getDrawable(R.drawable.ic_add));
                }
            }
        };
    }

    private void anhxa() {
        mCardViewValue = findViewById(R.id.cardView_value_addcoupon);
        mCardViewValueType = findViewById(R.id.cardView_valuetype_addcoupon);
        mCoupon = new Coupon();
        mUser = new User();
        mCategory = new Category();
        mProduct = new Product();
        mProductImages = new ArrayList<>();
        mDoneAddCoupon = findViewById(R.id.doneAddCoupon);
        mUploadImageAddCoupon = findViewById(R.id.uploadImageAddCoupon);
        mCardViewProduct = findViewById(R.id.cardView_product_addcoupon);
        mChangeStateProductGroup = findViewById(R.id.change_state_product_group_addcoupon);
        mGroupProduct = findViewById(R.id.group_product_addcoupon);
        mRecyclerViewListImageProduct = findViewById(R.id.list_image_product_addcoupon);
        mListImageProduct = new ArrayList<>();
        mImageUploads = new ArrayList<>();
        mImageProductAdapter = new ImageProductAdapter(this, mListImageProduct, this);
        mRequestQueue = MyVolley.getInstance(getApplicationContext()).getRequestQueue();
        mTypeCoupon = findViewById(R.id.type_addcoupon);
        mValueTypeCoupon = findViewById(R.id.valuetype_addcoupon);
        mCategoryCoupon = findViewById(R.id.category_addcoupon);
        mTitle = findViewById(R.id.title_addcoupon);
        mValue = findViewById(R.id.value_addcoupon);
        mDescription = findViewById(R.id.description_addcoupon);
        mNameProduct = findViewById(R.id.name_product_addcoupon);
        mManufacturerProduct = findViewById(R.id.manufacturer_product_addcoupon);
        mContactProduct = findViewById(R.id.contact_product_addcoupon);
    }

    public void doneAddCouponNew(View view) {
        if(mTitle.getText().toString().equals("")){
            Toast.makeText(this, "bạn chưa nhập tiêu đề", Toast.LENGTH_SHORT).show();
            mTitle.setCursorVisible(true);
        }
        else if(mValue.getText().toString().equals("") && typeCoupon == 2){
            Toast.makeText(this, "bạn chưa nhập giá trị", Toast.LENGTH_SHORT).show();
            mValue.setCursorVisible(true);
        }else if(mDescription.getText().toString().equals("")){
            Toast.makeText(this, "bạn chưa nhập mô tả", Toast.LENGTH_SHORT).show();
            mDescription.setCursorVisible(true);
        } else if(mNameProduct.getText().toString().equals("")){
            Toast.makeText(this, "bạn chưa nhập tên cho sản phẩm", Toast.LENGTH_SHORT).show();
            mNameProduct.setCursorVisible(true);
        }else if(mManufacturerProduct.getText().toString().equals("")){
            Toast.makeText(this, "bạn chưa nhập nhà sản xuất cho sản phẩm", Toast.LENGTH_SHORT).show();
            mManufacturerProduct.setCursorVisible(true);
        }else if(mContactProduct.getText().toString().equals("")){
            Toast.makeText(this, "bạn chưa nhập liên hệ cho sản phẩm", Toast.LENGTH_SHORT).show();
            mContactProduct.setCursorVisible(true);
        }else{
            mCoupon.setTitle(mTitle.getText().toString());
            mProduct.setName(mNameProduct.getText().toString());
            mProduct.setManufacturer(mManufacturerProduct.getText().toString());
            mProduct.setProductImages(mProductImages);
            mProduct.setContact(mContactProduct.getText().toString());
            mCoupon.setProduct(mProduct);
            mCoupon.setType(typeCoupon);
            // createDate
            Calendar calendar = Calendar.getInstance();
            Date expireDate = new Date(calendar.get(Calendar.YEAR) - 1900,calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            mCoupon.setCreateDate(expireDate);
            //
           try{
               mCoupon.setValue(Double.valueOf(mValue.getText().toString()));
           }catch (Exception ex){
               mCoupon.setValue(0);
           }
            mCoupon.setValuetype(valueTypeCoupon);
            mCoupon.setDescription(mDescription.getText().toString());
            mCoupon.setCreateBy(mUser);
            mCategory.setId(categoryCoupon);
            mCoupon.setCategory(mCategory);
            createCouponNew(mCoupon);
        }

    }

    private void createCouponNew(Coupon coupon){
        User user = coupon.getCreateBy();
        HashMap<String, String> accountParams = new HashMap<>();
        accountParams.put("id", String.valueOf(user.getAccount().getId()));
        accountParams.put("username", user.getAccount().getUsername());
        accountParams.put("password", user.getAccount().getPassword());
        accountParams.put("userId", user.getAccount().getUserId());
        JSONObject accountJson = new JSONObject(accountParams);
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("id", String.valueOf(user.getId()));
            userJson.put("account", accountJson);
            userJson.put("avatarUrl", user.getAvatarUrl());
            userJson.put("name", user.getName());
            userJson.put("dob", DateFormat.format("yyyy-MM-dd", user.getDob()));
            userJson.put("email", user.getEmail());
            userJson.put("address", user.getAddress());
            userJson.put("phoneNumber", user.getPhoneNumber());
            userJson.put("citizenId", user.getCitizenId());
            userJson.put("gender", String.valueOf(user.getGender()));
            userJson.put("role", String.valueOf(user.getRole()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject imageProductJson = new JSONObject();
        JSONArray imageList = new JSONArray();
        for(int i = 0 ; i < mProductImages.size(); i ++){
            JSONObject image = new JSONObject();
            try {
                image.put("image",mProductImages.get(i).getImage());
                imageList.put(i,image);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        JSONObject productJson = new JSONObject();
        try {
            productJson.put("name",coupon.getProduct().getName());
            productJson.put("manufacturer",coupon.getProduct().getManufacturer());
            productJson.put("productImages",imageList);
            productJson.put("contact",coupon.getProduct().getContact());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject categoryJson = new JSONObject();
        try {
            categoryJson.put("id",coupon.getCategory().getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject couponJson = new JSONObject();
        try {
            couponJson.put("title",coupon.getTitle());
            couponJson.put("product",productJson);
            couponJson.put("type",coupon.getType());
            couponJson.put("value",coupon.getValue());
            couponJson.put("valueType",coupon.getValuetype());
            couponJson.put("description",coupon.getDescription());
            couponJson.put("createdDate",DateFormat.format("yyyy-MM-dd",coupon.getCreateDate()));
            couponJson.put("createdBy",userJson);
            couponJson.put("category",categoryJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("AAA couponJson",couponJson.toString());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_CREATE_COUPON, couponJson, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("AAA createcoupon res",response.toString());
                    try {
                        String status = response.getString("status");
                        if(status.equals("success")){
                            Toast.makeText(getApplicationContext(), "ưu đãi được thêm thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("AAA createcoupon error",error.toString());
                    Toast.makeText(getApplicationContext(), Constant.errorMessage, Toast.LENGTH_SHORT).show();


                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("Content-Type","application/json");
                    return headers;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(request);


    }


    public void uploadImage(View view) {
        if(mImageUploads.isEmpty()){
            Toast.makeText(this, "bạn chưa chọn hình ảnh cho sản phẩm", Toast.LENGTH_SHORT).show();
        }else{
            dialogWait = new ProgressDialog(this);
            dialogWait.setMessage("đang tải ảnh lên, vui lòng chờ ...");
            dialogWait.setCancelable(false);
            dialogWait.show();
            progressUploadImage();
        }
    }
    private void progressUploadImage(){
        for(int i = 0 ; i < mImageUploads.size(); i ++){
            uploadImageToServer(mImageUploads.get(i),i,mImageUploads.size() -1);
        }

    }

    public void uploadImageToServer(ImageUpload imageUpload, final int count, final int stop) {
        MultipartRequest request = new MultipartRequest(Request.Method.POST, URL_UPLOAD_IMAGE, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject errorRes = new JSONObject(result);
                        Log.d("AAA error imgUpload ", errorRes.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.Listener<NetworkResponse>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    String status = result.getString("status");
                    String data = result.getString("data");
                    if(status.equals("success")){
                        mNumberImgUpLoadSuccess ++;
                        ProductImage productImage = new ProductImage();
                        productImage.setImage(data);
                        mProductImages.add(productImage);
                    }
                    Log.d("AAA response imgUpload ", result.toString());
                    if(count == stop){
                        mDoneAddCoupon.setVisibility(View.VISIBLE);
                        mUploadImageAddCoupon.setVisibility(View.GONE);
                        Toast.makeText(AddCouponNew.this, mNumberImgUpLoadSuccess +" ảnh tải lên thành công", Toast.LENGTH_SHORT).show();
                        if(dialogWait.isShowing()){
                            dialogWait.dismiss();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, imageUpload,"upload");

//        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, URL_UPLOAD_IMAGE, new Response.Listener<NetworkResponse>() {
//            @Override
//            public void onResponse(NetworkResponse response) {
//                String resultResponse = new String(response.data);
//                try {
//                    JSONObject result = new JSONObject(resultResponse);
//                    Log.d("AAA response imgUpload ",result.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkResponse networkResponse = error.networkResponse;
//                if(networkResponse != null){
//                    String result = new String(networkResponse.data);
//                    try {
//                        JSONObject response = new JSONObject(result);
//                        Log.d("AAA error imgUpload ",result.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }){
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("description",mImageUploads.get(0).getDescription());
//                return params;
//            }
//
//            @Override
//            protected Map<String, DataPart> getByteData() throws AuthFailureError {
//                Map<String,DataPart> params = new HashMap<>();
//                ImageUpload imageUpload = mImageUploads.get(0);
//                DataPart dataPart = new DataPart(imageUpload.getNameFile(), AppHelper.getFileDataFromDrawable(getBaseContext(),imageUpload.getDrawable()),"image/jpeg");
//                params.put("file",  dataPart);
//                return params;
//
//            }
//        };

        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);

    }

    public void pickImage(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    //
                    ImageView imageView = new ImageView(this);
                    imageView.setImageURI(selectedImage);
                    mListImageProduct.add(imageView);
                    mImageProductAdapter.notifyDataSetChanged();
                    //
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ImageUpload imageUpload = new ImageUpload(convertBitmapToFile(bitmap, data.getData().getLastPathSegment() + ".jpeg"), descriptionCategoryCoupon);
//                    ImageUpload imageUpload = new ImageUpload(imageView.getDrawable(),data.getData().getLastPathSegment() + ".jpeg","chair");
                    mImageUploads.add(imageUpload);
                    break;
            }
        }
    }

    private File convertBitmapToFile(Bitmap bitmap, String fileName) {
        File file = new File(this.getCacheDir(), fileName);
        try {
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, bos);
            byte[] bitmapData = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public void clickedItem(View v, int position) {
        switch (v.getId()) {
            case R.id.remove_image_product:
                mListImageProduct.remove(position);
                mImageUploads.remove(position);
                mImageProductAdapter.notifyItemRemoved(position);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.type_addcoupon:
                String[] type = getResources().getStringArray(R.array.define_type_coupon);
                typeCoupon = Integer.valueOf(type[position]);
                switch (typeCoupon){
                    case  1 :
                        mCardViewValue.setVisibility(View.GONE);
                        mCardViewValueType.setVisibility(View.GONE);
                        break;
                    case 2 :
                        mCardViewValue.setVisibility(View.VISIBLE);
                        mCardViewValueType.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case R.id.valuetype_addcoupon:
                String[] valueType = getResources().getStringArray(R.array.define_valuetype_coupon);
                valueTypeCoupon = Integer.valueOf(valueType[position]);
                break;
            case R.id.category_addcoupon:
                String[] category = getResources().getStringArray(R.array.define_category_coupon);
                categoryCoupon = Integer.valueOf(category[position]);
                String[] descriptionCat = getResources().getStringArray(R.array.define_description_category_coupon);
                descriptionCategoryCoupon = descriptionCat[position];
                for (ImageUpload i: mImageUploads) {
                    i.setDescription(descriptionCategoryCoupon);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
