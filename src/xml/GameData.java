package xml;

import logic.Map;
import player.Player;

public class GameData {
	private Map map;
	private Player player;

	public GameData() {
	}

	public GameData(Map map, Player player) {
		this.setMap(map);
		this.setPlayer(player);
	}

	public GameData(Map map) {
		this.setMap(map);
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
