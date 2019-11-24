package com.ptit.cms.dao;

import com.ptit.cms.model.other.ImageResponse;

import java.math.BigInteger;
import java.util.List;

public interface ImageDao {
    List<BigInteger> getCouponByImage(List<ImageResponse> listImage, int limit);
}
