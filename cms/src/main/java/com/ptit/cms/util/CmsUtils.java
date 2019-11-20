package com.ptit.cms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CmsUtils {

    public static String convertToResourcePath(String absolutePath)
    {
        String temp = absolutePath.replace("\\", "/");
        int index = temp.lastIndexOf("cmsdata/");
        index += 8;
        temp = temp.substring(index, temp.length());
        return temp;
    }

    public static String convertDateToString(Date date, String dateFormat)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }
}
