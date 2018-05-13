package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import application.Main;
import field.Field;
import field.FieldType;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import pokemon.Pokemon;
import pokemon.PokemonType;
import views.MapView;
import views.PokemonView;

public class GameLogic extends Thread {
	MapView mapView;
	Player player;
	long lastMovement = 0;
	AnchorPane anchor2;

	public GameLogic(MapView mapView, AnchorPane anchor2) {
		this.mapView = mapView;
		List<Field> field = this.mapView.getFields().stream().filter(each -> !each.isBlocked())
				.collect(Collectors.toList());

		Field f = field.get(new Random().nextInt(field.size()));
		player = new Player();

		player.setImage(new Image("/images/player_straight.png"));
		player.setField(f);
		f.setEntity(player);
		mapView.update();
		this.anchor2 = anchor2;
	}

	public void update() {
		Platform.runLater(() -> {
			this.mapView.update();

			long count = anchor2.getChildren().stream().filter(each -> each.getClass().equals(PokemonView.class))
					.count();

			if (count < player.getPokemon().size()) {
				for (int i = (int) count; i < player.getPokemon().size(); i++) {
					Pokemon pokemon = player.getPokemon().get(i);
					PokemonView pv = new PokemonView(pokemon);
					pv.setLayoutX(45);
					pv.setLayoutY(10 + i * 55);
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
		alert.setTitle("Aktions Fenster");
		List<Pokemon> listOfPokemons = new ArrayList<>();

		if (player.getPokemon().size() > 0) {
			List<PokemonType> pokemonTypes = mapView.getMap().getPokemonTypes();

			for (PokemonType type : pokemonTypes) {
				listOfPokemons.addAll(Main.xmlControll.getPokemonsByType(type));
			}

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

		alert.setHeaderText("Ein wildes " + spawnedPokemon.getName() + " ist erschienen");
		alert.setContentText("Bitte waehle deine Aktion.");

		ButtonType kampfButton = new ButtonType("Angreifen");
		ButtonType fangButton = new ButtonType("Fangen");
		ButtonType fliehButton = new ButtonType("Fliehen");

		alert.getButtonTypes().setAll(fliehButton);
		if (player.getPokemon().size() != 0) {
			alert.getButtonTypes().add(kampfButton);
		}

		if (player.getPokemon().size() < 3) {
			alert.getButtonTypes().add(fangButton);
		}

		Optional<ButtonType> result = alert.showAndWait();
		ButtonType buttonType = result.get();
		if (buttonType != null) {
			if (buttonType.equals(kampfButton)) {
				int q = 0;
				Pokemon winner = null;
				for (int i = 0; i < player.getPokemon().size(); i++) {
					Pokemon pokemon = player.getPokemon().get(i);
					winner = pokemon.fight(spawnedPokemon);
					if(winner != null) {
						System.out.println(pokemon.getName() + " hat " + spawnedPokemon.calcXp() + " erhalten");
						winner.addXp(spawnedPokemon.calcXp());
						break;
					}
				}
			} else if (buttonType.equals(fangButton)) {
				spawnedPokemon.setXp(0);
				if (player.getPokemon().size() == 0) {
					player.getPokemon().add(spawnedPokemon);
				} else {
					int iValue = new Random().nextInt(100);
					if (spawnedPokemon.getSpawn() > 3) {
						if (iValue > 45)
							player.getPokemon().add(spawnedPokemon);
					} else if (spawnedPokemon.getSpawn() > 2) {
						if (iValue > 60)
							player.getPokemon().add(spawnedPokemon);
					} else if (spawnedPokemon.getSpawn() > 1) {
						if (iValue > 70)
							player.getPokemon().add(spawnedPokemon);
					} else {
						if (iValue > 80)
							player.getPokemon().add(spawnedPokemon);
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
			newY -= 40;
			player.setImage(Main.player_straight);
			break;
		case "D":
			newX += 40;
			player.setImage(Main.player_right);
			break;
		case "S":
			newY += 40;
			player.setImage(Main.player_back);
			break;
		case "A":
			newX -= 40;
			player.setImage(Main.player_left);
			break;
		default:
			break;
		}
		double x = newX;
		double y = newY;

		Optional<Field> newField = mapView.getFields().stream().filter(each -> each.getX() == x && each.getY() == y)
				.findFirst();
		if (newField.isPresent()) {
			if (!newField.get().isBlocked()) {
				int difference = player.getField().getType().equals(FieldType.TIEFERSAND) ? 250 : 110;
				if (lastMovement == 0 || System.currentTimeMillis() - lastMovement > difference) {
					for (Pokemon mon : player.getPokemon()) {
						double hp = mon.getHp();
						double hpBase = mon.calculateHp();
						if (hpBase > hp) {
							hp = hp + 1.0;
							mon.setHp(hp);
						}
					}
					lastMovement = System.currentTimeMillis();
					Field newF = newField.get();
					player.getField().setEntity(null);
					newF.setEntity(player);
					player.setField(newField.get());
					Platform.runLater(() -> mapView.update());
					if (newF.getType().equals(FieldType.HOHESGRASS) || newF.getType().equals(FieldType.TIEFERSAND)) {
						int randDig = new Random().nextInt(100);
						if (randDig < 14) {
							System.out.println("Ein wildes Pokemon greift an...");
							fightMenu();
						}
					}
					mapView.update();
				}

			}
		}

	}
}
