package utils.db;

import com.alibaba.druid.pool.DruidDataSource;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public interface DbTypeStategy {
    public DruidDataSource getDatasource(DbConnection conInfo) throws SQLException;

    public Connection getConnection(DbConnection conInfo) throws SQLException;

    public QueryRunner initStreamQueryRunner(Set<Throwable> errors);
}
