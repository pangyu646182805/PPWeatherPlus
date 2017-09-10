package com.ppyy.ppweatherplus.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mmin18.widget.RealtimeBlurView;
import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.base.BaseFragment;
import com.ppyy.ppweatherplus.utils.UIUtils;

import butterknife.BindView;
import im.delight.android.webview.AdvancedWebView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by NeuroAndroid on 2017/9/5.
 */

public class ReaderDetailFragment extends BaseFragment implements AdvancedWebView.Listener {
    public static ReaderDetailFragment newInstance(String url) {
        ReaderDetailFragment readerDetailFragment = new ReaderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        readerDetailFragment.setArguments(bundle);
        return readerDetailFragment;
    }

    @BindView(R.id.web_view)
    AdvancedWebView mWebView;
    @BindView(R.id.pb)
    ContentLoadingProgressBar mPb;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.blur_view)
    RealtimeBlurView mBlurView;
    @BindView(R.id.iv_img)
    PhotoView mIvImg;

    private String mLoadUrl;
    private float mBlurRadius;
    public boolean isImgShow;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_reader_detail;
    }

    @Override
    protected void initView() {
        setDisplayHomeAsUpEnabled();
        mIvImg.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    @Override
    protected void initData() {
        mBlurRadius = UIUtils.getDimen(R.dimen.x32);
        mLoadUrl = getArguments().getString("url");
        mWebView.loadUrl(mLoadUrl);
    }

    @Override
    protected void initListener() {
        mWebView.setListener(mActivity, this);
        // 添加js交互接口类，并起别名 imagelistner
        mWebView.addJavascriptInterface(new JavascriptInterface(), "imagelistner");
        mIvImg.setOnViewTapListener((view, x, y) -> dismissImg());
    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        mPb.show();
    }

    @Override
    public void onPageFinished(String url) {
        mPb.hide();
        addImageClickListner();
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
    }

    @Override
    public void onExternalPageRequest(String url) {
    }

    // js通信接口
    public class JavascriptInterface {
        // 4.2及以上版本需要增加注释语句@JavascriptInterface
        @android.webkit.JavascriptInterface
        public void openImage(String url) {
            showImg(url);
        }
    }

    private void showImg(String url) {
        mActivity.runOnUiThread(() -> {
            isImgShow = true;
            mFlContainer.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(url)
                    .error(R.color.transparent)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.color.transparent)
                    .crossFade()
                    .into(mIvImg);
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
            animator.addUpdateListener(valueAnimator -> {
                float percent = (float) valueAnimator.getAnimatedValue();
                mBlurView.setBlurRadius(percent * mBlurRadius);
                mIvImg.setAlpha(percent);
            });
            animator.setDuration(333);
            animator.start();
        });
    }

    public void dismissImg() {
        ValueAnimator animator = ValueAnimator.ofFloat(1f, 0f);
        animator.addUpdateListener(valueAnimator -> {
            float percent = (float) valueAnimator.getAnimatedValue();
            mBlurView.setBlurRadius(percent * mBlurRadius);
            mIvImg.setAlpha(percent);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFlContainer.setVisibility(View.GONE);
                isImgShow = false;
            }
        });
        animator.setDuration(333);
        animator.start();
    }

    public boolean onBackPressed() {
        return mWebView.onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onDestroyView() {
        mWebView.onDestroy();
        super.onDestroyView();
    }
}
