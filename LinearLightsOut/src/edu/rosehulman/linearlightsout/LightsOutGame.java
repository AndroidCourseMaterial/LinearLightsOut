package edu.rosehulman.linearlightsout;

import java.util.Random;

/**
 * 
 * Model class for a linear lights out game. Clicking a button toggles the state
 * of that button and any neighboring buttons. The goal is to make all the
 * buttons match.
 * 
 */
public class LightsOutGame {
    private int[] buttonValues;
    private int numPresses;
    private boolean doingSetup;
    private static final int RANDOMIZER_MULTIPLIER = 10;
    public static final int MIN_NUM_BUTTONS = 3;

    /**
     * Default constructor (defaults to 7 buttons)
     */
    public LightsOutGame() {
        this(7);
    }

    /**
     * Create a new linear lights out game with a certain number of buttons
     * 
     * @param numButtons
     *            Number of buttons in this linear lights out game
     */
    public LightsOutGame(int numButtons) {
        if (numButtons < MIN_NUM_BUTTONS) {
            // CONSIDER: Throw an exception. For now just enforce the minimum.
            numButtons = MIN_NUM_BUTTONS;
        }
        this.doingSetup = true;
        this.buttonValues = new int[numButtons];
        this.randomizeButtons();
        this.doingSetup = false;
    }

    private void randomizeButtons() {
        // Attempt #1: Randomly select a value for each button.
        // Might result in an unwinable game with some number of buttons
        // Random generator = new Random();
        // for (int i = 0 ; i < this.buttonValues.length ; i++) {
        // this.buttonValues[i] = generator.nextInt(2);
        // }
        // Attempt #2: Start with a win and randomly press buttons
        Random generator = new Random();
        for (int i = 0; i < this.buttonValues.length * RANDOMIZER_MULTIPLIER; i++) {
            pressedButtonAtIndex(generator.nextInt(this.buttonValues.length));
        }
        // Make sure the game is not currently a win
        while (checkForWin() && this.buttonValues.length > 2) {
            pressedButtonAtIndex(generator.nextInt(this.buttonValues.length));
        }
        this.numPresses = 0;
    }

    /**
     * When the user presses a button call this method to update the game then
     * read values to determine the updated game state.
     * 
     * @param buttonIndex
     *            The button that is pressed in the view
     * @return Returns if this press resulted in all the buttons matching
     */
    public boolean pressedButtonAtIndex(int buttonIndex) {
        if (buttonIndex < 0 || buttonIndex >= this.buttonValues.length)
            return false;
        if (!this.doingSetup && checkForWin())
            return true;
        this.numPresses++;
        this.toggleValueAtIndex(buttonIndex - 1);
        this.toggleValueAtIndex(buttonIndex);
        this.toggleValueAtIndex(buttonIndex + 1);
        return this.checkForWin();
    }

    /**
     * Convenience method that returns if the game is in a win state. See also:
     * Return value of pressedButtonAtIndex
     * 
     * @return true if all the button values match
     */
    public boolean checkForWin() {
        int winnerColor = this.buttonValues[0];
        for (int i = 1; i < this.buttonValues.length; i++) {
            if (this.buttonValues[i] != winnerColor)
                return false;
        }
        return true;
    }

    private void toggleValueAtIndex(int i) {
        if (i >= 0 && i < this.buttonValues.length) {
            this.buttonValues[i] ^= 1;
        }
    }

    /**
     * Get the state of a button at index
     * 
     * @param buttonIndex
     *            The button of interest
     * @return A 0 or 1 value to indicate the button state (0 for OFF, 1 for ON)
     */
    public int getValueAtIndex(int buttonIndex) {
        return this.buttonValues[buttonIndex];
    }

    /**
     * Get the number of button presses so far in this game
     * 
     * @return The number of legal calls to pressedButtonAtIndex
     */
    public int getNumPresses() {
        return numPresses;
    }

    @Override
    public String toString() {
        String ret = "";
        for (int aButtonValue : this.buttonValues) {
            ret += aButtonValue;
        }
        return ret;
    }
}
