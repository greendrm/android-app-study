package greendrm.listview2;

public class ListViewContactsBook {
	private String Name;
	private String PhoneNumber;
	private int Image;
	
	public ListViewContactsBook() {}
	public ListViewContactsBook(int image, String name, String number) {
		Image = image;
		Name = name;
		PhoneNumber = number;
	}
	public int getImage() {
		return Image;
	}
	public void setImage(int image) {
		Image = image;
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
	public void setPhoneNumber(String number) {
		PhoneNumber = number;
	}
}
