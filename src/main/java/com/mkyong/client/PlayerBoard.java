package com.mkyong.client;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerBoard {

	@SerializedName("playerId")
	@Expose
	private String playerId;
	@SerializedName("playerName")
	@Expose
	private String playerName;
	@SerializedName("fighters")
	@Expose
	private List<Fighter> fighters = null;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public List<Fighter> getFighters() {
		return fighters;
	}

	public void setFighters(List<Fighter> fighters) {
		this.fighters = fighters;
	}

}
