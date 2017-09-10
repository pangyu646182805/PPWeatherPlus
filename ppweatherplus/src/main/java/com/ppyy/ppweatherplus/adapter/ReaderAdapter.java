package com.ppyy.ppweatherplus.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.ppyy.ppweatherplus.R;
import com.ppyy.ppweatherplus.adapter.base.BaseRvAdapter;
import com.ppyy.ppweatherplus.adapter.base.BaseViewHolder;
import com.ppyy.ppweatherplus.adapter.base.IMultiItemViewType;
import com.ppyy.ppweatherplus.model.response.ReaderResponse;
import com.ppyy.ppweatherplus.utils.ImageLoader;
import com.ppyy.ppweatherplus.utils.L;
import com.ppyy.ppweatherplus.utils.TimeUtils;
import com.ppyy.ppweatherplus.utils.UIUtils;

import java.util.List;

/**
 * Created by NeuroAndroid on 2017/9/5.
 */

public class ReaderAdapter extends BaseRvAdapter<ReaderResponse.DataBean.ListBean> {
    private static final int HAS_NO_PIC_VIEW_TYPE = 0;
    private static final int HAS_ONE_PIC_VIEW_TYPE = 1;
    private static final int HAS_TWO_PIC_VIEW_TYPE = 2;
    private static final int HAS_THREE_PIC_VIEW_TYPE = 3;

    public ReaderAdapter(Context context, List<ReaderResponse.DataBean.ListBean> dataList, IMultiItemViewType<ReaderResponse.DataBean.ListBean> multiItemViewType) {
        super(context, dataList, multiItemViewType);
    }

    @Override
    public void convert(BaseViewHolder holder, ReaderResponse.DataBean.ListBean item, int position, int viewType) {
        if (item != null) {
            ImageView iv;
            ReaderResponse.DataBean.ListBean.SiteInfoBean siteInfoBean = item.getSiteInfo();
            if (siteInfoBean != null) {
                iv = holder.getView(R.id.iv_site_pic);
                ImageLoader.getInstance().displayImage(mContext, siteInfoBean.getPic(), R.drawable.border_translate, iv);
                holder.setText(R.id.tv_site_name, siteInfoBean.getName());
            }
            holder.setText(R.id.tv_time, TimeUtils.millis2String(
                    Long.parseLong(item.getUpdate_time()) * 1000, "yyyy-MM-dd"));
            holder.setText(R.id.tv_title, item.getTitle())
                    .setText(R.id.tv_sub_title, item.getBrief());
            switch (viewType) {
                case HAS_NO_PIC_VIEW_TYPE:
                    holder.setVisibility(R.id.iv_pic, View.GONE);
                    break;
                case HAS_ONE_PIC_VIEW_TYPE:
                    holder.setVisibility(R.id.iv_pic, View.VISIBLE);
                    iv = holder.getView(R.id.iv_pic);
                    ImageLoader.getInstance().displayImage(mContext, item.getPrepic1(), R.drawable.border_translate, iv);
                    break;
                case HAS_TWO_PIC_VIEW_TYPE:
                    holder.setVisibility(R.id.iv_pic_2, View.GONE);
                    iv = holder.getView(R.id.iv_pic_0);
                    ImageLoader.getInstance().displayImage(mContext, item.getPrepic1(), R.drawable.border_translate, iv);
                    iv = holder.getView(R.id.iv_pic_1);
                    ImageLoader.getInstance().displayImage(mContext, item.getPrepic2(), R.drawable.border_translate, iv);
                    break;
                case HAS_THREE_PIC_VIEW_TYPE:
                    holder.setVisibility(R.id.iv_pic_2, View.VISIBLE);
                    iv = holder.getView(R.id.iv_pic_0);
                    ImageLoader.getInstance().displayImage(mContext, item.getPrepic1(), R.drawable.border_translate, iv);
                    iv = holder.getView(R.id.iv_pic_1);
                    ImageLoader.getInstance().displayImage(mContext, item.getPrepic2(), R.drawable.border_translate, iv);
                    iv = holder.getView(R.id.iv_pic_2);
                    ImageLoader.getInstance().displayImage(mContext, item.getPrepic3(), R.drawable.border_translate, iv);
                    break;
            }
        }
    }

    @Override
    protected IMultiItemViewType<ReaderResponse.DataBean.ListBean> provideMultiItemViewType() {
        return new IMultiItemViewType<ReaderResponse.DataBean.ListBean>() {
            @Override
            public int getItemViewType(int position, ReaderResponse.DataBean.ListBean listBean) {
                String pic0 = listBean.getPrepic1();
                String pic1 = listBean.getPrepic2();
                String pic2 = listBean.getPrepic3();
                if (UIUtils.isEmpty(pic0) && UIUtils.isEmpty(pic1) && UIUtils.isEmpty(pic2)) {
                    // 图片全部为空
                    return HAS_NO_PIC_VIEW_TYPE;
                } else if ((!UIUtils.isEmpty(pic0) && !UIUtils.isEmpty(pic1)) && UIUtils.isEmpty(pic2)) {
                    // 第一第二张图片不为空
                    return HAS_TWO_PIC_VIEW_TYPE;
                } else if ((UIUtils.isEmpty(pic1) && UIUtils.isEmpty(pic2)) && !UIUtils.isEmpty(pic0)) {
                    // 第一张不为空，其余都为空
                    return HAS_ONE_PIC_VIEW_TYPE;
                } else {
                    // 三张都不为空
                    return HAS_THREE_PIC_VIEW_TYPE;
                }
            }

            @Override
            public int getLayoutId(int viewType) {
                switch (viewType) {
                    case HAS_NO_PIC_VIEW_TYPE:
                    case HAS_ONE_PIC_VIEW_TYPE:
                    default:
                        return R.layout.item_reader_0;
                    case HAS_TWO_PIC_VIEW_TYPE:
                    case HAS_THREE_PIC_VIEW_TYPE:
                        return R.layout.item_reader_1;
                }
            }
        };
    }
}
