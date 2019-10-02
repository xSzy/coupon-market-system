package com.ptit.cms.util;

public class Constant
{
	//settings
	public static final String IMAGE_UPLOAD_PATH = "C:\\CMS\\images\\";
	
	//gender
	public static final int GENDER_MALE = 1;
	public static final int GENDER_FEMALE = 2;
	public static final int GENDER_UNKNOWN = 0;

	//role
	public static final int ROLE_USER = 1;
	public static final int ROLE_ADMIN = 2;
	
	//coupon type
	public static final int COUPON_TYPE_FREE_SHIPPING = 1;
	public static final int COUPON_TYPE_PRODUCT_DISCOUNT = 2;
	
	//coupon value type
	public static final int VALUE_TYPE_PERCENT = 1;
	public static final int VALUE_TYPE_VND = 2;
	public static final int VALUE_TYPE_USD = 3;
	public static final int VALUE_TYPE_OTHER = 4;
	
	//response constant
	public static final String STATUS_ERROR = "error";
	public static final String STATUS_SUCCESS = "success";
	public static final int VALIDATION_FAILED = -1;
	
	//exceptions
	public static final String EXCEPTION_ACCOUNT_EXISTS = "username exists";
	public static final String EXCEPTION_INCORRECT_LOGIN = "wrong username or password";
	public static final String EXCEPTION_FILE_CORRUPT = "file corrupted";
	
}
