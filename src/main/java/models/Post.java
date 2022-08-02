package models;

import java.util.Objects;

public abstract class Post {
    public String title;
    public String content;
    public int user_id;
    public int department_id;
    public String type;
    public long created;
    public long updated;
    public int id;
    public String deleted;


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


    public String getType() {
        return type;
    }
}
