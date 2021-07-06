package com.hcf.nszh.provider.mz.enums;

/**
 * 〈一句话功能简述〉<br>
 * 〈数据库驱动枚举〉
 *
 * @author maruko
 * @date 2021/7/6 16:17
 * @since 1.0.0
 */

public enum JdbcEnum {
    /**
     * MYSQL
     */
    MYSQL("MySQL", "com.mysql.jdbc.Driver"),
    /**
     * ORACLE
     */
    ORACLE("Oracle", "oracle.jdbc.driver.OracleDriver"),
    /**
     * PostgreSQL
     */
    PostgreSQL("PostgreSQL", "org.postgresql.Driver");

    private String type;
    private String driverClass;

    JdbcEnum(String type, String driverClass) {
        this.type = type;
        this.driverClass = driverClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public static String getDriverClassByType(String type) {
        for (JdbcEnum c : JdbcEnum.values()) {
            if (c.getType() == type) {
                return c.driverClass;
            }
        }
        return null;
    }
}
