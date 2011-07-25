package greendrm.gridview;

public class GridViewContactsBook {
	private boolean bSelected = false;
	private String Name;
	private String PhoneNumber;
	
	public GridViewContactsBook() {}
	public GridViewContactsBook(String name, String phoneNumber) {
		super();
		Name = name;
		PhoneNumber = phoneNumber;
	}
	
	public void setSelection(boolean selected) {
		bSelected = selected;
	}
	
	public boolean isSelected() {
		return bSelected;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
}
