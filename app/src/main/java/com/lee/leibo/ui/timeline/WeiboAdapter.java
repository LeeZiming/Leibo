package com.lee.leibo.ui.timeline;

import static com.blankj.utilcode.util.ConvertUtils.dp2px;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lee.leibo.R;
import com.lee.leibo.app.LeiboApplication;

import java.util.List;

import com.lee.leibo.model.WeiboInfo;

public class WeiboAdapter extends BaseQuickAdapter<WeiboInfo, BaseViewHolder> {
    public WeiboAdapter(@Nullable List<WeiboInfo> data) {
        super(R.layout.item_weibo, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder viewHolder, WeiboInfo weiboInfo) {
        Glide.with(LeiboApplication.getInstance())
                .load(weiboInfo.getUser().getAvatarLarge())
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(dp2px(50))))
                .into((ImageView) viewHolder.getView(R.id.iv_avator));
        viewHolder.setText(R.id.tv_name, weiboInfo.getUser().getScreenName());
        viewHolder.setText(R.id.tv_description, weiboInfo.getUser().getVerifiedReason());
        viewHolder.setText(R.id.tv_content, weiboInfo.getText());

        if (weiboInfo.getRepostWeiboInfo() != null) {
            viewHolder.setText(R.id.tv_repost, weiboInfo.getRepostWeiboInfo().getText());
        }
    }
}
