package com.tca.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.tca.enums.AddressType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="addresses")
public class Address {
       
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long addressId;
	
	@Column(name="address_type",nullable=false)
	@Enumerated(EnumType.STRING)
	private AddressType addressType;
	
	@Column(name="receiver_name",nullable=false, length=50)
	private String receiverName;
	
	@Column(name="city" ,nullable = false,length=50)
	private String city;
	
	@Column(name="address_line1",nullable=false,length=255)
	private String addressLine1;
    
	@Column(name="address_line2",length=150)
	private String addressLine2;
	
	@Column(name="phone",length=10,nullable=false)
	private String phone;
	
	@Column(name="state",length=50,nullable=false)
    private String state;
    
	@Column(name="country" ,length = 50,nullable=false)
    private String country;
    
	@Column(name="postal_code",nullable=false,length=10)
    private String postalCode;
    
	@Column(name="is_default")
    private Boolean isDefault=false;
    
	@CreationTimestamp
	@Column(name="created_at",nullable=false,updatable=false)
    private LocalDateTime createdAt;
    
	@UpdateTimestamp
	@Column(name="updated_at",updatable=false)
	private LocalDateTime updatedAt;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="customer_id")
	private Customer customer;
    
    	public Address() {}

		public Address(AddressType addresstype, String receiverName, String city, String addressLine1,
				String addressLine2, String phone, String state, String country, String postalCode, Boolean isDefault,
				Customer customer) {
			super();
			this.addressType = addresstype;
			this.receiverName = receiverName;
			this.city = city;
			this.addressLine1 = addressLine1;
			this.addressLine2 = addressLine2;
			this.phone = phone;
			this.state = state;
			this.country = country;
			this.postalCode = postalCode;
			this.isDefault = isDefault;
			this.customer = customer;
		}

		public AddressType getAddressType() {
			return addressType;
		}

		public void setAddresstype(AddressType addressType) {
			this.addressType = addressType;
		}

		public String getReceiverName() {
			return receiverName;
		}

		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getAddressLine1() {
			return addressLine1;
		}

		public void setAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
		}

		public String getAddressLine2() {
			return addressLine2;
		}

		public void setAddressLine2(String addressLine2) {
			this.addressLine2 = addressLine2;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(String postalCode) {
			this.postalCode = postalCode;
		}

		public Boolean getIsDefault() {
			return isDefault;
		}

		public void setIsDefault(Boolean isDefault) {
			this.isDefault = isDefault;
		}

		public Customer getCustomer() {
			return customer;
		}

		public void setCustomer(Customer customer) {
			this.customer = customer;
		}

		@Override
		public String toString() {
			return "Address [addressId=" + addressId + ", addressType=" + addressType + ", receiverName=" + receiverName
					+ ", city=" + city + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2
					+ ", phone=" + phone + ", state=" + state + ", country=" + country + ", postalCode=" + postalCode
					+ ", isDefault=" + isDefault + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
		}
    	
        	
}
