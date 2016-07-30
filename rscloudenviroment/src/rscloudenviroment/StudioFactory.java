package rscloudenviroment;

public class StudioFactory {

	public static VEStudio creator(String which){
		if (which == "RSVE")
			return new RSVEStudio();
		return null;
	}
}
