package com.tnq.ngocquang.datn.support;

import android.media.Image;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.tnq.ngocquang.datn.model.ImageUpload;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class MultipartRequest extends Request<NetworkResponse> {

    private MultipartEntity entity = new MultipartEntity();
    private final Response.Listener<NetworkResponse> mListener;
    private HashMap<String,String> mParams;
    private ImageUpload mImageUpload;
    private String mKind;

    public MultipartRequest(int method, String url, @Nullable Response.ErrorListener errorListener, Response.Listener listener,
                            ImageUpload imageUploads,String kind) {
        super(method, url, errorListener);
        mListener = listener;
        mImageUpload = imageUploads;
        mKind = kind;
        buildMultipartEntity();
        Log.d("AAA image ",mImageUpload.getFile() + "");
    }

    private void buildMultipartEntity() {
        if(mKind == "upload"){
            entity.addPart("file", new FileBody(mImageUpload.getFile()));
            try {
                entity.addPart("description",new StringBody(mImageUpload.getDescription()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if(mKind == "search"){
            entity.addPart("file",new FileBody(mImageUpload.getFile()));
            try {
                entity.addPart("limit", new StringBody(mImageUpload.getDescription()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response,HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        mListener.onResponse(response);
    }
}
