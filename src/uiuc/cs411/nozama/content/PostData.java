package uiuc.cs411.nozama.content;

import org.json.JSONObject;

public class PostData {
    private String title;
    private String tag;
    private String keyword;
    private String body;
    private String msg;
    private String user;
    private boolean success;


    public String getTitle() { return title; }
    public String getTag() { return tag; }
    public String getKeyword() { return keyword; }
    public String getBody() { return body; }
    public String getMsg() { return msg; }
    public String getUser() { return user; }
    public boolean getSuccess() { return success; }

    public void setTitle(String title) { this.title = title; }
    public void setTag(String tag) { this.tag = tag; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public void setBody(String body) { this.body = body; }
    public void setMsg(String msg) { this.msg = msg; }
    public void setSuccess(boolean success) { this.success = success; }

    public PostData(String title, String body) {
    	this.title = title;
    	this.body = body;
    }
    
    public PostData(JSONObject json) {
    	// TODO: Do parsing here
    }
    
    public String toString() {
        return String.format("title: %s, body: %s", title, body);
    }
}