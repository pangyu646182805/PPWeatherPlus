package com.ppyy.ppweatherplus.ui.activity;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.CityManagerAdapter;
import com.ppyy.ppweatherplus.adapter.base.ISelect;
import com.ppyy.ppweatherplus.adapter.base.SimpleItemTouchHelperCallback;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.loader.AsyncCityListLoader;
import com.ppyy.ppweatherplus.utils.DividerUtils;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.NavUtils;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/8/29.
 */

public class CityManagerActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<ArrayList<CityBean>> {
    @BindView(R.id.rv_city_manager)
    RecyclerView mRvCityManager;
    private CityManagerAdapter mCityManagerAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_city_manager;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(R.string.city_manager);

        mRvCityManager.setLayoutManager(new LinearLayoutManager(this));
        mRvCityManager.addItemDecoration(DividerUtils.defaultHorizontalDivider(this));
        mCityManagerAdapter = new CityManagerAdapter(this, null, R.layout.item_city_manager);
        mCityManagerAdapter.setSelectedMode(ISelect.MULTIPLE_MODE);
        mCityManagerAdapter.updateSelectMode(false);
        mCityManagerAdapter.longTouchSelectModeEnable(false);
        mCityManagerAdapter.clearRvAnim(mRvCityManager);
        mRvCityManager.setAdapter(mCityManagerAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(mCityManagerAdapter);

        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(callback);

        mCityManagerAdapter.setItemTouchHelper(mItemTouchHelper);

        mCityManagerAdapter.setDragViewId(R.id.tv_temp);

        mItemTouchHelper.attachToRecyclerView(mRvCityManager);
    }

    @Override
    protected void initData() {
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void initListener() {
        mCityManagerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount);
                L.e("onItemRangeMoved");
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkIsEmpty();
                L.e("positionStart : " + positionStart);
            }
        });
    }

    private void checkIsEmpty() {
        if (mCityManagerAdapter.getItemCount() == 0) {
            getStateLayout().setErrorImgLayoutParams(UIUtils.getDimen(R.dimen.x250), UIUtils.getDimen(R.dimen.y167))
                    .setErrorText(UIUtils.getString(R.string.to_add_desc))
                    .setImageResource(R.mipmap.empty)
                    .setReloadBtnText(UIUtils.getString(R.string.to_add));
            showError(() -> NavUtils.toSelectCityPage(this));
        } else {
            hideLoading();
        }
    }

    @Override
    public Loader<ArrayList<CityBean>> onCreateLoader(int id, Bundle args) {
        return new AsyncCityListLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<CityBean>> loader, ArrayList<CityBean> data) {
        mCityManagerAdapter.replaceAll(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CityBean>> loader) {

    }

    @Override
    public void onBackPressed() {
        List<CityBean> dataList = mCityManagerAdapter.getDataList();
        for (CityBean cityBean : dataList) {
            L.e("city name : " + cityBean.getCityName());
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_city_manager, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_batch_manager:
                ShowUtils.showToast("批量管理");
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
