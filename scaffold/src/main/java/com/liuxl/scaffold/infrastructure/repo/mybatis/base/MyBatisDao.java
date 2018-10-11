package com.liuxl.scaffold.infrastructure.repo.mybatis.base;



import com.liuxl.plugs.domain.component.MapMapper;
import com.liuxl.plugs.domain.component.Page;
import com.liuxl.plugs.domain.component.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * The interface My batis dao.
 */

public interface MyBatisDao {

    /**
     * 插入一个实体
     *
     * @param sqlMapId mybatis 映射id
     * @param object   实体参数
     * @return int
     */
    int insert(final String sqlMapId, final Object object);

    /**
     * 查询列表
     *
     * @param sqlMapId mybatis映射id
     * @return list
     */
    List findForList(String sqlMapId);

    /**
     * 查询列表
     *
     * @param sqlMapId mybatis映射id
     * @param param    查询参数
     * @return list
     */
    List findForList(final String sqlMapId, final Object param);

    /**
     * 查询列表
     *
     * @param sqlMapId mybatis映射id
     * @param param    查询参数
     * @param offset   查询起始位置(偏移量),从1开始
     * @param limit    查询数量,必须大于0
     * @return list
     */
    List findForList(final String sqlMapId, final Object param, final int offset, final int limit);

    /**
     * 获取结果集的map
     *
     * @param sqlMapId  mybatis映射id
     * @param mapMapper 处理多行结果集的接口,指定map的key和value
     * @return 结果对象的map map
     */
    Map findForMap(final String sqlMapId, final MapMapper mapMapper);

    /**
     * 获取结果集的map
     *
     * @param sqlMapId  mybatis映射id
     * @param parameter 参数数组
     * @param mapMapper 处理多行结果集的接口,指定map的key和value
     * @return map
     */
    Map findForMap(final String sqlMapId, final Object parameter, final MapMapper mapMapper);

    /**
     * 带有分页信息的查询
     *
     * @param sqlMapId    mybatis映射id
     * @param pageRequest 分页请求参数信息
     * @return page
     */
    Page findForPage(String sqlMapId, PageRequest pageRequest);

    /**
     * 查询得到一个实体
     *
     * @param sqlMapId mybatis映射id
     * @return object
     */
    Object findForObject(final String sqlMapId);

    /**
     * 查询得到一个实体
     *
     * @param sqlMapId mybatis映射id
     * @param param    查询参数
     * @return object
     */
    Object findForObject(final String sqlMapId, final Object param);

    /**
     * 修改
     *
     * @param sqlMapId mybatis映射id
     * @param param    参数
     * @return int
     */
    int update(final String sqlMapId, final Object param);

    /**
     * 删除
     *
     * @param sqlMapId mybatis映射id
     * @return int
     */
    int delete(final String sqlMapId);

    /**
     * 带有参数删除
     *
     * @param sqlMapId mybatis映射id
     * @param param    参数
     * @return int
     */
    int delete(final String sqlMapId, final Object param);

}
