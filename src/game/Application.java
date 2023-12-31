package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;
import game.actors.Peach;
import game.actors.Player;
import game.grounds.*;

import game.actors.Bowser;
import game.actors.Toad;
import game.items.PowerStar;

/**
 * The Application class is the main class that drives the Mario World game.
 *
 * @author FIT2099 Teaching Team, Connor Gibson, Shang-Fu Tsou, Lucus Choy
 * @version 2.0
 * @since 02-May-2022
 */
public class Application {

	public static void main(String[] args) {

		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout(),
				new WarpPipe(), new Lava(), new PowerFountain(), new HealthFountain());

		List<String> map = Arrays.asList(
				"..........................................##..........+.........................",
				"............+............+..................#...................................",
				"............................................#...................................",
				".............C...............................##......................+..........",
				"...............................................#................................",
				"......................................C.........#...............................",
				".................+................................#.............................",
				"....................................H......A.....##.............................",
				"................................................##..............................",
				".........+......................C.......+#____####.................+............",
				".......................................+#_____###++.............................",
				".......................................+#______###..............................",
				"........................................+#_____###.........C....................",
				"........................+........................##.............+...............",
				".......................................C...........#............................",
				"....................................................#...........................",
				"...................+.................................#..........................",
				"......................................................#.........................",
				".......................................................##.......................");
		GameMap gameMap = new GameMap(groundFactory, map);
		world.addGameMap(gameMap);
		TeleportManager.getInstance().addGameMap("gameMap", gameMap);

		List<String> lavaMap = Arrays.asList(
				"C...................##..................................",
				"............................................#...........",
				"..........LLLLLL..........L......#......................",
				"..............L......##..........L......................",
				".............L..............#..........L................",
				".................L.............#.......L................",
				"................LLLLLLL.L..............#.......L........",
				".................LL..........##..........L..............",
				"...............LL...............................##......",
				"...............................___..L...................",
				"........................L......___..................LL..",
				"............................L..___..........LLL.........",
				".............................L..........................",
				".................................................##.....",
				".................................L.................#....");
		GameMap lavaZone = new GameMap(groundFactory, lavaMap);
		world.addGameMap(lavaZone);
		TeleportManager.getInstance().addGameMap("lavaZone", lavaZone);

		Actor mario = new Player("Player", 'm', 100);
		//world.addPlayer(mario, lavaZone.at(6, 10));
		world.addPlayer(mario, gameMap.at(42, 10));

		Actor toad = new Toad();
		Actor bowser = new Bowser(4, 10);
		Actor peach = new Peach();
		gameMap.addActor(bowser, lavaZone.at(4, 10));
		gameMap.addActor(peach, lavaZone.at(3, 10));
		gameMap.addActor(toad, gameMap.at(44, 10));

		world.run();

	}
}
