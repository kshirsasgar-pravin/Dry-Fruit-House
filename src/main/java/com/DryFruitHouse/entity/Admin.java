package com.DryFruitHouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="admins")
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {
	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName;

	@Column(name = "phone", nullable = false, length = 10)
	private String phone;

	@Column(name="profile_image")
	private String profileImage;
	
	public Admin() {}

	public Admin(String fullName, String phone, String profileImage) {
		super();
		this.fullName = fullName;
		this.phone = phone;
		this.profileImage = profileImage;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "Admin [fullName=" + fullName + ", phone=" + phone + ", profileImage=" + profileImage + "]";
	}
	
	
	

}
