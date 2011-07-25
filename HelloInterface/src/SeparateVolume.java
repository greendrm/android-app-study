
public class SeparateVolume implements Lendable {
	String requestNo;
	String bookTitle;
	String writer;
	String borrower;
	String checkOutDate;
	byte state;
	
	SeparateVolume(String requestNo, String bookTitle, String writer) {
		this.requestNo = requestNo;
		this.bookTitle = bookTitle;
		this.writer = writer;
	}
	
	@Override
	public void checkOut(String borrower, String date) {
		if (state != 0)
			return;
		
		this.borrower = borrower;
		this.checkOutDate = date;
		
		this.state = 1;
		
		System.out.println("*" + bookTitle + "이(가) 대출되었습니다");
		System.out.println("대출인:" + borrower);
		System.out.println("대출일:" + checkOutDate);
	}
	@Override
	public void checkIn() {
		this.borrower = null;
		this.checkOutDate = null;
		this.state = 0;
		System.out.println("*" + bookTitle + "이(가) 반납되었습니다");
	}
	
}
