package com.liuxl.sacffold.domain.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/8/30
 */
public abstract class BaseDTO  implements Serializable {

    private static final long serialVersionUID = -7979796872300446201L;
    /**
     * 创建者ID
     */
    private long createId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人ID
     */
    private long updateId;
    /**
     * 更新时间
     */
    private Date updateTime;

    public long getCreateId() {
        return createId;
    }

    public void setCreateId(long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(long updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
