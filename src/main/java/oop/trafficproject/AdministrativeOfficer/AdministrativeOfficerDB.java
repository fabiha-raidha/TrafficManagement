package oop.trafficproject.AdministrativeOfficer;

import oop.trafficproject.User;

import java.time.LocalDate;

public class AdministrativeOfficerDB extends User {
    private int employeeId;
    private String department;
    private LocalDate doj;

    public AdministrativeOfficerDB(String gender, LocalDate dob, String name, String email, String phone, String password, int employeeId, String department, LocalDate doj) {
        super (employeeId, gender, dob);
        this.employeeId = employeeId;
        this.department = department;
        this.doj = doj;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public LocalDate getDoj(){
        return doj;
    }

    public void sendProfileStatusRequest(){

    }

    public void assignZone(User officer, String zone) {
        officer.setAssignedZone(zone);
        System.out.println("Assigned zone" + zone + "to" + officer.getEmpId());
    }

    @Override
    public boolean updateProfile() {
        System.out.println("Administrative profile is updated.");
        return true;
    }

    @Override
    public String toString() {
        return "AdministrativeOfficerDB{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", doj=" + doj +
                ", designation='" + designation + '\'' +
                '}';
    }
}
