package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import application.Main;
import controller.FightGuiController;
import field.Field;
import field.FieldType;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import pokemon.Pokemon;
import views.MapView;
import views.PokemonView;
import xml.GameData;

/* Noch fehlt:

	- NN	evtl Level (automatisches Laden der n�chsten Karte bei Besiegen eines Trainers o.�.
	- CHECK Bilder der Pokemon, sowohl rechts in der Liste als auch beim Kampf
	- CHECK	Pl�tze tauschen (erstes Pokemon der Liste durch ein anderes ersetzen)
	- CHECK	Motivation abh�ngig vom Ausgang der letzten 5-10 K�mpfe setzen
	- CHECK XP-Balken zeigt noch keinen Fortschritt CHECK
	- CHECK	Klick auf Beenden beendet Spiel
	- CHECK Spielerposition bei Kartenwechsel anpassen
	- CHECK	mehrere �berg�nge auf einer Map
	- 		Bild bei Entwicklung anpassen
	- CHECK	Maps bauen
	- CHECK	Pokedex im Spiel

*/

public class GameLogic extends Thread {
	private MapView mapView;
	private Player player;
	private long lastMovementTime = 0;
	private AnchorPane anchor2;

	public GameLogic(MapView mapView, AnchorPane anchor2, GameData gameData) {
		this.mapView = mapView;
		List<Field> field = this.mapView.getFields().stream().filter(each -> !each.isBlocked())
				.collect(Collectors.toList());

		Field f = field.get(new Random().nextInt(field.size()));

		if (gameData.getPlayer() == null) {
			this.player = new Player();
			List<Integer> pokedexList = new ArrayList<>();
			for (int i = 0; i <= 150; i++) {
				pokedexList.add(0);
			}
			player.setPokedex(pokedexList);
			player.setImage(new Image("/images/player_straight.png"));
			player.setField(f);
			f.setEntity(player);
			Main.gameData.setPlayer(player);
		} else {
			player = gameData.getPlayer();
			System.out.println("Player: " + player);
		}
		mapView.update();
		this.anchor2 = anchor2;
	}

	public void update() {
		Platform.runLater(() -> {

			// Wenn der Spieler noch auf keinem Feld registriert ist
			if (this.mapView.getFields().stream().anyMatch(each -> each.getEntity()!=null)) {
				Field field = player.getField();
				field.getEntity().getClass().equals(Player.class);
				Field new_field = this.mapView.getFields().stream()
						.filter(each -> each.getX() == field.getX() && each.getY() == field.getY()).findFirst().get();
				player.setField(new_field);
				new_field.setEntity(player);
			}

			this.mapView.update();

			long countOfPokeViews = anchor2.getChildren().stream().filter(each -> each.getClass().equals(PokemonView.class))
					.count();

			if (countOfPokeViews < player.getPokemon().size()) {
				for (int i = (int) countOfPokeViews; i < player.getPokemon().size(); i++) {
					Pokemon myPokemon = player.getPokemon().get(i);
					PokemonView pv = new PokemonView(myPokemon);
					pv.setLayoutX(45);
					pv.setLayoutY(10 + i * 95);
					pv.getUpButton().setOnAction(event -> {

						// Pokemon Swap Mechanic
						int oldIndex = player.getPokemon().indexOf(pv.getPokemon());
						int newIndex = (oldIndex == 0 ? player.getPokemon().size() - 1 : oldIndex - 1);

						Pokemon oldPokemon = player.getPokemon().get(oldIndex);
						Pokemon newPokemon = player.getPokemon().get(newIndex);

						PokemonView oldView = (PokemonView) anchor2.getChildren().get(oldIndex);
						PokemonView newView = (PokemonView) anchor2.getChildren().get(newIndex);

						player.getPokemon().set(newIndex, oldPokemon);
						player.getPokemon().set(oldIndex, newPokemon);

						oldView.setPokemon(newPokemon);
						newView.setPokemon(oldPokemon);

					});
					anchor2.getChildren().add(pv);
				}
			} else {
				for (int i = 0; i < player.getPokemon().size(); i++) {
					((PokemonView) anchor2.getChildren().get(i)).update();
				}
			}
			mapView.requestFocus();
		});
	}

	@Override
	public void run() {
		while (true) {
			update();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void fightMenu() {	
		
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		Alert acceptAlert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Aktionsfenster");
		List<Pokemon> listOfPokemons = new ArrayList<>();

		if (player.getPokemon().size() > 0) {
			listOfPokemons = mapView.getMap().getPokemons();
			listOfPokemons.forEach(each -> each
					.setLevel(1 + new Random().nextInt(player.getAverageLevel()) + 1 + player.getPokemon().size()));
		} else {
			listOfPokemons.add(Main.xmlControll.getPokemonByName("Schiggy"));
			listOfPokemons.add(Main.xmlControll.getPokemonByName("Bisasam"));
			listOfPokemons.add(Main.xmlControll.getPokemonByName("Glumanda"));
			listOfPokemons.forEach(each -> each.setLevel(5));
		}

		List<Double> chances = listOfPokemons.stream().map((each) -> each.getSpawn()).collect(Collectors.toList());
		double sumChances = chances.stream().reduce(0.0, (a, b) -> a + b);
		double randomValue = sumChances * new Random().nextDouble();

		Pokemon spawnedPokemon = null;
		for (Pokemon currentPokemon : listOfPokemons) {
			if ((sumChances - currentPokemon.getSpawn()) < randomValue) {
				spawnedPokemon = new Pokemon(currentPokemon);
				break;
			} else {
				sumChances -= currentPokemon.getSpawn();
			}
		}
		
//		Kampf
//		if(player.getPokemon().size()>0) {
//			FightGuiController controller = FightGuiController.create(player.getPokemon().get(0), spawnedPokemon);
//			Platform.runLater(() -> {
//				Main.changer.changeWindow("/guis/FightGui.fxml", (loader) -> {
//					loader.setController(controller);
//				});
//			});
//		}
		

		
		
		int entryPokedex = player.getPokedex().get(spawnedPokemon.getId());
		String entryOutput = "";
		if (entryPokedex == 1) {
			entryOutput = "Du hast dieses Pokemon bereits gesehen.";
		} else if (entryPokedex == 2) {
			entryOutput = "Du hast dieses Pokemon bereits gefangen.";
		} else {
			entryOutput = "Ein Eintrag im Pokedex wurde hinzugef�gt";
			player.setPokedex(spawnedPokemon, 1);
		}

		alert.setHeaderText("Ein wildes " + spawnedPokemon.getName() + " Lvl. " + spawnedPokemon.getLevel()
				+ " ist erschienen.\n" + entryOutput);
		//Pokemonbild
		String pathToImg = "/pokemon/images/" + spawnedPokemon.getId() + ".png";
		ImageView picture = new ImageView(getClass().getResource(pathToImg).toExternalForm());
		picture.setLayoutX(0);
		picture.setLayoutY(0);
		picture.setFitWidth(128);
		picture.setFitHeight(128);
		alert.setGraphic(picture);

		alert.setContentText("Bitte waehle deine Aktion.");

		ButtonType kampfButton = new ButtonType("Angreifen");
		ButtonType fangButton = new ButtonType("Fangen");
		ButtonType fliehButton = new ButtonType("Fliehen");

		alert.getButtonTypes().setAll(fliehButton);
		if (player.getPokemon().size() != 0) {
			alert.getButtonTypes().add(kampfButton);
		}

		if (player.getPokemon().size() < 5) {
			alert.getButtonTypes().add(fangButton);
		}

		Optional<ButtonType> result = alert.showAndWait();
		ButtonType buttonType = result.get();
		if (buttonType != null) {
			if (buttonType.equals(kampfButton)) {
				Pokemon winner = null;
				for (int i = 0; i < player.getPokemon().size(); i++) {
					Pokemon pokemon = player.getPokemon().get(i);
					int currentLvl = pokemon.getLevel();
					winner = pokemon.fight(spawnedPokemon);
					if (winner != null) {
						if (player.getPokemon().contains(winner)) {
							winner.addXp(spawnedPokemon.calcXp());
							acceptAlert.setHeaderText(winner.getName() + " hat " + spawnedPokemon.calcXp()
									+ " Erfahrungspunkte erhalten.");
							if (winner.getLevel() != currentLvl) {
								acceptAlert.setContentText(winner.getName() + " ist ein Level aufgestiegen.");
							}
							ButtonType acceptButton = new ButtonType("Ok");
							acceptAlert.getButtonTypes().setAll(acceptButton);
							acceptAlert.show();
							break;
						} else {
							System.out.println("Dein Pokemon: " + pokemon.getName() + " wurde besiegt");
						}
					}
				}
			} else if (buttonType.equals(fangButton)) {
				spawnedPokemon.setXp(0);
				if (player.getPokemon().size() == 0) {
					player.getPokemon().add(spawnedPokemon);
					spawnedPokemon.setTrained(true);
				} else {
					int iValue = new Random().nextInt(100);
					if (spawnedPokemon.getSpawn() > 3) {
						if (iValue > 45) {
							player.getPokemon().add(spawnedPokemon);
							spawnedPokemon.setTrained(true);
						}
					} else if (spawnedPokemon.getSpawn() > 2) {
						if (iValue > 60) {
							player.getPokemon().add(spawnedPokemon);
							spawnedPokemon.setTrained(true);
						}
					} else if (spawnedPokemon.getSpawn() > 1) {
						if (iValue > 70) {
							player.getPokemon().add(spawnedPokemon);
							spawnedPokemon.setTrained(true);
						}
					} else {
						if (iValue > 80) {
							player.getPokemon().add(spawnedPokemon);
							spawnedPokemon.setTrained(true);
						}
					}
					if (spawnedPokemon.isTrained() == true) {
						player.setPokedex(spawnedPokemon, 2);
					}
				}
			} else {

			}
		}
	}

	public void moveEvent(String keyName) {
		double newX = player.getField().getX();
		double newY = player.getField().getY();
		Optional<Field> newField = null;

		switch (keyName) {
		case "W":
			newY -= 30;
			player.setImage(Main.player_straight);
			break;
		case "D":
			newX += 30;
			player.setImage(Main.player_right);
			break;
		case "S":
			newY += 30;
			player.setImage(Main.player_back);
			break;
		case "A":
			newX -= 30;
			player.setImage(Main.player_left);
			break;
		case "Space":
			Optional<Field> oField = null;
			if (player.getImage() != null) {
				Image playerImage = player.getImage();
				if (playerImage.equals(Main.player_left))
					oField = mapView.getMap().leftField(player.getField());
				else if (playerImage.equals(Main.player_right))
					oField = mapView.getMap().rightField(player.getField());
				else if (playerImage.equals(Main.player_straight))
					oField = mapView.getMap().upField(player.getField());
				else if (playerImage.equals(Main.player_back))
					oField = mapView.getMap().bottomField(player.getField());
				if (oField.isPresent()) {
					Field field = oField.get();
					if (field.getEntity() != null) {
						fightAgainstTrainer((Trainer) field.getEntity());
					}
				}
			}
			break;
		default:
			break;
		}

		double x = newX;
		double y = newY;

		newField = mapView.getFields().stream().filter(each -> each.getX() == x && each.getY() == y).findFirst();

		if (newField.isPresent()) {
			if (!newField.get().equals(player.getField())) {
				if (!newField.get().isBlocked()) {
					int difference = player.getField().getType().equals(FieldType.TIEFERSAND) ? 300 : 110;
					if (lastMovementTime == 0 || System.currentTimeMillis() - lastMovementTime > difference) {
						for (Pokemon mon : player.getPokemon()) {
							double hp = mon.getHp();
							double hpBase = mon.calculateHp();
							if (hpBase > hp) {
								hp = hp + 1.0;
								mon.setHp(hp);
							}
						}
						lastMovementTime = System.currentTimeMillis();

						Field newF = newField.get();
						if (newF.getType().equals(FieldType.UEBERGANG)) {
							if (x == 570) {
								newX = 0;
							} else if (x == 0) {
								newX = 570;
							} else if (y == 480) {
								newY = 0;
							} else if (y == 0) {
								newY = 480;
							}
							newF.setX(newX);
							newF.setY(newY);
							double xx = newX;
							double yy = newY;
							newField = mapView.getFields().stream()
									.filter(each -> each.getX() == xx && each.getY() == yy).findFirst();
						}
						player.getField().setEntity(null);
						newF.setEntity(player);
						player.setField(newField.get());
						Platform.runLater(() -> mapView.update());
						if (newF.getType().equals(FieldType.HOHESGRASS)
								|| newF.getType().equals(FieldType.TIEFERSAND)) {
							int randDig = new Random().nextInt(100);
							if (randDig < 12) {
								fightMenu();
							}
						} else if (newF.getNextMap() != null) {
							Map map = Main.xmlControll.getMap(new File(newF.getNextMap()));
							mapView.setMap(map);
							Main.gameData.setMap(map);
						}
						mapView.update();
					}

				}

			}
		}

	}

	private void fightAgainstTrainer(Trainer entity) {
		// new Alert(AlertType.CONFIRMATION, "Kampf gegen " + entity.getName() + ".
		// Bereits gewonnen: " + entity.isWin()).show();
		ButtonType acceptButton = new ButtonType("Ok");
		Alert acceptAlert = new Alert(AlertType.CONFIRMATION);
		acceptAlert.setHeaderText("Hey, du!");
		acceptAlert.setContentText(entity.getName()
				+ ": Ja, du! Meinen Pokemon ist langweilig.\nIch wette, dass sie deine mit links besiegen!");
		acceptAlert.getButtonTypes().setAll(acceptButton);
		acceptAlert.showAndWait();
		Pokemon winner = null;
		for (int j = 0; j < entity.getPokemons().size(); j++) {
			Pokemon enemyPokemon = entity.getPokemons().get(j);
			acceptAlert.setHeaderText("Kampf gegen " + entity.getName());
			acceptAlert.setContentText(entity.getName() + " schickt " + enemyPokemon.getName() + " Lvl "
					+ enemyPokemon.getLevel() + " in den Kampf.");
			acceptAlert.getButtonTypes().setAll(acceptButton);
			acceptAlert.showAndWait();
			for (int i = 0; i < player.getPokemon().size(); i++) {
				Pokemon pokemon = player.getPokemon().get(i);
				int currentLvl = pokemon.getLevel();
				winner = pokemon.fight(enemyPokemon);
				if (winner != null) {
					if (player.getPokemon().contains(winner)) {
						System.out.println(pokemon.getName() + " hat " + enemyPokemon.calcXp() + " Xp erhalten");
						winner.addXp(enemyPokemon.calcXp());
						acceptAlert.setHeaderText(
								winner.getName() + " hat " + enemyPokemon.calcXp() + " Erfahrungspunkte erhalten.");
						if (winner.getLevel() != currentLvl) {
							acceptAlert.setContentText(winner.getName() + " ist ein Level aufgestiegen.");
						}
						acceptAlert.getButtonTypes().setAll(acceptButton);
						Optional<ButtonType> result = acceptAlert.showAndWait();
						entity.setWin(true);
						break;
					} else {
						// new Alert(AlertType.INFORMATION, "Haha! Was habe ich gesagt? Meine Pokemon
						// sind nicht so einfach zu besiegen!\n" + winner.getName() + " hat " + " dein
						// Pokemon besiegt.").show();
						acceptAlert.setHeaderText(winner.getName() + " hat " + " dein Pokemon besiegt.");
						acceptAlert.setContentText(entity.getName()
								+ ": Haha! Was habe ich gesagt?\nMeine Pokemon sind nicht so einfach zu besiegen!");
						acceptAlert.getButtonTypes().setAll(acceptButton);
						acceptAlert.showAndWait();
						for (int k = 0; k < entity.getPokemons().size(); k++) {
							entity.getPokemons().get(k).setHp(entity.getPokemons().get(k).getBase_hp());
							;
						}
					}
				}
			}
		}

	}
}
