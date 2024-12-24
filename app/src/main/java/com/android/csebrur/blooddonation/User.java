package com.android.csebrur.blooddonation;

public class User {
    String Name, Address, BloodGroup, DateOfBirth, LastDonationDate, Phone, Email;

    private User(){}

    public User(String name, String address, String bloodGroup, String dateOfBirth, String lastDonationDate, String phone, String email) {
        this.Name = name;
        this.Address = address;
        this.BloodGroup = bloodGroup;
        this.DateOfBirth = dateOfBirth;
        this.LastDonationDate = lastDonationDate;
        this.Phone = phone;
        this.Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getLastDonationDate() {
        return LastDonationDate;
    }

    public void setLastDonationDate(String lastDonationDate) {
        LastDonationDate = lastDonationDate;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}