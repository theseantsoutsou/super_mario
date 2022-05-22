package game.actors;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import game.Status;
import game.actions.AttackAction;
import game.behaviours.AttackBehaviour;
import game.behaviours.Behaviour;
import game.behaviours.FollowBehaviour;
import game.behaviours.SpeechBehaviour;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public abstract class Enemy extends Actor implements Speaks{
    //Private attribute
    private final Map<Integer, Behaviour> behaviours = new TreeMap<>(); // priority, behaviour
    private ArrayList<String> monologues = new ArrayList<>();
    /**
     * Constructor.
     *
     * @param name        the name of the Actor
     * @param displayChar the character that will represent the Actor in the display
     * @param hitPoints   the Actor's starting hit points
     */
    public Enemy(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
        this.behaviours.put(1, new AttackBehaviour());
        this.behaviours.put(2, new SpeechBehaviour(this));
        this.registerSpeech();
    }

    public Map<Integer, Behaviour> getBehaviours() {
        return this.behaviours;
    }

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {

        ActionList actions = new ActionList();

        if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction));
            if (this.hasCapability(Status.ATTACKED) || this.hasCapability(Status.GOT_ATTACKED)) {
                this.behaviours.put(2, new FollowBehaviour(otherActor));
            }
        }

        return actions;
    }

    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {

        for (Behaviour behaviour : behaviours.values()) {
            Action action = behaviour.getAction(this, map);
            if (action != null) {
                return action;
            }
        }

        return new DoNothingAction();
    }
    public ArrayList<String> getMonologues(){
        return monologues;
    }
}