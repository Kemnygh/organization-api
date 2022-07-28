package models;

import java.sql.Timestamp;
import java.util.Objects;

public class Department {
    private String name;
    private String description;
    private int id;
    private long created;
    private long updated;
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
        return getId() == that.getId() && getCreated() == that.getCreated() && getUpdated() == that.getUpdated() && getName().equals(that.getName()) && getDescription().equals(that.getDescription()) && getDeleted().equals(that.getDeleted());
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

    public long getCreated() {
        return created;
    }

    public long getUpdated() {
        return updated;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setCreated() {
        this.created = System.currentTimeMillis();
    }

    public void setUpdated() {
        this.updated = System.currentTimeMillis();
    }
}
