package com.ptit.cms.dao;

import com.ptit.cms.model.other.ImageResponse;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

public interface ImageDao {
    Set<BigInteger> getCouponByImage(List<ImageResponse> listImage);
}
