package com.tnq.ngocquang.datn.list_coupon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tnq.ngocquang.datn.R;
import com.tnq.ngocquang.datn.model.Coupon;
import com.tnq.ngocquang.datn.support.ConvertCouponValue;

public class DetailCouponActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDescription;
    private ImageView mImage;
    private ImageView mShare;
    private TextView mCouponValue;
    private TextView mClickCount;
    private TextView mContact;
    private TextView mAddress;
    private ImageView mMap;
    private Coupon coupon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_coupon);
        anhxa();
        Intent intent = getIntent();
         coupon = intent.getParcelableExtra("coupon");
        Log.d("AAA",coupon.getProduct().getContact());
        if (coupon != null) {
            initData(coupon);
        }
    }

    private void initData(final Coupon coupon) {
        mTitle.setText(coupon.getTitle());
        mDescription.setText(coupon.getDescription());
        // this is demo...
        TypedArray image = getResources().obtainTypedArray(R.array.demo_icon_category);
        Glide.with(this).load(image.getResourceId(0, 0)).into(mImage);
        image.recycle();
        // convert coupon value //////////////////////
        String couponValue = ConvertCouponValue.convert(coupon.getType(),coupon.getValue(),coupon.getValuetype());

        ////////////////////////////////////////////
        mCouponValue.setText(couponValue);
        mClickCount.setText(coupon.getClickCount() + " lượt xem");
        if(coupon.getCreateBy() != null){
            mAddress.setText(coupon.getCreateBy().getAddress());
        }else{
            mAddress.setText("no address");
            mMap.setVisibility(View.INVISIBLE);
        }

    }

    private void anhxa() {
        mTitle = findViewById(R.id.coupon_detail_title);
        mDescription = findViewById(R.id.description_detail_coupon);
        mImage = findViewById(R.id.image_detail_coupon);
        mShare = findViewById(R.id.share_detail_coupon);
        mCouponValue = findViewById(R.id.coupon_detail_value);
        mClickCount = findViewById(R.id.count_click_detail_coupon);
        mAddress = findViewById(R.id.address_detail_coupon);
        mMap = findViewById(R.id.map_detail_coupon);
        mContact = findViewById(R.id.contact_detail_coupon);
    }

    public void shareCoupon(View view)
    {
        String content = coupon.getTitle() + "\n" + "khuyến mãi : " + ConvertCouponValue.convert(coupon.getType(),coupon.getValue(),coupon.getValuetype());
        String mimeType = "text/plain";
        String title = "bạn muốn chia sẻ với :";
        ShareCompat.IntentBuilder.from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(content)
                .startChooser();
    }

    public void callToAuthor(View view)
    {
        if(coupon.getCreateBy() != null){
            String phoneNumber = coupon.getCreateBy().getPhoneNumber().trim();
            Uri data = Uri.parse("tel:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(data);
            startActivity(intent);
        }else{
            String phoneNumber = "0904424714";
            Uri data = Uri.parse("tel:" + phoneNumber);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(data);
            startActivity(intent);
        }

    }

    public void mapOfCoupon(View view)
    {

        if(coupon.getCreateBy() != null){
            String loc = coupon.getCreateBy().getAddress().trim();
            Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
            Intent intent = new Intent(Intent.ACTION_VIEW,addressUri);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "không thực hiện được hành động", Toast.LENGTH_SHORT).show();
            }
        }else{
            String loc = "PTIT";
            Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
            Intent intent = new Intent(Intent.ACTION_VIEW,addressUri);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "không thực hiện được hành động", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void openWebsite(View view) {
        if (coupon != null){
            String url = coupon.getProduct().getContact().trim();
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "không thực hiện được hành động", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
