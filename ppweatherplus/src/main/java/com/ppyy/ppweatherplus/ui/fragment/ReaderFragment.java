package com.ppyy.ppweatherplus.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.base.BaseLazyFragment;
import com.ppyy.ppweatherplus.utils.DividerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/4.
 */

public class ReaderFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView mRv;

    private int mCateId;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_reader;
    }

    @Override
    protected void initView() {
        mCateId = getArguments().getInt("cate_id");

        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add("py : " + i);
        }
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.addItemDecoration(DividerUtils.defaultHorizontalDivider(mContext));
        mRv.setAdapter(new BaseRvAdapter<String>(mContext, dataList, R.layout.item) {
            @Override
            public void convert(BaseViewHolder holder, String item, int position, int viewType) {
                holder.setText(R.id.tv, item);
            }
        });
    }
}
