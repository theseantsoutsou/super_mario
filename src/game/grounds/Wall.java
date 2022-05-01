package game.grounds;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;
import game.actions.FlattenAction;
import game.actions.JumpAction;
import game.Status;

/**
 * Class for a Wall Ground Object (High Ground)
 */
public class Wall extends Ground implements Jumpable {
	//Private Attributes
	private static final int JUMP_SUCCESS_RATE = 80;
	private static final int FALL_DAMAGE = 20;

	public Wall() {
		super('#');
	}

	public int getSuccessRate() {
		return JUMP_SUCCESS_RATE;
	}

	public int getFallDamage() {
		return FALL_DAMAGE;
	}

	@Override
	public boolean canActorEnter(Actor actor) {
		return false;
	}
	
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	/**
	 * Allows the player to jump onto a Wall from lower ground (if dice roll succeeds)
	 * Allows the player to traverse between Wall objects
	 * @param actor     the Actor acting
	 * @param location  the current Location
	 * @param direction the direction of the Ground from the Actor
	 * @return
	 */
	@Override
	public ActionList allowableActions(Actor actor, Location location, String direction){
		ActionList actions = new ActionList();

		Boolean sameGround = location.map().locationOf(actor).equals(location);

		if (actor.hasCapability(Status.POWER_STAR) && !sameGround) {
			actions.add(new FlattenAction(this, direction));
		}else if(actor.hasCapability(Status.HOSTILE_TO_ENEMY) && !sameGround) {
			actions.add(new JumpAction(this, direction));
		}

		return actions;
	}
}
