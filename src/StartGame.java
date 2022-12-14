import java.util.*;

/**
 * Start class
 */
public class StartGame {
    GridController oceanGrid, targetGrid, targetGrid_show; //oceanGrid: human  targetGrid: computer  targetGrid_show: grid shown to human
    Tools tool;

    /**
     * Place user boats
     *
     * @param input
     * @param f
     * @param name
     * @param guess
     */

    public boolean placeBoat(FLAGS f, String name, boolean guess, String input, int length) {
        boolean valid = tool.placementValidation(input, length);
        //System.out.println(valid);
        if (!valid) {
            System.out.println("Your input value is invalid. Please try again.");
            return false;
        }


        List<int[]> loc = tool.formatInput(length, input);

        // check if the input value is duplicate
        boolean dup = true;
        for (int i = 0; i < loc.size(); i++) {
            if (oceanGrid.retrieveFlag(loc.get(i)).isPresent()) {
                dup = false;
                break;
            }
        }

        if (dup) {
            if(loc.get(0)[0] == loc.get(1)[0]){ //Horizontal placement of boats
                int min = Math.min(loc.get(0)[1], loc.get(1)[1]);
                for (int i = 0; i < length; i++){
                    int[] location = new int[]{loc.get(0)[0], min + i};
                    oceanGrid.setGrid(location, Optional.of(f), Optional.of(name), guess);
                }
            } else {
                int min = Math.min(loc.get(0)[0], loc.get(1)[0]);
                for (int i = 0; i < length; i++){
                    int[] location = new int[]{min + i, loc.get(0)[1]};
                    oceanGrid.setGrid(location, Optional.of(f), Optional.of(name), guess);
                }
            }
            return true;
        } else {
            System.out.println("Your input value already exists. Please try again.");
            return false;
        }
    }

    /**
     * Initialize game board and setup game
     */
    public void setup() {
        List<int[]> carrierLoc, battleShip1Loc, battleShip2Loc, submarine1Loc,
                submarine2Loc, submarine3Loc, patrol1Loc, patrol2Loc, patrol3Loc, patrol4Loc;

        oceanGrid = new GridController("OCEAN GRID");
        targetGrid = new GridController("TARGET GRID - ACTUAL");
        targetGrid_show = new GridController("TARGET GRID");

        // Empty game board
        targetGrid_show.drawGrid();
        oceanGrid.drawGrid();

        tool = new Tools();

        // Place boat carrier-----------------------------------------------------------------------------------------
        boolean c_dup = false;
        while (c_dup == false) {
            System.out.println("Place boats carrier (length 6): ");
            String input = tool.readUser();
            // carrierLoc = tool.FormatInput(6, input);
            c_dup = placeBoat(FLAGS.C, "C", false, input, 6);

        }
        oceanGrid.drawGrid();

        // Place boat battleship------------------------------------------------------------------------------------
        boolean b1_dup = false;
        while (b1_dup == false) {
            System.out.println("Place boats BattleShip 1 (length 4): ");
            String input = tool.readUser();
            // battleShip1Loc = tool.FormatInput(4, input);
            b1_dup = placeBoat(FLAGS.B, "B1", false, input, 4);

        }
        oceanGrid.drawGrid();

        boolean b2_dup = false;
        while (b2_dup == false) {
            System.out.println("Place boats BattleShip 2 (length 4): ");
            String input = tool.readUser();
            // battleShip2Loc = tool.FormatInput(4, input);
            b2_dup = placeBoat(FLAGS.B, "B2", false, input, 4);
        }
        oceanGrid.drawGrid();

        // Place boat s---------------------------------------------------------------------------------------------
        boolean s1_dup = false;
        while (s1_dup == false) {
            System.out.println("Place boats Submarine 1 (length 3): ");
            String input = tool.readUser();
            // submarine1Loc = tool.FormatInput(3, input);
            s1_dup = placeBoat(FLAGS.S, "S1", false, input, 3);
        }
        oceanGrid.drawGrid();

        boolean s2_dup = false;
        while (s2_dup == false) {
            System.out.println("Place boats Submarine 2 (length 3): ");
            String input = tool.readUser();
            // submarine2Loc = tool.FormatInput(3, input);
            s2_dup = placeBoat(FLAGS.S, "S2", false, input, 3);
        }
        oceanGrid.drawGrid();

        boolean s3_dup = false;
        while (s3_dup == false) {
            System.out.println("Place boats Submarine 3 (length 3): ");
            String input = tool.readUser();
            // submarine3Loc = tool.FormatInput(3, input);
            s3_dup = placeBoat(FLAGS.S, "S3", false, input, 3);
        }
        oceanGrid.drawGrid();

        // Place boat p------------------------------------------------------------------------------------------------
        boolean p1_dup = false;
        while (p1_dup == false) {
            System.out.println("Place boats Patrol 1 (length 2): ");
            String input = tool.readUser();
            // patrol1Loc = tool.FormatInput(2, input);
            p1_dup = placeBoat(FLAGS.P, "P1", false, input, 2);
        }
        oceanGrid.drawGrid();

        boolean p2_dup = false;
        while (p2_dup == false) {
            System.out.println("Place boats Patrol 2 (length 2): ");
            String input = tool.readUser();
            // patrol2Loc = tool.FormatInput(2, input);
            p2_dup = placeBoat(FLAGS.P, "P2", false, input, 2);
        }
        oceanGrid.drawGrid();

        boolean p3_dup = false;
        while (p3_dup == false) {
            System.out.println("Place boats Patrol 3 (length 2): ");
            String input = tool.readUser();
            // patrol3Loc = tool.FormatInput(2, input);
            p3_dup = placeBoat( FLAGS.P, "P3", false, input, 2);
        }
        oceanGrid.drawGrid();

        boolean p4_dup = false;
        while (p4_dup == false) {
            System.out.println("Place boats Patrol 4 (length 2): ");
            String input = tool.readUser();
            // patrol4Loc = tool.FormatInput(2, input);
            p4_dup = placeBoat(FLAGS.P, "P4", false, input, 2);
        }
        oceanGrid.drawGrid();

        System.out.println("Human has placed all boats.");

        targetGrid.generateRandomGrid();
        System.out.println("Computer has placed all boats.");
        targetGrid_show.drawGrid();
        //targetGrid.drawGrid();
        oceanGrid.drawGrid();
    }

    /**
     * Decide the first player
     *
     * @return true: human
     * false:computer
     */
    public boolean isFirst() {
        return new Random().nextBoolean();
    }

    public int[] computerGuess(GridController gridController){
        List<int[]> allLoc = new ArrayList<>();

        for(int i =0; i<10; i++){
            for(int j=0; j<10; j++){
                if(gridController.retrieveFlag(new int[]{i,j}).isEmpty()){
                    allLoc.add(new int[]{i,j});
                }
                else if(!((gridController.retrieveFlag(new int[]{i,j}).get() == FLAGS.X) || (gridController.retrieveFlag(new int[]{i,j}).get() == FLAGS.O))){
                    allLoc.add(new int[]{i,j});
                }
            }
        }

        int index = (int)(Math.random() * allLoc.size());
        return allLoc.get(index);
    }

    /**
     * Guess process
     *
     * @param firstPlayer
     * @return result
     */
    public boolean guess(boolean firstPlayer) {
        // System.out.println(Arrays.toString(guessNum));
        boolean result = false;

        // Human guess round
        if (firstPlayer) {
            boolean valid = false;
            int[] guessNum = {-1, -1};
            while (!valid) {
                System.out.println("Human guess round");
                System.out.println("Enter the guess location:");
                String input = tool.readUser();
                valid = tool.guessValidation(input);
                if (!valid) {
                    System.out.println("Sorry, your input is invalid. Please try again");
                } else {
                    guessNum[1] = tool.convertLetterToInt(input.charAt(0));
                    guessNum[0] = Integer.parseInt(input.substring(1));
                }
            }

            if (targetGrid.retrieveFlag(guessNum).isEmpty()) {  //Grid that never been chosen before
                result = false;
                targetGrid.setGrid(guessNum, Optional.of(FLAGS.O), Optional.empty(), true);
                targetGrid_show.setGrid(guessNum, Optional.of(FLAGS.O), Optional.empty(), true);
                System.out.println("MISS");
                targetGrid_show.drawGrid();
            } else if ((targetGrid.retrieveFlag(guessNum).get() == FLAGS.C) || (targetGrid.retrieveFlag(guessNum).get() == FLAGS.B)
                    || (targetGrid.retrieveFlag(guessNum).get() == FLAGS.S) || (targetGrid.retrieveFlag(guessNum).get() == FLAGS.P)) {

                if (!targetGrid.retrieveGuess(guessNum)) {  // never been chosen before
                    targetGrid.setGrid(guessNum, Optional.of(FLAGS.X), targetGrid.retrieveName(guessNum), true);
                    targetGrid_show.setGrid(guessNum, Optional.of(FLAGS.X), targetGrid.retrieveName(guessNum), true);

                    if (targetGrid.isSunk(targetGrid.retrieveName(guessNum).get())) {
                        result = false;
                        List<int[]> locs = targetGrid.recoverFlag(targetGrid.retrieveName(guessNum).get());
                        for (int i = 0; i < locs.size(); i++) {
                            targetGrid_show.setGrid(locs.get(i), targetGrid.retrieveFlag(locs.get(i)),
                                    targetGrid.retrieveName(locs.get(i)), true);
                            targetGrid.setGrid(locs.get(i), targetGrid.retrieveFlag(locs.get(i)),
                                    targetGrid.retrieveName(locs.get(i)), true);
                        }

                        System.out.println("Boats " + targetGrid.retrieveName(guessNum).get() + " of computer sunk");
                        targetGrid.setSunkNum(targetGrid.getSunkNum() + 1);

                        if (targetGrid.isFinish()) {

                            result = true;
                            System.out.println("All boats of computer sunk");

                        }
                        targetGrid_show.drawGrid();
                    } else {
                        result = false;
                        targetGrid.setGrid(guessNum, Optional.of(FLAGS.X), targetGrid.retrieveName(guessNum), true);
                        targetGrid_show.setGrid(guessNum, Optional.of(FLAGS.X), targetGrid.retrieveName(guessNum), true);
                        System.out.println("HIT");
                        targetGrid_show.drawGrid();
                    }
                } else {
                    System.out.println("Has been chosen before, try again");
                    guess(firstPlayer);
                }

            } else if ((targetGrid.retrieveFlag(guessNum).get() == FLAGS.X) || (targetGrid.retrieveFlag(guessNum).get() == FLAGS.O)) {
                System.out.println("Has been chosen before, try again");
                guess(firstPlayer);
            }
        } else {
            System.out.println("Computer guess round");
            // Randomly generate the guess location
            int[] guessNum = computerGuess(oceanGrid);
            System.out.println("Computer guesses location: " + Arrays.toString(guessNum));

            if (oceanGrid.retrieveFlag(guessNum).isEmpty()) {
                result = false;
                oceanGrid.setGrid(guessNum, Optional.of(FLAGS.O), oceanGrid.retrieveName(guessNum), true);
                System.out.println("MISS");
                oceanGrid.drawGrid();
            } else if ((oceanGrid.retrieveFlag(guessNum).get() == FLAGS.C) || (oceanGrid.retrieveFlag(guessNum).get() == FLAGS.B)
                    || (oceanGrid.retrieveFlag(guessNum).get() == FLAGS.S) || (oceanGrid.retrieveFlag(guessNum).get() == FLAGS.P)) {
                result = false;
                oceanGrid.setGrid(guessNum, Optional.of(FLAGS.X), oceanGrid.retrieveName(guessNum), true);
                System.out.println("HIT");
                System.out.println("Computer hits boat "+oceanGrid.retrieveName(guessNum).get());

                if (oceanGrid.isSunk(oceanGrid.retrieveName(guessNum).get())) {
                    result = false;
                    System.out.println("Boats " + oceanGrid.retrieveName(guessNum).get() + " of human sunk");
                    oceanGrid.setSunkNum(oceanGrid.getSunkNum() + 1);

                    if (oceanGrid.isFinish()) {
                        result = true;
                        System.out.println("All boats of human sunk");
                        return result;
                    }
                }

                oceanGrid.drawGrid();
            } else if ((oceanGrid.retrieveFlag(guessNum).get() == FLAGS.X) || (oceanGrid.retrieveFlag(guessNum).get() == FLAGS.O)) {
                System.out.println("Has been chosen before, try again");
                guess(firstPlayer);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("-------Start Game----------");
        StartGame startGame = new StartGame();

        // Initialize game board
        startGame.setup();

        // Decide first player of each round, true means human first
        boolean firstPlayer = startGame.isFirst();

        boolean result = false;
        while (!result) {

            result = startGame.guess(firstPlayer);
            firstPlayer = !firstPlayer;
        }

        if(firstPlayer){
            System.out.println("Computer wins!");
        }else{
            System.out.println("Human wins!");
        }
    }
}
