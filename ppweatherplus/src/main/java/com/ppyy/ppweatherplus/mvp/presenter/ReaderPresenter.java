package com.ppyy.ppweatherplus.mvp.presenter;

import com.ppyy.ppweatherplus.base.BaseObserver;
import com.ppyy.ppweatherplus.base.BasePresenter;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.model.response.ReaderResponse;
import com.ppyy.ppweatherplus.mvp.contract.IReaderContract;
import com.ppyy.ppweatherplus.mvp.model.impl.ReaderModelImpl;
import com.ppyy.ppweatherplus.utils.RxUtils;

/**
 * Created by NeuroAndroid on 2017/9/5.
 */

public class ReaderPresenter extends BasePresenter<ReaderModelImpl, IReaderContract.View>
        implements IReaderContract.Presenter {
    public ReaderPresenter(IReaderContract.View view) {
        super(view);
        mModel = new ReaderModelImpl(Constant.READER_BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getReaderJsonData(int categoryId, Integer artId, Integer pageSize) {
        getModelFilteredFactory(ReaderResponse.class).compose(mModel.getReaderJsonData(categoryId, artId, pageSize))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<ReaderResponse>() {
                    @Override
                    protected void onHandleSuccess(ReaderResponse readerResponse) {
                        mView.showReaderJsonData(readerResponse);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }
}
