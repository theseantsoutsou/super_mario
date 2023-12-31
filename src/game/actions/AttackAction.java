package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.weapons.Weapon;
import game.Status;

import java.util.Random;

/**
 * Abstract class which extends the Action class and contains the logic for attacking.
 * @author Sean Tsou
 * @since 15/05/22
 */
public abstract class AttackAction extends Action {

    /**
     * The Actor that is to be attacked
     */
    protected Actor target;

    /**
     * The direction of incoming attack.
     */
    protected String direction;

    /**
     * Constructor
     * @param target
     * @param direction
     */
    protected AttackAction (Actor target, String direction) {
        this.target = target;
        this.direction = direction;
    }

    @Override
    /**
     * Execute an attack on target on another actor
     * @param actor, map
     * @return String
     */
    public String execute(Actor actor, GameMap map) {
        Random rand = new Random();

        Weapon weapon = actor.getWeapon();

        int chance = weapon.chanceToHit();

        if ((rand.nextInt(100) > chance)) {
            return actor + " misses " + this.target + ".";
        }

        if (this.getTarget().hasCapability(Status.POWER_STAR)) {
            return this.target + " is invincible! " + this.target + " takes no damage!";
        }

        this.implementAttack(actor, map);

        int damage = weapon.damage();

        this.target.hurt(damage);

        return actor + " " + weapon.verb() + " " + this.target + " for " + damage + " damage." + this.result(map);
    }

    @Override
    public abstract String menuDescription(Actor actor);

    public void implementAttack(Actor actor, GameMap map) {

    }

    /**
     * Get result of the attack. Removes deceased Actor, makes Koopa dormant.
     * @param map
     * @return String
     */
    public String result(GameMap map) {
        String result = "";
        if (!this.target.isConscious()) {
            if (!this.target.hasCapability(Status.CAN_SLEEP)) {
                // remove actor
                map.removeActor(this.target);
                result = System.lineSeparator() + this.target + " was killed";
            }
            else {
                this.target.addCapability(Status.DORMANT);
                result = System.lineSeparator() + this.target + " went dormant.";
            }
        }
        return result;
    }

    /**
     * Defeated Actor drops contents of inventory.
     * @param actor
     * @param map
     */

    public void dropLoot(Actor actor, GameMap map) {
        ActionList dropActions = new ActionList();
        // drop all items
        for (Item item : this.target.getInventory())
            dropActions.add(item.getDropAction(actor));
        for (Action drop : dropActions)
            drop.execute(this.target, map);
    }

    /**
     * Return a reference to the target of the attack.
     * @return Actor
     */
    public Actor getTarget() {
        return this.target;
    }

    /**
     * Get the direction of the attack
     * @return String
     */

    public String getDirection() {
        return this.direction;
    }
}
