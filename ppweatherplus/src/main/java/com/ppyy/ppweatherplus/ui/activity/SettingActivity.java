package com.ppyy.ppweatherplus.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.event.LineTypeEvent;
import com.ppyy.ppweatherplus.event.StatusBarColoringEvent;
import com.ppyy.ppweatherplus.event.WeatherServiceEvent;
import com.ppyy.ppweatherplus.service.AppWidgetService;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.ppyy.ppweatherplus.utils.SystemUtils;
import com.ppyy.ppweatherplus.widget.dialog.ColorPickerDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by NeuroAndroid on 2017/8/31.
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(R.string.settings);

        getFragmentManager().beginTransaction().replace(R.id.fl_container, new SettingFragment()).commit();
    }

    public static class SettingFragment extends PreferenceFragment {
        public static final int[] NOTIFICATION_BACKGROUND_COLOR = {Color.TRANSPARENT, Color.parseColor("#333333")};
        public static final int[] NOTIFICATION_TEXT_COLOR = {Color.parseColor("#333333"), Color.WHITE};

        public static final String CUSTOM_NOTIFICATION_BACKGROUND_COLOR = "custom_notification_background_color";
        public static final String CUSTOM_NOTIFICATION_TEXT_COLOR = "custom_notification_text_color";

        private SharedPreferences mPrefs;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ListView lv = (ListView) view.findViewById(android.R.id.list);
            lv.setBackgroundColor(Color.WHITE);
            lv.setDivider(null);
            lv.setDividerHeight(0);
            lv.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
            invalidateSettings();
        }

        private void invalidateSettings() {
            mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Preference customWeatherBackground = findPreference("custom_weather_background");
            ListPreference customWeatherLineType = (ListPreference) findPreference("custom_weather_line_type");
            customWeatherLineType.setSummary(
                    mPrefs.getString("custom_weather_line_type", "0").equals("0") ? "折线图" : "曲线图");

            CheckBoxPreference cbNotificationShow = (CheckBoxPreference) findPreference("cb_notification_show");
            CheckBoxPreference cbNotificationNumberIcon = (CheckBoxPreference) findPreference("notification_number_icon");
            CheckBoxPreference cbNotificationShowIcon = (CheckBoxPreference) findPreference("notification_show_icon");
            CheckBoxPreference cbNotificationShowLockScreen = (CheckBoxPreference) findPreference("notification_show_lock_screen");

            cbNotificationNumberIcon.setEnabled(cbNotificationShow.isChecked());
            cbNotificationNumberIcon.setSummary(cbNotificationNumberIcon.isChecked() ? "开启" : "关闭");
            cbNotificationShowIcon.setEnabled(cbNotificationShow.isChecked());
            cbNotificationShowIcon.setSummary(cbNotificationShowIcon.isChecked() ? "隐藏" : "显示");
            cbNotificationShowLockScreen.setEnabled(cbNotificationShow.isChecked());
            cbNotificationShowLockScreen.setSummary(cbNotificationShowLockScreen.isChecked() ?
                    "在锁屏界面隐藏通知" : "在锁屏界面显示通知");

            ListPreference notificationBackgroundColor = (ListPreference) findPreference("notification_background_color");
            CharSequence[] stringArr = notificationBackgroundColor.getEntries();
            notificationBackgroundColor.setSummary(stringArr[Integer.parseInt(mPrefs.getString("notification_background_color", "0"))]);
            notificationBackgroundColor.setEnabled(cbNotificationShow.isChecked());

            ListPreference notificationTextColor = (ListPreference) findPreference("notification_text_color");
            stringArr = notificationTextColor.getEntries();
            notificationTextColor.setSummary(stringArr[Integer.parseInt(mPrefs.getString("notification_text_color", "0"))]);
            notificationTextColor.setEnabled(cbNotificationShow.isChecked());

            CheckBoxPreference cbAutoUpdate = (CheckBoxPreference) findPreference("cb_auto_update_notification_widget");
            cbAutoUpdate.setSummary(mPrefs.getBoolean("cb_auto_update_notification_widget", false) ? "已开启" : "已关闭");

            ListPreference autoUpdateFrequency = (ListPreference) findPreference("auto_update_frequency");
            stringArr = autoUpdateFrequency.getEntries();
            autoUpdateFrequency.setSummary(stringArr[Integer.parseInt(mPrefs.getString("auto_update_frequency", "2"))]);
            autoUpdateFrequency.setEnabled(cbAutoUpdate.isChecked());

            SwitchPreference switchStatusBarColoring = (SwitchPreference) findPreference("switch_status_bar_coloring");
            SwitchPreference switchNightMode = (SwitchPreference) findPreference("switch_night_mode");
            switchNightMode.setSummary(mPrefs.getBoolean("switch_night_mode", false) ? "夜间模式已开启" : "夜间模式已关闭");

            customWeatherBackground.setOnPreferenceClickListener(preference -> {
                new AlertDialog.Builder(getContext())
                        .setTitle("自定义天气背景")
                        .setMessage("可以有以下操作")
                        .setPositiveButton("选择图片", (dialogInterface, i) -> {
                            ShowUtils.showToast("选择图片");
                        })
                        .setNegativeButton("清除背景", (dialogInterface, i) -> {
                            ShowUtils.showToast("清除背景");
                        })
                        .setNeutralButton("取消", (dialogInterface, i) -> {
                            ShowUtils.showToast("取消");
                        })
                        .show();
                return true;
            });

            customWeatherLineType.setOnPreferenceChangeListener((preference, o) -> {
                preference.setSummary(customWeatherLineType.getEntries()[Integer.parseInt((String) o)]);
                EventBus.getDefault().post(new LineTypeEvent());
                return true;
            });

            switchStatusBarColoring.setOnPreferenceChangeListener((preference, o) -> {
                boolean statusBarColoring = (boolean) o;
                SystemUtils.setStatusBarDarkMode(getActivity(), statusBarColoring);
                EventBus.getDefault().post(new StatusBarColoringEvent().setStatusBarColoring(statusBarColoring));
                return true;
            });

            switchNightMode.setOnPreferenceChangeListener((preference, o) -> {
                boolean nightMode = (boolean) o;
                preference.setSummary(nightMode ? "夜间模式已开启" : "夜间模式已关闭");
                return true;
            });

            cbNotificationShow.setOnPreferenceChangeListener((preference, o) -> {
                boolean showNotification = (boolean) o;
                notificationBackgroundColor.setEnabled(showNotification);
                notificationTextColor.setEnabled(showNotification);
                cbNotificationNumberIcon.setEnabled(showNotification);
                cbNotificationShowIcon.setEnabled(showNotification);
                cbNotificationShowLockScreen.setEnabled(showNotification);
                EventBus.getDefault().post(new WeatherServiceEvent()
                        .setOpenNotificationOperate(true)
                        .setShowNotification(showNotification));
                return true;
            });

            cbNotificationNumberIcon.setOnPreferenceChangeListener((preference, o) -> {
                boolean numberIcon = (boolean) o;
                preference.setSummary(numberIcon ? "开启" : "关闭");
                EventBus.getDefault().post(generateWeatherServiceEvent().setNotificationNumberIcon(numberIcon));
                return true;
            });

            cbNotificationShowIcon.setOnPreferenceChangeListener((preference, o) -> {
                boolean showIcon = (boolean) o;
                preference.setSummary(showIcon ? "隐藏" : "显示");
                EventBus.getDefault().post(generateWeatherServiceEvent().setNotificationShowIcon(showIcon));
                return true;
            });

            cbNotificationShowLockScreen.setOnPreferenceChangeListener((preference, o) -> {
                boolean showLockScreen = (boolean) o;
                preference.setSummary(showLockScreen ? "在锁屏界面隐藏通知" : "在锁屏界面显示通知");
                EventBus.getDefault().post(generateWeatherServiceEvent().setNotificationShowLockScreen(showLockScreen));
                return true;
            });

            notificationBackgroundColor.setOnPreferenceChangeListener((preference, o) -> {
                int index = Integer.parseInt((String) o);
                preference.setSummary(notificationBackgroundColor.getEntries()[index]);
                if (index == 2) {
                    // 自定义，弹出颜色选择dialog
                    new ColorPickerDialog(getContext())
                            .setOnLeftBtnClickListener(null)
                            .setDialogCancelable(false)
                            .setDialogCanceledOnTouchOutside(false)
                            .setOnRightBtnClickListener((dialog, view) -> {
                                int selectedColor = dialog.getSelectedColor();
                                mPrefs.edit().putInt(CUSTOM_NOTIFICATION_BACKGROUND_COLOR, selectedColor).apply();
                                WeatherServiceEvent weatherServiceEvent = generateWeatherServiceEvent();
                                weatherServiceEvent.setNotificationBackgroundColor(selectedColor);
                                EventBus.getDefault().post(weatherServiceEvent);
                                dialog.dismissDialog();
                            })
                            .showDialog();
                } else {
                    WeatherServiceEvent weatherServiceEvent = generateWeatherServiceEvent();
                    weatherServiceEvent.setNotificationBackgroundColor(NOTIFICATION_BACKGROUND_COLOR[index]);
                    EventBus.getDefault().post(weatherServiceEvent);
                }
                return true;
            });

            notificationTextColor.setOnPreferenceChangeListener((preference, o) -> {
                int index = Integer.parseInt((String) o);
                preference.setSummary(notificationTextColor.getEntries()[index]);
                if (index == 2) {
                    // 自定义，弹出颜色选择dialog
                    new ColorPickerDialog(getContext())
                            .setOnLeftBtnClickListener(null)
                            .setDialogCancelable(false)
                            .setDialogCanceledOnTouchOutside(false)
                            .setOnRightBtnClickListener((dialog, view) -> {
                                int selectedColor = dialog.getSelectedColor();
                                mPrefs.edit().putInt(CUSTOM_NOTIFICATION_TEXT_COLOR, selectedColor).apply();
                                WeatherServiceEvent weatherServiceEvent = generateWeatherServiceEvent();
                                weatherServiceEvent.setNotificationTextColor(selectedColor);
                                EventBus.getDefault().post(weatherServiceEvent);
                                dialog.dismissDialog();
                            })
                            .showDialog();
                } else {
                    WeatherServiceEvent weatherServiceEvent = generateWeatherServiceEvent();
                    weatherServiceEvent.setNotificationTextColor(NOTIFICATION_TEXT_COLOR[index]);
                    EventBus.getDefault().post(weatherServiceEvent);
                }
                return true;
            });

            cbAutoUpdate.setOnPreferenceChangeListener((preference, o) -> {
                boolean autoUpdate = (boolean) o;
                autoUpdateFrequency.setEnabled(autoUpdate);
                preference.setSummary(autoUpdate ? "已开启" : "已关闭");
                AppWidgetService appWidgetService = AppWidgetService.getInstance();
                if (appWidgetService != null) {
                    appWidgetService.setAppWidgetUpdateCycle(autoUpdate, autoUpdateFrequency.getValue());
                } else {
                    L.e("AppWidgetService 没有在运行");
                    AppWidgetService.startAppWidgetService();
                }
                Intent requestWeatherInfoSender = new Intent();
                requestWeatherInfoSender.setAction(AppWidgetService.ACTION_REQUEST_WEATHER_INFO);
                getContext().sendBroadcast(requestWeatherInfoSender);
                return true;
            });

            autoUpdateFrequency.setOnPreferenceChangeListener((preference, o) -> {
                preference.setSummary(autoUpdateFrequency.getEntries()[Integer.parseInt((String) o)]);
                AppWidgetService appWidgetService = AppWidgetService.getInstance();
                if (appWidgetService != null) {
                    appWidgetService.setAppWidgetUpdateCycle(true, (String) o);
                } else {
                    L.e("AppWidgetService 没有在运行");
                    AppWidgetService.startAppWidgetService();
                }
                Intent requestWeatherInfoSender = new Intent();
                requestWeatherInfoSender.setAction(AppWidgetService.ACTION_REQUEST_WEATHER_INFO);
                getContext().sendBroadcast(requestWeatherInfoSender);
                return true;
            });
        }

        private WeatherServiceEvent generateWeatherServiceEvent() {
            WeatherServiceEvent weatherServiceEvent = new WeatherServiceEvent();
            boolean notificationNumberIcon = mPrefs.getBoolean("notification_number_icon", false);
            boolean notificationShowIcon = mPrefs.getBoolean("notification_show_icon", false);
            boolean notificationShowLockScreen = mPrefs.getBoolean("notification_show_lock_screen", false);

            int backgroundColor;
            int textColor;

            int index = Integer.parseInt(mPrefs.getString("notification_background_color", "0"));
            if (index == 2) {
                backgroundColor = mPrefs.getInt(CUSTOM_NOTIFICATION_BACKGROUND_COLOR, Color.TRANSPARENT);
            } else {
                backgroundColor = NOTIFICATION_BACKGROUND_COLOR[index];
            }

            index = Integer.parseInt(mPrefs.getString("notification_text_color", "0"));
            if (index == 2) {
                textColor = mPrefs.getInt(CUSTOM_NOTIFICATION_TEXT_COLOR, Color.parseColor("#333333"));
            } else {
                textColor = NOTIFICATION_TEXT_COLOR[index];
            }

            weatherServiceEvent.setNotificationNumberIcon(notificationNumberIcon)
                    .setNotificationShowIcon(notificationShowIcon)
                    .setNotificationShowLockScreen(notificationShowLockScreen)
                    .setNotificationBackgroundColor(backgroundColor)
                    .setNotificationTextColor(textColor);
            return weatherServiceEvent;
        }
    }
}
