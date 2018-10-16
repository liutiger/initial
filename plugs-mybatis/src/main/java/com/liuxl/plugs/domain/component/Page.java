package com.liuxl.plugs.domain.component;


import com.liuxl.plugs.utils.PageUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:分页信息 第一页从1开始
 *
 * @author liuxl
 * @date 2018/8/30
 */
public class Page<T> implements Serializable, Iterable<T> {
    protected List<T> result;

    protected int pageSize;

    protected int pageNumber;

    protected int totalCount;

    public Page() {

    }

    public Page(PageRequest p, int totalCount) {

        this(p.getPageNumber(), p.getPageSize(), totalCount);
    }

    public Page(int pageNumber, int pageSize, int totalCount) {

        this(pageNumber, pageSize, totalCount, new ArrayList(0));
    }

    public Page(int pageNumber, int pageSize, int totalCount, List<T> result) {

        if (pageSize <= 0) {
            throw new IllegalArgumentException(
                    "[pageSize] must great than zero");
        }
        this.pageSize = pageSize;
        this.pageNumber = PageUtils.computePageNumber(pageNumber, pageSize,totalCount);
        this.totalCount = totalCount;
        setResult(result);
    }

    public void setResult(List<T> elements) {

        if (elements == null)
            throw new IllegalArgumentException("'result' must be not null");
        this.result = elements;
    }

    /**
     * 当前页包含的数据
     *
     * @return 当前页数据源
     */
    public List<T> getResult() {

        return result;
    }

    /**
     * 是否是首页（第一页），第一页页码为1
     *
     * @return 首页标识
     */
    public boolean isFirstPage() {

        return getPageNumber() == 1;
    }

    /**
     * 是否是最后一页
     *
     * @return 末页标识
     */
    public boolean isLastPage() {

        return getPageNumber() >= getLastPageNumber();
    }

    /**
     * 是否有下一页
     *
     * @return 下一页标识
     */
    public boolean isHasNextPage() {

        return getLastPageNumber() > getPageNumber();
    }

    /**
     * 是否有上一页
     *
     * @return 上一页标识
     */
    public boolean isHasPreviousPage() {

        return getPageNumber() > 1;
    }

    /**
     * 获取最后一页页码，也就是总页数
     *
     * @return 最后一页页码
     */
    public int getLastPageNumber() {

        return PageUtils.computeLastPageNumber(totalCount, pageSize);
    }

    /**
     * 总的数据条目数量，0表示没有数据
     *
     * @return 总数量
     */
    public int getTotalCount() {

        return totalCount;
    }

    /**
     * 获取当前页的首条数据的行编码
     *
     * @return 当前页的首条数据的行编码
     */
    public int getThisPageFirstElementNumber() {

        return (getPageNumber() - 1) * getPageSize() + 1;
    }

    /**
     * 获取当前页的末条数据的行编码
     *
     * @return 当前页的末条数据的行编码
     */
    public int getThisPageLastElementNumber() {

        int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
        return getTotalCount() < fullPage ? getTotalCount() : fullPage;
    }

    /**
     * 获取下一页编码
     *
     * @return 下一页编码
     */
    public int getNextPageNumber() {

        return getPageNumber() + 1;
    }

    /**
     * 获取上一页编码
     *
     * @return 上一页编码
     */
    public int getPreviousPageNumber() {

        return getPageNumber() - 1;
    }

    /**
     * 每一页显示的条目数
     *
     * @return 每一页显示的条目数
     */
    public int getPageSize() {

        return pageSize;
    }

    /**
     * 当前页的页码
     *
     * @return 当前页的页码
     */
    public int getPageNumber() {

        return pageNumber;
    }

    /**
     * 得到用于多页跳转的页码
     *
     * @return
     */
    public List<Integer> getLinkPageNumbers() {

        return PageUtils.generateLinkPageNumbers(getPageNumber(),
                getLastPageNumber(), 10);
    }

    /**
     * 得到数据库的第一条记录号
     *
     * @return
     */
    public int getFirstResult() {

        return PageUtils.getFirstResult(pageNumber, pageSize);
    }

    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>) (result == null ? Collections.emptyList()
                .iterator() : result.iterator());
    }


    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
