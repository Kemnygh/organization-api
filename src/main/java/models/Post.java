package models;

import java.sql.Timestamp;
import java.util.Objects;

public class Post {
    private String title;
    private String content;
    private int userId;
    private int departmentId;
    private long created;
    private long updated;
    private int id;
    private String deleted;

    public Post(String title, String content, int userId, int departmentId){
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return getUserId() == post.getUserId() && getDepartmentId() == post.getDepartmentId() && getCreated() == post.getCreated() && getUpdated() == post.getUpdated() && getId() == post.getId() && getTitle().equals(post.getTitle()) && getContent().equals(post.getContent()) && getDeleted().equals(post.getDeleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContent(), getUserId(), getDepartmentId(), getCreated(), getUpdated(), getId(), getDeleted());
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getUserId() {
        return userId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public long getCreated() {
        return created;
    }

    public long getUpdated() {
        return updated;
    }

    public int getId() {
        return id;
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
