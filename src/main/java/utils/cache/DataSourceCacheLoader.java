package utils.cache;

import com.google.common.cache.CacheLoader;

import com.alibaba.druid.pool.DruidDataSource;

import java.util.Map;

import javax.annotation.Nonnull;

import lombok.RequiredArgsConstructor;
import utils.db.DbConnection;
import utils.db.DbTypeStategy;
import utils.db.DbUtils;

@RequiredArgsConstructor
class DataSourceCacheLoader extends CacheLoader<DbConnection, DruidDataSource> {
    private final Map<DbConnection.DbType, DbTypeStategy> dbStategies;

    @Override
    public DruidDataSource load(@Nonnull DbConnection key) throws Exception {
        return DbUtils.getDataSource(key, dbStategies);
    }
}
