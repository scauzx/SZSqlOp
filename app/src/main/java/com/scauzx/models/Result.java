package com.scauzx.models;

/**
 *
 * @author scauzx
 * @date 2017/12/6
 */

public class Result {
    public static int CODE_SUCCESS = 0;
    public static int CODE_ERROR_EXIST = 1;
    public static int CODE_ERROR_NOT_EXIST = 2;
    public static int CODE_ERROR_UNKNOWN = 3;

    public User user;
    public int code;

    public void setSuccess(User user) {
        this.user = user;
        this.code = CODE_SUCCESS;
    }

}
