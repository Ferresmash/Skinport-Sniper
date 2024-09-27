package skinportApp;

import java.util.ArrayList;

import java.util.List;

import filters.FilterHandler;
import filters.FilterSettings;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class SkinBot {

	private List<CalculatedSkin> latestSavedSkins = new ArrayList<CalculatedSkin>();
	private FilterHandler fH;
	private boolean isNewSkins = true;
	private String allNotifiedSkins = new String("");



	private String TOKEN = "MTI4NTI5MDExMTg3MjkyOTg5Mw.Gfb4Rb.8r3dMtTzrkaXn6SIir1lXKBJro9jEWU0KeSxZI";
	private String CHANNEL_ID = "1285291404905680930"; // Channel where the bot will send notifications

	public SkinBot(FilterHandler fH) {
		this.fH = fH;
	}

	public void start() {

	}

	public void checkForDeals() {
		List<CalculatedSkin> newLatestSavedSkins = new ArrayList<CalculatedSkin>();
		SkinHandler newSkinHandler = new SkinHandler(APIHandler.callAPI("https://api.skinport.com/v1/sales/history"),
				APIHandler.callAPI("https://api.skinport.com/v1/items"));

		// looks at the top 20 skins
		int amountOfSkins = 20;
		for (FilterSettings filter : fH.getFilters()) {
			newSkinHandler.setSettings(filter.getMin(), filter.getMax(), filter.getCheckLast(), filter.getVolume(),
					filter.isHideUnrelSkins());
			newSkinHandler.filterList();
			if(newSkinHandler.getFilteredSkins().size() < 20) {
				amountOfSkins = newSkinHandler.getFilteredSkins().size();
			}
			for (int i = 0; i < amountOfSkins; i++) {
				CalculatedSkin skin = newSkinHandler.getFilteredSkins().get(i);
				if (isGoodDeal(skin, filter)) {
					newLatestSavedSkins.add(skin);
				}
			}
		}
		
		//This dont work
		if (newLatestSavedSkins.equals(latestSavedSkins)) {
			isNewSkins = false;
		} else {
			isNewSkins = true;
		}
		latestSavedSkins = newLatestSavedSkins;
	}

	private boolean isGoodDeal(CalculatedSkin skin, FilterSettings fS) {
		if (skin.getColorCode().equals("NNNN")) {
			System.out.println(skin.getMarket_hash_name() + " because of NNNN");
			return false;
		}
		if (skin.getColorCode().equals("RRRR")) {
			System.out.println(skin.getMarket_hash_name() + " because of RRRR");
			return false;
		}
		
		//THIS IS VERY IMPORTANT (could be changed)
		if (skin.getPointsAvg() < -0.2) {
			System.out.println(skin.getMarket_hash_name() + " did not make it because getPointsAVG < -0,15");
			return false;
		}
		// if the skin have been sold better the last 30 days with 20%. Could be changed
		// to something like negative 15-25%
		if (skin.getPoints90Days() != null && skin.getPoints90Days() < -0.08) {
			System.out.println(skin.getMarket_hash_name() + " did not make it because getPoints 90 < -0,2 or Null");
			return false;
		}

		Double limitValue;
		if (fS.isSaveYellow()) {
			limitValue = 0.0;
		} else {
			limitValue = 0.1;
		}
		if (fS.getCheckLast() == 24 && skin.getPoints24Hours() < limitValue) {
			System.out.println(skin.getMarket_hash_name() + " limitvalue at 24hours");
			return false;
		}
		if (fS.getCheckLast() == 7 && skin.getPoints7Days() < limitValue) {
			System.out.println(skin.getMarket_hash_name() + " limitvalue at 7 days");

			return false;
		}
		if (fS.getCheckLast() == 30 && skin.getPoints30Days() < limitValue) {
			System.out.println(skin.getMarket_hash_name() + " limitvalue at 30 days");

			return false;
		}
		if (fS.getCheckLast() == 90 && skin.getPoints90Days() < limitValue) {
			System.out.println(skin.getMarket_hash_name() + " limitvalue at 90 days");
			return false;
		}
		return true;
	}

	public void sendNotification(String message) {
		try {
			System.out.println("Trying to send notification");
			JDABuilder builder = JDABuilder.createDefault(TOKEN);

			// Disable privileged intents
			builder.disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.MESSAGE_CONTENT,
					GatewayIntent.GUILD_MEMBERS);

			JDA jda = builder.build();
			jda.awaitReady();

			// Get the channel by ID
			TextChannel channel = jda.getTextChannelById(CHANNEL_ID);

			if (channel != null) {
				// Send the message
				channel.sendMessage(message).queue();
			}

			// Shut down the bot after sending the message
			jda.shutdown();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CalculatedSkin> getLatestSavedSkins() {
		return latestSavedSkins;
	}

	public boolean isNewSkins() {
		return isNewSkins;
	}
	
	public String getAllNotifiedSkins() {
		return allNotifiedSkins;
	}
	
	public void setAllNotifiedSkins(String allNotifiedSkins) {
		this.allNotifiedSkins = allNotifiedSkins;
	}

}
