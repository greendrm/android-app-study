package greendrm.system.browser;

public class AddressedText {
	private String mAddress = "0000 0000";
	private String mHex = "";
	private String mAsc = "";
	
	public AddressedText() {}
	public AddressedText(String address, String hex, String asc) {
		this.mAddress = address;
		this.mHex = hex;
		this.mAsc = asc;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		this.mAddress = address;
	}

	public String getHex() {
		return mHex;
	}

	public void setHex(String hex) {
		this.mHex = hex;
	}
	
	public String getAsc() {
		return mAsc;
	}

	public void setAsc(String asc) {
		this.mAsc = asc;
	}
}
