package oop.trafficproject.AdministrativeOfficer;

import oop.trafficproject.User;

import java.time.LocalDate;

public class AdministrativeOfficer extends User {
    private final int empId;
    private String department;
    private final LocalDate doj;

    public AdministrativeOfficer(String gender, LocalDate dob, String name, String email, String phone, String password, int empId, String department, LocalDate doj) {
        super (empId, gender, dob);
        this.empId = empId;
        this.department = department;
        this.doj = doj;
    }

    public int getEmployeeId() {
        return empId;
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
                "employeeId=" + empId +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", doj=" + doj +
                ", designation='" + designation + '\'' +
                '}';
    }
}
