package com.mkyong.client;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Board {

		@SerializedName("playerBoards")
		@Expose
		private List<PlayerBoard> playerBoards = null;
		@SerializedName("nbrTurnsLeft")
		@Expose
		private Integer nbrTurnsLeft;

		public List<PlayerBoard> getPlayerBoards() {
		return playerBoards;
		}

		public void setPlayerBoards(List<PlayerBoard> playerBoards) {
		this.playerBoards = playerBoards;
		}

		public Integer getNbrTurnsLeft() {
		return nbrTurnsLeft;
		}

		public void setNbrTurnsLeft(Integer nbrTurnsLeft) {
		this.nbrTurnsLeft = nbrTurnsLeft;
		}
		
}

