package com.ptit.cms.dao.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ptit.cms.dao.ImageDao;
import com.ptit.cms.model.other.ImageResponse;
import com.ptit.cms.util.CmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

@Repository
public class ImageDaoImpl implements ImageDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<BigInteger> getCouponByImage(List<ImageResponse> listImage, int limit) {
        String sql = "SELECT DISTINCT " +
                "    ( tmp.id )  " +
                "FROM " +
                "    ( " +
                "    SELECT " +
                "        coupon.*, " +
                "        img.image  " +
                "    FROM " +
                "        tbl_product_image img " +
                "        INNER JOIN tbl_product prod ON prod.id = img.product_id " +
                "        INNER JOIN tbl_coupon coupon ON coupon.product_id = prod.id  " +
                "    WHERE " +
                "        img.image IN ( addSql1 )  " +
                "    ) tmp  " +
                "ORDER BY " +
                "CASE addSql2 ELSE 100000  " +
                "END " +
                "LIMIT " + limit;
        StringBuilder addSql1Builder = new StringBuilder("");
        StringBuilder addSql2Builder = new StringBuilder("");
        ObjectMapper mapper = new ObjectMapper();
        int end = limit*50 < listImage.size() ? limit*50 : listImage.size();
        for(int i = 0; i < end; i++)
        {
            listImage.set(i, mapper.convertValue(listImage.get(i), ImageResponse.class));
            listImage.get(i).setImage(CmsUtils.convertToResourcePath(listImage.get(i).getImage()));
            addSql1Builder.append("?, ");
            addSql2Builder.append("WHEN tmp.image = ? THEN " + i + " ");
        }
        String addSql1 = addSql1Builder.substring(0, addSql1Builder.lastIndexOf(","));
        sql = sql.replace("addSql1", addSql1);
        sql = sql.replace("addSql2", addSql2Builder.toString());
        System.out.println(StringUtils.countOccurrencesOf(sql, "?"));
        Query query = entityManager.createNativeQuery(sql);
        for(int i = 0; i < end; i++)
        {
            query = query.setParameter(i+1, listImage.get(i).getImage());
            query = query.setParameter(end+i+1, listImage.get(i).getImage());
        }
        List<BigInteger> listIds = query.getResultList();
        return listIds;
    }
}
