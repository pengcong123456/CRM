package com.pc.crm.vo;

import java.util.List;

/**
 * @author : 彭聪
 * @date : 2020-08-06 11:03
 **/
public class PaginationVO<T> {

    private int total;
    private List<T> dataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
