package models;

import java.sql.Timestamp;
import java.util.Objects;

public class Department {
    private String name;
    private String description;
    private int id;
    private Timestamp created;
    private Timestamp updated;
    private String deleted;

    public Department(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return getId() == that.getId() && getName().equals(that.getName()) && getDescription().equals(that.getDescription()) && getCreated().equals(that.getCreated()) && Objects.equals(getUpdated(), that.getUpdated()) && getDeleted().equals(that.getDeleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getId(), getCreated(), getUpdated(), getDeleted());
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public String getDeleted() {
        return deleted;
    }
}
