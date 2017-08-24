package com.ppyy.ppweatherplus.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.ppyy.ppweatherplus.bean.CityBean;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.ui.fragment.WeatherDetailFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NeuroAndroid on 2017/8/24.
 */

public class WeatherPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private ArrayList<CityBean> mAllCities;
    private final SparseArray<WeakReference<Fragment>> mFragmentArray = new SparseArray<>();
    private final List<Holder> mHolderList = new ArrayList<>();

    public WeatherPagerAdapter(Context context, FragmentManager fm, ArrayList<CityBean> allCities) {
        super(fm);
        this.mContext = context;
        mAllCities = allCities;

        Bundle bundle;
        for (CityBean bean : allCities) {
            bundle = new Bundle();
            bundle.putSerializable(Constant.CITY, bean);
            add(WeatherDetailFragment.class, bundle);
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
        return mAllCities.size();
    }

    private final static class Holder {
        String mClassName;
        Bundle mParams;
    }
}
