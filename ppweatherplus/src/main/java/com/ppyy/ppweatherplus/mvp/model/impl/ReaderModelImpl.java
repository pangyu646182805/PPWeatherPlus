package com.ppyy.ppweatherplus.mvp.model.impl;

import com.ppyy.ppweatherplus.base.BaseModel;
import com.ppyy.ppweatherplus.model.response.ReaderResponse;
import com.ppyy.ppweatherplus.mvp.model.IReaderModel;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/9/5.
 */

public class ReaderModelImpl extends BaseModel implements IReaderModel {
    public ReaderModelImpl(String baseUrl) {
        super(baseUrl);
    }

    @Override
    public Observable<ReaderResponse> getReaderJsonData(int categoryId) {
        return mService.getReaderJsonData(categoryId);
    }
}
