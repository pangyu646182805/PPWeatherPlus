package com.ppyy.ppweatherplus.ui.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialcab.MaterialCab;
import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.CityManagerAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.adapter.base.ISelect;
import com.ppyy.ppweatherplus.adapter.base.SelectAdapter;
import com.ppyy.ppweatherplus.adapter.base.SimpleItemTouchHelperCallback;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.event.SelectCityEvent;
import com.ppyy.ppweatherplus.loader.AsyncCityListLoader;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.manager.SettingManager;
import com.ppyy.ppweatherplus.provider.PPCityStore;
import com.ppyy.ppweatherplus.utils.DividerUtils;
import com.ppyy.ppweatherplus.utils.NavUtils;
import com.ppyy.ppweatherplus.utils.SPUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/8/29.
 */

public class CityManagerActivity extends BaseActivity implements
        LoaderManager.LoaderCallbacks<ArrayList<CityBean>>, MaterialCab.Callback {
    @BindView(R.id.rv_city_manager)
    RecyclerView mRvCityManager;

    private CityManagerAdapter mCityManagerAdapter;
    private MaterialCab mCab;
    private SimpleItemTouchHelperCallback mItemTouchHelperCallback;
    private List<CityBean> mSelectedCities = new ArrayList<>();
    private List<CityBean> mOriginDataList;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_city_manager;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(R.string.city_manager);
        showSwipeTip();

        mRvCityManager.setLayoutManager(new LinearLayoutManager(this));
        mRvCityManager.addItemDecoration(DividerUtils.defaultHorizontalDivider(this));
        mCityManagerAdapter = new CityManagerAdapter(this, null, R.layout.item_city_manager);
        mCityManagerAdapter.setSelectedMode(ISelect.MULTIPLE_MODE);
        mCityManagerAdapter.updateSelectMode(false);
        mCityManagerAdapter.longTouchSelectModeEnable(false);
        mCityManagerAdapter.clearRvAnim(mRvCityManager);
        mRvCityManager.setAdapter(mCityManagerAdapter);

        mItemTouchHelperCallback = new SimpleItemTouchHelperCallback(mCityManagerAdapter);
        mItemTouchHelperCallback.setCanSwipe(true);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mItemTouchHelperCallback);
        mCityManagerAdapter.setItemTouchHelper(itemTouchHelper);
        mCityManagerAdapter.setDragViewId(R.id.iv_reorder);
        itemTouchHelper.attachToRecyclerView(mRvCityManager);
    }

    @Override
    protected void initData() {
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void initListener() {
        mCityManagerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                checkIsEmpty();

            }
        });
        mCityManagerAdapter.setAfterDragCallBack(cityBean -> {
            // 侧滑删除item
            if (cityBean.getLocation() == 1)
                SettingManager.setFirstIntoApp(CityManagerActivity.this, true);
            PPCityStore.getInstance(CityManagerActivity.this).deleteByCityId(cityBean.getCityId());
            CacheManager.deleteWeatherInfo(CityManagerActivity.this, cityBean.getCityId());
            EventBus.getDefault().post(new SelectCityEvent());
        });
        mCityManagerAdapter.setItemSelectedListener(new SelectAdapter.OnItemSelectedListener<CityBean>() {
            @Override
            public void onItemSelected(BaseViewHolder viewHolder, int position, boolean isSelected, CityBean cityBean) {
                if (isSelected) {
                    mSelectedCities.add(cityBean);
                } else {
                    mSelectedCities.remove(cityBean);
                }
                setMaterialCabState();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    /**
     * 设置MaterialCab状态
     */
    private void setMaterialCabState() {
        setMaterialCabTitle();
        mCab.getMenu().findItem(R.id.action_delete).setEnabled(!mSelectedCities.isEmpty());
        mCab.getMenu().findItem(R.id.action_select_all).setTitle(mSelectedCities.size() == mCityManagerAdapter.getDataListSize() ?
                UIUtils.getString(R.string.un_select_all) : UIUtils.getString(R.string.select_all));
    }

    /**
     * 设置MaterialCab的标题
     */
    private void setMaterialCabTitle() {
        if (mSelectedCities.isEmpty()) {
            mCab.setTitleRes(R.string.action_batch_manager);
        } else if (mSelectedCities.size() == 1) {
            mCab.setTitle(mSelectedCities.get(0).getCityName());
        } else if (mSelectedCities.size() > 1) {
            mCab.setTitle(String.format(UIUtils.getString(R.string.x_selected), mSelectedCities.size()));
        }
    }

    /**
     * 显示左右滑动可以快速移除城市
     */
    private void showSwipeTip() {
        if (!SPUtils.getBoolean(this, Constant.CITY_MANAGER_TIP, false)) {
            SPUtils.putBoolean(this, Constant.CITY_MANAGER_TIP, true);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.tip)
                    .setMessage(R.string.swipe_tip)
                    .setPositiveButton(R.string.confirm, null)
                    .setCancelable(false)
                    .show();
        }
    }

    private void checkIsEmpty() {
        if (mCityManagerAdapter.getItemCount() == 0) {
            getStateLayout().setErrorImgLayoutParams(UIUtils.getDimen(R.dimen.x250), UIUtils.getDimen(R.dimen.y167))
                    .setErrorText(UIUtils.getString(R.string.to_add_desc))
                    .setImageResource(R.mipmap.empty)
                    .setReloadBtnText(UIUtils.getString(R.string.to_add));
            showError(() -> {
                NavUtils.toSelectCityPage(this);
                finish();
            });
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
        mOriginDataList = new ArrayList<>();
        mOriginDataList.addAll(data);
        mCityManagerAdapter.replaceAll(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<CityBean>> loader) {

    }

    @Override
    public void onBackPressed() {
        if (mCab != null && mCab.isActive()) {
            mCab.finish();
        } else {
            super.onBackPressed();
        }
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
                mCab = openCab(R.menu.menu_batch_manage, this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    public MaterialCab openCab(int menuRes, MaterialCab.Callback callback) {
        if (mCab != null && mCab.isActive()) mCab.finish();
        mCab = new MaterialCab(this, 0)
                .setPopupMenuTheme(R.style.AppBarStyle)
                .setTitleRes(R.string.action_batch_manager)
                .setMenu(menuRes)
                .setCloseDrawableRes(R.drawable.ic_close_black)
                .setBackgroundColor(UIUtils.getColor(R.color.white))
                .start(callback);
        return mCab;
    }

    @Override
    public boolean onCabCreated(MaterialCab cab, Menu menu) {
        mItemTouchHelperCallback.setCanSwipe(false);
        mCityManagerAdapter.updateSelectMode(true);
        mCityManagerAdapter.longTouchSelectModeEnable(true);
        menu.findItem(R.id.action_delete).setEnabled(!mSelectedCities.isEmpty());
        return true;
    }

    @Override
    public boolean onCabItemClicked(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_select_all:
                if (UIUtils.getString(R.string.select_all).equals(item.getTitle())) {
                    // 全选
                    item.setTitle(UIUtils.getString(R.string.un_select_all));
                    mCityManagerAdapter.selectAll();
                    mSelectedCities.clear();
                    mSelectedCities.addAll(mCityManagerAdapter.getDataList());
                    setMaterialCabTitle();
                } else {
                    // 取消全选
                    item.setTitle(UIUtils.getString(R.string.select_all));
                    mCityManagerAdapter.clearSelected();
                    mCab.setTitle(UIUtils.getString(R.string.action_batch_manager));
                    mSelectedCities.clear();
                }
                mCab.getMenu().findItem(R.id.action_delete).setEnabled(!mSelectedCities.isEmpty());
                mCityManagerAdapter.notifyDataSetChanged();
                break;
            case R.id.action_delete:
                batchManage();
                break;
        }
        return true;
    }

    private void batchManage() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.tip)
                .setMessage(R.string.remove_selected_city)
                .setPositiveButton("确定", (dialogInterface, which) ->
                        new BatchManagerTask().execute(mSelectedCities))
                .setNegativeButton("取消", null)
                .create().show();
    }

    @Override
    public boolean onCabFinished(MaterialCab cab) {
        mItemTouchHelperCallback.setCanSwipe(true);
        mCityManagerAdapter.longTouchSelectModeEnable(false);
        mCityManagerAdapter.updateSelectMode(false);
        mSelectedCities.clear();
        return true;
    }

    private class BatchManagerTask extends AsyncTask<List<CityBean>, Void, Void> {
        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(CityManagerActivity.this);
            mProgressDialog.setMessage("请稍等...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(List<CityBean>... lists) {
            List<CityBean> selectedCities = lists[0];
            if (selectedCities != null && !selectedCities.isEmpty()) {
                PPCityStore ppCityStore = PPCityStore.getInstance(CityManagerActivity.this);
                for (CityBean cityBean : selectedCities) {
                    ppCityStore.deleteByCityId(cityBean.getCityId());
                    CacheManager.deleteWeatherInfo(CityManagerActivity.this, cityBean.getCityId());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
            for (CityBean cityBean : mSelectedCities) {
                mCityManagerAdapter.remove(cityBean);
            }
            mCab.finish();
            EventBus.getDefault().post(new SelectCityEvent());
        }
    }
}
