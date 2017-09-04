package com.ppyy.ppweatherplus.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.ppyy.ppweatherplus.ui.fragment.ReaderFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/9/4.
 */

public class ReaderPagerAdapter extends FragmentPagerAdapter {
    private static final int[] CATE_IDS = {0, 1, 2, 3, 4, 5};

    private Context mContext;
    private final SparseArray<WeakReference<Fragment>> mFragmentArray = new SparseArray<>();
    private List<String> mTitles = Arrays.asList("文艺", "科技", "社会", "生活", "商业", "科学");
    private final List<Holder> mHolderList = new ArrayList<>();

    public ReaderPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;

        Bundle bundle;
        for (int i = 0; i < mTitles.size(); i++) {
            bundle = new Bundle();
            bundle.putInt("cate_id", CATE_IDS[i]);
            add(ReaderFragment.class, bundle);
        }
    }

    @SuppressWarnings("synthetic-access")
    public void add(@NonNull final Class<? extends Fragment> className, final Bundle params) {
        final Holder mHolder = new Holder();
        mHolder.mClassName = className.getName();
        mHolder.mParams = params;

        final int mPosition = mHolderList.size();
        mHolderList.add(mPosition, mHolder);
        notifyDataSetChanged();
    }

    public Fragment getFragment(final int position) {
        final WeakReference<Fragment> mWeakFragment = mFragmentArray.get(position);
        if (mWeakFragment != null && mWeakFragment.get() != null) {
            return mWeakFragment.get();
        }
        return getItem(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        final Fragment mFragment = (Fragment) super.instantiateItem(container, position);
        final WeakReference<Fragment> mWeakFragment = mFragmentArray.get(position);
        if (mWeakFragment != null) {
            mWeakFragment.clear();
        }
        mFragmentArray.put(position, new WeakReference<>(mFragment));
        return mFragment;
    }

    @Override
    public Fragment getItem(int position) {
        final Holder holder = mHolderList.get(position);
        return Fragment.instantiate(mContext,
                holder.mClassName, holder.mParams);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        super.destroyItem(container, position, object);
        final WeakReference<Fragment> mWeakFragment = mFragmentArray.get(position);
        if (mWeakFragment != null) {
            mWeakFragment.clear();
        }
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    private final static class Holder {
        String mClassName;
        Bundle mParams;
    }
}
