package com.lee.leibo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeiboResponse {
    @SerializedName("statuses")
    private List<WeiboInfo> weiboInfoList;

    public List<WeiboInfo> getWeiboInfoList() {
        return weiboInfoList;
    }

    public void setWeiboInfoList(List<WeiboInfo> weiboInfoList) {
        this.weiboInfoList = weiboInfoList;
    }
}
