package com.ppyy.ppweatherplus.mvp.contract;

import com.ppyy.ppweatherplus.base.IPresenter;
import com.ppyy.ppweatherplus.base.IView;
import com.ppyy.ppweatherplus.model.response.ReaderResponse;

/**
 * Created by NeuroAndroid on 2017/8/18.
 */

public interface IReaderContract {
    interface Presenter extends IPresenter {
        /**
         * 获取阅读数据
         */
        void getReaderJsonData(int categoryId, Integer artId, Integer pageSize);
    }

    interface View extends IView<Presenter> {
        /**
         * 显示数据
         */
        void showReaderJsonData(ReaderResponse readerResponse);
    }
}
