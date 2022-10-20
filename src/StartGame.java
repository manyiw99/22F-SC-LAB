import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StartGame {
    List<int[]> carrierLoc,battleShip1Loc,battleShip2Loc,submarine1Loc,
            submarine2Loc,submarine3Loc, patrol1Loc, patrol2Loc, patrol3Loc, patrol4Loc;
    GridController oceanGrid, targetGrid;
    Tools tool;

    /**
     * Users place boats
     */
    public void setup(){
        oceanGrid = new GridController("OCEAN GRID");
        targetGrid = new GridController("TARGET GRID");

        oceanGrid.drawGrid();
        targetGrid.drawGrid();

        tool = new Tools();

        System.out.println("Place boats carrier: ");
        carrierLoc = tool.FormatInput(6);
        for(int i = 0;i < carrierLoc.size(); i++){
            oceanGrid.setGrid(carrierLoc.get(i), FLAGS.C, "C", false);
        }

        System.out.println("Place boats BattleShip 1: ");
        battleShip1Loc = tool.FormatInput(4);
        for(int i = 0;i < battleShip1Loc.size(); i++){
            oceanGrid.setGrid(battleShip1Loc.get(i), FLAGS.B, "B1", false);
        }

        System.out.println("Place boats BattleShip 2: ");
        battleShip2Loc = tool.FormatInput(4);
        for(int i = 0;i < battleShip2Loc.size(); i++){
            oceanGrid.setGrid(battleShip2Loc.get(i), FLAGS.B, "B2", false);
        }

        System.out.println("Place s1: ");
        submarine1Loc = tool.FormatInput(3);
        for(int i = 0;i < submarine1Loc.size(); i++){
            oceanGrid.setGrid(submarine1Loc.get(i), FLAGS.S, "S1", false);
        }

        System.out.println("Place s2: ");
        submarine2Loc = tool.FormatInput(3);
        for(int i = 0;i < submarine2Loc.size(); i++){
            oceanGrid.setGrid(submarine2Loc.get(i), FLAGS.S, "S2", false);
        }

        System.out.println("Place s3: ");
        submarine3Loc = tool.FormatInput(3);

        for(int i = 0;i < submarine3Loc.size(); i++){
            oceanGrid.setGrid(submarine3Loc.get(i), FLAGS.S, "S3", false);
        }

        System.out.println("Place p1: ");
        patrol1Loc = tool.FormatInput(2);
        System.out.println("Place p2: ");
        patrol2Loc = tool.FormatInput(2);
        System.out.println("Place p3: ");
        patrol3Loc = tool.FormatInput(2);
        System.out.println("Place p4: ");
        patrol4Loc = tool.FormatInput(2);
        for(int i = 0;i < patrol1Loc.size(); i++){
            oceanGrid.setGrid(patrol1Loc.get(i), FLAGS.P, "P1", false);
        }
        for(int i = 0;i < patrol2Loc.size(); i++){
            oceanGrid.setGrid(patrol2Loc.get(i), FLAGS.P, "P2", false);
        }
        for(int i = 0;i < patrol3Loc.size(); i++){
            oceanGrid.setGrid(patrol3Loc.get(i), FLAGS.P, "P3", false);
        }
        for(int i = 0;i < patrol4Loc.size(); i++){
            oceanGrid.setGrid(patrol4Loc.get(i), FLAGS.P, "P4", false);
        }

        System.out.println("human has placed all boats");

        targetGrid.generateRandomGrid();
        System.out.println("computer has placed all boats");
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

    public String guess(boolean firstPlayer){
        // System.out.println(Arrays.toString(guessNum));
        String result =null;

        if (firstPlayer==true){

            System.out.println("Enter the guess location:");
            int[] guessNum = tool.FormatInput(0).get(0);

            if(targetGrid.retrieveFlag(guessNum)==null){
                result="MISS";
                System.out.println("MISS");
                targetGrid.setGrid(guessNum,FLAGS.O,targetGrid.retrieveName(guessNum),true);
                targetGrid.drawGrid();
            }else if((targetGrid.retrieveFlag(guessNum)==FLAGS.C)||(targetGrid.retrieveFlag(guessNum)==FLAGS.B)
                    ||(targetGrid.retrieveFlag(guessNum)==FLAGS.S)||(targetGrid.retrieveFlag(guessNum)==FLAGS.P)){
                if(targetGrid.retrieveGuess(guessNum)==false) {

                    FLAGS original_flag = (targetGrid.retrieveFlag(guessNum));
                    targetGrid.setGrid(guessNum, FLAGS.X, targetGrid.retrieveName(guessNum), true);

                    if (targetGrid.isSunk(targetGrid.retrieveName(guessNum))) {
                        result = "sunk";
                        System.out.println("Boats " + targetGrid.retrieveName(guessNum) + " sunk");
                        targetGrid.setGrid(guessNum, original_flag, targetGrid.retrieveName(guessNum), true);

                        if (targetGrid.isFinish()) {
                            result = "human win";
                            System.out.println("all boats sunk");
                        }
                        targetGrid.drawGrid();
                    } else {
                        result = "HIT";
                        System.out.println("HIT");
                        targetGrid.setGrid(guessNum, FLAGS.X, targetGrid.retrieveName(guessNum), true);
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
            // random生成一个坐标 int[]
            // test case
            int[] guessNum = {1,2};

            if(oceanGrid.retrieveFlag(guessNum)==null){
                result="MISS";
                System.out.println("MISS");
                oceanGrid.setGrid(guessNum,FLAGS.O,oceanGrid.retrieveName(guessNum),true);
                oceanGrid.drawGrid();
            }else if((oceanGrid.retrieveFlag(guessNum)==FLAGS.C)||(oceanGrid.retrieveFlag(guessNum)==FLAGS.B)
                    ||(oceanGrid.retrieveFlag(guessNum)==FLAGS.S)||(oceanGrid.retrieveFlag(guessNum)==FLAGS.P)){
                result="HIT";
                System.out.println("HIT");
                oceanGrid.setGrid(guessNum,FLAGS.X,oceanGrid.retrieveName(guessNum),true);
                oceanGrid.drawGrid();
            }else if((oceanGrid.retrieveFlag(guessNum)==FLAGS.X)||(oceanGrid.retrieveFlag(guessNum)==FLAGS.O)){
                System.out.println("Has been chosen before, try again");
                // guess(firstPlayer);
            }
        }

        return result;
    }

    public static void main(String[] args){
        System.out.println("---------Start Game-------------");
        StartGame startGame = new StartGame();
        startGame.setup();
        boolean firstPlayer = startGame.isFirst();

        String result = null;

        while((result!="human win")||(result!="computer win")){
            if(firstPlayer==true){
                System.out.println("human guess round");
            }else{
                System.out.println("computer guess round");
            }
            result = startGame.guess(firstPlayer);
            firstPlayer = !firstPlayer;
        }
    }
}
