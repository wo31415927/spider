package utils.db;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DbUtils {
    public static final QueryRunner queryRunner = new QueryRunner() {
        @Override
        protected void rethrow(SQLException cause, String sql, Object... params)
                throws SQLException {
            String causeMessage = cause.getMessage();
            if (causeMessage == null) {
                causeMessage = "";
            }
            StringBuffer msg = new StringBuffer(causeMessage);
            msg.append(" Query: ");
            msg.append(sql);
            msg.append(" Parameters: ");
            // 避免Object过大时造成oom
            msg.append("[]");
            SQLException e =
                    new SQLException(msg.toString(), cause.getSQLState(), cause.getErrorCode());
            e.setNextException(cause);
//            logger.error("", e);
            throw e;
        }
    };
    public static final List<String> jdbcOrder = Lists.newArrayList("oracle", "mysql", "dbproxy");

    public static Properties initProp(Config jdbc, boolean needTransToStr) {
        Properties prop = new Properties();
        for (Entry<String, ConfigValue> entry : jdbc.entrySet()) {
            Object val = entry.getValue().unwrapped();
            prop.put(entry.getKey(), needTransToStr ? val.toString() : val);
        }
        return prop;
    }

    public static Connection getConnection(DataSource dataSource, String schema)
            throws SQLException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(schema), "schema is null or empty!");
        Connection con = dataSource.getConnection();
        queryRunner.update(con, "use " + schema); // 不会关闭con
        return con;
    }

    public static Connection getConnection(DbConnection conInfo, Map<DbConnection.DbType, DbTypeStategy> dbStategies) throws SQLException {
        if (null != conInfo.getDbType()) {
            return dbStategies.get(conInfo.getDbType()).getConnection(conInfo);
        }
        for (String strategyName : jdbcOrder) {
            DbTypeStategy dbStategy = dbStategies.get(DbConnection.DbType.valueOf(strategyName.toUpperCase()));
            if (dbStategy != null) {
                Connection con = dbStategy.getConnection(conInfo);
                if (null != con) {
                    return con;
                }
            }
        }
        throw new RuntimeException("Can't find appropriate Database for ConnectionInfo:" + conInfo);
    }

    /**
     * 1)探测数据库时发生探测异常 2)未找到合适的数据库
     */
    public static DruidDataSource getDataSource(DbConnection conInfo, Map<DbConnection.DbType, DbTypeStategy> dbStategies) throws SQLException {
        if (null != conInfo.getDbType()) {
            return dbStategies.get(conInfo.getDbType()).getDatasource(conInfo);
        }
        for (String strategyName : jdbcOrder) {
            DbTypeStategy dbStategy = dbStategies.get(DbConnection.DbType.valueOf(strategyName.toUpperCase()));
            if (dbStategy != null) {
                Connection con = dbStategy.getConnection(conInfo);
                if (null != con) {
                    con.close();
                    try {
                        return dbStategies.get(conInfo.getDbType()).getDatasource(conInfo);
                    } catch (Exception e) {
                        log.error("ConInfo: " + conInfo.toString());
                        throw e;
                    }
                }
            }
        }
        throw new RuntimeException("Can't find appropriate Database for ConnectionInfo:" + conInfo);
    }

    public static String asSql(PreparedStatement ps) throws SQLException {
        if (ps instanceof com.mysql.jdbc.PreparedStatement) {
            return ((com.mysql.jdbc.PreparedStatement) ps).asSql();
        } else if (ps instanceof DruidPooledPreparedStatement) {
            return asSql(((DruidPooledPreparedStatement) ps).getRawPreparedStatement());
        }
        throw new SQLException("Illeg PreparedStatement Trans Usage!");
    }
}
