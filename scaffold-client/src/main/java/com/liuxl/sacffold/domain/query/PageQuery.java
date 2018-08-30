package com.liuxl.sacffold.domain.query;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:初始化
 *
 * @author liuxl
 * @date 2018/8/30
 */
public class PageQuery implements Serializable {
    private static final long serialVersionUID = -2033377857521140600L;
    private static final Integer defaultPageSize = new Integer(20);
    private static final Integer defaultFristPage = new Integer(1);
    private static final Integer defaultTotleItem = new Integer(0);
    private Integer totalItem;
    private Integer pageSize;
    private Integer currentPage;
    private int startRow;
    private int endRow;
    private int startIndex;

    public PageQuery() {
    }

    protected Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public boolean isFirstPage() {
        return this.getCurrentPage() == 1;
    }

    public int getPreviousPage() {
        int back = this.getCurrentPage() - 1;
        if (back <= 0) {
            back = 1;
        }

        return back;
    }

    public boolean isLastPage() {
        return this.getTotalPage() == this.getCurrentPage();
    }

    public int getNextPage() {
        int back = this.getCurrentPage() + 1;
        if (back > this.getTotalPage()) {
            back = this.getTotalPage();
        }

        return back;
    }

    public Integer getCurrentPage() {
        return this.currentPage == null ? defaultFristPage : this.currentPage;
    }

    public void setCurrentPageString(String pageString) {
        if (this.isBlankString(pageString)) {
            this.setCurrentPage(defaultFristPage);
        }

        try {
            Integer integer = new Integer(pageString);
            this.setCurrentPage(integer);
        } catch (NumberFormatException var3) {
            this.setCurrentPage(defaultFristPage);
        }

    }

    public void setCurrentPage(Integer cPage) {
        if (cPage != null && cPage > 0) {
            this.currentPage = cPage;
        } else {
            this.currentPage = null;
        }

        this.setStartEndRow();
    }

    private void setStartEndRow() {
        this.startRow = this.getPageSize() * (this.getCurrentPage() - 1) + 1;
        this.startIndex = this.getPageSize() * (this.getCurrentPage() - 1);
        this.endRow = this.startRow + this.getPageSize() - 1;
    }

    public Integer getPageSize() {
        return this.pageSize == null ? this.getDefaultPageSize() : this.pageSize;
    }

    public boolean hasSetPageSize() {
        return this.pageSize != null;
    }

    public void setPageSizeString(String pageSizeString) {
        if (!this.isBlankString(pageSizeString)) {
            try {
                Integer integer = new Integer(pageSizeString);
                this.setPageSize(integer);
            } catch (NumberFormatException var3) {
                ;
            }

        }
    }

    private boolean isBlankString(String pageSizeString) {
        if (pageSizeString == null) {
            return true;
        } else {
            return pageSizeString.trim().length() == 0;
        }
    }

    public void setPageSize(Integer pSize) {
        if (pSize != null && pSize > 0) {
            this.pageSize = pSize;
        } else {
            this.pageSize = null;
        }

        this.setStartEndRow();
    }

    public Integer getTotalItem() {
        return this.totalItem == null ? defaultTotleItem : this.totalItem;
    }

    public void setTotalItem(Integer tItem) {
        if (tItem == null) {
            throw new IllegalArgumentException("TotalItem can't be null.");
        } else {
            this.totalItem = tItem;
            int current = this.getCurrentPage();
            int lastPage = this.getTotalPage();
            if (current > lastPage) {
                this.setCurrentPage(new Integer(lastPage));
            }

        }
    }

    public int getTotalPage() {
        int pgSize = this.getPageSize();
        int total = this.getTotalItem();
        int result = total / pgSize;
        if (total == 0 || total % pgSize != 0) {
            ++result;
        }

        return result;
    }

    public int getPageFristItem() {
        int cPage = this.getCurrentPage();
        if (cPage == 1) {
            return 1;
        } else {
            --cPage;
            int pgSize = this.getPageSize();
            return pgSize * cPage + 1;
        }
    }

    public int getPageLastItem() {
        int cPage = this.getCurrentPage();
        int pgSize = this.getPageSize();
        int assumeLast = pgSize * cPage;
        int totalItem = this.getTotalItem();
        return assumeLast > totalItem ? totalItem : assumeLast;
    }

    public int getEndRow() {
        return this.endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getStartRow() {
        return this.startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public boolean nextPage() {
        if (this.currentPage != null && this.currentPage >= this.getTotalPage()) {
            return false;
        } else {
            if (this.currentPage == null) {
                this.setCurrentPage(defaultFristPage);
            } else {
                this.setCurrentPage(this.getNextPage());
            }

            return true;
        }
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void validation() {
    }
}
