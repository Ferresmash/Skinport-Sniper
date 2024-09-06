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
		Collections.sort(this.listedSkins, Comparator.comparingDouble(CalculatedSkin::getPoints).reversed());
	}
	
	public void sortList() {
		Collections.sort(this.listedSkins, Comparator.comparingDouble(CalculatedSkin::getPoints).reversed());
	}
	
	public List<CalculatedSkin> getListedSkins(){
		return this.listedSkins;
	}

	public List<Skin> getSkinList() {
		return skinList;
	}
}