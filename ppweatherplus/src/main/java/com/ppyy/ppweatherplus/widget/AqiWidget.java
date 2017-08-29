package com.ppyy.ppweatherplus.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.utils.AqiUtils;

/**
 * Created by NeuroAndroid on 2017/8/24.
 */

public class AqiWidget extends FrameLayout {
    private Context mContext;
    private ImageView mIvAqiBackground;
    private NoPaddingTextView mTvAqi;
    private NoPaddingTextView mTvAqiDesc;
    private boolean mShowAqi;

    private int mAqi;

    public AqiWidget(@NonNull Context context) {
        this(context, null);
    }

    public AqiWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AqiWidget(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AqiWidget);
        mShowAqi = typedArray.getBoolean(R.styleable.AqiWidget_aqi_widget_show_aqi, true);
        typedArray.recycle();
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_aqi_widget, this);
        mIvAqiBackground = (ImageView) findViewById(R.id.iv_aqi_background);
        mTvAqi = (NoPaddingTextView) findViewById(R.id.tv_aqi);
        mTvAqiDesc = (NoPaddingTextView) findViewById(R.id.tv_aqi_desc);
        mTvAqi.setVisibility(mShowAqi ? View.VISIBLE : View.GONE);
    }

    public void setAqi(int aqi) {
        mAqi = aqi;
        mTvAqi.setText(String.valueOf(aqi));
        mTvAqiDesc.setText(AqiUtils.getAqiText(aqi));
        mIvAqiBackground.setColorFilter(getResources().getColor(AqiUtils.getAqiColor()));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        View aqiView = findViewById(R.id.ll_aqi);
        mIvAqiBackground.getLayoutParams().width = aqiView.getMeasuredWidth();
        mIvAqiBackground.getLayoutParams().height = aqiView.getMeasuredHeight();
        mIvAqiBackground.requestLayout();
        setMeasuredDimension(aqiView.getMeasuredWidth(), aqiView.getMeasuredHeight());
    }
}
