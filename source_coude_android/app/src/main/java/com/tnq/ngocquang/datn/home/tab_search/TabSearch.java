package com.tnq.ngocquang.datn.home.tab_search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.tnq.ngocquang.datn.BuildConfig;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.adapter.CouponAdapter;
import com.tnq.ngocquang.datn.constant.Constant;
import com.tnq.ngocquang.datn.home.tab_info.manage_coupon.AddCouponNew;
import com.tnq.ngocquang.datn.model.Coupon;
import com.tnq.ngocquang.datn.model.ImageUpload;
import com.tnq.ngocquang.datn.model.ProductImage;
import com.tnq.ngocquang.datn.support.EndlessRecyclerViewScrollListener;
import com.tnq.ngocquang.datn.support.MultipartRequest;
import com.tnq.ngocquang.datn.support.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TabSearch extends Fragment {


    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 3;
    private ImageView mImageSearch;
    private FloatingActionButton mBtnSearchImage;
    private ImageView mCamera;
    private RelativeLayout mChildrenLayout;
    private RelativeLayout mParentLayout;
    private EditText mEdtSearch;
    private static View mView;
    private static RecyclerView mRecyclerView;
    private static ArrayList<Coupon> mListCouponSearch;
    private CouponAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private String mKeySearch;
    private RequestQueue mRequestQueue;
    private RelativeLayout mLoadingProgress;
    private CardView mPenKeySearch, mChairKeySearch, mDressKeySearch;
    private static final String URL_SEARCH_COUPON_BY_IMAGE = Constant.hostname + Constant.findByImageAPI;
    private static final String URL_SEARCH_COUPON_BY_TEXT = Constant.hostname + Constant.findByNameAPI;
    private String cameraFilePath;

    public TabSearch() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_tab_search, container, false);
        anhxa();
        setOnTouchParentLayout();
        mEdtSearch.setOnEditorActionListener(searchActionListener());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.d("AAA page : ", page + "");
                loadDataSearchByText(page);

            }
        };
        mRecyclerView.addOnScrollListener(mScrollListener);
        mPenKeySearch.setOnClickListener(searchByKeyHot());
        mChairKeySearch.setOnClickListener(searchByKeyHot());
        mDressKeySearch.setOnClickListener(searchByKeyHot());
        searchByImage();
        progressSearchByImage();
        return mView;
    }

    private void searchByImage() {


        //
        mCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(mView.getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                        Toast.makeText(mView.getContext(), "xin hãy thao tác lại!", Toast.LENGTH_SHORT).show();
                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    captureFromCamera();

                }
            }
        });
    }

    private void progressSearchByImage(){
        mBtnSearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetListResult();
                mLoadingProgress.setVisibility(View.VISIBLE);
                mChildrenLayout.setVisibility(View.GONE);
                mParentLayout.setVisibility(View.VISIBLE);
                Bitmap bitmap = ((BitmapDrawable) mImageSearch.getDrawable()).getBitmap();
                ImageUpload imageUpload = new ImageUpload(convertBitmapToFile(bitmap,Uri.parse(cameraFilePath).getLastPathSegment()), "100");
//
                // send request
                MultipartRequest request = new MultipartRequest(Request.Method.POST, URL_SEARCH_COUPON_BY_IMAGE, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            String result = new String(networkResponse.data);
                            try {
                                JSONObject errorRes = new JSONObject(result);
                                Log.d("AAA error imgsearch ", errorRes.toString());
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
                            if(status.equals("success")){
                                JSONArray listCouponJson = result.getJSONArray("data");
                                Log.d("AAA searchcoupon ", result.toString());
                                for (int i = 0; i < listCouponJson.length(); i++) {
                                    JSONObject couponJson = listCouponJson.getJSONObject(i);
                                    Coupon coupon = new Gson().fromJson(couponJson.toString(), Coupon.class);
                                    mListCouponSearch.add(coupon);
                                    mAdapter.notifyDataSetChanged();
                                }

                                mLoadingProgress.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, imageUpload,"search");
                request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                mRequestQueue.add(request);
            }
        });

    }


    private File convertBitmapToFile(Bitmap bitmap, String fileName) {
        File file = new File(getActivity().getCacheDir(), fileName);
        try {
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }

    private void captureFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(mView.getContext(), BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    mChildrenLayout.setVisibility(View.VISIBLE);
                    mParentLayout.setVisibility(View.GONE);
                    mImageSearch.setImageURI(Uri.parse(cameraFilePath));
                    break;
            }
        }

    }

    private void setOnTouchParentLayout() {
        mParentLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                setHideKeyboard();
                return true;
            }
        });
    }

    private void setHideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mView.getWindowToken() != null) {
            imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
        }
    }

    private void fetchDataByText(int page, String keySearch) {
        mLoadingProgress.setVisibility(View.VISIBLE);
        String url = URL_SEARCH_COUPON_BY_TEXT.toString();
        url += "?pageNum=" + page + "&pageSize=20" + "&query=" + keySearch;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray listCouponJson = response.getJSONArray("data");
                    Log.d("AAA searchcoupon ", response.toString());
                    for (int i = 0; i < listCouponJson.length(); i++) {
                        JSONObject couponJson = listCouponJson.getJSONObject(i);
                        Coupon coupon = new Gson().fromJson(couponJson.toString(), Coupon.class);
                        mListCouponSearch.add(coupon);
                        mAdapter.notifyDataSetChanged();
                    }

                    mLoadingProgress.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);
    }

    private void loadDataSearchByText(int page) {
        fetchDataByText(page, mKeySearch);
    }


    private View.OnClickListener searchByKeyHot() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                switch (id) {
                    case R.id.pen_key_search: {
                        performSearchText("bút");
                        break;
                    }
                    case R.id.chair_key_search: {
                        performSearchText(" ghế ");
                        break;
                    }
                    case R.id.dress_key_search: {
                        performSearchText(" váy ");
                        break;
                    }


                }
            }
        };
    }

    private TextView.OnEditorActionListener searchActionListener() {
        return new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearchText(textView.getText().toString());
                    return true;
                }
                return false;
            }
        };
    }


    private void performSearchText(String keySearch) {
        resetListResult();
        mKeySearch = keySearch.toString();
        setHideKeyboard();
        loadDataSearchByText(1);
    }

    private void resetListResult(){
        mListCouponSearch.clear();
        mAdapter.notifyDataSetChanged();
        mScrollListener.resetState();
    }

    private void anhxa() {
        mBtnSearchImage = mView.findViewById(R.id.btn_search_image);
        mImageSearch = mView.findViewById(R.id.image_search);
        mCamera = mView.findViewById(R.id.camera_search);
        mChildrenLayout = mView.findViewById(R.id.children_layout_tabsarch);
        mParentLayout = mView.findViewById(R.id.parent_layout_tabsearch);
        mEdtSearch = mView.findViewById(R.id.search_text_coupon);
        mRecyclerView = mView.findViewById(R.id.recyclerViewCouponBySearch);
        mLayoutManager = new LinearLayoutManager(mView.getContext());
        mListCouponSearch = new ArrayList<>();
        mAdapter = new CouponAdapter(mView.getContext(), mListCouponSearch);
        mRequestQueue = MyVolley.getInstance(mView.getContext()).getRequestQueue();
        mLoadingProgress = mView.findViewById(R.id.loading_progress_search);
        mPenKeySearch = mView.findViewById(R.id.pen_key_search);
        mChairKeySearch = mView.findViewById(R.id.chair_key_search);
        mDressKeySearch = mView.findViewById(R.id.dress_key_search);
    }


}
