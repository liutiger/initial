package com.liuxl.sacffold.domain.query;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/8/30
 */
public class DefaultPageQuery extends PageQuery {


    private Integer pageNum;

    private String sortExpression;

    public Integer getPageNum() {
        return this.pageNum == null ? super.getCurrentPage() : this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        this.setCurrentPage(pageNum);
    }

    public String getSortExpression() {
        return sortExpression;
    }

    public void setSortExpression(String sortExpression) {
        this.sortExpression = sortExpression;
    }
}
