package game.actions;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.MoveActorAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class WarpAction extends MoveActorAction {

    public WarpAction(Location moveToLocation) {
        super(moveToLocation, "other map");
    }
    @Override
    public String execute(Actor actor, GameMap map) {
        Actor actorAtDestination = moveToLocation.getActor();
        if (actorAtDestination!=null){map.removeActor(actorAtDestination);}
        map.moveActor(actor, moveToLocation);
        return menuDescription(actor);
    }
    @Override
    public String menuDescription(Actor actor) {
        return actor + " teleports to " + direction;
    }
}