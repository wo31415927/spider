package utils.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.RowDataDynamic;

import org.apache.commons.dbutils.BaseResultSetHandler;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import static utils.db.DbUtils.queryRunner;

@Slf4j
public class MysqlStrategy extends AbstractDbTypeStrategy {
    public static final String mysqlQuote = "`";
    public static final String sqlServerId = "SHOW VARIABLES LIKE 'SERVER_ID'";
    public static final String sqlGtid = "select @@gtid_current_pos";
    public static final String sqlBinlog = "show master status";
    public static final String sqlSlave = "show slave status";
    public static final String sqlVariables = "show variables";
    public static final String sqlCreateTable = "show create table %s";
    public static final String sqlCreateSchema = "show create database %s";
    public static final String sqlShowTables = "show create database %s";
    public static final String renameSql = "%s alter table %s rename %s";
    public static final String clearSql = "truncate %s";
    public static final String countSql = "select count(*) from %s";
    public static final String checkMysqlSql = "select version()";
    public static final String checkDBProxySql = "select version()";
    public static final String useSql = "use %s";
    public static final String lockTable = "lock tables %s read";
    public static final String lockAllRead = "FLUSH TABLES WITH READ LOCK";
    public static final String unlockTable = "unlock tables";
    public static final String waitTimeout = "set wait_timeout=%s";
    public static final String dropTable = "drop table if exists %s";
    public static final String dropSchema = "drop database if exists %s";
    public static final String commonSelect = "select 1 from %s limit 1";
    public static final String HINT = "/* !HINT({\"dn\":[\"%s\"]})*/ %s";
    public static final String sqlModeSql = "set session sql_mode=''";
    public static final String forKeySql = "SET FOREIGN_KEY_CHECKS = 0";
    public static final String uniKeySql = "SET UNIQUE_CHECKS = 0";
    public static final String queryPrivilege = "SELECT %s FROM mysql.user where user = '%s' and host = '%s'";
    public static final String queryTableEngine = "select count(*) from information_schema.`TABLES` where TABLE_SCHEMA = '%s' and `ENGINE` != " +
            "'InnoDB'";
    public static final String queryTablePartitioned = "select count(*) from information_schema.`TABLES` where TABLE_SCHEMA = '%s' and " +
            "`CREATE_OPTIONS` = " +
            "'partitioned'";
    public static final String DBPROXY_SELECT_ITEM = "@@version_compile_os";
    public static final int GTID_RETRY_TIMES = 5;
    public static final long binlogClientTimeoutMs = 600000; // ms
    public final String urlPattern = "jdbc:mysql://%s:%s";
    public final String urlSchemaPattern = "jdbc:mysql://%s:%s/%s";
    public final long GTID_WAIT_INTERVAL = 300; // ms

    public MysqlStrategy(Properties dbProp, Properties poolProp) {
        super(dbProp, poolProp);
    }

    /**
     * 1)正常返回连接 2)不合适返回null 3)运行时异常直接抛出
     */
    @Override
    public Connection getConnection(DbConnection conInfo) throws SQLException {
        Connection con = getConnection(urlPattern, urlSchemaPattern, conInfo);
        if (null != conInfo.getDbType()) {
            return con;
        }
        try {
            queryRunner.query(con, checkMysqlSql, new BaseResultSetHandler<Object>() {
                        @Override
                        protected Object handle() throws SQLException {
                            return null;
                        }
                    }
            );
            conInfo.setDbType(DbConnection.DbType.MYSQL);
            return con;
        } catch (SQLException e) {
            if (e.getMessage().toLowerCase().contains("udal")) {
                conInfo.setDbType(DbConnection.DbType.DBPROXY);
                return con;
            } else {
                org.apache.commons.dbutils.DbUtils.close(con);
                throw e;
            }
        }
    }

    @Override
    public DruidDataSource getDatasource(DbConnection conInfo) {
        return getDataSource(getUrl(urlPattern, urlSchemaPattern, conInfo), conInfo);
    }

    @Override
    public QueryRunner initStreamQueryRunner(final Set<Throwable> errors) {
        return new QueryRunner() {
            @Override
            protected PreparedStatement prepareStatement(Connection conn, String sql)
                    throws SQLException {
                PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
                stmt.setFetchSize(Integer.MIN_VALUE);
                // ((StatementImpl) stmt).enableStreamingResults();
                return stmt;
            }

            @Override
            protected void close(ResultSet rs) throws SQLException {
                if (errors.size() > 0 && rs instanceof RowDataDynamic) {
                    log.warn(
                            "Found errors during RowDataDynamic loop,Omit close this ResultSet!");
                } else {
                    org.apache.commons.dbutils.DbUtils.close(rs);
                }
            }
        };
    }
}
