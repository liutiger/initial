package com.liuxl.scaffold.domain.component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:分页请求
 *
 * @author liuxl
 * @date 2018/8/30
 */
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 7292186438918641362L;
    /**
     * 过滤参数
     */
    private Map filters;

    /**
     * 页号码,页码从1开始
     */
    private int pageNumber;

    /**
     * 分页大小
     */
    private int pageSize;

    public PageRequest() {

        this(0, 0, null);
    }

    public PageRequest(int pageNumber, int pageSize) {

        this(pageNumber, pageSize, new HashMap());
    }

    public PageRequest(int pageNumber, int pageSize, Map filters) {

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.filters = filters;
    }

    public Map getFilters() {

        return filters;
    }

    public void setFilters(Map filters) {

        this.filters = filters;
    }

    public int getPageNumber() {

        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {

        this.pageNumber = pageNumber;
    }

    public int getPageSize() {

        return pageSize;
    }

    public void setPageSize(int pageSize) {

        this.pageSize = pageSize;
    }

    public void setPageNumberAndSize(int start, int limit) {

        this.pageSize = limit;
        this.pageNumber = start / limit;
    }
}
