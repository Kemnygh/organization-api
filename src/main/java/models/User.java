package models;

import java.sql.Timestamp;
import java.util.Objects;

public class User {
    private String firstName;
    private String lastName;
    private String staffId;
//    private int departmentId;
    private String position;
    private String phoneNo;
    private String email;
    private long created;
    private long updated;
    private String deleted;
    private int id;
    private String photo;

    public User(String firstName, String lastName, String staffId, String position, String phoneNo, String email, String photo){
        this.firstName = firstName;
        this.lastName = lastName;
        this.staffId = staffId;
        this.position = position;
        this.phoneNo = phoneNo;
        this.email = email;
//        this.departmentId = departmentId;
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getCreated() == user.getCreated() && getUpdated() == user.getUpdated() && getId() == user.getId() && getFirstName().equals(user.getFirstName()) && getLastName().equals(user.getLastName()) && getStaffId().equals(user.getStaffId()) && getPosition().equals(user.getPosition()) && getPhoneNo().equals(user.getPhoneNo()) && getEmail().equals(user.getEmail()) && getDeleted().equals(user.getDeleted()) && getPhoto().equals(user.getPhoto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getStaffId(), getPosition(), getPhoneNo(), getEmail(), getCreated(), getUpdated(), getDeleted(), getId(), getPhoto());
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStaffId() {
        return staffId;
    }

//    public int getDepartmentId() {
//        return departmentId;
//    }

    public String getPosition() {
        return position;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public long getCreated() {
        return created;
    }

    public long getUpdated() {
        return updated;
    }

    public String getDeleted() {
        return deleted;
    }

    public int getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setCreated() {
        this.created = System.currentTimeMillis();
    }

    public void setUpdated() {
        this.updated = System.currentTimeMillis();
    }
}
