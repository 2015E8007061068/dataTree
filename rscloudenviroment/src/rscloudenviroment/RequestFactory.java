package rscloudenviroment;

public class RequestFactory {

	public static VERequest creator(String which){
		if (which == "RSVE")
			return new RSVERequest();
		if (which == "RSVET")
			return new RSVETRequest();
		return null;
	}
	
}
