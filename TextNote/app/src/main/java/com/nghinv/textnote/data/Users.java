package com.nghinv.textnote.data;

/**
 * Created by NghiNV on 11/04/2017.
 */

public class Users {
    public int objectId;
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users() {
    }
}
