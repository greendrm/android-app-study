
public class GoodsStockMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GoodsStock stock = new GoodsStock(0);
		
		stock.addStock(100);
		System.out.println("remain = " + stock.subtractStock(50));	
	}

	static class GoodsStock {
		String goodsCode;
		int stockNum;
		
		public GoodsStock(int sn) {
			stockNum = sn;
		}
		
		void addStock(int amount) {
			stockNum += amount;
		}

		int subtractStock(int amount) {
			if (stockNum < amount)
				return 0;
			stockNum -= amount;
			return amount;
		}
	}
}