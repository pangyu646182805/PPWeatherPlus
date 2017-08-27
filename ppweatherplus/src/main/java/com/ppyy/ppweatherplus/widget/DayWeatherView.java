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
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.utils.AqiUtils;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.SystemUtils;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;
import com.ppyy.ppweatherplus.utils.WeatherIconAndDescUtils;
import com.ppyy.ppweatherplus.utils.WeatherTimeUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/26.
 */

public class DayWeatherView extends View {
    private static final int AVERAGE_AREA = 6;

    private Context mContext;

    private List<WeatherInfoResponse.Forecast15Bean> mForecast15DataList;
    private int mScreenWidth;
    private float mAverageAreaWidth;
    private int mHeight, mWidth;

    /**
     * 文本大小
     */
    private float mTextSize11Sp, mTextSize12Sp, mTextSize13Sp, mTextSize14sp;

    /**
     * 温度线的Path
     */
    private Path mMaxLinePath, mMinLinePath;

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

    private Paint mRoundRectPaint;

    /**
     * 最高温度
     */
    private int mMaxTemp;

    /**
     * 最低温度
     */
    private int mMinTemp;

    private Rect mTextRect;

    private RectF mRoundRect;

    private int mTextColor;

    private float mDimen8;

    /**
     * 图标的大小
     */
    private float mIconWidthAndHeight;
    private float mLineStrokeWidth;

    /**
     * 折线图的绘制区域的高度
     */
    private float mLineChartRectHeight;

    private ArrayMap<Integer, Bitmap> mDayBitmapPool = new ArrayMap<>();
    private ArrayMap<Integer, Bitmap> mNightBitmapPool = new ArrayMap<>();
    private float mTopHeight;

    public void setForecast15(List<WeatherInfoResponse.Forecast15Bean> forecast15DataList) {
        mForecast15DataList = forecast15DataList;
        mScreenWidth = SystemUtils.getScreenWidth((Activity) mContext);
        mAverageAreaWidth = mScreenWidth * 1.0f / AVERAGE_AREA;
        mWidth = (int) (mAverageAreaWidth * mForecast15DataList.size());
        requestLayout();
        invalidate();
    }

    public List<WeatherInfoResponse.Forecast15Bean> getForecast15DataList() {
        return mForecast15DataList;
    }

    public DayWeatherView(Context context) {
        this(context, null);
    }

    public DayWeatherView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayWeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mTextRect = new Rect();
        mRoundRect = new RectF();

        Resources resources = getResources();
        mHeight = (int) resources.getDimension(R.dimen.y720);
        mLineStrokeWidth = resources.getDimension(R.dimen.x2);
        mIconWidthAndHeight = resources.getDimension(R.dimen.x48);
        mDimen8 = resources.getDimension(R.dimen.x8);
        mTextColor = resources.getColor(R.color.white);
        mTextSize11Sp = UIUtils.getRawSize(mContext, TypedValue.COMPLEX_UNIT_SP, 11f);
        mTextSize12Sp = UIUtils.getRawSize(mContext, TypedValue.COMPLEX_UNIT_SP, 12f);
        mTextSize13Sp = UIUtils.getRawSize(mContext, TypedValue.COMPLEX_UNIT_SP, 13f);
        mTextSize14sp = UIUtils.getRawSize(mContext, TypedValue.COMPLEX_UNIT_SP, 14f);

        // Path
        mMaxLinePath = new Path();
        mMinLinePath = new Path();

        // Paint
        mTempLinePaint = new Paint();
        mTempLinePaint.setDither(true);
        mTempLinePaint.setColor(mTextColor);
        mTempLinePaint.setStrokeWidth(mLineStrokeWidth);
        mTempLinePaint.setStyle(Paint.Style.STROKE);
        mTempLinePaint.setAntiAlias(true);

        mCirclePaint = new Paint(mTempLinePaint);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mRoundRectPaint = new Paint(mCirclePaint);
        mRoundRectPaint.setColor(resources.getColor(R.color.air_quality_level_1));

        mTextPaint = new Paint();
        mTextPaint.setDither(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mForecast15DataList != null && !mForecast15DataList.isEmpty()) {
            getMaxAndMinTemp(mForecast15DataList);
            float startX = mAverageAreaWidth * 0.5f;
            float preY;
            long start = System.currentTimeMillis();
            for (int i = 0; i < mForecast15DataList.size(); i++) {
                WeatherInfoResponse.Forecast15Bean forecast15Bean = mForecast15DataList.get(i);
                WeatherInfoResponse.Forecast15Bean.DayBeanX dayBeanX = forecast15Bean.getDay();
                WeatherInfoResponse.Forecast15Bean.NightBeanX nightBeanX = forecast15Bean.getNight();

                mTextPaint.setTextSize(mTextSize14sp);
                String weekText;
                if (i == 0)
                    weekText = "昨天";
                else if (i == 1)
                    weekText = "今天";
                else
                    weekText = TimeUtils.getWeekText(forecast15Bean.getDate());
                UIUtils.getTextBounds(mTextPaint, weekText, mTextRect);
                preY = mTextRect.height();
                canvas.drawText(weekText, startX, preY, mTextPaint);

                String weatherDate = WeatherTimeUtils.getWeatherDate(forecast15Bean.getDate());
                mTextPaint.setTextSize(mTextSize11Sp);
                UIUtils.getTextBounds(mTextPaint, weatherDate, mTextRect);
                preY += mTextRect.height() + mDimen8;
                canvas.drawText(weatherDate, startX, preY, mTextPaint);

                mTextPaint.setTextSize(mTextSize12Sp);
                UIUtils.getTextBounds(mTextPaint, "优质", mTextRect);
                preY += mTextRect.height() + 1.5f * mDimen8 + 0.5f * mDimen8;

                float left = startX - 0.5f * mTextRect.width() - mDimen8;
                float top = preY - mTextRect.height() - 0.5f * mDimen8;
                float right = startX + 0.5f * mTextRect.width() + mDimen8;
                float bottom = preY + 1.1f * mDimen8;
                mRoundRect.set(left, top, right, bottom);

                String aqiText = AqiUtils.getAqiText(forecast15Bean.getAqi());
                mRoundRectPaint.setColor(getResources().getColor(AqiUtils.getAqiColor()));
                canvas.drawRoundRect(mRoundRect, mDimen8, mDimen8, mRoundRectPaint);
                canvas.drawText(aqiText, startX, preY, mTextPaint);

                mTextPaint.setTextSize(mTextSize11Sp);
                String weatherDesc = dayBeanX.getWthr();
                UIUtils.getTextBounds(mTextPaint, weatherDesc, mTextRect);
                preY = bottom + mTextRect.height() + 1.5f * mDimen8;
                canvas.drawText(weatherDesc, startX, preY, mTextPaint);

                preY += 2 * mDimen8;
                int iconType = dayBeanX.getType();
                Bitmap bitmap = mDayBitmapPool.get(iconType);
                if (bitmap == null) {
                    bitmap = UIUtils.zoomImage(BitmapFactory.decodeResource(getResources(),
                            WeatherIconAndDescUtils.getWeatherIconResByType(dayBeanX.getType(),
                                    false)), mIconWidthAndHeight, mIconWidthAndHeight);
                    mDayBitmapPool.put(iconType, bitmap);
                }
                canvas.drawBitmap(bitmap, startX - mIconWidthAndHeight * 0.5f, preY, null);

                String wp = dayBeanX.getWp();
                mTextPaint.setTextSize(mTextSize11Sp);
                UIUtils.getTextBounds(mTextPaint, wp, mTextRect);
                preY = mHeight - 2 * mDimen8;
                canvas.drawText(wp, startX, preY, mTextPaint);
                preY -= mTextRect.height() + 1.5f * mDimen8;

                String wd = dayBeanX.getWd();
                mTextPaint.setTextSize(mTextSize13Sp);
                UIUtils.getTextBounds(mTextPaint, wd, mTextRect);
                canvas.drawText(wd, startX, preY, mTextPaint);
                preY -= mTextRect.height() + 2 * mDimen8;

                weatherDesc = nightBeanX.getWthr();
                mTextPaint.setTextSize(mTextSize14sp);
                UIUtils.getTextBounds(mTextPaint, weatherDesc, mTextRect);
                canvas.drawText(weatherDesc, startX, preY, mTextPaint);
                preY -= mTextRect.height() + 2 * mDimen8 + mIconWidthAndHeight;

                iconType = nightBeanX.getType();
                bitmap = mNightBitmapPool.get(iconType);
                if (bitmap == null) {
                    bitmap = UIUtils.zoomImage(BitmapFactory.decodeResource(getResources(),
                            WeatherIconAndDescUtils.getWeatherIconResByType(dayBeanX.getType(),
                                    true)), mIconWidthAndHeight, mIconWidthAndHeight);
                    mNightBitmapPool.put(iconType, bitmap);
                }
                canvas.drawBitmap(bitmap, startX - mIconWidthAndHeight * 0.5f, preY, null);

                int highTemp = forecast15Bean.getHigh();
                float highTempYAxis = calTempYAxis(highTemp);
                int lowTemp = forecast15Bean.getLow();
                float lowTempYAxis = calTempYAxis(lowTemp);
                if (i == 0) {
                    mMaxLinePath.moveTo(startX, highTempYAxis);
                    mMinLinePath.moveTo(startX, lowTempYAxis);
                } else {
                    mMaxLinePath.lineTo(startX, highTempYAxis);
                    mMinLinePath.lineTo(startX, lowTempYAxis);
                }
                mTextPaint.setTextSize(mTextSize13Sp);
                UIUtils.getTextBounds(mTextPaint, lowTemp + Constant.TEMP, mTextRect);
                int lowHeight = mTextRect.height();
                UIUtils.getTextBounds(mTextPaint, Constant.TEMP, mTextRect);
                canvas.drawText(highTemp + Constant.TEMP, startX + mTextRect.width(), highTempYAxis - mDimen8 * 1.5f, mTextPaint);
                canvas.drawText(lowTemp + Constant.TEMP, startX + mTextRect.width(), lowTempYAxis + mDimen8 * 1.5f + lowHeight, mTextPaint);

                canvas.drawCircle(startX, highTempYAxis, mDimen8 * 0.8f, mCirclePaint);
                canvas.drawCircle(startX, lowTempYAxis, mDimen8 * 0.8f, mCirclePaint);

                startX += mAverageAreaWidth;
            }
            canvas.drawPath(mMaxLinePath, mTempLinePaint);
            canvas.drawPath(mMinLinePath, mTempLinePaint);
            L.e("time : " + (System.currentTimeMillis() - start));
        }
    }

    /**
     * 获取最高温度和最低温度
     */
    private void getMaxAndMinTemp(List<WeatherInfoResponse.Forecast15Bean> dataList) {
        mMaxTemp = dataList.get(0).getHigh();
        mMinTemp = dataList.get(0).getLow();
        for (int i = 1; i < dataList.size(); i++) {
            int maxTemp = dataList.get(i).getHigh();
            int minTemp = dataList.get(i).getLow();
            if (maxTemp > mMaxTemp) {
                mMaxTemp = maxTemp;
            }
            if (minTemp < mMinTemp) {
                mMinTemp = minTemp;
            }
        }
        // += -= 是为了画出更加好看的折线图
        int diffTemp = mMaxTemp - mMinTemp;
        mMaxTemp += diffTemp / 2;
        mMinTemp -= diffTemp / 2;
    }

    /**
     * 线的y坐标
     */
    private float calTempYAxis(int temp) {
        int diff = mMaxTemp - temp;
        int diffTemp = mMaxTemp - mMinTemp;
        float percent = diff * 1.0f / diffTemp;
        return mLineChartRectHeight * percent + mTopHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mForecast15DataList != null && !mForecast15DataList.isEmpty()) {
            setMeasuredDimension(mWidth, mHeight);

            mTextPaint.setTextSize(mTextSize14sp);
            UIUtils.getTextBounds(mTextPaint, "今天", mTextRect);
            mTopHeight = mTextRect.height() + mDimen8;

            mTextPaint.setTextSize(mTextSize11Sp);
            UIUtils.getTextBounds(mTextPaint, "08/27", mTextRect);
            mTopHeight += mTextRect.height() + 1.5f * mDimen8;

            mTextPaint.setTextSize(mTextSize13Sp);
            UIUtils.getTextBounds(mTextPaint, "优质", mTextRect);
            mTopHeight += mTextRect.height() + 2f * mDimen8 + 1.5f * mDimen8;

            mTextPaint.setTextSize(mTextSize14sp);
            UIUtils.getTextBounds(mTextPaint, "雷阵雨", mTextRect);
            mTopHeight += mTextRect.height() + 2 * mDimen8 + mIconWidthAndHeight;

            float bottomHeight = mIconWidthAndHeight + 2 * mDimen8;
            bottomHeight += mTextRect.height() + 2 * mDimen8;

            mTextPaint.setTextSize(mTextSize13Sp);
            UIUtils.getTextBounds(mTextPaint, "东北风", mTextRect);
            bottomHeight += mTextRect.height() + mDimen8;

            mTextPaint.setTextSize(mTextSize11Sp);
            UIUtils.getTextBounds(mTextPaint, "1级", mTextRect);
            bottomHeight += mTextRect.height();

            mLineChartRectHeight = mHeight - mTopHeight - bottomHeight;
            L.e("15日预报 mLineChartRectHeight : " + mLineChartRectHeight);
        }
    }
}
