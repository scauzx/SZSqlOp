package com.scauzx.models;

/**
 *
 * @author sacuzx
 * @date 2017/12/5
 */

public class User {
    public int id;
    public String username;
    public String password;

    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User{ id = " + id + " username = " + username + " password = " + password + "}" );
        return builder.toString();
    }
}
