package com.example.abdallahmohammed.indextask1.model;

/**
 * Created by Abdallah Mohammed on 12/4/2017.
 */

public class ClientInformation {
    private String id ;
    private String name ;
    private String city ;
    private String country ;
    private String mobile ;
    private String address ;
    private String token ;

    public ClientInformation(String id, String name, String city, String country, String mobile, String address, String token) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.mobile = mobile;
        this.address = address;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
