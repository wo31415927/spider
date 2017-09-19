package utils.db;

public class DbConnection extends ConnectionInfo {
    protected String schema;
    protected DbType dbType;

    public DbConnection() {
        super();
    }

    public DbConnection(String host, int port, String user, String pwd) {
        super(host, port, user, pwd);
    }

    public DbConnection(String host, int port, String user, String pwd, String schema) {
        super(host, port, user, pwd);
        this.schema = schema;
    }

    public DbConnection(DbConnection con) {
        super(con.getHost(), con.getPort(), con.getUsername(), con.getPwd());
        schema = con.getSchema();
        dbType = con.getDbType();
    }

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }

    public String getSchema() {
        return null == schema ? null : schema.trim();
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public boolean isOracle() {
        return dbType.equals(DbType.ORACLE);
    }

    public boolean isDBProxy() {
        return dbType.equals(DbType.DBPROXY);
    }

    public boolean isMysql() {
        return dbType.equals(DbType.MYSQL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DbConnection)) return false;
        if (!super.equals(o)) return false;
        DbConnection that = (DbConnection) o;
        return schema != null ? schema.equals(that.schema) : that.schema == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (schema != null ? schema.hashCode() : 0);
        return result;
    }

    @Override
    public String toOrginString() {
        return super.toOrginString();
    }

    @Override
    public String toString() {
        return "DbConnection [schema=" + schema + ", dbType=" + dbType + ", host=" + host
                + ", port=" + port + ", username=" + username + "]";
    }

    public static enum DbType {
        NONE, MYSQL, ORACLE, DBPROXY
    }
}
