package models;

public class DepartmentalPost extends Post {
    public static final String DATABASE_TYPE = "departmental";

    public DepartmentalPost(String title, String content, int userId, int departmentId){
        this.title = title;
        this.content = content;
        this.user_id = userId;
        this.department_id = departmentId;
        this.type = DATABASE_TYPE;
    }

    public String getType() {
        return type;
    }

    public void setType() {
        this.type = "departmental";
    }
}
