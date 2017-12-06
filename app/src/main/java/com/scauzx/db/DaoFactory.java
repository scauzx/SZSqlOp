package com.scauzx.db;

/**
 *如果还有其他数据库操作类在这里还可以进行其他操作
 * @author Administrator
 * @date 2017/12/6
 */

public class DaoFactory {

    public static UserDaoImp getUserDaoInstance() {
        return UserDaoImp.getInstance();
    }

}
