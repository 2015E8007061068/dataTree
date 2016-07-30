package rscloudenviroment;

public class InstanceFactory {

	public static VEInstance creator(String which){
		if (which == "RSVE")
			return new RSVEInstance();
		return null;
	}
}
