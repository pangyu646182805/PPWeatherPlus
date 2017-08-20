package com.ppyy.ppweatherplus.mvp.presenter;

import com.ppyy.ppweatherplus.base.BaseObserver;
import com.ppyy.ppweatherplus.base.BasePresenter;
import com.ppyy.ppweatherplus.config.Constant;
import com.ppyy.ppweatherplus.manager.CacheManager;
import com.ppyy.ppweatherplus.model.response.HotCityResponse;
import com.ppyy.ppweatherplus.model.response.SearchCityResponse;
import com.ppyy.ppweatherplus.mvp.contract.ISelectCityContract;
import com.ppyy.ppweatherplus.mvp.model.impl.SelectCityModelImpl;
import com.ppyy.ppweatherplus.utils.RxUtils;

/**
 * Created by Administrator on 2017/8/19.
 */

public class SelectCityPresenter extends BasePresenter<SelectCityModelImpl, ISelectCityContract.View>
        implements ISelectCityContract.Presenter {
    public SelectCityPresenter(ISelectCityContract.View view) {
        super(view);
        mModel = new SelectCityModelImpl(Constant.BASE_URL);
        mView.setPresenter(this);
    }

    @Override
    public void getHotCity() {
        HotCityResponse hotCityResponse = CacheManager.getHotCityList(mView.getContext());
        if (hotCityResponse != null) {
            mView.showHotCity(hotCityResponse);
            return;
        }
        getModelFilteredFactory(HotCityResponse.class).compose(mModel.getHotCity())
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<HotCityResponse>() {
                    @Override
                    protected void onHandleSuccess(HotCityResponse hotCityResponse) {
                        mView.showHotCity(hotCityResponse);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });
    }

    @Override
    public void searchCityByKeyword(String keyword) {
        getModelFilteredFactory(SearchCityResponse.class).compose(mModel.searchCityByKeyword(keyword))
                .compose(RxUtils.bindToLifecycle(mView))
                .subscribe(new BaseObserver<SearchCityResponse>() {
                    @Override
                    protected void onHandleSuccess(SearchCityResponse searchCityResponse) {
                        mView.showSearchResponse(searchCityResponse);
                    }

                    @Override
                    protected void onHandleError(String tip) {
                        mView.showTip(tip);
                    }
                });

    }
}
