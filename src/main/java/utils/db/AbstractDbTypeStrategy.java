package utils.db;

import com.google.common.base.Strings;

import com.alibaba.druid.pool.DruidDataSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractDbTypeStrategy implements DbTypeStategy {
    protected Properties dbParams;
    protected Properties poolParams;

    public AbstractDbTypeStrategy(@Nullable Properties dbParams, @Nullable Properties poolParams) {
        this.dbParams = dbParams;
        this.poolParams = poolParams;
    }

    protected String getUrl(String urlPattern, String urlSchemaPattern, DbConnection conInfo) {
        String url;
        if (Strings.isNullOrEmpty(conInfo.getSchema())) {
            url = String.format(urlPattern, checkNotNull(conInfo.getHost()),
                    checkNotNull(conInfo.getPort()));
        } else {
            url = String.format(urlSchemaPattern, checkNotNull(conInfo.getHost()),
                    checkNotNull(conInfo.getPort()), checkNotNull(conInfo.getSchema()));
        }
        return url;
    }

    protected Connection getConnection(String urlPattern, String urlSchemaPattern,
                                       DbConnection conInfo) throws SQLException {
        return DriverManager.getConnection(getUrl(urlPattern, urlSchemaPattern, conInfo),
                conInfo.getUsername(), conInfo.getPwd());
    }

    protected DruidDataSource getDataSource(String url, DbConnection conInfo) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(conInfo.getUsername());
        druidDataSource.setPassword(conInfo.getPwd());
        if (dbParams != null) {
            druidDataSource.setConnectProperties(dbParams);
        }
        if (poolParams != null) {
            for (Entry<Object, Object> entry : poolParams.entrySet()) {
                for (Method method : DruidDataSource.class.getMethods()) {
                    if (method.getName().equalsIgnoreCase("set" + entry.getKey().toString())) {
                        try {
                            method.invoke(druidDataSource, entry.getValue());
                        } catch (IllegalAccessException | IllegalArgumentException
                                | InvocationTargetException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        break;
                    }
                }
            }
        /*
         * // 若不设置，则默认是8 druidDataSource.setMaxActive(256); druidDataSource.setMaxWait(30000);
         * druidDataSource.setUseUnfairLock(true); druidDataSource.setValidationQuery("select 1");
         * // 避免出现压力下访问到已关闭连接 druidDataSource.setTestOnBorrow(true);
         * druidDataSource.setTestWhileIdle(true);
         * druidDataSource.setTimeBetweenEvictionRunsMillis(500);
         * druidDataSource.setPoolPreparedStatements(true);
         * druidDataSource.setRemoveAbandoned(true); druidDataSource.setRemoveAbandonedTimeout(600);
         * druidDataSource.setLogAbandoned(true);
         */
        }
        return druidDataSource;
    }


}
