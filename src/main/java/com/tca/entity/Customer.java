package com.tca.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {
	@Column(name = "full_name", nullable = false, length = 100)
    private String fullName;
	
	 @Column(name = "phone", nullable = false, length = 10)
	    private String phone;
	 
	    @Column(name = "profile_image")
	    private String profileImage;
	
		@Column(name="loyalty_points")
		private Integer loyaltyPoints;
		
		@OneToMany(mappedBy="customer",fetch=FetchType.EAGER)
		private List<Address> addresses;

		private Cart cart;
         
		@OneToMany(mappedBy="customer",fetch=FetchType.EAGER)
		private List<Order> orders;
		
		public Customer() {}

		public Customer(String fullName, String phone, String profileImage, Integer loyaltyPoints,
				List<Address> addresses, Cart cart, List<Order> orders) {
			super();
			this.fullName = fullName;
			this.phone = phone;
			this.profileImage = profileImage;
			this.loyaltyPoints = loyaltyPoints;
			this.addresses = addresses;
			this.cart = cart;
			this.orders = orders;
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

		public Integer getLoyaltyPoints() {
			return loyaltyPoints;
		}

		public void setLoyaltyPoints(Integer loyaltyPoints) {
			this.loyaltyPoints = loyaltyPoints;
		}

		public List<Address> getAddresses() {
			return addresses;
		}

		public void setAddresses(List<Address> addresses) {
			this.addresses = addresses;
		}

		public Cart getCart() {
			return cart;
		}

		public void setCart(Cart cart) {
			this.cart = cart;
		}

		public List<Order> getOrders() {
			return orders;
		}

		public void setOrders(List<Order> orders) {
			this.orders = orders;
		}

		@Override
		public String toString() {
			return "Customer [fullName=" + fullName + ", phone=" + phone + ", profileImage=" + profileImage
					+ ", loyaltyPoints=" + loyaltyPoints + ", addresses=" + addresses + ", cart=" + cart + ", orders="
					+ orders + "]";
		}
		
		
		
}
