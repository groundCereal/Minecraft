package groundCereal.minecraft.clanWars;

import org.bukkit.entity.Player;

public class BattlePlayer {
	private Player player;
	private Boolean acceptedInvitation;
	private Boolean isAlive;
	private Boolean isChallenging;

	public BattlePlayer(Player player, Boolean isChallenging) {
		this.player = player;
		this.isChallenging = isChallenging;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	public Boolean getAcceptedInvitation() {
		return acceptedInvitation;
	}

	public void setAcceptedInvitation(Boolean acceptedInvitation) {
		this.acceptedInvitation = acceptedInvitation;
	}

	public Boolean getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(Boolean isAlive) {
		this.isAlive = isAlive;
	}

	/**
	 * @return the isChallenging
	 */
	public Boolean getIsChallenging() {
		return isChallenging;
	}

	/**
	 * @param isChallenging the isChallenging to set
	 */
	public void setIsChallenging(Boolean isChallenging) {
		this.isChallenging = isChallenging;
	}
}
