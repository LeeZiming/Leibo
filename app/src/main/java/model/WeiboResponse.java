package model;

import com.google.gson.annotations.SerializedName;

public class WeiboResponse {
    @SerializedName("idstr")
    private String id; //微博ID

    @SerializedName("created_at")
    private String createdTime; //创建时间
}
