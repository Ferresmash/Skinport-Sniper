package skinportApp;

public class PriceData {
	private Double min;
	private Double max;
	private Double avg;
	private Double median;
	private int volume;
	
	public Double getMin() {
		return min;
	}
	public void setMin(Double min) {
		this.min = min;
	}
	public Double getMax() {
		return max;
	}
	public void setMax(Double max) {
		this.max = max;
	}
	public Double getAvg() {
		return avg;
	}
	public void setAvg(Double avg) {
		this.avg = avg;
	}
	public Double getMedian() {
		return median;
	}
	public void setMedian(Double median) {
		this.median = median;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}

//	public PriceData(String min, String max, String avg, String median, String volume) {
//		this.min = (min.equals("null")) ? null : Double.valueOf(min);
//		this.max = (max.equals("null")) ? null : Double.valueOf(max);
//		this.avg = (avg.equals("null")) ? null : Double.valueOf(avg);
//		this.median = (median.equals("null")) ? null : Double.valueOf(median);
//		this.volume = (volume.equals("null")) ? null : Integer.valueOf(volume);
//	}


}
