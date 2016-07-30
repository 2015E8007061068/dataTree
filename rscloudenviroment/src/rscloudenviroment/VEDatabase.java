package rscloudenviroment;

public interface VEDatabase {

	public abstract void usedatabase();
	
	public abstract String delete();
	public abstract String update();
	public abstract String select();
	public abstract String show();
	String insert(String name, String url);
	
}
