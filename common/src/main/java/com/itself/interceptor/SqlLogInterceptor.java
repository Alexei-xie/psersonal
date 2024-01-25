package com.itself.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;

/**
 * 打印完整可执行的sql
 * 将该类注册到spring.factories文件中完成自动装配
 */
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
@Component
public class SqlLogInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 计算执行SQL耗时
        long timing = 0L;
        Object result = null;
        try {
            long start = SystemClock.now();
            result = invocation.proceed();
            timing = SystemClock.now() - start;
        } finally {
            // SQL 打印执行结果
            // 获取真正的代理对象
            Object target = PluginUtils.realTarget(invocation.getTarget());
            MetaObject metaObject = SystemMetaObject.forObject(target);

            // 获取执行的MappedStatement
            MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

            // 获取运行的SQL
            BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
            Configuration configuration = ms.getConfiguration();

            // 查询参数
            List<Object> params = getRuntimeParams(configuration, boundSql);
            String runTimeSql = getRuntimeSql(boundSql, params);
//            String formatterSql = SqlFormatter.format(runTimeSql);

            // 打印 sql
            log.info("\n==============  Sql Start  ==============" +
                            "\nExecute Time：{} ms - ID：{}" +
                            "\nExecute SQL ：{}" +
                            "\n==============  Sql  End   ==============",
                    timing, ms.getId(), runTimeSql);
        }
        return result;
    }

    /**
     * 取得运行时的SQL 替换对应的参数
     */
    private String getRuntimeSql(BoundSql boundSql, List<Object> params) {
        String sql = boundSql.getSql();
        for (Object object : params) {
            sql = sql.replaceFirst("\\?", getParameterValue(object));
        }
        return sql;
    }

    /**
     * 取得运行时的参数
     */
    private static List<Object> getRuntimeParams(Configuration configuration, BoundSql boundSql) {
        List<Object> params = new ArrayList<>();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                params.add(parameterObject);
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        params.add(obj);
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        params.add(obj);
                    }
                }
            }
        }
        return params;
    }


    /**
     * 取得参数值
     */
    private static String getParameterValue(Object obj) {
        String value = "";
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            value = "'" + formatter.format((Date) obj) + "'";
        } else if (obj != null) {
            value = obj.toString();
        }
        value = value.replace("$", "\\$");
        return value;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}