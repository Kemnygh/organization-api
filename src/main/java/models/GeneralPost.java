package models;

public class GeneralPost extends Post {
    public static final String DATABASE_TYPE = "general";

    public GeneralPost(String title, String content, int userId){
        this.title = title;
        this.content = content;
        this.user_id = userId;
        this.type = DATABASE_TYPE;
    }
}
