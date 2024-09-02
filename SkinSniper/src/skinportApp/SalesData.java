package skinportApp;

public class SalesData {
    private Double price;
    private Double wear_value;
    private Long sold_at;

    public SalesData(Double price, Double wear_value, Long sold_at) {
        this.price = price;
        this.wear_value = wear_value;
        this.sold_at = sold_at;
    }
    public double getPrice() {
        return price;
    }
    public Double getWear_value() {
        return wear_value;
    }
    public long getSold_at() {
        return sold_at;
    }
}