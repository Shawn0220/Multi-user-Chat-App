package Model;

public class messageModel{
    private String userID;
    private String user;
    private String time;
    private String content;
    private String gid;

    public messageModel(String userID, String user, String time, String content, String gid) {
        this.userID = userID;
        this.user = user;
        this.time = time;
        this.content = content;
        this.gid = gid;
    }

    public String getUserID(){
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getGid(){ return gid;}
    public void setGid(String gid){this.gid = gid;}


    @Override
    public String toString() {
        return "Message{" + "userID" + userID +
                "user='" + user + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}