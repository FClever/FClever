package cn.hnhczn.app.mvp.model.entity;

import java.util.Date;

/**
 * Created by FClever on 2018/8/10.
 */
public class UserBean {
	private Integer id;
	private String loginName;
	private String userName;
	private Byte sex;
	private String photo;
	private String telephone;
	private String mobilephone;
	private String mailbox;
	private Byte status;
	private Byte isDelete;
	private Date regDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Byte isDelete) {
		this.isDelete = isDelete;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", loginName='" + loginName + '\'' +
				", userName='" + userName + '\'' +
				", sex=" + sex +
				", photo='" + photo + '\'' +
				", telephone='" + telephone + '\'' +
				", mobilephone='" + mobilephone + '\'' +
				", mailbox='" + mailbox + '\'' +
				", status=" + status +
				", isDelete=" + isDelete +
				", regDate=" + regDate +
				'}';
	}
}
