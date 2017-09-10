package com.ppyy.ppweatherplus.mvp.model;

import com.ppyy.ppweatherplus.model.response.ReaderResponse;

import io.reactivex.Observable;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public interface IReaderModel {
    Observable<ReaderResponse> getReaderJsonData(int categoryId);
}
