package greendrm.system.browser;

public class StringEx {
	private String obj;

	public StringEx() {}
	
	public StringEx(String obj) {
		this.obj = obj;
	}

	public void setStringEx(String obj) {
		this.obj = obj;
	}
	
	public String getStringEx() {
		return obj;
	}
	
	public boolean isNumber() {
		return isNumber(obj);
	}

	public boolean isNumber(String str) {
		boolean hex = false;

		if (str == null || str.length() == 0)
			return false;

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (c >= '0' && c <= '9')
				continue;
			hex = true;
			if (c >= 'a' && c <= 'f')
				continue;
			if (c >= 'A' && c <= 'F')
				continue;
			if (c == 'x')
				continue;

			return false;
		}

		if (hex) {
			if (isHex(str) == false)
				return false;
		}
		return true;
	}

	public boolean isHex() {
		return isHex(obj);
	}
	
	public boolean isHex(String str) {
		if (str == null || str.length() == 0)
			return false;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '0') {
				if (str.length() > i+1) {
					if (str.charAt(i+1) == 'x') {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isDec() {
		return isDec(obj);
	}
	
	public boolean isDec(String str) {
		if (str == null || str.length() == 0)
			return false;

		if (isNumber(str))
			if (isHex(str) == false)
				return true;
		return false;
	}
	
	public int parseInt() {
		return Integer.parseInt(obj);
	}

	public int parseHex() {
		return parseHex(obj);
	}
	
	public int parseHex(String str) {
		int res = 0;

		String[] tmp;
		tmp = str.split("0x");
		if (tmp.length < 2)
			return 0;

		str = tmp[1];

		if (str.length() == 0)
			return 0;

		int j = 0;
		for (int i = str.length()-1; i >= 0; i--) {
			int d = 0;
			char c = str.charAt(i);
			if (c >= '0' && c <= '9')
				d = c - '0';
			else if (c >= 'A' && c <= 'F')
				d = c - 'A' + 10;
			else if (c >= 'a' && c <= 'f')
				d = c - 'a' + 10;
			else
				return 0;
			res += d * (1 << (j++*4));

		}
		return res;
	}

	public String toString() {
		return obj;
	}
	
	public static String toHexString(int d) {
		String str = new String();

		do {
			int r = d % 16;
			if (r < 10)
				str = ""+r + str;
			else
				str = (char)('a'+ r - 10) + str;

		} while ((d /= 16) > 0);
		str = "0x" + str;

		return str;
	}
	
	public int length() {
		return obj.length();
	}
}
