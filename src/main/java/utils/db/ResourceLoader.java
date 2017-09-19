package utils.db;

import com.google.common.collect.Maps;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.Map;
import java.util.Properties;

import utils.cache.DbLoadingCache;

import static utils.db.DbUtils.initProp;

public class ResourceLoader {
    public static final String PARAM_CNF_NAME = "param";
    public static final String SYS_CNF_NAME = "dump";
    public static final String MYSQL_JDBC_CNF_NAME = "mysqlJdbc";
    public static final String JDBC_CNF_NAME = "jdbc";
    public static final String DRUID_CNF_NAME = "druid";
    public static final Config system = ConfigFactory.load(SYS_CNF_NAME).getConfig("dump");
    public static final Config cmd = ConfigFactory.load(PARAM_CNF_NAME);
    //jdbc set Prop should trans num to str
    public static final Properties DEFAULT_MYSQL_PROPS =
            initProp(ConfigFactory.load(MYSQL_JDBC_CNF_NAME).getConfig("jdbc"), true);
    //druid reflect should not trans num to str
    public static final Properties DEFAULT_DRUID_PROPS = initProp(ConfigFactory.load(DRUID_CNF_NAME).getConfig("druid"), false);
    public static final Map<DbConnection.DbType, DbTypeStategy> dbStategies = Maps.newHashMap();
    public static DbLoadingCache dbLoadingCache;

    static {
        dbStategies.put(DbConnection.DbType.MYSQL, new MysqlStrategy(DEFAULT_MYSQL_PROPS, DEFAULT_DRUID_PROPS));
        dbLoadingCache = new DbLoadingCache(dbStategies);
    }
}