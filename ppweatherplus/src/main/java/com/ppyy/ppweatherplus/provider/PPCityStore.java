package com.ppyy.ppweatherplus.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ppyy.ppweatherplus.bean.CityBean;

import java.util.ArrayList;

/**
 * Created by NeuroAndroid on 2017/8/21.
 */

public class PPCityStore extends SQLiteOpenHelper {
    @Nullable
    private static PPCityStore sInstance = null;
    private static final String DATABASE_NAME = "py_weather.db";
    private static final String TABLE_NAME = "city_list";

    public PPCityStore(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase, TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * 单例模式
     */
    @NonNull
    public static synchronized PPCityStore getInstance(@NonNull final Context context) {
        if (sInstance == null) {
            sInstance = new PPCityStore(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * 创建数据库表
     * location 1 定位地址  0 非定位地址
     */
    private void createTable(SQLiteDatabase db, String tableName) {
        String sql = "create table " + tableName + " (city_id varchar(20) primary key, city_name varchar(20) not null, " +
                "max integer not null, min integer not null, weather_desc string, upper string, location integer)";
        db.execSQL(sql);
    }

    /**
     * 获取所有的城市
     */
    public ArrayList<CityBean> getAllCity() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null,
                null, null, null);
        if (cursor.getCount() == 0) return null;
        ArrayList<CityBean> dataList = new ArrayList<>();
        CityBean cityBean;
        while (cursor.moveToNext()) {
            cityBean = new CityBean();
            cityBean.setCityId(cursor.getString(cursor.getColumnIndex("city_id")));
            cityBean.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            cityBean.setUpper(cursor.getString(cursor.getColumnIndex("upper")));
            cityBean.setMax(cursor.getInt(cursor.getColumnIndex("max")));
            cityBean.setMin(cursor.getInt(cursor.getColumnIndex("min")));
            cityBean.setLocation(cursor.getInt(cursor.getColumnIndex("location")));
            cityBean.setWeatherDesc(cursor.getString(cursor.getColumnIndex("weather_desc")));
            dataList.add(cityBean);
        }
        db.close();
        cursor.close();
        return dataList;
    }

    /**
     * 城市列表是否为空
     */
    public boolean isEmptyCityList() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null,
                null, null, null);
        if (cursor.getCount() == 0) return true;
        return false;
    }

    /**
     * 更新数据库
     */
    public synchronized int update(String cityId, String cityName, String upper,
                                   int max, int min, String weatherDesc, int location) {
        SQLiteDatabase db = getWritableDatabase();
        int update = db.update(TABLE_NAME, getContentValues(cityId, cityName, upper, max, min, weatherDesc, location),
                "city_id=?", new String[]{cityId});
        db.close();
        return update;
    }

    /**
     * 更新数据库
     */
    public synchronized int update(String cityId, String cityName, String upper,
                                   int max, int min, String weatherDesc) {
        SQLiteDatabase db = getWritableDatabase();
        int update = db.update(TABLE_NAME, getContentValues(cityId, cityName, upper, max, min, weatherDesc),
                "city_id=?", new String[]{cityId});
        db.close();
        return update;
    }

    /**
     * @param cityId 城市ID
     * @return 删除的记录数量
     */
    public synchronized int deleteByCityId(String cityId) {
        SQLiteDatabase db = getWritableDatabase();
        int delete = db.delete(TABLE_NAME, "city_id=?", new String[]{cityId});
        db.close();
        return delete;
    }

    /**
     * 添加到数据库
     */
    public synchronized void addItem(CityBean cityBean) {
        addItem(cityBean.getCityId(), cityBean.getCityName(), cityBean.getUpper(),
                cityBean.getMax(), cityBean.getMin(), cityBean.getWeatherDesc(), cityBean.getLocation());
    }

    /**
     * 添加到数据库
     */
    public synchronized void addItem(String cityId, String cityName, String upper,
                                     int max, int min, String weatherDesc, int location) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, getContentValues(cityId, cityName, upper, max, min, weatherDesc, location));
        db.close();
    }

    /**
     * @param cityId 城市ID
     * @return 返回找到符合记录的数量
     */
    public synchronized int find(String cityId) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "city_id=?", new String[]{cityId},
                null, null, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }

    /**
     * 查找已经定位的城市
     */
    public int findLocationCity() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "location=?", new String[]{"1"},
                null, null, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        return count;
    }

    public CityBean findLocationCityBean() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, "location=?", new String[]{"1"},
                null, null, null);
        int count = cursor.getCount();
        CityBean cityBean;
        if (count == 0 || count > 1) {
            // 没有定位的城市
            cityBean = null;
        } else {
            cityBean = new CityBean();
            cityBean.setCityId(cursor.getString(cursor.getColumnIndex("city_id")));
            cityBean.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
            cityBean.setUpper(cursor.getString(cursor.getColumnIndex("upper")));
            cityBean.setMax(cursor.getInt(cursor.getColumnIndex("max")));
            cityBean.setMin(cursor.getInt(cursor.getColumnIndex("min")));
            cityBean.setLocation(cursor.getInt(cursor.getColumnIndex("location")));
            cityBean.setWeatherDesc(cursor.getString(cursor.getColumnIndex("weather_desc")));
            db.close();
            cursor.close();
        }
        db.close();
        cursor.close();
        return cityBean;
    }

    private ContentValues getContentValues(String cityId, String cityName, String upper,
                                           int max, int min, String weatherDesc, int location) {
        ContentValues values = new ContentValues();
        values.put("city_id", cityId);
        values.put("city_name", cityName);
        values.put("upper", upper);
        values.put("max", max);
        values.put("min", min);
        values.put("location", location);
        values.put("weather_desc", weatherDesc);
        return values;
    }

    private ContentValues getContentValues(String cityId, String cityName, String upper,
                                           int max, int min, String weatherDesc) {
        ContentValues values = new ContentValues();
        values.put("city_id", cityId);
        values.put("city_name", cityName);
        values.put("upper", upper);
        values.put("max", max);
        values.put("min", min);
        values.put("weather_desc", weatherDesc);
        return values;
    }
}
