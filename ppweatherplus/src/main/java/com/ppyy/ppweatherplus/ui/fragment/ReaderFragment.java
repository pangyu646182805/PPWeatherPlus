package com.ppyy.ppweatherplus.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.ReaderAdapter;
import com.ppyy.ppweatherplus.base.BaseLazyFragment;
import com.ppyy.ppweatherplus.model.response.ReaderResponse;
import com.ppyy.ppweatherplus.mvp.contract.IReaderContract;
import com.ppyy.ppweatherplus.mvp.presenter.ReaderPresenter;
import com.ppyy.ppweatherplus.ui.activity.ReaderActivity;
import com.ppyy.ppweatherplus.utils.DividerUtils;
import com.ppyy.ppweatherplus.utils.ShowUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/4.
 */

public class ReaderFragment extends BaseLazyFragment<IReaderContract.Presenter> implements IReaderContract.View {
    private static final int PAGE_SIZE = 20;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_reader)
    RecyclerView mRvReader;

    /**
     * 分类ID，根据此ID去请求阅读接口
     */
    private int mCategoryId;
    private ReaderAdapter mReaderAdapter;

    private boolean hasLoad;
    private boolean isRefresh;

    private int mRequestPageSize = PAGE_SIZE;

    @Override
    protected void initPresenter() {
        mPresenter = new ReaderPresenter(this);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_reader;
    }

    @Override
    protected void initView() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(mContext));
        mRvReader.setLayoutManager(new LinearLayoutManager(mContext));
        mRvReader.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
        mReaderAdapter = new ReaderAdapter(mContext, null, null);
        mRvReader.setAdapter(mReaderAdapter);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {  // 不可见 -> 可见
            if (!hasLoad) {
                hasLoad = true;
                mCategoryId = getArguments().getInt("cate_id");
                startRefresh();
            }
        } else {
            finishRefresh();
        }
    }

    private void startRefresh() {
        if (mRefreshLayout != null && !mRefreshLayout.isRefreshing())
            mRefreshLayout.autoRefresh();
    }

    private void finishRefresh() {
        if (mRefreshLayout != null && mRefreshLayout.isRefreshing())
            mRefreshLayout.finishRefresh();
        if (mRefreshLayout != null)
            mRefreshLayout.finishLoadmore(250);
    }

    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshLoadmoreListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                super.onRefresh(refreshlayout);
                isRefresh = true;
                mPresenter.getReaderJsonData(mCategoryId, null, mRequestPageSize);
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                super.onLoadmore(refreshlayout);
                isRefresh = false;
                refreshlayout.finishLoadmore(2000);
                String id = mReaderAdapter.getDataList().get(mReaderAdapter.getDataListSize() - 1).getId();
                mPresenter.getReaderJsonData(mCategoryId, Integer.parseInt(id), PAGE_SIZE);
            }
        });
        mReaderAdapter.setOnItemClickListener((holder, position, item) -> {
            String originUrl = item.getOrigin_url();
            getActivity(ReaderActivity.class).openReaderDetailFragment(originUrl);
        });
    }

    @Override
    public void showReaderJsonData(ReaderResponse readerResponse) {
        finishRefresh();
        List<ReaderResponse.DataBean.ListBean> dataList = readerResponse.getData().getList();
        if (isRefresh) {
            mReaderAdapter.replaceAll(dataList);
        } else {
            mReaderAdapter.addAll(dataList);
            mRequestPageSize += dataList.size();
        }
    }

    @Override
    public void showTip(String tip) {
        ShowUtils.showToast(tip);
        finishRefresh();
    }
}
