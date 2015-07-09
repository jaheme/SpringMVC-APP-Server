package com.tony.core.db;

/**
 * jdbc操作结果描述
 * @author jahe.me
 *
 */
public class DbResult {

    public boolean success = false;
    /**执行成功与否*/
    public boolean result = false;

    /**执行消息,数据库异常会显示在此.*/
    public String execMsg = "";

    public static DbResult valueOf(boolean result) {
        DbResult r = new DbResult();
        r.success = r.result = result;
        return r;
    }
    public static DbResult valueOf(boolean result, String execMsg) {
        DbResult r = new DbResult();
        r.success = r.result = result;
        r.execMsg = execMsg;
        return r;
    }

}