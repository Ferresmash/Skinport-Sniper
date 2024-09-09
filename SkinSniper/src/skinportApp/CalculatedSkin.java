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

	private String colorCode;

	private Skin corresponding;

	// new point system (array with price, earn, percent and volume! can be null)
	private Double[] points24Hours = new Double[4];
	private Double[] points7Days = new Double[4];
	private Double[] points30Days = new Double[4];
	private Double[] points90Days = new Double[4];

	/**
	 * Sets points for the skin
	 */
	public void setPoints(Skin corresponding) {
//		PriceData pD = corresponding.getLast_24_hours();
		this.corresponding = corresponding;
		
		if(min_price == null) {
			return;
		}
		
		if (corresponding.getLast_24_hours().getMin() != null) {
			points24Hours[0] = corresponding.getLast_24_hours().getMin();
			points24Hours[1] = points24Hours[0]*0.88 - min_price;
			points24Hours[2] = points24Hours[1] / min_price;
			
		}
		points24Hours[3] = (double) corresponding.getLast_24_hours().getVolume();

		if (corresponding.getLast_7_days().getMin() != null) {
			points7Days[0] = corresponding.getLast_7_days().getMin();
			points7Days[1] = (points7Days[0]*0.88) - min_price;
			points7Days[2] = points7Days[1] / min_price;
			
		}
		points7Days[3] = (double) corresponding.getLast_7_days().getVolume();

		if (corresponding.getLast_30_days().getMin() != null) {
			points30Days[0] = corresponding.getLast_30_days().getMin();
			points30Days[1] = (points30Days[0]*0.88) - min_price;
			points30Days[2] = points30Days[1] / min_price;
			
		}
		points30Days[3] = (double) corresponding.getLast_30_days().getVolume();

		if (corresponding.getLast_90_days().getMin() != null) {
			points90Days[0] = corresponding.getLast_90_days().getMin();
			points90Days[1] = (points90Days[0]*0.88) - min_price;
			points90Days[2] = points90Days[1] / min_price;
			
		}
		points90Days[3] = (double) corresponding.getLast_90_days().getVolume();
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

		if (min_price == null) {
			return;
		}

		// 24 hours comparison
		if (points24Hours[0] != null) {
			if (0 < points24Hours[2] && points24Hours[2] < 0.1) {
				colorCode = "Y" + colorCode.substring(1);
			} else if (points24Hours[2] >= 0.1) {
				colorCode = "G" + colorCode.substring(1);
			} else if (points24Hours[2] <= 0.0) {
				colorCode = "R" + colorCode.substring(1);
			}
		}

		// 7 days comparison
		if (points7Days[0] != null) {
			if (0 < points7Days[2] && points7Days[2] < 0.1) {
				colorCode = colorCode.substring(0, 1) + "Y" + colorCode.substring(2);
			} else if (points7Days[2] >= 0.1) {
				colorCode = colorCode.substring(0, 1) + "G" + colorCode.substring(2);
			} else if (points7Days[2] <= 0.0) {
				colorCode = colorCode.substring(0, 1) + "R" + colorCode.substring(2);
			}
		}

		// 30 days comparison
		if (points30Days[0] != null) {
			if (0 < points30Days[2] && points30Days[2] < 0.1) {
				colorCode = colorCode.substring(0, 2) + "Y" + colorCode.substring(3);
			} else if (points30Days[2] >= 0.1) {
				colorCode = colorCode.substring(0, 2) + "G" + colorCode.substring(3);
			} else if (points30Days[2] <= 0.0) {
				colorCode = colorCode.substring(0, 2) + "R" + colorCode.substring(3);
			}
		}

		// 90 days comparison
		if (points90Days[0] != null) {
			if (0 < points90Days[2] && points90Days[2] < 0.1) {
				colorCode = colorCode.substring(0, 3) + "Y";
			} else if (points90Days[2] >= 0.1) {
				colorCode = colorCode.substring(0, 3) + "G";
			} else if (points90Days[2] <= 0.0) {
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

	public Double[] getPoints(int sortBy) {
		if(sortBy == 90) {
			return points90Days;
		}else if(sortBy == 30) {
			return points30Days;
		}else if(sortBy == 7) {
			return points7Days;
		}else {
			return points24Hours;
		}
	}

	public String getColorCode() {
		return colorCode;
	}

	public Skin getCorresponding() {
		return corresponding;
	}
	
	//Can mess with the evaluation due to setting the value to zero. But it is needed for sorting the list

	public Double getPoints24Hours() {
		return (points24Hours[2] != null)? points24Hours[2] : 0.0;
	}

	public Double getPoints7Days() {
		return (points7Days[2] != null)? points7Days[2] : 0.0;
	}

	public Double getPoints30Days() {
		return (points30Days[2] != null)? points30Days[2] : 0.0;
	}

	public Double getPoints90Days() {
		return (points90Days[2] != null)? points90Days[2] : 0.0;
	}
	
	public Double getPointsAvg() {
		Double sum = 0.0;
		int count = 0;
		if(getPoints24Hours() != 0) {
			sum += getPoints24Hours();
			count++;
		}
		if(getPoints7Days() != 0) {
			sum += getPoints7Days();
			count++;
		}
		if(getPoints30Days() != 0) {
			sum += getPoints30Days();
			count++;
		}
		if(getPoints90Days() != 0) {
			sum += getPoints90Days();
			count++;
		}
		
		if(count == 0) {
			return 0.0;
		}
		return sum / count;
		
	}
	
	
	public Double getPrice24Hours() {
		return points24Hours[0];
	}
	public Double getPrice7Days() {
		return points7Days[0];
	}
	public Double getPrice30Days() {
		return points30Days[0];
	}
	public Double getPrice90Days() {
		return points90Days[0];
	}

}
