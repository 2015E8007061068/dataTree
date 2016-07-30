package rscloudenviroment;

public class RSVETRequest implements VERequest {

	public String VEName;
	public String VEImage;
	
	@Override
	public void setVEName(String vename) {
		this.VEName = vename;
		// TODO Auto-generated method stub

	}

	@Override
	public String getVEName() {
		// TODO Auto-generated method stub
		return VEName;
	}

	@Override
	public void setVEImage(String veimage) {
		this.VEImage = veimage;
		// TODO Auto-generated method stub

	}

	@Override
	public String getVEImage() {
		// TODO Auto-generated method stub
		return VEImage;
		
	}

	@Override
	public String toXML() {
		// TODO Auto-generated method stub
		return null;

	}

}
