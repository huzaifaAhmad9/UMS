package com.example.project.modals;



public class Teacher {
    public String id;

    public String name;
    public String contact;
    public String qualification;
    public String city;
    public String department;

    public Teacher(){
        // for firebase
    }
    public Teacher(String id, String name, String contact, String qualification, String city, String department) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.qualification = qualification;
        this.city = city;
        this.department = department;

    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getQualification() {
        return qualification;
    }

    public String getCity() {
        return city;
    }

    public String getDepartment() {
        return department;
    }
}


