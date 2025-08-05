package oop.trafficproject;

import java.time.LocalDate;

public abstract class User {
    protected final int empId;
    protected String name, address, email, phone;
    protected final String gender;
    protected final LocalDate dob;
    protected String designation;
    protected String password;
    private String assignedZone;

    public User() {
        this.empId = 0;
        gender = "TBA";
        dob = null;
    }

    public User(int empId, String name, String address, String email, String phone, String gender, LocalDate dob, String designation, String password, String assignedZone) {
        this.empId = empId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.designation = designation;
        this.password = password;
        this.assignedZone= assignedZone;

    }


    public long getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public boolean changePassword(String oldPassword, String newPassword, String confirmPassword) {
        if (this.password.equals(oldPassword) && newPassword.equals(confirmPassword)) {
            this.password = newPassword;
            return true;
        }
        return false;
    }
    public abstract boolean updateProfile();


    public void setAssignedZone(String zone) {
        this.assignedZone= zone;
    }

    public String getAssignedZone(){
        return assignedZone;
    }
}
