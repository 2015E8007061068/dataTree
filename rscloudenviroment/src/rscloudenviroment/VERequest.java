package rscloudenviroment;

import java.io.IOException;

public interface VERequest {

	public abstract void setVEName(String vename);
	public abstract  String getVEName();
	public abstract  void setVEImage(String veimage);
	public abstract  String getVEImage();
	
	public abstract String toXML() throws IOException;
	
}
