package action_and_validation_tracker;

import frontend.game_components.GameObject;
import frontend.game_components.Player;
import map_tracker.GameMapInitializer;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;

public class KeyboardAnimation implements ActionListener {
    private final static String PRESSED = "pressed ";
    private final static String RELEASED = "released ";

    private JComponent component;
    private Timer timer;
    private Timer timer2;
    private GameMapInitializer floor;
    private ActionTracker tracker;
    private Player player;
    private Map<String, GameObject.Move> pressedKeys = new HashMap<String, GameObject.Move>();
    private Map<String, Player> bomb = new HashMap<String, Player>();

    public KeyboardAnimation(JComponent component, Player player, int delay, GameMapInitializer floor, ActionTracker tracker) {
        this.component = component;
        this.floor = floor;
        this.tracker = tracker;
        this.player = player;

        timer = new Timer(delay, this);
        timer.setInitialDelay(0);

        timer2 = new Timer(delay, this::actionPerformed2);
        timer2.setInitialDelay(0);
    }

    /*
     *  &param keyStroke - see KeyStroke.getKeyStroke(String) for the format of
     *                     of the String. Except the "pressed|released" keywords
     *                     are not to be included in the string.
     */

    public void addBombAction(String keyStroke, Player player){


        int offset = keyStroke.lastIndexOf(" ");
        String key = offset == -1 ? keyStroke : keyStroke.substring(offset + 1);
        String modifiers = keyStroke.replace(key, "");

        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();

        Action pressedAction = new BombAction(key, player);
        String pressedKey = modifiers + PRESSED + key;
        KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(pressedKey);
        inputMap.put(pressedKeyStroke, pressedKey);
        actionMap.put(pressedKey, pressedAction);

        Object actionName = inputMap.get(pressedKeyStroke);


        Action releasedAction = new BombAction(key, null);
        String releasedKey = modifiers + RELEASED + key;
        KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(releasedKey);
        inputMap.put(releasedKeyStroke, releasedKey);
        actionMap.put(releasedKey, releasedAction);



    }

    public void addAction(String keyStroke, GameObject.Move move) {
        //  Separate the key identifier from the modifiers of the KeyStroke


        int offset = keyStroke.lastIndexOf(" ");
        String key = offset == -1 ? keyStroke : keyStroke.substring(offset + 1);
        String modifiers = keyStroke.replace(key, "");



        //  Get the InputMap and ActionMap of the component

        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();

        //  Create Action and add binding for the pressed key
//        Point point  = new Point(move.getX(), move.getY());
        // System.out.println("POINT: " + move.getX() + "  " + move.getY());

        Action pressedAction = new AnimationAction(key, move);
        String pressedKey = modifiers + PRESSED + key;
        KeyStroke pressedKeyStroke = KeyStroke.getKeyStroke(pressedKey);
        inputMap.put(pressedKeyStroke, pressedKey);
        actionMap.put(pressedKey, pressedAction);

        //  Create Action and add binding for the released key

        Action releasedAction = new AnimationAction(key, null);
        String releasedKey = modifiers + RELEASED + key;
        KeyStroke releasedKeyStroke = KeyStroke.getKeyStroke(releasedKey);
        inputMap.put(releasedKeyStroke, releasedKey);
        actionMap.put(releasedKey, releasedAction);

    }

    //  Invoked whenever a key is pressed or released

    private void handleBombEvent(String key, Player player){
        if (player == null)
        {
            bomb.remove( key );
        }
        else
        {
            bomb.put(key, player);
        }

        //  Start the Timer when the first key is pressed

        if (bomb.size() == 1) {
            timer2.start();
        }

        //  Stop the Timer when all keys have been released

        if (bomb.size() == 0) {
            timer2.stop();
        }
    }

    private void handleKeyEvent(String key, GameObject.Move moveDelta) {
        //  Keep track of which keys are pressed
        if (moveDelta == null)
        {
            pressedKeys.remove( key );
        }
        else
        {
            pressedKeys.put(key, moveDelta);
        }
        //  Start the Timer when the first key is pressed
        if (pressedKeys.size() == 1) {
            timer.start();
        }
        //  Stop the Timer when all keys have been released
        if (pressedKeys.size() == 0) {
            timer.stop();
        }
    }

    //  Invoked when the Timer fires

    public void actionPerformed(ActionEvent e) {
        if(player.IsAlive()){
            moveComponent();
        }
    }

    public void actionPerformed2(ActionEvent e) {
        if(player.IsAlive()){
            dropBomb();
        }
    }

    //  Move the component to its new location

    private void dropBomb(){
        for (Player player : bomb.values()) {
            int row = player.getRowIndex();
            int col = player.getColIndex();
            tracker.dropBomb(row,col,floor,player);
        }
    }

    private void moveComponent() {
        //  Calculate new move

        int deltaX = 0;
        int deltaY = 0;
        GameObject.Move move = null;

        int count = 0;

        for (GameObject.Move delta : pressedKeys.values()) {
            count++;
            if(count == 1){
                move = delta;
            }
            deltaX += delta.getX();
            deltaY += delta.getY();
        }
        tracker.movePlayer(move, deltaX, deltaY, this.player, this.floor);
    }

    private class AnimationAction extends AbstractAction implements ActionListener
    {
        private GameObject.Move moveDelta;

        public AnimationAction(String key, GameObject.Move moveDelta)
        {
            super(key);

            this.moveDelta = moveDelta;
        }

        public void actionPerformed(ActionEvent e)
        {
            handleKeyEvent((String)getValue(NAME), moveDelta);
        }
    }

    private class BombAction extends AbstractAction implements ActionListener
    {
        private Player player;

        public BombAction(String key, Player player)
        {
            super(key);

            this.player = player;
        }

        public void actionPerformed(ActionEvent e)
        {
            handleBombEvent((String)getValue(NAME), player);
        }
    }
}

