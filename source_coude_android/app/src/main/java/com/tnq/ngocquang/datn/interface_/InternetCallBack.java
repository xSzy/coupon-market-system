package com.tnq.ngocquang.datn.interface_;

public interface InternetCallBack {
    void onProgress(int progress);
    void onSuccess();
    void onFailure(Exception e);
}
