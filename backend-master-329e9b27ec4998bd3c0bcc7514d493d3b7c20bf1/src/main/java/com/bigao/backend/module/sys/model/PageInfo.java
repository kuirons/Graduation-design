package com.bigao.backend.module.sys.model;

/**
 * @author yuan-hai
 * @date 2013-10-10
 */
public class PageInfo {

    private int startRow = 1;

    private int pageSize = 99999;

    private int resultSize = 0;

    public static PageInfo valueOf(int startRow, int pageSize) {
        return new PageInfo(startRow, pageSize);
    }

    public PageInfo(int startRow, int pageSize) {
        super();
        this.startRow = startRow;
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getResultSize() {
        return resultSize;
    }

    public void setResultSize(int resultSize) {
        this.resultSize = resultSize;
    }


}
