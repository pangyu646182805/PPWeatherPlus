package com.ppyy.ppweatherplus.config;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class Constant {
    // weather?app_key=99817749&citykey=101210114
    public static final String BASE_URL = "http://zhwnlapi.etouch.cn/Ecalender/api/";
    public static final String READER_BASE_URL = "http://reader.smartisan.com/";

    public static final int APP_KEY = 99817749;

    public static final String PACKAGE_NAME_PREFERENCES = "config";

    public static final String IS_FIRST_INTO_APP = "is_first_into_app";

    public static final String CITY = "city";
    public static final String TEMP = "°";

    public static final String TRANSITION = "transition";

    public static final String CITY_MANAGER_TIP = "city_manager_tip";

    /**
     * 返回的响应码
     */
    public static final int RESPONSE_CODE_OK = 1000;
    public static final int RESPONSE_CODE_CREATED = 201;
    public static final int RESPONSE_CODE_NO_CONTENT = 204;
    public static final int RESPONSE_CODE_DELETE_OK = 260;
    public static final int RESPONSE_CODE_UPDATE_OK = 261;
    public static final int RESPONSE_CODE_CONTACTS_HAS_ADDED = -3001;
    public static final int RESPONSE_CODE_CONTACTS_USER_NONE = -1002;
    public static final int RESPONSE_CODE_NO_ROW_ERROR = -2003;
    public static final int RESPONSE_CODE_IMAGE_UPLOAD_FAILED = -3005;
    public static final int RESPONSE_CODE_USER_HAS_REGISTER = -1004;
    public static final int RESPONSE_CODE_PASSWORD_ERROR = -1001;
}
