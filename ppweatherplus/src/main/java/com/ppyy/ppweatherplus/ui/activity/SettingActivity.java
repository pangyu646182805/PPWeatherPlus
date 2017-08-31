package com.ppyy.ppweatherplus.ui.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.event.LineTypeEvent;
import com.ppyy.ppweatherplus.event.StatusBarColoringEvent;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.ppyy.ppweatherplus.utils.SystemUtils;

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
            SharedPreferences prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(getContext());
            Preference customWeatherBackground = findPreference("custom_weather_background");
            ListPreference customWeatherLineType = (ListPreference) findPreference("custom_weather_line_type");
            customWeatherLineType.setSummary(
                    prefs.getString("custom_weather_line_type", "0").equals("0") ? "折线图" : "曲线图");

            CheckBoxPreference cbNotificationShow = (CheckBoxPreference) findPreference("cb_notification_show");
            ListPreference notificationBackgroundColor = (ListPreference) findPreference("notification_background_color");
            CharSequence[] stringArr = notificationBackgroundColor.getEntries();
            notificationBackgroundColor.setSummary(stringArr[Integer.parseInt(prefs.getString("notification_background_color", "0"))]);

            ListPreference notificationTextColor = (ListPreference) findPreference("notification_text_color");
            stringArr = notificationTextColor.getEntries();
            notificationBackgroundColor.setSummary(stringArr[Integer.parseInt(prefs.getString("notification_text_color", "0"))]);

            CheckBoxPreference cbAutoUpdate = (CheckBoxPreference) findPreference("cb_auto_update_notification_widget");
            cbAutoUpdate.setSummary(prefs.getBoolean("cb_auto_update_notification_widget", false) ? "已开启" : "已关闭");

            ListPreference autoUpdateFrequency = (ListPreference) findPreference("auto_update_frequency");
            stringArr = autoUpdateFrequency.getEntries();
            autoUpdateFrequency.setSummary(stringArr[Integer.parseInt(prefs.getString("auto_update_frequency", "2"))]);
            autoUpdateFrequency.setEnabled(cbAutoUpdate.isChecked());

            SwitchPreference switchStatusBarColoring = (SwitchPreference) findPreference("switch_status_bar_coloring");
            SwitchPreference switchNightMode = (SwitchPreference) findPreference("switch_night_mode");
            switchNightMode.setSummary(prefs.getBoolean("switch_night_mode", false) ? "夜间模式已开启" : "夜间模式已关闭");

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
                return true;
            });

            notificationBackgroundColor.setOnPreferenceChangeListener((preference, o) -> {
                preference.setSummary(notificationBackgroundColor.getEntries()[Integer.parseInt((String) o)]);
                return true;
            });

            notificationTextColor.setOnPreferenceChangeListener((preference, o) -> {
                preference.setSummary(notificationTextColor.getEntries()[Integer.parseInt((String) o)]);
                return true;
            });

            cbAutoUpdate.setOnPreferenceChangeListener((preference, o) -> {
                boolean autoUpdate = (boolean) o;
                autoUpdateFrequency.setEnabled(autoUpdate);
                preference.setSummary(autoUpdate ? "已开启" : "已关闭");
                return true;
            });

            autoUpdateFrequency.setOnPreferenceChangeListener((preference, o) -> {
                preference.setSummary(autoUpdateFrequency.getEntries()[Integer.parseInt((String) o)]);
                return true;
            });
        }
    }
}
