import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Start class
 */
public class StartGame {
    GridController oceanGrid, targetGrid;
    Tools tool;

    /**
     * Place user boats
     * @param loc
     * @param f
     * @param name
     * @param guess
     */
    public boolean placeBoat(List<int[]> loc, FLAGS f, String name, boolean guess){
        // check if the input value is duplicate
        boolean dup = true;
        for(int i =0; i<loc.size();i++){
            if(oceanGrid.retrieveFlag(loc.get(i))!=null){
                dup=false;
                break;
            }
        }

        if(dup) {
            for (int i = 0; i < loc.size(); i++) {
                oceanGrid.setGrid(loc.get(i), f, name, guess);
            }
            return true;
        }else{
            System.out.println("Your input value already exists. Please try again.");
            return false;
        }
    }

    /**
     * Initialize game board and setup game
     */
    public void setup(){
        List<int[]> carrierLoc,battleShip1Loc,battleShip2Loc,submarine1Loc,
                submarine2Loc,submarine3Loc, patrol1Loc, patrol2Loc, patrol3Loc, patrol4Loc;

        oceanGrid = new GridController("OCEAN GRID");
        targetGrid = new GridController("TARGET GRID");

        // Empty game board
        oceanGrid.drawGrid();
        targetGrid.drawGrid();

        tool = new Tools();

        // Place boat carrier-----------------------------------------------------------------------------------------
        boolean c_dup=false;
        while(c_dup==false){
            System.out.println("Place boats carrier (length 6): ");
            carrierLoc = tool.FormatInput(6);
            c_dup = placeBoat(carrierLoc,FLAGS.C,"C",false);
        }

        // Place boat battleship------------------------------------------------------------------------------------
        boolean b1_dup = false;
        while(b1_dup==false) {
            System.out.println("Place boats BattleShip 1: ");
            battleShip1Loc = tool.FormatInput(4);
            b1_dup=placeBoat(battleShip1Loc, FLAGS.B, "B1", false);
        }

        boolean b2_dup = false;
        while(b2_dup==false) {
            System.out.println("Place boats BattleShip 2: ");
            battleShip2Loc = tool.FormatInput(4);
            b2_dup=placeBoat(battleShip2Loc, FLAGS.B, "B2", false);
        }

        // Place boat s---------------------------------------------------------------------------------------------
        boolean s1_dup = false;
        while(s1_dup==false) {
            System.out.println("Place s1: ");
            submarine1Loc = tool.FormatInput(3);
            s1_dup=placeBoat(submarine1Loc, FLAGS.S, "S1", false);
        }

        boolean s2_dup = false;
        while (s2_dup==false) {
            System.out.println("Place s2: ");
            submarine2Loc = tool.FormatInput(3);
            s2_dup=placeBoat(submarine2Loc, FLAGS.S, "S2", false);
        }

        boolean s3_dup = false;
        while (s3_dup==false) {
            System.out.println("Place s3: ");
            submarine3Loc = tool.FormatInput(3);
            s3_dup=placeBoat(submarine3Loc, FLAGS.S, "S3", false);
        }

        // Place boat p------------------------------------------------------------------------------------------------
        boolean p1_dup = false;
        while (p1_dup==false) {
            System.out.println("Place p1: ");
            patrol1Loc = tool.FormatInput(2);
            p1_dup=placeBoat(patrol1Loc, FLAGS.P, "P1", false);
        }

        boolean p2_dup = false;
        while(p2_dup==false) {
            System.out.println("Place p2: ");
            patrol2Loc = tool.FormatInput(2);
            p2_dup=placeBoat(patrol2Loc, FLAGS.P, "P2", false);
        }

        boolean p3_dup = false;
        while (p3_dup==false) {
            System.out.println("Place p3: ");
            patrol3Loc = tool.FormatInput(2);
            p3_dup=placeBoat(patrol3Loc, FLAGS.P, "P3", false);
        }

        boolean p4_dup = false;
        while (p4_dup==false) {
            System.out.println("Place p4: ");
            patrol4Loc = tool.FormatInput(2);
            p4_dup=placeBoat(patrol4Loc, FLAGS.P, "P4", false);
        }

        System.out.println("Human has placed all boats.");

        targetGrid.generateRandomGrid();
        System.out.println("Computer has placed all boats.");
        targetGrid.drawGrid();
        oceanGrid.drawGrid();
    }

    /**
     * Decide the first player
     * @return true: human
     *         false:computer
     */
    public boolean isFirst(){
        return new Random().nextBoolean();
    }

    /**
     * Guess process
     * @param firstPlayer
     * @return result
     */
    public String guess(boolean firstPlayer){
        // System.out.println(Arrays.toString(guessNum));
        String result =null;

        // Human guess round
        if (firstPlayer==true){
            System.out.println("Human guess round");
            System.out.println("Enter the guess location:");
            int[] guessNum = tool.FormatInput(0).get(0);

            if(targetGrid.retrieveFlag(guessNum)==null){  //Grid that never been chosen before
                result="MISS";
                targetGrid.setGrid(guessNum,FLAGS.O,targetGrid.retrieveName(guessNum),true);
                System.out.println("MISS");
                targetGrid.drawGrid();
            }else if((targetGrid.retrieveFlag(guessNum)==FLAGS.C)||(targetGrid.retrieveFlag(guessNum)==FLAGS.B)
                    ||(targetGrid.retrieveFlag(guessNum)==FLAGS.S)||(targetGrid.retrieveFlag(guessNum)==FLAGS.P)){

                if(targetGrid.retrieveGuess(guessNum)==false) {  // never been chosen before
                    targetGrid.setGrid(guessNum, FLAGS.X, targetGrid.retrieveName(guessNum), true);

                    if (targetGrid.isSunk(targetGrid.retrieveName(guessNum))) {
                        result = "sunk";
                        targetGrid.recoverFlag(targetGrid.retrieveName(guessNum));
                        System.out.println("Boats " + targetGrid.retrieveName(guessNum) + " of computer sunk");
                        targetGrid.setSunkNum(targetGrid.getSunkNum()+1);

                        if (targetGrid.isFinish()) {
                            result = "Human wins";
                            System.out.println("all boats of computer sunk");
                        }
                        targetGrid.drawGrid();
                    } else {
                        result = "HIT";
                        targetGrid.setGrid(guessNum, FLAGS.X, targetGrid.retrieveName(guessNum), true);
                        System.out.println("HIT");
                        targetGrid.drawGrid();
                    }
                }else{
                    System.out.println("Has been chosen before, try again");
                    guess(firstPlayer);
                }

            }else if((targetGrid.retrieveFlag(guessNum)==FLAGS.X)||(targetGrid.retrieveFlag(guessNum)==FLAGS.O)){
                System.out.println("Has been chosen before, try again");
                guess(firstPlayer);
            }
        }else{
            System.out.println("Computer guess round");
            // Randomly generate the guess location
            int[] guessNum = {new Random().nextInt(10),new Random().nextInt(10)};
            System.out.println("Computer guesses location: "+Arrays.toString(guessNum));

            if(oceanGrid.retrieveFlag(guessNum)==null){
                result="MISS";
                oceanGrid.setGrid(guessNum,FLAGS.O,oceanGrid.retrieveName(guessNum),true);
                System.out.println("MISS");
                oceanGrid.drawGrid();
            }else if((oceanGrid.retrieveFlag(guessNum)==FLAGS.C)||(oceanGrid.retrieveFlag(guessNum)==FLAGS.B)
                    ||(oceanGrid.retrieveFlag(guessNum)==FLAGS.S)||(oceanGrid.retrieveFlag(guessNum)==FLAGS.P)){
                result="HIT";
                oceanGrid.setGrid(guessNum,FLAGS.X,oceanGrid.retrieveName(guessNum),true);
                System.out.println("HIT");

                if (oceanGrid.isSunk(targetGrid.retrieveName(guessNum))){
                    result = "sunk";
                    System.out.println("Boats " + oceanGrid.retrieveName(guessNum) + " of human sunk");
                    oceanGrid.setSunkNum(oceanGrid.getSunkNum()+1);

                    if (oceanGrid.isFinish()) {
                        result = "Computer wins";
                        System.out.println("All boats of human sunk");
                    }
                }

                oceanGrid.drawGrid();
            }else if((oceanGrid.retrieveFlag(guessNum)==FLAGS.X)||(oceanGrid.retrieveFlag(guessNum)==FLAGS.O)){
                System.out.println("Has been chosen before, try again");
                guess(firstPlayer);
            }
        }

        return result;
    }

    public static void main(String[] args){
        System.out.println("----------Start Game-------------");
        StartGame startGame = new StartGame();

        // Initialize game board
        startGame.setup();

        // Decide first player of each round, true means human first
        boolean firstPlayer = startGame.isFirst();

        String result = null;
        while((result!="Human wins")||(result!="Computer wins")){
            result = startGame.guess(firstPlayer);
            firstPlayer = !firstPlayer;
        }
    }
}
