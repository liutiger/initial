package com.liuxl.scaffold.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:将查询的sql 转出查询总数sql
 *
 * @author liuxl
 * @date 2018/8/30
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class,
        ResultHandler.class})})
public class SelectCountSqlInterceptor implements Interceptor {

    public static String COUNT = "_count";

    private static int MAPPED_STATEMENT_INDEX = 0;

    private static int PARAMETER_INDEX = 1;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        processCountSql(invocation.getArgs());

        return invocation.proceed();
    }

    private void processCountSql(final Object[] queryArgs) {

        if (queryArgs[PARAMETER_INDEX] instanceof Map) {
            Map parameter = (Map) queryArgs[PARAMETER_INDEX];
            if (parameter.containsKey(COUNT)) {
                MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
                BoundSql boundSql = ms.getBoundSql(parameter);
                String sql = ms.getBoundSql(parameter).getSql().trim();
                BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),
                        getCountSQL(sql), boundSql.getParameterMappings(),
                        boundSql.getParameterObject());
                MappedStatement newMs = copyFromMappedStatement(ms,
                        new OffsetLimitInterceptor.BoundSqlSqlSource(newBoundSql));
                queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
            }
        }
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms,
                                                    SqlSource newSqlSource) {

        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms
                .getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(getKeyProperty(ms.getKeyProperties()));

        // setStatementTimeout()
        builder.timeout(ms.getTimeout());

        // setParameterMap()
        builder.parameterMap(ms.getParameterMap());

        // setStatementResultMap()
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        String id = "-inline";
        if (ms.getResultMaps() != null) {
            id = ms.getResultMaps().get(0).getId() + "-inline";
        }
        ResultMap resultMap = new ResultMap.Builder(null, id, Long.class,
                new ArrayList()).build();
        resultMaps.add(resultMap);
        builder.resultMaps(resultMaps);
        builder.resultSetType(ms.getResultSetType());

        // setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    private String getKeyProperty(String[] keyProperties) {

        StringBuilder builder = new StringBuilder();
        if (keyProperties != null && keyProperties.length > 0) {
            int length = keyProperties.length;
            for (int i = 0; i < length; i++) {
                builder.append(keyProperties[i]);
                if (i < length - 1) {
                    builder.append(",");
                }
            }
            return builder.toString();
        }

        return null;
    }

    private String getCountSQL(String sql) {

        String lowerCaseSQL = sql.toLowerCase().replace("\n", " ").replace(
                "\t", " ");
        int index = lowerCaseSQL.indexOf(" order ");

        if (index != -1) {
            sql = sql.substring(0, index);
        }

        if (lowerCaseSQL.indexOf(" group ") != -1) {
            return "SELECT COUNT(*) FROM (SELECT COUNT(*) AS COUNT_" + sql.substring(lowerCaseSQL.indexOf(" from ")) + ") TABLE_";
        }

        return "SELECT COUNT(*) AS COUNT_" + sql.substring(lowerCaseSQL.indexOf(" from "));
    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
