package location_gps;

public class LOCATION {

	private String Social;
	private String District;
	private String Province;

	public LOCATION() {
		this.setSocial("");
		this.setDistrict("");
		this.setProvince("");

	}

	public String getSocial() {
		return Social;
	}

	public void setSocial(String Social) {
		this.Social = Social;
	}

	public String getDistrict() {
		return District;
	}

	public void setDistrict(String District) {
		this.District = District;
	}

	public String getProvince() {
		return Province;
	}

	public void setProvince(String Province) {
		this.Province = Province;
	}

}
