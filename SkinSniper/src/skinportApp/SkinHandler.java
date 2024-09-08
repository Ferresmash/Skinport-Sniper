package skinportApp;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import com.google.gson.Gson;

import java.util.List;

public class SkinHandler {

	private List<Skin> skinList = new ArrayList<>();
	private List<CalculatedSkin> listedSkins = new ArrayList<>();
	private List<CalculatedSkin> filteredSkins = new ArrayList<>();

	// Settings:
	private Double sortPriceMin = 50.0;
	private Double sortPriceMax = 300.0;
	// 24, 7, 30, 90
	private int sortByDays = 7;
	private int volume = 5;
	private boolean hideUnreliableSkins = true;
	

	public SkinHandler(String skinHistory, String currentSkin) {

		Gson gson = new Gson();
		Skin[] skinsArray = gson.fromJson(skinHistory, Skin[].class);
		skinList = Arrays.asList(skinsArray);

		CalculatedSkin[] listedSkins = gson.fromJson(currentSkin, CalculatedSkin[].class);
		this.listedSkins = Arrays.asList(listedSkins);

		for (CalculatedSkin skin2 : this.listedSkins) {
			for (Skin skin : this.skinList) {
				if (skin2.getMarket_hash_name().equals(skin.getMarket_hash_name())) {
					skin2.setPoints(skin);
					skin2.setColors(skin);
				}
			}
		}
		filterList();
	}
	
	public void sortList(int sortByDays) {
		if(sortByDays == 90) {
			Collections.sort(this.filteredSkins, Comparator.comparingDouble(CalculatedSkin::getPoints90Days).reversed());
		}else if(sortByDays == 30) {
			Collections.sort(this.filteredSkins, Comparator.comparingDouble(CalculatedSkin::getPoints30Days).reversed());
		}else if(sortByDays == 7) {
			Collections.sort(this.filteredSkins, Comparator.comparingDouble(CalculatedSkin::getPoints7Days).reversed());
		}else {
			Collections.sort(this.filteredSkins, Comparator.comparingDouble(CalculatedSkin::getPoints24Hours).reversed());
		}
	}

	public void filterList() {
		filteredSkins = new ArrayList<>();
		for (CalculatedSkin calculatedSkin : listedSkins) {
			if (calculatedSkin.getMin_price() != null) {
				if (calculatedSkin.getMin_price() >= sortPriceMin && calculatedSkin.getMin_price() <= sortPriceMax) {
					if(!isUnreliable(calculatedSkin) && volume <= calculatedSkin.getPoints(sortByDays)[3]) {
						filteredSkins.add(calculatedSkin);
					}
				}
			}
		}
		sortList(sortByDays);
	}
	
	public boolean isUnreliable(CalculatedSkin skin) {
		if(!hideUnreliableSkins) {
			return false;
		}
		if(skin.getMarket_hash_name().contains("Case Hardened")||skin.getMarket_hash_name().contains("Doppler")||skin.getMarket_hash_name().contains("Music Kit")) {
			return true;
		}
		return false;
	}

	public List<CalculatedSkin> getListedSkins() {
		return this.listedSkins;
	}
	
	public List<CalculatedSkin> getFilteredSkins() {
		return this.filteredSkins;
	}

	public List<Skin> getSkinList() {
		return skinList;
	}
	
	
	public void setSettings(Double min, Double max, int days, int volume, boolean hideUnreliableSkins) {
		sortPriceMin = min;
		sortPriceMax = max;
		sortByDays = days;
		this.volume = volume;
		this.hideUnreliableSkins = hideUnreliableSkins;
	}
	
	
	
}