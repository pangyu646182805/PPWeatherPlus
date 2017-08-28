package com.ppyy.ppweatherplus.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.SystemUtils;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;
import com.ppyy.ppweatherplus.utils.WeatherIconAndDescUtils;
import com.ppyy.ppweatherplus.utils.WeatherTimeUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/25.
 */

public class HourWeatherView extends View {
    private static final int AVERAGE_AREA = 6;

    private Context mContext;

    private List<WeatherInfoResponse.HourfcBean> mHourWeatherDataList;

    private int mWidth, mHeight;

    /**
     * 折线图的绘制区域的高度
     */
    private float mLineChartRectHeight;

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 屏幕宽度下平均每块区域的宽度
     */
    private float mAverageAreaWidth;

    /**
     * 线的宽度
     */
    private float mLineStrokeWidth;

    /**
     * 文本大小
     */
    private float mTextSize;

    private float mTempTextSize;

    /**
     * 温度线的Path
     */
    private Path mLinePath;

    /**
     * 线的画笔
     */
    private Paint mTempLinePaint;

    /**
     * 圆点的画笔
     */
    private Paint mCirclePaint;

    /**
     * 文本的画笔
     */
    private Paint mTextPaint;

    /**
     * 最高温度
     */
    private int mMaxTemp;

    /**
     * 最低温度
     */
    private int mMinTemp;

    private Rect mTextRect;

    private int mTimeTextColor;

    private int mTextColor;

    private float mDimen8;

    /**
     * 图标的大小
     */
    private float mIconWidthAndHeight;

    private int mTempTextRectHeight;

    private ArrayMap<Integer, Bitmap> mArrayMap = new ArrayMap<>();

    public void setHourWeatherDataList(List<WeatherInfoResponse.HourfcBean> hourWeatherDataList) {
        mHourWeatherDataList = hourWeatherDataList;
        mScreenWidth = SystemUtils.getScreenWidth((Activity) mContext);
        mAverageAreaWidth = mScreenWidth * 1.0f / AVERAGE_AREA;
        mWidth = (int) (mAverageAreaWidth * hourWeatherDataList.size());
        requestLayout();
        invalidate();
    }

    public HourWeatherView(Context context) {
        this(context, null);
    }

    public HourWeatherView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HourWeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mTextRect = new Rect();

        Resources resources = getResources();
        mHeight = (int) resources.getDimension(R.dimen.y252);
        mLineStrokeWidth = resources.getDimension(R.dimen.x2);
        mIconWidthAndHeight = resources.getDimension(R.dimen.x42);
        mDimen8 = resources.getDimension(R.dimen.x8);
        mTimeTextColor = resources.getColor(R.color.white_c);
        mTextColor = resources.getColor(R.color.white);
        mTextSize = UIUtils.getRawSize(mContext, TypedValue.COMPLEX_UNIT_SP, 11f);
        mTempTextSize = UIUtils.getRawSize(mContext, TypedValue.COMPLEX_UNIT_SP, 13f);

        // Path
        mLinePath = new Path();

        // Paint
        mTempLinePaint = new Paint();
        mTempLinePaint.setDither(true);
        mTempLinePaint.setColor(mTextColor);
        mTempLinePaint.setStrokeWidth(mLineStrokeWidth);
        mTempLinePaint.setStyle(Paint.Style.STROKE);
        mTempLinePaint.setAntiAlias(true);
        // 曲线
        /*CornerPathEffect cornerPathEffect = new CornerPathEffect(100);
        mTempLinePaint.setPathEffect(cornerPathEffect);*/

        mCirclePaint = new Paint(mTempLinePaint);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTimeTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mHourWeatherDataList != null && !mHourWeatherDataList.isEmpty()) {
            boolean isDay = TimeUtils.judgeDayOrNight();
            getMaxAndMinTemp(mHourWeatherDataList);
            float startX = mAverageAreaWidth * 0.5f;
            int preType, currentType;
            long start = System.currentTimeMillis();
            for (int i = 0; i < mHourWeatherDataList.size(); i++) {
                WeatherInfoResponse.HourfcBean hourfcBean = mHourWeatherDataList.get(i);
                String weatherTime = WeatherTimeUtils.getWeatherTime(hourfcBean.getTime());

                mTextPaint.setTextSize(mTextSize);
                canvas.drawText(weatherTime, startX, getMeasuredHeight() - 2 * mDimen8, mTextPaint);
                int temp = hourfcBean.getWthr();
                float tempYAxis = calTempYAxis(temp);
                currentType = hourfcBean.getType();
                if (i == 0) {
                    mLinePath.moveTo(startX, tempYAxis);
                    preType = currentType;
                } else {
                    WeatherInfoResponse.HourfcBean preHourfcBean = mHourWeatherDataList.get(i - 1);
                    preType = preHourfcBean.getType();
                    mLinePath.lineTo(startX, tempYAxis);
                    // 曲线
                    /*float previousStartX = startX - mAverageAreaWidth;
                    float previousTempYAxis;
                    int previousTemp = mHourWeatherDataList.get(i - 1).getWthr();

                    previousTempYAxis = calTempYAxis(previousTemp);
                    mLinePath.cubicTo((previousStartX + startX) / 2, getMeasuredHeight() - (getMeasuredHeight() - previousTempYAxis),
                            (previousStartX + startX) / 2, tempYAxis, startX, tempYAxis);*/
                }
                mTextPaint.setTextSize(mTempTextSize);
                UIUtils.getTextBounds(mTextPaint, Constant.TEMP, mTextRect);
                canvas.drawText(temp + Constant.TEMP, startX + mTextRect.width(), tempYAxis - mDimen8 * 1.5f, mTextPaint);
                canvas.drawCircle(startX, tempYAxis, mDimen8 * 0.8f, mCirclePaint);

                if (i == 0 || currentType != preType) {
                    Bitmap bitmap = mArrayMap.get(currentType);
                    if (bitmap == null) {
                        bitmap = UIUtils.zoomImage(BitmapFactory.decodeResource(getResources(),
                                WeatherIconAndDescUtils.getWeatherIconResByType(currentType,
                                        !isDay)), mIconWidthAndHeight, mIconWidthAndHeight);
                        mArrayMap.put(currentType, bitmap);
                    }
                    canvas.drawBitmap(bitmap, startX - mIconWidthAndHeight * 0.5f,
                            tempYAxis - mDimen8 * 2f - mTempTextRectHeight - mIconWidthAndHeight, null);
                }

                startX += mAverageAreaWidth;
            }
            L.e("HourWeatherView time : " + (System.currentTimeMillis() - start));
            canvas.drawPath(mLinePath, mTempLinePaint);
        }
    }

    /**
     * 获取最高温度和最低温度
     */
    private void getMaxAndMinTemp(List<WeatherInfoResponse.HourfcBean> dataList) {
        mMaxTemp = dataList.get(0).getWthr();
        mMinTemp = mMaxTemp;
        for (int i = 1; i < dataList.size(); i++) {
            int maxTemp = dataList.get(i).getWthr();
            int minTemp = dataList.get(i).getWthr();
            if (maxTemp > mMaxTemp) {
                mMaxTemp = maxTemp;
            }
            if (minTemp < mMinTemp) {
                mMinTemp = minTemp;
            }
        }
        L.e(mMaxTemp + " : " + mMinTemp);
        /*WeatherInfoResponse.HourfcBean max = Collections.max(dataList, new Comparator<WeatherInfoResponse.HourfcBean>() {
            @Override
            public int compare(WeatherInfoResponse.HourfcBean hourfcBean, WeatherInfoResponse.HourfcBean t1) {
                return hourfcBean.getWthr() - t1.getWthr();
            }
        });
        L.e("max temp : " + max.getWthr());*/
    }

    /**
     * 线的y坐标
     */
    private float calTempYAxis(int temp) {
        int diff = mMaxTemp - temp;
        int diffTemp = mMaxTemp - mMinTemp;
        float percent = diff * 1.0f / diffTemp;
        return mLineChartRectHeight * percent + mIconWidthAndHeight + mTempTextRectHeight + mDimen8 * 1.5f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHourWeatherDataList != null && !mHourWeatherDataList.isEmpty()) {
            setMeasuredDimension(mWidth, mHeight);
            mTextPaint.setTextSize(mTextSize);
            UIUtils.getTextBounds(mTextPaint, "12:00", mTextRect);
            mLineChartRectHeight = mHeight - mTextRect.height() - 6 * mDimen8;

            mTextPaint.setTextSize(mTempTextSize);
            UIUtils.getTextBounds(mTextPaint, "32°", mTextRect);
            mTempTextRectHeight = mTextRect.height();
            mLineChartRectHeight = mLineChartRectHeight - mTempTextRectHeight - mDimen8 * 1.5f - mIconWidthAndHeight;
            L.e("24小时预报 mLineChartRectHeight : " + mLineChartRectHeight);
        }
    }
}
