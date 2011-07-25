
public class AppCDInfo extends CDInfo implements Lendable {
	String borrower;         // 대출인
	String checkOutDate;     // 대출일
	byte state;              // 대출상태
	AppCDInfo(String registerNo, String title) {
		super(registerNo, title);
	}
	@Override
	public void checkOut(String borrower, String date) {   
		if (state != 0)
			return;
		this.borrower = borrower;
		this.checkOutDate = date;
		this.state = 1;
		System.out.println("*" + title + " CD가 대출되었습니다.");
		System.out.println("대출인:" + borrower);
		System.out.println("대출일:" + date + "\n");    
	}
	@Override
	public void checkIn() { 
		this.borrower = null;
		this.checkOutDate = null;
		this.state = 0;
		System.out.println("*" + title + " CD가 반납되었습니다.\n");
	}
}
