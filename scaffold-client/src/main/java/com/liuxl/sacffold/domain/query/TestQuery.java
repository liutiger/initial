package com.liuxl.sacffold.domain.query;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author liuxl
 * @date 2018/8/30
 */
public class TestQuery extends BaseQuery {

    private String name;

    @Override
    public void validation() {


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
