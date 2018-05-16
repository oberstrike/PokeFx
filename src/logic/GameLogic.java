package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import application.Main;
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

	- NN	evtl Level (automatisches Laden der nächsten Karte bei Besiegen eines Trainers o.Ä.
	- CHECK Bilder der Pokemon, sowohl rechts in der Liste als auch beim Kampf
	- 		Plätze tauschen (erstes Pokemon der Liste durch ein anderes ersetzen)
	- CHECK	Motivation abhängig vom Ausgang der letzten 5-10 Kämpfe setzen
	- CHECK XP-Balken zeigt noch keinen Fortschritt CHECK
	- CHECK	Klick auf Beenden beendet Spiel
	- CHECK Spielerposition bei Kartenwechsel anpassen
	- CHECK	mehrere Übergänge auf einer Map
	- 		Bild bei Entwicklung anpassen
	- CHECK	Maps bauen
	- 		Pokedex im Spiel

*/

public class GameLogic extends Thread {
	private MapView mapView;
	private Player player;
	private long lastMovementTime = 0;
	private String lastMovement;
	private AnchorPane anchor2;

	public GameLogic(MapView mapView, AnchorPane anchor2, GameData gameData) {
		this.mapView = mapView;
		List<Field> field = this.mapView.getFields().stream().filter(each -> !each.isBlocked())
				.collect(Collectors.toList());

		Field f = field.get(new Random().nextInt(field.size()));

		if (gameData.getPlayer() == null) {
			player = new Player();
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
			
			if(this.mapView.getFields().stream().anyMatch(each -> each.getEntity()!=null)) {
				Field field = player.getField();
				Field new_field = this.mapView.getFields().stream().filter(each -> each.getX() == field.getX() && each.getY() == field.getY()).findFirst().get();
				player.setField(new_field);
			}
			
			this.mapView.update();

			long count = anchor2.getChildren().stream().filter(each -> each.getClass().equals(PokemonView.class))
					.count();

			if (count < player.getPokemon().size()) {
				for (int i = (int) count; i < player.getPokemon().size(); i++) {
					Pokemon pokemon = player.getPokemon().get(i);
					PokemonView pv = new PokemonView(pokemon);
					pv.setLayoutX(45);
					pv.setLayoutY(10 + i * 95);
					anchor2.getChildren().add(pv);
				}
			} else {
				for (int i = 0; i < player.getPokemon().size(); i++) {
					((PokemonView) anchor2.getChildren().get(i)).update();
				}
			}

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
			listOfPokemons.forEach(each -> each.setLevel(1 + new Random().nextInt(player.getAverageLevel()) + 1 + player.getPokemon().size()));
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
		
		int entryPokedex = player.getPokedex().get(spawnedPokemon.getId());
		String entryOutput = "";
		if (entryPokedex == 1) {
			entryOutput = "Du hast dieses Pokemon bereits gesehen.";
		} else if (entryPokedex == 2) {
			entryOutput = "Du hast dieses Pokemon bereits gefangen.";
		} else {
			entryOutput = "Ein Eintrag im Pokedex wurde hinzugefügt";
			player.setPokedex(spawnedPokemon, 1);
		}

		alert.setHeaderText(
				"Ein wildes " + spawnedPokemon.getName() + " Lvl. " + spawnedPokemon.getLevel() + " ist erschienen.\n" + entryOutput);
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
							System.out.println(pokemon.getName() + " hat " + spawnedPokemon.calcXp() + " Xp erhalten");
							winner.addXp(spawnedPokemon.calcXp());
							acceptAlert.setHeaderText(winner.getName() + " hat " + spawnedPokemon.calcXp()
									+ " Erfahrungspunkte erhalten.");
							if (winner.getLevel() != currentLvl) {
								acceptAlert.setContentText(winner.getName() + " ist ein Level aufgestiegen.");
							}
							ButtonType acceptButton = new ButtonType("Ok");
							acceptAlert.getButtonTypes().setAll(acceptButton);
							result = acceptAlert.showAndWait();
							buttonType = result.get();
							break;
						} else {
							System.out.println("Dein Pokemon: " + pokemon.getName());
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
			if (lastMovement != null) {
				switch (lastMovement) {
				case "W":
					oField = mapView.getMap().upField(player.getField());
					break;
				case "A":
					oField = mapView.getMap().leftField(player.getField());
					break;
				case "S":
					oField = mapView.getMap().bottomField(player.getField());
					break;
				case "D":
					oField = mapView.getMap().rightField(player.getField());
					break;
				default:
					break;
				}
				System.out.println(oField);
				if (oField.isPresent()) {
					Field field = oField.get();
					if (field.getEntity() != null) {
						field.getEntity().interact(player);
					}
				}
			}
			break;
		default:
			break;
		}
		
		double x = newX;
		double y = newY;

		// System.out.println("Playerposition: (" + x + "|" + y + ")");
		
		Optional<Field> newField = mapView.getFields().stream().filter(each -> each.getX() == x && each.getY() == y)
				.findFirst();
		
		if (newField.isPresent()) {
			if (!newField.get().equals(player.getField())) {
				if (!newField.get().isBlocked()) {
					int difference = player.getField().getType().equals(FieldType.TIEFERSAND) ? 250 : 110;
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
						lastMovement = keyName;
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
							newField = mapView.getFields().stream().filter(each -> each.getX() == xx && each.getY() == yy)
									.findFirst();
						}
						player.getField().setEntity(null);
						newF.setEntity(player);
						player.setField(newField.get());
						Platform.runLater(() -> mapView.update());
						if (newF.getType().equals(FieldType.HOHESGRASS)
								|| newF.getType().equals(FieldType.TIEFERSAND)) {
							int randDig = new Random().nextInt(100);
							if (randDig < 14) {
								System.out.println("Ein wildes Pokemon greift an...");
								fightMenu();
							}
						}else if(newF.getNextMap()!= null){
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
}
