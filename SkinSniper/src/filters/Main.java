package filters;

public class Main {
    public static void main(String[] args) {
        // Initialize FilterHandler
        FilterHandler filterHandler = new FilterHandler();

        // Load existing filters from JSON
        filterHandler.loadFilters();

        // Add a new filter
        FilterSettings newFilter = new FilterSettings("PriceFilter", 500.0, 100.0, 24, 100, true, false);
        filterHandler.addFilter(newFilter);

        // Update an existing filter
        FilterSettings updatedFilter = new FilterSettings("PriceFilter", 420.0, 50.0, 24, 150, false, true);

        // Print all filters
        for (FilterSettings filter : filterHandler.getFilters()) {
            System.out.println(filter);
        }
    }
}
