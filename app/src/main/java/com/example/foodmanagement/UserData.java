package com.example.foodmanagement;

public class UserData {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private  String role;

    public UserData() {
        // Default constructor required for Firebase
    }

    public UserData(String name, String email, String phoneNumber, String address,String role) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }
}
