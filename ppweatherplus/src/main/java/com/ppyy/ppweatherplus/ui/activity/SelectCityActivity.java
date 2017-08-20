package com.ppyy.ppweatherplus.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.nex3z.flowlayout.FlowLayout;
import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.SearchCityAdapter;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.interfaces.SimpleTextWatcher;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.model.response.HotCityResponse;
import com.ppyy.ppweatherplus.model.response.SearchCityResponse;
import com.ppyy.ppweatherplus.mvp.contract.ISelectCityContract;
import com.ppyy.ppweatherplus.mvp.presenter.SelectCityPresenter;
import com.ppyy.ppweatherplus.permission.DangerousPermissions;
import com.ppyy.ppweatherplus.permission.PermissionsHelper;
import com.ppyy.ppweatherplus.utils.DividerUtils;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.ppyy.ppweatherplus.utils.SoftKeyboardStateWatcher;
import com.ppyy.ppweatherplus.utils.UIUtils;
import com.ppyy.ppweatherplus.widget.NoPaddingTextView;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public class SelectCityActivity extends BaseActivity<ISelectCityContract.Presenter> implements TencentLocationListener, ISelectCityContract.View {
    private static final String[] PERMISSIONS = new String[]{DangerousPermissions.LOCATION};

    @BindView(R.id.ll_root)
    LinearLayout mLlRoot;
    @BindView(R.id.tv_location)
    NoPaddingTextView mTvLocation;
    @BindView(R.id.tag_layout_national)
    FlowLayout mNationalTagLayout;
    @BindView(R.id.tag_layout_international)
    FlowLayout mInternationalTagLayout;
    @BindView(R.id.et_search_city)
    EditText mEtSearchCity;
    @BindView(R.id.rv_result)
    RecyclerView mRvResult;

    private TencentLocationManager mManager;
    private PermissionsHelper mPermissionsHelper;
    private InputMethodManager mInputMethodManager;
    private SearchCityAdapter mSearchCityAdapter;

    @Override
    protected void initPresenter() {
        mPresenter = new SelectCityPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(R.string.select_city);
        checkPermission();

        mRvResult.setLayoutManager(new LinearLayoutManager(this));
        mRvResult.addItemDecoration(DividerUtils.defaultHorizontalDivider(this));
        mSearchCityAdapter = new SearchCityAdapter(this, null, R.layout.item);
        mRvResult.setAdapter(mSearchCityAdapter);
    }

    @Override
    protected void initData() {
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mPresenter.getHotCity();
    }

    @Override
    protected void initListener() {
        mEtSearchCity.setOnKeyListener((v, keyCode, event) -> {
            // 修改键盘回车键功能
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                hideSoftKeyboard();
                String keyword = mEtSearchCity.getText().toString();
                if (!UIUtils.isEmpty(keyword)) {
                    mPresenter.searchCityByKeyword(keyword);
                }
                return true;
            }
            return false;
        });
        SoftKeyboardStateWatcher watcher = new SoftKeyboardStateWatcher(mLlRoot, this);
        watcher.addSoftKeyboardStateListener(new SoftKeyboardStateWatcher.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
            }

            @Override
            public void onSoftKeyboardClosed() {
                mEtSearchCity.clearFocus();
            }
        });
        mEtSearchCity.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                if (UIUtils.isEmpty(editable.toString())) {
                    goneRvResult();
                }
            }
        });
    }

    private void checkPermission() {
        mPermissionsHelper = new PermissionsHelper(this, PERMISSIONS);
        if (mPermissionsHelper.checkAllPermissions(PERMISSIONS)) {
            requestLocation();
            mPermissionsHelper.onDestroy();
        } else {
            // 申请权限
            mPermissionsHelper.startRequestNeedPermissions();
        }
        mPermissionsHelper.setonAllNeedPermissionsGrantedListener(new PermissionsHelper.onAllNeedPermissionsGrantedListener() {
            @Override
            public void onAllNeedPermissionsGranted() {
                requestLocation();
            }

            @Override
            public void onPermissionsDenied() {
                mTvLocation.setText(R.string.location_fail);
            }
        });
    }

    private void requestLocation() {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);
        if (mManager == null) {
            mManager = TencentLocationManager.getInstance(this);
        }
        mManager.requestLocationUpdates(request, this);
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int errorCode, String s) {
        if (TencentLocation.ERROR_OK == errorCode) {
            // 定位成功
            String province = tencentLocation.getProvince();
            String city = tencentLocation.getCity();
            String district = tencentLocation.getDistrict();
            mTvLocation.setText(district + "," + city + "," + province);
            new AlertDialog.Builder(this)
                    .setTitle("定位提示")
                    .setMessage("您位于" + district + "，是否添加?")
                    .setPositiveButton("添加", (dialogInterface, i) -> {

                    })
                    .setNegativeButton("取消", null)
                    .show();
        } else {
            // 定位失败
            mTvLocation.setText(R.string.location_fail);
            ShowUtils.showToast("定位失败，请手动选择城市");
        }
        // 移除定位监听
        removeUpdate();
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    private void removeUpdate() {
        if (mManager != null) {
            mManager.removeUpdates(this);
            mManager = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionsHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionsHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showHotCity(HotCityResponse hotCityResponse) {
        CacheManager.saveHotCityList(this, hotCityResponse);
        HotCityResponse.DataBean data = hotCityResponse.getData();
        if (data != null) {
            List<HotCityResponse.DataBean.HotNationalBean> hotNational = data.getHotNational();
            if (hotNational != null && !hotNational.isEmpty()) {
                for (HotCityResponse.DataBean.HotNationalBean hotNationalBean : hotNational) {
                    mNationalTagLayout.addView(buildTagView(hotNationalBean.getName(), hotNationalBean));
                }
            }
            List<HotCityResponse.DataBean.HotInternationalBean> hotInternational = data.getHotInternational();
            if (hotInternational != null && !hotInternational.isEmpty()) {
                for (HotCityResponse.DataBean.HotInternationalBean hotInternationalBean : hotInternational) {
                    mInternationalTagLayout.addView(buildTagView(hotInternationalBean.getName(), hotInternationalBean));
                }
            }
        }
    }

    @Override
    public void showSearchResponse(SearchCityResponse searchCityResponse) {
        List<HotCityResponse.DataBean.HotNationalBean> data = searchCityResponse.getData();
        if (data != null && !data.isEmpty()) {
            visible(mRvResult);
            mSearchCityAdapter.replaceAll(data);
        } else {
            ShowUtils.showToast(UIUtils.getString(R.string.search_city_null));
        }
    }

    @Override
    public void showTip(String tip) {
        ShowUtils.showToast(tip);
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            if (getCurrentFocus().getApplicationWindowToken() != null) {
                mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mRvResult.getVisibility() == View.VISIBLE) {
            mEtSearchCity.setText("");
            goneRvResult();
        } else {
            super.onBackPressed();
        }
    }

    private void goneRvResult() {
        gone(mRvResult);
        mSearchCityAdapter.clear();
    }

    /**
     * 生成标签TextView
     */
    private NoPaddingTextView buildTagView(String tag, Object obj) {
        NoPaddingTextView tvTag = new NoPaddingTextView(this);
        tvTag.setText(tag);
        tvTag.setTextColor(UIUtils.getColor(R.color.colorGray333));
        tvTag.setBackgroundResource(R.drawable.shape_hot_city_tag_selector);
        int padding = (int) UIUtils.getDimen(R.dimen.x12);
        tvTag.setPadding(padding * 2, padding, padding * 2, padding);
        tvTag.setOnClickListener(new TagClickListener(obj));
        return tvTag;
    }

    private class TagClickListener implements View.OnClickListener {
        private Object mObject;

        public TagClickListener(Object object) {
            mObject = object;
        }

        @Override
        public void onClick(View v) {
            if (mObject instanceof HotCityResponse.DataBean.HotNationalBean) {
                HotCityResponse.DataBean.HotNationalBean nationalBean = (HotCityResponse.DataBean.HotNationalBean) mObject;
                ShowUtils.showToast(nationalBean.getName());
            } else {
                HotCityResponse.DataBean.HotInternationalBean internationalBean = (HotCityResponse.DataBean.HotInternationalBean) mObject;
                ShowUtils.showToast(internationalBean.getName());
            }
        }
    }
}
