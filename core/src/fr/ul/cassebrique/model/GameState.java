package fr.ul.cassebrique.model;

/**
 * Created by noahd on 02/02/2018.
 */

public class GameState {
    public enum State{
        Running,
        BallLoss,
        GameOver,
        Won,
        Pause,
        Quit;
    }

    private State state;

    public GameState(){
        state = State.Running;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
