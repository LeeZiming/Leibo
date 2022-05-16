package com.lee.leibo.model;


public class WeiboInfo {
    private String id; //微博ID

    private String createdTime; //创建时间

    private String text; //微博文本内容

    private String source; //微博来源

    private boolean favorited; //是否已收藏，true：是，false：否

    private boolean truncated; //是否被截断，true：是，false：否

    private String thumbnailPic; //缩略图片地址，没有时不返回此字段

    private String bmiddlePic; //中等尺寸图片地址，没有时不返回此字段

    private String originalPic; //原始图片地址，没有时不返回此字段

    private UserInfo user; //微博作者的用户信息字段

    private int repostsCount; //转发数

    private int commentsCount; //评论数

    private int attitudesCount; //表态数

    private WeiboInfo repostWeiboInfo;

//    @SerializedName("visible")
//    private int visible; //微博的可见性及指定可见分组信息。
    // 该object中type取值，0：普通微博，1：私密微博，3：指定分组微博，4：密友微博；list_id为分组的组号


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public String getThumbnailPic() {
        return thumbnailPic;
    }

    public void setThumbnailPic(String thumbnailPic) {
        this.thumbnailPic = thumbnailPic;
    }

    public String getBmiddlePic() {
        return bmiddlePic;
    }

    public void setBmiddlePic(String bmiddlePic) {
        this.bmiddlePic = bmiddlePic;
    }

    public String getOriginalPic() {
        return originalPic;
    }

    public void setOriginalPic(String originalPic) {
        this.originalPic = originalPic;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public int getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(int repostsCount) {
        this.repostsCount = repostsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getAttitudesCount() {
        return attitudesCount;
    }

    public void setAttitudesCount(int attitudesCount) {
        this.attitudesCount = attitudesCount;
    }

    public WeiboInfo getRepostWeiboInfo() {
        return repostWeiboInfo;
    }

    public void setRepostWeiboInfo(WeiboInfo repostWeiboInfo) {
        this.repostWeiboInfo = repostWeiboInfo;
    }
}
