package oop.trafficproject;

import java.time.LocalDate;
import java.io.Serializable;

public abstract class User implements Serializable{
    private static final long   serialVersionUID = 1L;

    protected final int empId;
    protected String name, address, email, phone;
    protected final String gender;
    protected final LocalDate dob;
    protected String designation;
    protected String password;
    private String assignedZone;
    private String department;
    private String status;

    public User(int empId, String department, String name, String address, String email, String phone, String gender, LocalDate dob, String designation, String password, String assignedZone, String status) {
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
        this.department = department;
        this.status = status;
    }

    public User(int empId, String gender, LocalDate dob, int empId1, String gender1, LocalDate dob1) {
        this.empId = empId1;
        this.gender = gender1;
        this.dob = dob1;
    }

    public User(int empId, String gender, LocalDate dob) {
        this.empId = 0;
        this.gender = "TBA";
        this.dob = null;
        status = "Active";
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEmpId() {
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
