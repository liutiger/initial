package com.liuxl.plugs.plugIn;

import com.liuxl.plugs.domain.component.Dialect;
import com.liuxl.plugs.utils.PropertiesHelper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * 为ibatis3提供基于方言(Dialect)的分页查询的插件<br>
 * <p>
 * 将拦截Executor.query()方法实现分页方言的插入<br>
 *
 * @author liuxl
 * @date 2018/8/30
 */

@Intercepts( { @Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class,
        ResultHandler.class }) })
public class OffsetLimitInterceptor implements Interceptor {

    private static int MAPPED_STATEMENT_INDEX = 0;

    private static int PARAMETER_INDEX = 1;

    private static int ROWBOUNDS_INDEX = 2;

    private Dialect dialect;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        processIntercept(invocation.getArgs());
        Object o = invocation.proceed();
        return o;
    }

    void processIntercept(final Object[] queryArgs) {
        MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        Object parameter = queryArgs[PARAMETER_INDEX];
        final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();

        if (dialect.supportsLimit()
                && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
            BoundSql boundSql = ms.getBoundSql(parameter);
            String sql = boundSql.getSql().trim();
            if (dialect.supportsLimitOffset()) {
                sql = dialect.getLimitString(sql, offset, limit);
                offset = RowBounds.NO_ROW_OFFSET;
            }
            else {
                sql = dialect.getLimitString(sql, 0, offset+limit);
            }
            limit = RowBounds.NO_ROW_LIMIT;

            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(offset, limit);
            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql,
                    boundSql.getParameterMappings(), boundSql
                    .getParameterObject());
            MappedStatement newMs = copyFromMappedStatement(ms,
                    new BoundSqlSqlSource(newBoundSql));
            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
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

        // setStatementResultMap()
        builder.parameterMap(ms.getParameterMap());

        // setStatementResultMap()
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        // setStatementCache()
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

        String dialectClass = new PropertiesHelper(properties)
                .getRequiredString("dialectClass");
        try {
            dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(
                    "cannot create dialect instance by dialectClass:"
                            + dialectClass, e);
        }

    }

    private String getKeyProperty(String[] keyProperties) {

        StringBuilder builder = new StringBuilder();
        if (keyProperties != null && keyProperties.length > 0) {
            int length = keyProperties.length;
            for (int i = 0; i < length; i++) {
                builder.append(keyProperties[i]);
                if (i < length -1) {
                    builder.append(",");
                }
            }
            return builder.toString();
        }

        return null;
    }

    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
