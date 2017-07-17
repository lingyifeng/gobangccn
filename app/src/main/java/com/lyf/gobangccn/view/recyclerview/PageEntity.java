package com.lyf.gobangccn.view.recyclerview;

import java.util.ArrayList;
import java.util.List;

public class PageEntity<O> {

    /**
     * pageNum : 1
     * pageSize : 20
     * startRow : 0
     * endRow : 20
     * total : 4
     * pages : 1
     */

    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;
    private List<O> result = new ArrayList<O>();

    public PageEntity() {
        this.pageNum = 0;
        this.pages = 1;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<O> getResult() {
        return result;
    }

    public void setResult(List<O> result) {
        this.result = result;
    }

    public boolean hasNextPage() {
        return pages > pageNum;
    }

    public int nextPage() {
        return pageNum + 1;
    }
}