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
import xml.XmlControll;

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
		
//		player.getPokemon().add(Main.xmlControll.getPokedex().get(2));
//		player.getPokemon().add(Main.xmlControll.getPokedex().get(1));
		player.setImage(new Image("/images/player_straight.png"));
		player.setField(f);
		f.setEntity(player);
		mapView.update();
		this.anchor2 = anchor2;
	}
	
	public void update() {
		Platform.runLater(() ->{
			this.mapView.update();
			
			long count = anchor2.getChildren().stream().filter(each -> each.getClass().equals(PokemonView.class)).count();
			
			if(count < player.getPokemon().size()) {
				for(int i = 0; i < player.getPokemon().size(); i++) {
					Pokemon pokemon = player.getPokemon().get(i);
					PokemonView pv = new PokemonView(pokemon);
					pv.setLayoutX(45);
					pv.setLayoutY(10+i*55);
					anchor2.getChildren().add(pv);
				}
			}else {
				for(int i = 0; i < player.getPokemon().size(); i++) {
					((PokemonView)anchor2.getChildren().get(i)).setPokemon(player.getPokemon().get(i));
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

		List<PokemonType> pokemonTypes = mapView.getMap().getPokemonTypes();
		List<Pokemon> listOfPokemons = new ArrayList<>();
	
		for(PokemonType type: pokemonTypes) {
			listOfPokemons.addAll(Main.xmlControll.getPokemonsByType(type));			
		}
		
		List<Pokemon> allPokemons = new ArrayList<>();
		if(player.getPokemon().size() > 0 ) {
			for(Pokemon pokemon: listOfPokemons) {
				int i = (int) (pokemon.getSpawn() * 100);
				for(int j = 0; j < i; j++) {
					allPokemons.add(pokemon);
				}
			}	
		}else {
			allPokemons.add(Main.xmlControll.getPokemonByName("Schiggy"));
			allPokemons.add(Main.xmlControll.getPokemonByName("Bisasam"));
			allPokemons.add(Main.xmlControll.getPokemonByName("Glumanda"));
		}
		
	
		System.out.println(allPokemons);
		Pokemon pokemon = allPokemons.get(new Random().nextInt(allPokemons.size()));
		pokemon.setLevel(2);
				
		
		alert.setHeaderText("Ein wildes " + pokemon.getName() + " level:  " + pokemon.getLevel() + " ist erschienen");
		alert.setContentText("Bitte waehle deine Aktion.");
	
		ButtonType kampfButton = new ButtonType("Angreifen");
		ButtonType fangButton = new ButtonType("Fangen");
		ButtonType fliehButton = new ButtonType("Fliehen");
	
		alert.getButtonTypes().add(fliehButton);
		if(player.getPokemon().size() != 0) {
			alert.getButtonTypes().add(kampfButton);
		}
		
		if(player.getPokemon().size() < 3) {
			alert.getButtonTypes().add(fangButton);
		}
		
		Optional<ButtonType> result = alert.showAndWait();
		ButtonType buttonType = result.get();
		if(buttonType != null) {
			if(buttonType.equals(kampfButton)) {
				Pokemon.fight(pokemon, player.getPokemon().get(0));
			}else if(buttonType.equals(fangButton)) {
				if(player.getPokemon().size() == 0) {
					player.getPokemon().add(pokemon);
				}else {
					int iValue = new Random().nextInt(100);
					if(pokemon.getSpawn()>3) {
						if(iValue>45)
							player.getPokemon().add(pokemon);
					}else if(pokemon.getSpawn()>2) {
						if(iValue>60)
							player.getPokemon().add(pokemon);
					}else if(pokemon.getSpawn()>1) {
						if(iValue>70) 
							player.getPokemon().add(pokemon);
					}else {
						if(iValue>80)
							player.getPokemon().add(pokemon);
					}
				}
			}else {
				
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
					lastMovement = System.currentTimeMillis();
					Field newF = newField.get();
					player.getField().setEntity(null);
					newF.setEntity(player);
					player.setField(newField.get());
					Platform.runLater(() -> mapView.update());
			//		mapView.update();
					if(newF.getType().equals(FieldType.HOHESGRASS) || newF.getType().equals(FieldType.TIEFERSAND)) {
						int randDig = new Random().nextInt(100);
						if(randDig < 14) {
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
