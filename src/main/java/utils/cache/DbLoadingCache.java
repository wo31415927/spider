package utils.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import com.alibaba.druid.pool.DruidDataSource;

import java.util.Map;

import javax.sql.DataSource;

import utils.db.DbConnection;
import utils.db.DbTypeStategy;

/**
 * zeyu
 * 2016/11/17
 */
public class DbLoadingCache {
    private final Map<DbConnection.DbType, DbTypeStategy> dbStategies;
    private LoadingCache<DbConnection, DruidDataSource> dbSourceCache;

    public DbLoadingCache(Map<DbConnection.DbType, DbTypeStategy> dbStategies) {
        this.dbStategies = dbStategies;
        this.dbSourceCache = CacheBuilder.newBuilder().build(new DataSourceCacheLoader(dbStategies));
    }

    public DruidDataSource getUnchecked(DbConnection dbCon) {
        DruidDataSource dataSource = dbSourceCache.getUnchecked(dbCon);
        if (null == dbCon.getDbType()) {
            for (DbConnection conInfo : dbSourceCache.asMap().keySet()) {
                if (conInfo.equals(dbCon)) {
                    dbCon.setDbType(conInfo.getDbType());
                }
            }
        }
        return dataSource;
    }

    public void clear() {
        for (DataSource dataSource : dbSourceCache.asMap().values()) {
            if (dataSource instanceof DruidDataSource) {
                DruidDataSource druidDataSource = (DruidDataSource) dataSource;
                druidDataSource.close();
            }
        }
    }
}
