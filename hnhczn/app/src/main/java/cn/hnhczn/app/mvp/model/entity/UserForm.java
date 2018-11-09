package cn.hnhczn.app.mvp.model.entity;

/**
 * Created by FClever on 2018/8/10.
 * 用户登录提交的表单
 */
public class UserForm {

    private String username;
    private String password;
    private Boolean remember;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }
}
