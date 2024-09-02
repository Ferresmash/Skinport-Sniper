package skinportApp;


public class Skin {
	private String market_hash_name;
	private String version;
	private String currency;
	private String item_page;
	private String market_page;
	private PriceData last_24_hours;
	private PriceData last_7_days;
	private PriceData last_30_days;
	private PriceData last_90_days;

	public String getMarket_hash_name() {
		return market_hash_name;
	}

	public void setMarket_hash_name(String market_hash_name) {
		this.market_hash_name = market_hash_name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getItem_page() {
		return item_page;
	}

	public void setItem_page(String item_page) {
		this.item_page = item_page;
	}

	public String getMarket_page() {
		return market_page;
	}

	public void setMarket_page(String market_page) {
		this.market_page = market_page;
	}

	public PriceData getLast_24_hours() {
		return last_24_hours;
	}

	public void setLast_24_hours(PriceData last_24_hours) {
		this.last_24_hours = last_24_hours;
	}

	public PriceData getLast_7_days() {
		return last_7_days;
	}

	public void setLast_7_days(PriceData last_7_days) {
		this.last_7_days = last_7_days;
	}

	public PriceData getLast_30_days() {
		return last_30_days;
	}

	public void setLast_30_days(PriceData last_30_days) {
		this.last_30_days = last_30_days;
	}

	public PriceData getLast_90_days() {
		return last_90_days;
	}

	public void setLast_90_days(PriceData last_90_days) {
		this.last_90_days = last_90_days;
	}

// Repeat for other fields
}


//public class Skin {
//
//	private String minPrice;
//	private String market_hash_name;
//	private String version;
//	private String currency;
//	private String item_page;
//	private String market_page;
//	private List<SalesData> sales;
//	private PriceData last_7_days;
//	private PriceData last_30_days;
//	private PriceData last_90_days;
//
//	// Constructor, getters, and setters
//	public Skin(String market_hash_name,String version, String currency, String item_page, String market_page, List<SalesData> sales,
//			PriceData last_24_hours, PriceData last_7_days, PriceData last_30_days, PriceData last_90_days) {
//		this.market_hash_name = market_hash_name;
//		this.version = version;
//		this.currency = currency;
//		this.item_page = item_page;
//		this.market_page = market_page;
//		this.sales = sales;
//		this.last_7_days = last_7_days;
//		this.last_30_days = last_30_days;
//		this.last_90_days = last_90_days;
//	}
//
//	// Getters and setters
//	public String getMarket_hash_name() {
//		return market_hash_name;
//	}
//
//	public String getCurrency() {
//		return currency;
//	}
//
//
//	public String getItem_page() {
//		return item_page;
//	}
//
//
//	public String getMarket_page() {
//		return market_page;
//	}
//
//
//	public List<SalesData> getSales() {
//		return sales;
//	}
//
//
//	public PriceData getLast_7_days() {
//		return last_7_days;
//	}
//
//
//	public PriceData getLast_30_days() {
//		return last_30_days;
//	}
//
//
//	public PriceData getLast_90_days() {
//		return last_90_days;
//	}
//
//	public String getVersion() {
//		return version;
//	}
//
//	public String getMinPrice() {
//		return minPrice;
//	}
//
//	public void setMinPrice(String minPrice) {
//		this.minPrice = minPrice;
//	}
//
//}
