package groundCereal.minecraft.clanWars.managers;

import org.bukkit.configuration.file.FileConfiguration;

import groundCereal.minecraft.clanWars.ClanWars;

public class MessagesManager {
	private MessagesManager instance;

	private String unknownCommand;
	private String lobbyLocationSet;
	private String endLobbyLocationSet;
	private String balconyLocationSet;
	private String aSpawnLocationSet;
	private String bSpawnLocationSet;
	private String errorSettingLocation;
	private String reservedForLeaders;
	private String invalidParameters;
	private String clanName;
	private String numberOfPlayers;
	private String unknownClan;
	private String atLeastNPlayers;
	private String clanChallengingClan;
	private String battleFieldNotSet;
	private String battleAlreadyOn;
	private String challengeAccept;
	private String challengeDecline;
	private String cannotChallengeSelf;
	private String declinedBattle;
	private String challengeExpired;
	private String commandNotAvailable;
	private String selectionExpired;
	private String noLeaderOnline;
	private String notEnoughElemets;
	private String playersListFull;
	private String selectPlayer;
	private String finishSelection;
	private String selectMorePlayers;
	private String playersCanNowEnter;
	private String playerInvitation;
	private String playerOffline;
	private String selectAll;
	private String limitedPlaces;
	private String alreadyStarted;
	private String notInvited;
	private	String battleFull;
	private String battleStartsIn;
	private String battleWon;
	private String pickupItems;
	private String prizeAnnouncement;
	private String pickupCountdown;
	
	public MessagesManager() {
		instance = this;

		FileConfiguration config = ClanWars.getInstance().getConfig();

		unknownCommand = config.getString("messages.unknownCommand");
		lobbyLocationSet = config.getString("messages.lobbyLocationSet");
		endLobbyLocationSet = config.getString("messages.endLobbyLocationSet");
		balconyLocationSet = config.getString("messages.balconyLocationSet");
		aSpawnLocationSet = config.getString("messages.aSpawnLocationSet");
		bSpawnLocationSet = config.getString("messages.bSpawnLocationSet");
		errorSettingLocation = config.getString("messages.errorSettingLocation");
		reservedForLeaders = config.getString("messages.reservedForLeaders");
		invalidParameters = config.getString("messages.invalidParameters");
		clanName = config.getString("messages.clanName");
		numberOfPlayers = config.getString("messages.numberOfPlayers");
		unknownClan = config.getString("messages.unknownClan");
		atLeastNPlayers = config.getString("messages.alLeastNPlayers");
		clanChallengingClan = config.getString("messages.clanChallengingClan");
		battleFieldNotSet = config.getString("messages.battleFieldNotSet");
		battleAlreadyOn = config.getString("messages.battleAlreadyOn");
		challengeAccept = config.getString("messages.challengeAccept");
		challengeDecline = config.getString("messages.challengeDecline");
		cannotChallengeSelf = config.getString("messages.cannotChallengeSelf");
		declinedBattle = config.getString("messages.declinedBattle");
		challengeExpired = config.getString("messages.challengeExpired");
		commandNotAvailable = config.getString("messages.commandNotAvailable");
		selectionExpired = config.getString("messages.selectionExpired");
		noLeaderOnline = config.getString("messages.noLeaderOnline");
		notEnoughElemets = config.getString("messages.notEnoughElemets");
		playersListFull = config.getString("messages.playersListFull");
		selectPlayer = config.getString("messages.selectPlayer");
		finishSelection = config.getString("messages.finishSelection");
		selectMorePlayers = config.getString("messages.selectMorePlayers");
		playersCanNowEnter = config.getString("messages.playersCanNowEnter");
		playerInvitation = config.getString("messages.playerInvitation");
		playerOffline = config.getString("messages.playerOffline");
		selectAll = config.getString("messages.selectAll");
		limitedPlaces = config.getString("messages.limitedPlaces");
		alreadyStarted = config.getString("messages.alreadyStarted");
		notInvited = config.getString("messages.notInvited");
		battleFull = config.getString("messages.battleFull");
		battleStartsIn = config.getString("messages.battleStartsIn");
		battleWon = config.getString("messages.battleWon");
		pickupItems = config.getString("messages.pickupItems");
		prizeAnnouncement = config.getString("messages.prizeAnnouncement");
		pickupCountdown = config.getString("messages.pickupCountdown");
	}

	public MessagesManager getInstance() {
		return instance;
	}

	public String getUnknownCommand() {
		if (unknownCommand == null) {
			return "Comando desconhecido";
		} else {
			return unknownCommand;
		}
	}

	public String getLobbyLocationSet() {
		if (lobbyLocationSet == null) {
			return "Localiza��o do lobby definida com sucesso";
		} else {
			return lobbyLocationSet;
		}
	}

	public String getEndLobbyLocationSet() {
		if (endLobbyLocationSet == null) {
			return "Localiza��o do lobby de fim de jogo definida com sucesso";
		} else {
			return endLobbyLocationSet;
		}
	}

	public String getBalconyLocationSet() {
		if (balconyLocationSet == null) {
			return "Localiza��o do camarote definida com sucesso";
		} else {
			return balconyLocationSet;
		}
	}

	public String getASpawnLocationSet() {
		if (aSpawnLocationSet == null) {
			return "Localiza��o do spawn da equipa A definida com sucesso";
		} else {
			return aSpawnLocationSet;
		}
	}

	public String getBSpawnLocationSet() {
		if (bSpawnLocationSet == null) {
			return "Localiza��o do spawn da equipa B definida com sucesso";
		} else {
			return bSpawnLocationSet;
		}
	}

	public String getErrorSettingLocation() {
		if (errorSettingLocation == null) {
			return "Erro na defini��o da localiza��o";
		} else {
			return errorSettingLocation;
		}
	}

	public String getReservedForLeaders() {
		if (reservedForLeaders == null) {
			return "O desafio s� pode ser feito pelo lider";
		} else {
			return reservedForLeaders;
		}
	}

	public String getInvalidParameters() {
		if (invalidParameters == null) {
			return "Parametros inv�lidos para este comando";
		} else {
			return invalidParameters;
		}
	}

	public String getClanName() {
		if (clanName == null) {
			return "Nome do cl�";
		} else {
			return clanName;
		}
	}

	public String getNumberOfPlayers() {
		if (numberOfPlayers == null) {
			return "N� jogadores";
		} else {
			return numberOfPlayers;
		}
	}

	public String getUnknownClan() {
		if (unknownClan == null) {
			return "Cl� inexistente";
		} else {
			return unknownClan;
		}
	}

	public String getAtLeastNPlayers() {
		if (atLeastNPlayers == null) {
			return "A batalha tem de ter no m�nimo " + ClanWars.getInstance().getConfigManager().getMinimumPlayers() + " jogadores";
		} else {
			return String.format(atLeastNPlayers, ClanWars.getInstance().getConfigManager().getMinimumPlayers());
		}
	}

	public String getClanChallengedClan(String challengingClan, String challengedClan) {
		if (clanChallengingClan == null) {
			return String.format("O cl� [%s] desafiou o cl� [%s] para uma batalha", challengingClan, challengedClan);
		} else {
			return String.format(clanChallengingClan, challengingClan, challengedClan);
		}
	}

	public String getBattleFieldNotSet() {
		if (battleFieldNotSet == null) {
			return "Arena ainda n�o definida. Contacta o administrador";
		} else {
			return battleFieldNotSet;
		}
	}

	public String getBattleAlreadyOn() {
		if (battleAlreadyOn == null) {
			return "J� est� uma batalha a decorrer. Tenta mais tarde.";
		} else {
			return battleAlreadyOn;
		}
	}

	public String getChallengeAccept() {
		if (challengeAccept == null) {
			return "Carrega aqui para aceitar o dasafio ou faz ";
		} else {
			return challengeAccept;
		}
	}
	
	public String getChallengeDecline() {
		if (challengeDecline == null) {
			return "Carrega aqui para rejeitar o dasafio ou faz ";
		} else {
			return challengeDecline;
		}
	}

	public String getCannotChallengeSelf() {
		if (cannotChallengeSelf == null) {
			return "N�o pode desafiar o seu pr�prio cl�";
		} else {
			return cannotChallengeSelf;
		}
	}

	public String getDeclinedBattle() {
		if (declinedBattle == null) {
			return "O desafio foi recusado. A batalha n�o vai ocorrer!";
		} else {
			return declinedBattle;
		}
	}

	public String getChallengeExpired() {
		if (challengeExpired == null) {
			return "O desafio expirou. A batalha n�o vai ocorrer!";
		} else {
			return challengeExpired;
		}
	}

	public String getCommandNotAvailable() {
		if (commandNotAvailable == null) {
			return "Comando indispon�vel!";
		} else {
			return commandNotAvailable;
		}
	}

	public String getSelectionExpired() {
		if (selectionExpired == null) {
			return "O tempo para selec��o de jogadores expirou. A batalha n�o vai ocorrer!";
		} else {
			return selectionExpired;
		}
	}

	public String getNoLeaderOnline() {
		if (noLeaderOnline == null) {
			return "O lider do cl� desafiado n�o est� online. A batalha n�o vai ocorrer!";
		} else {
			return noLeaderOnline;
		}
	}

	public String getNotEnoughElemets(String clanTag) {
		if (notEnoughElemets == null) {
			return String.format("O cl� [%s] n�o tem membros online suficientes. A batalha n�o vai ocorrer!", clanTag);
		} else {
			return String.format(notEnoughElemets, clanTag);
		}
	}

	public String getPlayersListFull() {
		if (playersListFull == null) {
			return "J� est�o seleccionados todos os jogadores";
		} else {
			return playersListFull;
		}
	}

	public String getSelectPlayer() {
		if (selectPlayer == null) {
			return "Selec��o de Jogadores";
		} else {
			return selectPlayer;
		}
	}

	public String getFinishSelection() {
		if (finishSelection == null) {
			return "Terminar selec��o";
		} else {
			return finishSelection;
		}
	}

	public String getSelectMorePlayers(int nbrMissingPlayers) {
		if (selectMorePlayers == null) {
			return String.format("Falta seleccionar mais %d jogadores", nbrMissingPlayers);
		} else {
			return String.format(selectMorePlayers, nbrMissingPlayers);
		}
	}

	public String getPlayersCanNowEnter() {
		if (playersCanNowEnter == null) {
			return "Selec��o efectuada os jogadores poder�o come�ar a entrar na batalha agora";
		} else {
			return playersCanNowEnter;
		}
	}

	public String getPlayerInvitation() {
		if (playerInvitation == null) {
			return "Foste seleccionado para a batalha. Clica aqui ou faz ";
		} else {
			return playerInvitation;
		}
	}

	public String getPlayerOffline() {
		if (playerOffline == null) {
			return "Jogador offline";
		} else {
			return playerOffline;
		}
	}

	public String getSelectAll() {
		if (selectAll == null) {
			return "Seleccionar todos";
		} else {
			return selectAll;
		}
	}

	public String getLimitedPlaces() {
		if (limitedPlaces == null) {
			return "Apressa-te! Os lugares na batalha s�o limitados";
		} else {
			return limitedPlaces;
		}
	}

	public String getAlreadyStarted() {
		if (alreadyStarted == null) {
			return "A batalha j� come�ou. J� n�o podes entrar!";
		} else {
			return alreadyStarted;
		}
	}

	public String getNotInvited() {
		if (notInvited == null) {
			return "N�o foste convidado para a batalha!";
		} else {
			return notInvited;
		}
	}

	public String getBattleFull() {
		if (battleFull == null) {
			return "As vagas para a batalha j� est�o todas ocupadas!";
		} else {
			return battleFull;
		}
	}

	public String getBattleStartsIn(int count) {
		if (battleStartsIn == null) {
			return String.format("A batalha come�a em ... %d segundos", count);
		} else {
			return String.format(battleStartsIn, count);
		}
	}

	public String getBattleWon(String wonClan) {
		if (battleWon == null) {
			return String.format("A batalha foi vencida por [%s]", wonClan);
		} else {
			return String.format(battleWon, wonClan);
		}	
	}

	public String getPickupItems() {
		if (pickupItems == null) {
			return String.format("Tem [%d] segundos para recolher os items", ClanWars.getInstance().getConfigManager().getPickupTimeout());
		} else {
			return String.format(pickupItems, ClanWars.getInstance().getConfigManager().getPickupTimeout());
		}	
	}

	public String getPrizeAnnouncement() {
		if (prizeAnnouncement == null) {
			return String.format("Todos os sobreviventes receber�o [%d]$", ClanWars.getInstance().getConfigManager().getPrize());
		} else {
			return String.format(prizeAnnouncement, ClanWars.getInstance().getConfigManager().getPrize());
		}	
	}

	public String getPickupCountdown(int count) {
		if (pickupCountdown == null) {
			return String.format("Tem ... %d segundos", count);
		} else {
			return String.format(pickupCountdown, count);
		}
	}
}
