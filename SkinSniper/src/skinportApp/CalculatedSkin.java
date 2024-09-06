package skinportApp;

public class CalculatedSkin {

	private String market_hash_name;
	private String currency;
	private Double suggested_price;
	private String item_page;
	private String market_page;
	private Double min_price; // current
	private Double max_price;
	private Double mean_price;
	private Double median_price;
	private int quantity;
	private long created_at;
	private long updated_at;
	private double points;

	private String colorCode;

	private Skin corresponding;

	// Settings:
	private Double sortPriceMin = 50.0;
	private Double sortPriceMax = 400.0;
	// 24, 7, 30, 90
	private int sortByDays = 24;

	/**
	 * Sets points for the skin
	 */
	public void setPoints(Skin corresponding) {
		PriceData pD = corresponding.getLast_24_hours();
		this.corresponding = corresponding;
		if (sortByDays == 24) {
			pD = corresponding.getLast_24_hours();
		}
		if (sortByDays == 7) {
			pD = corresponding.getLast_7_days();
		}
		if (sortByDays == 30) {
			pD = corresponding.getLast_30_days();
		}
		if (sortByDays == 90) {
			pD = corresponding.getLast_90_days();
		}

		if (pD.getVolume() < 2 || min_price == null || min_price == 0) {
			this.points = -100000;
			return;
		}
		if (sortPriceMin > min_price || min_price > sortPriceMax) {
			points = -10000;
			return;
		}

		if (corresponding.getMarket_hash_name().contains("Case Hardened")
				|| corresponding.getMarket_hash_name().contains("Doppler")
				|| corresponding.getMarket_hash_name().contains("Fade")
				|| corresponding.getMarket_hash_name().contains("Music Kit")) {
			points = -10000;
			return;
		}

		// counts how much we earn if we buy and sell for lowest price on market, 0.88
		// is tax on skinport
		points = (pD.getMin() * 0.88 - min_price) / min_price;
	}

	public void setSettings(Double sortPriceMin, Double sortPriceMax, int sortByDays) {
		this.sortPriceMin = (sortPriceMin == null) ? this.sortPriceMin : sortPriceMin;
		this.sortPriceMax = (sortPriceMax == null) ? this.sortPriceMax : sortPriceMax;
		this.sortByDays = (sortByDays == 0) ? this.sortByDays : sortByDays;
		if (!(corresponding == null)) {
			setPoints(corresponding);
		}

	}
	/**
	 * Sets colors for the skin
	 */
	public void setColors(Skin corresponding) {
		this.corresponding = corresponding;
		// Ensure colorCode is initialized to "NNNN"
		if (colorCode == null || colorCode.length() != 4) {
			colorCode = "NNNN";
		}
		
		if(min_price == null) {
			return;
		}

		// 24 hours comparison
		if (corresponding.getLast_24_hours().getMin() != null) {
			if (min_price.equals(corresponding.getLast_24_hours().getMin())) {
				colorCode = "Y" + colorCode.substring(1);
			} else if (min_price < corresponding.getLast_24_hours().getMin()) {
				colorCode = "G" + colorCode.substring(1);
			} else if (min_price > corresponding.getLast_24_hours().getMin()) {
				colorCode = "R" + colorCode.substring(1);
			}
		}

		// 7 days comparison
		if (corresponding.getLast_7_days().getMin() != null) {
			if (min_price == corresponding.getLast_7_days().getMin()) {
				colorCode = colorCode.substring(0, 1) + "Y" + colorCode.substring(2);
			} else if (min_price < corresponding.getLast_7_days().getMin()) {
				colorCode = colorCode.substring(0, 1) + "G" + colorCode.substring(2);
			} else if (min_price > corresponding.getLast_7_days().getMin()) {
				colorCode = colorCode.substring(0, 1) + "R" + colorCode.substring(2);
			}
		}

		// 30 days comparison
		if (corresponding.getLast_30_days().getMin() != null) {
			if (min_price == corresponding.getLast_30_days().getMin()) {
				colorCode = colorCode.substring(0, 2) + "Y" + colorCode.substring(3);
			} else if (min_price < corresponding.getLast_30_days().getMin()) {
				colorCode = colorCode.substring(0, 2) + "G" + colorCode.substring(3);
			} else if (min_price > corresponding.getLast_30_days().getMin()) {
				colorCode = colorCode.substring(0, 2) + "R" + colorCode.substring(3);
			}
		}

		// 90 days comparison
		if (corresponding.getLast_90_days().getMin() != null) {
			if (min_price == corresponding.getLast_90_days().getMin()) {
				colorCode = colorCode.substring(0, 3) + "Y";
			} else if (min_price < corresponding.getLast_90_days().getMin()) {
				colorCode = colorCode.substring(0, 3) + "G";
			} else if (min_price > corresponding.getLast_90_days().getMin()) {
				colorCode = colorCode.substring(0, 3) + "R";
			}
		}
	}

	public String getMarket_hash_name() {
		return market_hash_name;
	}

	public void setMarket_hash_name(String market_hash_name) {
		this.market_hash_name = market_hash_name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getSuggested_price() {
		return suggested_price;
	}

	public void setSuggested_price(double suggested_price) {
		this.suggested_price = suggested_price;
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

	public Double getMin_price() {
		return min_price;
	}

	public void setMin_price(double min_price) {
		this.min_price = min_price;
	}

	public Double getMax_price() {
		return max_price;
	}

	public void setMax_price(double max_price) {
		this.max_price = max_price;
	}

	public Double getMean_price() {
		return mean_price;
	}

	public void setMean_price(double mean_price) {
		this.mean_price = mean_price;
	}

	public Double getMedian_price() {
		return median_price;
	}

	public void setMedian_price(double median_price) {
		this.median_price = median_price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public long getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(long updated_at) {
		this.updated_at = updated_at;
	}

	public long getCreated_at() {
		return created_at;
	}

	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public String getColorCode() {
		return colorCode;
	}

}
