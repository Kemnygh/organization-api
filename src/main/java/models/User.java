package models;

import java.util.Objects;

public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String staff_id;
    private String user_position;
    private String phone_no;
    private String email;
    private String photo;
    private long created;
    private long updated;
    private String deleted;

    public User(String firstName, String lastName, String staffId, String position, String phoneNo, String email, String photo){
        this.first_name = firstName;
        this.last_name = lastName;
        this.staff_id = staffId;
        this.user_position = position;
        this.phone_no = phoneNo;
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && getCreated() == user.getCreated() && getUpdated() == user.getUpdated() && getFirst_name().equals(user.getFirst_name()) && getLast_name().equals(user.getLast_name()) && getStaff_id().equals(user.getStaff_id()) && getUser_position().equals(user.getUser_position()) && getPhone_no().equals(user.getPhone_no()) && getEmail().equals(user.getEmail()) && getPhoto().equals(user.getPhoto()) && getDeleted().equals(user.getDeleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirst_name(), getLast_name(), getStaff_id(), getUser_position(), getPhone_no(), getEmail(), getPhoto(), getCreated(), getUpdated(), getDeleted());
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public String getUser_position() {
        return user_position;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoto() {
        return photo;
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
}
