package skinportApp;

import java.util.ArrayList;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.google.gson.Gson;

import java.util.List;

public class SkinHandler {

	private List<Skin> skinList = new ArrayList<>();
	private List<Skin2> listedSkins = new ArrayList<>();

	public SkinHandler(String skinHistory, String currentSkin) {

		Gson gson = new Gson();
		Skin[] skinsArray = gson.fromJson(skinHistory, Skin[].class);
		skinList = Arrays.asList(skinsArray);

		Skin2[] listedSkins = gson.fromJson(currentSkin, Skin2[].class);
		this.listedSkins = Arrays.asList(listedSkins);

		for (Skin2 skin2 : this.listedSkins) {
			for (Skin skin : this.skinList) {
				if (skin2.getMarket_hash_name().equals(skin.getMarket_hash_name())) {
					skin2.setPoints(skin);
				}
			}
		}
		Collections.sort(this.listedSkins, Comparator.comparingDouble(Skin2::getPoints).reversed());
		for (Skin2 skin2 : this.listedSkins) {
			System.out.println(skin2.getItem_page() + " points: " + skin2.getPoints());
		}
	}
	
	public void sortList() {
		Collections.sort(this.listedSkins, Comparator.comparingDouble(Skin2::getPoints).reversed());
	}
	
	public List<Skin2> getListedSkins(){
		return this.listedSkins;
	}

	public List<Skin> getSkinList() {
		return skinList;
	}
}