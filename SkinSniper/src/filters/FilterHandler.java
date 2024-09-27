package filters;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FilterHandler {
    private static final String FILE_PATH = "filterSettings.json";
    private List<FilterSettings> filterSettingsList;

    public FilterHandler() {
        filterSettingsList = new ArrayList<>();
        loadFilters();
    }

    // Load filters from the JSON file
    public void loadFilters() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            Type filterListType = new TypeToken<List<FilterSettings>>() {}.getType();
            filterSettingsList = gson.fromJson(reader, filterListType);
            if (filterSettingsList == null) {
                filterSettingsList = new ArrayList<>(); // Initialize empty list if JSON is null
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save the current filters to the JSON file
    public void saveFilters() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(filterSettingsList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a new filter
    public void addFilter(FilterSettings filter) {
        filterSettingsList.add(filter);
        saveFilters(); // Save the updated list to JSON
    }

    // Delete a filter by name
    public void deleteFilter(Long tag) {
        filterSettingsList.removeIf(f -> f.getTag().equals(tag));
        saveFilters(); // Save the updated list
    }

    // Update an existing filter by name
    public void updateFilter(Long tag, FilterSettings newSettings) {
        for (FilterSettings filter : filterSettingsList) {
            if (filter.getTag().equals(tag)) {
            	filter.setName(newSettings.getName());
                filter.setMax(newSettings.getMax());
                filter.setMin(newSettings.getMin());
                filter.setCheckLast(newSettings.getCheckLast());
                filter.setVolume(newSettings.getVolume());
                filter.setHideUnrelSkins(newSettings.isHideUnrelSkins());
                filter.setSaveYellow(newSettings.isSaveYellow());
                saveFilters(); // Save the updated list
                return;
            }
        }
    }

    // Get the list of filters (for bot usage)
    public List<FilterSettings> getFilters() {
        return filterSettingsList;
    }
}
