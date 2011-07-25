
public class StringBuilderElapsedTime {
	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		long start = System.currentTimeMillis();
		
		System.out.print(rt.freeMemory() / 1024 + " KB");
		System.out.println("/" + rt.totalMemory() / 1024 + " KB");
		
		StringBuilder sb = new StringBuilder();
		sb.append("가");
		int iterates = 10000;
		for (int i = 0; i < iterates; i++) {
			sb.append("나");
		}
		long end = System.currentTimeMillis();
		System.out.println("Elapsed time:" + (end - start));
		System.out.print(rt.freeMemory() / 1024 + " KB");
		System.out.println("/" + rt.totalMemory() / 1024 + " KB");
	}
}
