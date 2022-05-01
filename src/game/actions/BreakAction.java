package game.actions;

import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Status;
import game.npcs.Koopa;

/**
 * Special Action for attacking other Actors.
 */
public class BreakAction extends Action {

    /**
     * The Koopa Shell that is to be broken
     */
    private Actor target;

    /**
     * The direction of incoming attack.
     */
    private String direction;

    /**
     * Random number generator
     */
    protected Random rand = new Random();

    /**
     * Constructor.
     *
     * @param target the Actor to attack
     */
    public BreakAction(Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    /**
     * Executes the action of destroying a koopa shell only when the koopa is in a dormant state
     * causes the dormant koopa to drop a SuperMushroom when shell is destroyed
     * @param actor The actor performing the action.
     * @param map   The map the actor is on.
     * @return
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        String result = null;

        ActionList dropActions = new ActionList();
        // drop all items
        for (Item item : this.target.getInventory())
            dropActions.add(item.getDropAction(actor));
        for (Action drop : dropActions)
            drop.execute(this.target, map);
        // remove actor
        map.removeActor(this.target);
        result = actor + " broke Koopa's shell";
        result += System.lineSeparator() + this.target + " is killed";

        return result;
    }

    /**
     * Display description of when player destroys a koopa shell
     * @param actor The actor performing the action.
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " breaks " + this.target + "'s shell at " + direction;

    }
}
