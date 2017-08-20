package com.ppyy.ppweatherplus.ui.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.manager.SettingManager;
import com.ppyy.ppweatherplus.model.response.WeatherInfoResponse;
import com.ppyy.ppweatherplus.mvp.contract.IWeatherInfoContract;
import com.ppyy.ppweatherplus.mvp.presenter.WeatherInfoPresenter;
import com.ppyy.ppweatherplus.utils.DividerUtils;
import com.ppyy.ppweatherplus.utils.NavUtils;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<IWeatherInfoContract.Presenter> implements IWeatherInfoContract.View {
    @BindView(R.id.rv_list)
    RecyclerView mRv;

    @Override
    protected void initPresenter() {
        mPresenter = new WeatherInfoPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.addItemDecoration(DividerUtils.defaultHorizontalDivider(this));

        UIUtils.getHandler().postDelayed(() -> judgeFirstIntoApp(), 500);
    }

    @Override
    protected void initData() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            dataList.add("py : " + i);
        }
        mRv.setAdapter(new MyAdapter(this, dataList, R.layout.item));
    }

    @Override
    protected void initListener() {

    }

    /**
     * 判断是否是第一次进入app
     * 如果是则进入城市选择界面
     */
    private void judgeFirstIntoApp() {
        // if (SettingManager.isFirstIntoApp(this)) {
        if (true) {
            SettingManager.firstIntoApp(this);
            NavUtils.toSelectCityPage(this);
        }
    }

    @Override
    public void showWeatherInfo(WeatherInfoResponse weatherInfoResponse) {
        ShowUtils.showToast(weatherInfoResponse.getMeta().getStatus() + " : " + weatherInfoResponse.getMeta().getCity());
    }

    private class MyAdapter extends BaseRvAdapter<String> {
        public MyAdapter(Context context, List<String> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void convert(BaseViewHolder holder, String item, int position, int viewType) {

        }
    }
}
