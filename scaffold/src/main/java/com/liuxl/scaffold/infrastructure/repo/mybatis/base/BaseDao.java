package com.liuxl.scaffold.infrastructure.repo.mybatis.base;

import com.liuxl.plugs.domain.component.MapMapper;
import com.liuxl.plugs.domain.component.Page;
import com.liuxl.plugs.domain.component.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by renyueliang on 17/3/23.
 */
public abstract class BaseDao {

    @Autowired
    private MyBatisDao myBatisDao;

    /**
     * 插入一个实体
     *
     * @param sqlMapId  mybatis 映射id
     * @param object  实体参数
     * @return
     */
    public int insert(final String sqlMapId, final Object object) {
        return myBatisDao.insert(sqlMapId,object);
    }

    /**
     * 查询列表
     *
     * @param sqlMapId  mybatis映射id
     * @return
     */
    public List findForList(String sqlMapId) {
        return myBatisDao.findForList(sqlMapId, null);
    }

    /**
     * 查询列表
     *
     * @param sqlMapId  mybatis映射id
     * @param param  查询参数
     * @return
     */
    public List findForList(final String sqlMapId, final Object param) {
        return myBatisDao.findForList(sqlMapId,param);
    }

    /**
     * 查询列表
     *
     * @param sqlMapId  mybatis映射id
     * @param param  查询参数
     * @param offset  查询起始位置(偏移量),从1开始
     * @param limit  查询数量,必须大于0
     * @return
     */
    public List findForList(final String sqlMapId, final Object param, final int offset, final int limit) {
        return myBatisDao.findForList(sqlMapId,param,offset,limit);
    }

    /**
     * 获取结果集的map
     *
     * @param sqlMapId  mybatis映射id
     * @param mapMapper  处理多行结果集的接口,指定map的key和value
     * @return 结果对象的map
     */
    public Map findForMap(final String sqlMapId, final MapMapper mapMapper) {
        return myBatisDao.findForMap(sqlMapId, mapMapper);
    }

    /**
     * 获取结果集的map
     *
     * @param sqlMapId  mybatis映射id
     * @param parameter  参数数组
     * @param mapMapper  处理多行结果集的接口,指定map的key和value
     * @return
     */
    public Map findForMap(final String sqlMapId, final Object parameter, final MapMapper mapMapper) {
        return myBatisDao.findForMap(sqlMapId,parameter,mapMapper);
    }

    /**
     * 带有分页信息的查询
     *
     * @param sqlMapId  mybatis映射id
     * @param pageRequest  分页请求参数信息
     * @return
     */
    public Page findForPage(String sqlMapId, PageRequest pageRequest) {
        return myBatisDao.findForPage(sqlMapId,pageRequest);
    }

    /**
     * 查询得到一个实体
     *
     * @param sqlMapId
     *            mybatis映射id
     * @return
     */
    public Object findForObject(final String sqlMapId) {
        return myBatisDao.findForObject(sqlMapId);
    }

    /**
     * 查询得到一个实体
     *
     * @param sqlMapId  mybatis映射id
     * @param param  查询参数
     * @return
     */
    public Object findForObject(final String sqlMapId, final Object param) {
        return myBatisDao.findForObject(sqlMapId,param);
    }

    /**
     * 修改
     *
     * @param sqlMapId  mybatis映射id
     * @param param  参数
     * @return
     */
    public int update(final String sqlMapId, final Object param) {
        return  myBatisDao.update(sqlMapId,param);
    }

    /**
     * 删除
     *
     * @param sqlMapId  mybatis映射id
     * @return
     */
    public int delete(final String sqlMapId) {
        return myBatisDao.delete(sqlMapId);
    }

    /**
     * 带有参数删除
     *
     * @param sqlMapId
     *            mybatis映射id
     * @param param
     *            参数
     * @return
     */
    public int delete(final String sqlMapId, final Object param) {
        return myBatisDao.delete(sqlMapId,param);
    }

    public void setMyBatisDao(MyBatisDao myBatisDao) {
        this.myBatisDao = myBatisDao;
    }
}
