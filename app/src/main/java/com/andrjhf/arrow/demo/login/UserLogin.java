package com.andrjhf.arrow.demo.login;

/**
 * Created by jiahongfei on 2017/11/24.
 */

public class UserLogin {


    /**
     * phone : 18701680736
     * code : password
     */

    private String phone;
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "phone='" + phone + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
