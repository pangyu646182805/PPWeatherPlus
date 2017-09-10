package com.ppyy.ppweatherplus.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.transition.Slide;
import android.view.Gravity;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.ReaderPagerAdapter;
import com.ppyy.ppweatherplus.base.BaseActivity;
import com.ppyy.ppweatherplus.base.BaseFragment;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.ui.fragment.ReaderDetailFragment;
import com.ppyy.ppweatherplus.utils.FragmentUtils;
import com.ppyy.ppweatherplus.utils.SystemUtils;

import butterknife.BindView;

/**
 * Created by NeuroAndroid on 2017/9/4.
 */

public class ReaderActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;

    private BaseFragment mCurrentFragment;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_reader;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        setToolbarTitle(R.string.reader);
        setUpViewPager();
    }

    @Override
    protected void initData() {
        if (SystemUtils.greaterLOLLIPOP()) {
            Intent intent = getIntent();
            String transition = intent.getStringExtra(Constant.TRANSITION);
            if ("slide".equals(transition)) {
                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.BOTTOM);
                slide.setDuration(400);
                getWindow().setEnterTransition(slide);
            }
        }
    }

    public void openReaderDetailFragment(String url) {
        mCurrentFragment = ReaderDetailFragment.newInstance(url);
        FragmentUtils.replaceFragment(getSupportFragmentManager(), mCurrentFragment, R.id.fl_container, false);
    }

    private void setUpViewPager() {
        ReaderPagerAdapter readerPagerAdapter = new ReaderPagerAdapter(this, getSupportFragmentManager());
        mVpContent.setAdapter(readerPagerAdapter);
        mVpContent.setOffscreenPageLimit(readerPagerAdapter.getCount() - 1);
        mTabs.setupWithViewPager(mVpContent);
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment != null) {
            ReaderDetailFragment readerDetailFragment = (ReaderDetailFragment) mCurrentFragment;
            if (readerDetailFragment.isImgShow) {
                readerDetailFragment.dismissImg();
                return;
            }
            if (!readerDetailFragment.onBackPressed()) {
                return;
            }
            FragmentUtils.removeFragment(mCurrentFragment);
            mCurrentFragment = null;
        } else {
            super.onBackPressed();
        }
    }
}
