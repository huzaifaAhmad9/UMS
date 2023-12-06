/*
package com.example.project;

public class Student {
    private boolean isPresent;

    public String id;

    public String name;
    public String father;
    public String city;
    public String gender;
    public String department;
    private String userId; // Add this field for user ID

    public Student() {
        // Default constructor required for Firebase
    }

    public Student(String id, String name, String father, String city, String gender, String department, String userId) {
        this.id = id;
        this.name = name;
        this.father = father;
        this.city = city;
        this.gender = gender;
        this.userId = userId; // Initialize user ID
        this.department = department;
    }



    public boolean isPresent() {
        return isPresent;
    }
    public void setPresent(boolean present) {
        isPresent = present;
    }


        public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getFather(){
        return father;
    }
    public String getCity(){
        return city;
    }
    public String getGender(){
        return gender;
    }
    public String getDepartment(){
        return department;
    }

}
*/





package com.example.project.modals;
import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private boolean isPresent;

    public String id;
    public String name;
    public String father;
    public String city;
    public String gender;
    public String department;
   // private String userId; // Add this field for user ID


    // Default constructor required for Firebase
    public Student() {
    }

    public Student(String id, String name, String father, String city, String gender, String department) {
        this.id = id;
        this.name = name;
        this.father = father;
        this.city = city;
        this.gender = gender;
       // this.userId = userId; // Initialize user ID
        this.department = department;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }


    // Add getters and setters for the new field (userId)
/*    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFather() {
        return father;
    }

    public String getCity() {
        return city;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }

    // Parcelable implementation
    protected Student(Parcel in) {
        isPresent = in.readByte() != 0;
        id = in.readString();
        name = in.readString();
        father = in.readString();
        city = in.readString();
        gender = in.readString();
        department = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isPresent ? 1 : 0));
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(father);
        dest.writeString(city);
        dest.writeString(gender);
        dest.writeString(department);
    }
}

