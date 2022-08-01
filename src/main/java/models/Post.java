package models;

import java.util.Objects;

public class Post {
    private String title;
    private String content;
    private int user_id;
    private int department_id;
    private String type;
    private long created;
    private long updated;
    private int id;
    private String deleted;

    public Post(String title, String content, String type, int userId, int departmentId){
        this.title = title;
        this.content = content;
        this.user_id = userId;
        this.department_id = departmentId;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return getUserId() == post.getUserId() && getDepartmentId() == post.getDepartmentId() && getCreated() == post.getCreated() && getUpdated() == post.getUpdated() && getId() == post.getId() && getTitle().equals(post.getTitle()) && getContent().equals(post.getContent()) && getType().equals(post.getType()) && getDeleted().equals(post.getDeleted());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getContent(), getUserId(), getDepartmentId(), getType(), getCreated(), getUpdated(), getId(), getDeleted());
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
        return user_id;
    }

    public int getDepartmentId() {
        return department_id;
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

    public String getType() {
        return type;
    }
}
