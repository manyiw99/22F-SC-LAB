import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class GridController {
    String gridName;
    Grid grids[][];
    int sunkNum;
    int NUM=10;

    public GridController(String gridName){
        this.gridName=gridName;
        sunkNum=0;

        grids = new Grid[10][10];
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[0].length; j++) {
                int[] loc={i,j};
                grids[i][j] = new Grid(loc, Optional.empty(),Optional.empty(),false);
            }
        }
    }

    public void drawGrid(){
        System.out.println(" ==== " + gridName + " ====");
        System.out.println("  A B C D E F G H I J");
        System.out.println(" +-+-+-+-+-+-+-+-+-+-+");

//        FLAGS[][] flags = new FLAGS[10][10];
        for (int i = 0; i < grids.length; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < grids[0].length; j++) {
                if(grids[i][j].getFlag().isEmpty()){
                    System.out.print(" |");
                }else{
                    FLAGS f = grids[i][j].getFlag().get();
                    if(f == FLAGS.X){
                        System.out.print("X|");
                    } else if(f == FLAGS.B){
                        System.out.print("B|");
                    } else if(f == FLAGS.C){
                        System.out.print("C|");
                    } else if(f == FLAGS.P){
                        System.out.print("P|");
                    } else if(f == FLAGS.O){
                        System.out.print("O|");
                    } else if(f == FLAGS.S){
                        System.out.print("S|");
                    }
                }
//                flags[i][j] = grids[i][j].getFlag().orElse(null);
            }
            System.out.println(i);
        }
        System.out.println(" +-+-+-+-+-+-+-+-+-+-+");
        System.out.println("  A B C D E F G H I J\n");
//        System.out.println(Arrays.deepToString(flags).replace("], ", "]\n"));

    }

    public void setGrid(int[] loc, Optional<FLAGS> f, Optional<String> name, boolean guess){
        //System.out.println("In gridController class:"+ Arrays.toString(loc) +" "+f +" name: "+name); //ok
        grids[loc[0]][loc[1]].setFlag(f);
        grids[loc[0]][loc[1]].setName(name);
        grids[loc[0]][loc[1]].setGuess(guess);
    }

    public Optional<FLAGS> retrieveFlag(int[] loc){
        return grids[loc[0]][loc[1]].getFlag();
    }

    public Optional<String> retrieveName(int[] loc){
        return grids[loc[0]][loc[1]].getName();
    }

    public boolean retrieveGuess(int[] loc){
        return grids[loc[0]][loc[1]].getGuess();
    }



    public void generateRandomGrid(){
        // Initialize first and final location of a boat
        int[] firstLoc = {0, 0};
        int[] finalLoc = {0, 1};
        int x,y; // x and y axis value
        int len = 2; // boat length
        // Init the direction of boat
        // value 0 for goes up, 1 for right, 2 for down, 3 for right
        int dir = 0;
        Random r = new Random();
        // 随机生成坐标， eg.[1,2],[1,3]
        // 生成所有船的位置之后，挨个set每个格子
        // Generate one Carrier
        // first randomly generate firstLoc
        boolean b = true;
        while (b){
            x = r.nextInt(10);
            y = r.nextInt(10);
            firstLoc = new int[]{x, y};
            dir = r.nextInt(4);
            switch (dir){
                case 0:
                    y = y-5;
                    finalLoc = new int[]{x,y};
                    break;
                case 1:
                    x = x+5;
                    finalLoc = new int[]{x,y};
                    break;
                case 2:
                    y = y+5;
                    finalLoc = new int[]{x,y};
                    break;
                case 3:
                    x = x-5;
                    finalLoc = new int[]{x,y};
                    break;
            }
            // check if the boat is in right bound
            if(finalLoc[0]<10 && finalLoc[1]<10 && finalLoc[0]>-1 && finalLoc[1]>-1){
                b = false; //correct, jump out of the loop
                // set grid
                switch (dir){
                    case 0:
                        for(int i=0;i<6;i++){
                            setGrid(firstLoc,Optional.of(FLAGS.C),Optional.of("C1"),false);
                            firstLoc[1] = firstLoc[1] - 1;
                        }
                        break;
                    case 1:
                        for(int i=0;i<6;i++){
                            setGrid(firstLoc,Optional.of(FLAGS.C),Optional.of("C1"),false);
                            firstLoc[0] = firstLoc[0] + 1;
                        }
                        break;
                    case 2:
                        for(int i=0;i<6;i++){
                            setGrid(firstLoc,Optional.of(FLAGS.C),Optional.of("C1"),false);
                            firstLoc[1] = firstLoc[1] + 1;
                        }
                        break;
                    case 3:
                        for(int i=0;i<6;i++){
                            setGrid(firstLoc,Optional.of(FLAGS.C),Optional.of("C1"),false);
                            firstLoc[0] = firstLoc[0] - 1;
                        }
                        break;
                }

            }
        }


        int[] randomLoc = {1,1};
        setGrid(randomLoc,Optional.of(FLAGS.B),Optional.of("B3"),false);
    }

    // method for generate correct boat and set grid to every location of boat
    // length 6 for carrier, 4 for battleship, 3 for submarines, 2 for Patrol boat
    public void generateRandomBoat(int len){
        int[] firstLoc = {0, 0};
        int[] finalLoc = {0, 1};
        int x,y; // x and y axis value
        // Init the direction of boat
        // value 0 for goes up, 1 for right, 2 for down, 3 for right
        int dir = 0;
        boolean b = true;
        Random r = new Random();
        while (b){
            x = r.nextInt(10);
            y = r.nextInt(10);
            firstLoc = new int[]{x, y};
            dir = r.nextInt(4);
            switch (dir){
                case 0:
                    y = y-len;
                    finalLoc = new int[]{x,y};
                    break;
                case 1:
                    x = x+len;
                    finalLoc = new int[]{x,y};
                    break;
                case 2:
                    y = y+len;
                    finalLoc = new int[]{x,y};
                    break;
                case 3:
                    x = x-len;
                    finalLoc = new int[]{x,y};
                    break;
            }
            // check if the boat is in right bound
            if(finalLoc[0]<10 && finalLoc[1]<10 && finalLoc[0]>-1 && finalLoc[1]>-1){
                b = false; //correct, jump out of the loop
                // set grid
                switch (dir){
                    case 0:
                        for(int i=0;i<len;i++){
                            setGrid(firstLoc,Optional.of(FLAGS.C),Optional.of("C1"),false);
                            firstLoc[1] = firstLoc[1] - 1;
                        }
                        break;
                    case 1:
                        for(int i=0;i<6;i++){
                            setGrid(firstLoc,Optional.of(FLAGS.C),Optional.of("C1"),false);
                            firstLoc[0] = firstLoc[0] + 1;
                        }
                        break;
                    case 2:
                        for(int i=0;i<6;i++){
                            setGrid(firstLoc,Optional.of(FLAGS.C),Optional.of("C1"),false);
                            firstLoc[1] = firstLoc[1] + 1;
                        }
                        break;
                    case 3:
                        for(int i=0;i<6;i++){
                            setGrid(firstLoc,Optional.of(FLAGS.C),Optional.of("C1"),false);
                            firstLoc[0] = firstLoc[0] - 1;
                        }
                        break;
                }

            }
        }

        /**
         * Check if the boat is sunk or not
         * @param name
         * @return true: sunk
         */
    public boolean isSunk(String name){
        boolean result=false;
        System.out.println(name);

        for(int i = 0;i < grids.length; i++){
            for (int j = 0; j < grids[0].length; j++) {
                if (grids[i][j].getName().isPresent()) {
                    if(grids[i][j].getName().get()==name) {
                        if (grids[i][j].getFlag().get() != FLAGS.X) {
                            result = false;
                            break;
                        } else {
                            result = true;

                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * If boat sunk, recover all flags of this boat from 'X' to it's type
     * @param name
     */
    public List<int[]> recoverFlag(String name){
        List<int[]> locs = new ArrayList<>();

        FLAGS originalFlag = null;
        for(FLAGS f: FLAGS.values()){
            if(f.name().equals(name.substring(0,1))){
                originalFlag = f;
                break;
            }
        }

        for(int i = 0;i < grids.length; i++){
            for (int j = 0; j < grids[0].length; j++) {
                if(grids[i][j].getName().isPresent()) {
                    if (grids[i][j].getName().get() == name) {
                        grids[i][j].setFlag(Optional.of(originalFlag));
                        locs.add(new int[]{i,j});
                    }
                }
            }
        }

        return locs;
    }

    public int getSunkNum(){
        return sunkNum;
    }
    public void setSunkNum(int num){
        this.sunkNum=num;
    }

    /**
     * Check if the game is finished
     * @return
     */
    public boolean isFinish(){
        if(sunkNum==NUM){
            return true;
        }else{
            return false;
        }

    }


}
