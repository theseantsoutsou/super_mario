package game.npcs;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.actions.SpeakAction;
import game.actions.TradeAction;
import game.behaviours.Behaviour;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.items.Wrench;

import java.util.HashMap;
import java.util.Map;

/**
 * Friendly character the player can interact with such as talking or trading
 */
public class Toad extends Actor {
    private final Map<Integer, Behaviour> behaviours = new HashMap<>();
    private static Toad instance;

    /**
     * Constructor
     */
    public Toad() {
        super("Toad", 'O', 50);
        addItemToInventory(new PowerStar());
        addItemToInventory(new SuperMushroom());
        addItemToInventory(new Wrench());
    }

    /**
     * Toad has the ability to either talk to the player or trade with the player
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return
     */
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        if (otherActor.hasCapability(Status.TRADE)) {
            actions.add(TradeAction.getTradeActions(this));
        }
        if (otherActor.hasCapability(Status.CONVERSES)) {
            actions.add(new SpeakAction(this));
        }
        return actions;
    }

    /**
     * Figure out what to do next.
     * Toad does nothing when not interacting with the player (does not move ever)
     * @see Actor#playTurn(ActionList, Action, GameMap, Display)
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Creates toad at a specific part of the game map
     * @param gameMap
     */
    public static void createToad(GameMap gameMap) {
        if (instance == null) {
            instance = new Toad();
            int toadX = gameMap.getXRange().max() / 2 + 5;
            int toadY = gameMap.getYRange().max() / 2 + 1;
            gameMap.at(toadX, toadY).addActor(instance);
        }
    }

    /**
     * creates a toad instance
     * @return
     */
    static public Toad getInstance() {
        return instance;
    }
}
