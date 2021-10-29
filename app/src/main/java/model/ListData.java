package model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 业务返回的固定字段
 *
 * @param <T>
 */
public class ListData<T> {
    @SerializedName("reposts")
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
