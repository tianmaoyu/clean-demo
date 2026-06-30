package com.example.ddd.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 收货地址值对象 - 体现DDD值对象概念
 * 不可变对象，无标识
 */
@Getter
@NoArgsConstructor
public class ShippingAddress {
    
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public ShippingAddress(String street, String city, String state, String zipCode, String country) {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street is required");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        if (zipCode == null || zipCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Zip code is required");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country is required");
        }
        
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShippingAddress that = (ShippingAddress) o;
        return street.equals(that.street) &&
               city.equals(that.city) &&
               zipCode.equals(that.zipCode) &&
               country.equals(that.country);
    }

    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + zipCode.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %s, %s", street, city, state, zipCode, country);
    }
}
