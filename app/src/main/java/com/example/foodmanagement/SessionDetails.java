package com.example.foodmanagement;

public class SessionDetails {
    private String name;
    private String email;
    private String phone;
    private  String address;
    private  String role;

    public SessionDetails(String name, String email, String phone,String address,String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address= address;
        this.role = role;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
