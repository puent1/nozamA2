package uiuc.cs411.nozama.listing;

public abstract class Post {
	public String id;
	public String title;
	public String body;
	public String user;
	public String pic;

	
	public abstract void delete();
	
	public abstract void edit();
}
