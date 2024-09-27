package skinportApp;

import filters.FilterHandler;
import filters.FilterSettings;

public class Test {

	public static void main(String[] args) {

		FilterSettings fS = new FilterSettings("test", 3000.0, 500.0, 7, 3, true, true);
		
		FilterHandler fH = new FilterHandler();
		fH.addFilter(fS);
		SkinBot sB = new SkinBot(fH);
		sB.start();
	}

}
