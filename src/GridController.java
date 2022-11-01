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
    int NUM = 10;

    public GridController(String gridName) {
        this.gridName = gridName;
        sunkNum = 0;

        grids = new Grid[10][10];
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[0].length; j++) {
                int[] loc = {i, j};
                grids[i][j] = new Grid(loc, Optional.empty(), Optional.empty(), false);
            }
        }
    }

    public void drawGrid() {
        System.out.println(" ==== " + gridName + " ====");
        System.out.println("  A B C D E F G H I J");
        System.out.println(" +-+-+-+-+-+-+-+-+-+-+");

//        FLAGS[][] flags = new FLAGS[10][10];
        for (int i = 0; i < grids.length; i++) {
            System.out.print(i + "|");
            for (int j = 0; j < grids[0].length; j++) {
                if (grids[i][j].getFlag().isEmpty()) {
                    System.out.print(" |");
                } else {
                    FLAGS f = grids[i][j].getFlag().get();
                    if (f == FLAGS.X) {
                        System.out.print("X|");
                    } else if (f == FLAGS.B) {
                        System.out.print("B|");
                    } else if (f == FLAGS.C) {
                        System.out.print("C|");
                    } else if (f == FLAGS.P) {
                        System.out.print("P|");
                    } else if (f == FLAGS.O) {
                        System.out.print("O|");
                    } else if (f == FLAGS.S) {
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

    public void setGrid(int[] loc, Optional<FLAGS> f, Optional<String> name, boolean guess) {
        //System.out.println("In gridController class:"+ Arrays.toString(loc) +" "+f +" name: "+name); //ok
        grids[loc[0]][loc[1]].setFlag(f);
        grids[loc[0]][loc[1]].setName(name);
        grids[loc[0]][loc[1]].setGuess(guess);
    }

    public Optional<FLAGS> retrieveFlag(int[] loc) {
        return grids[loc[0]][loc[1]].getFlag();
    }

    public Optional<String> retrieveName(int[] loc) {
        return grids[loc[0]][loc[1]].getName();
    }

    public boolean retrieveGuess(int[] loc) {
        return grids[loc[0]][loc[1]].getGuess();
    }


    // method for generate multiple ships using method generateRandomBoat
    public void generateRandomGrid(){
        // Generate one Carrier
        generateRandomBoat(6,"C1", FLAGS.C);
        // Generate two Battleships
        generateRandomBoat(4,"B1", FLAGS.B);
        generateRandomBoat(4,"B2", FLAGS.B);
        // Generate three Submarines
        generateRandomBoat(3, "S1", FLAGS.S);
        generateRandomBoat(3, "S2", FLAGS.S);
        generateRandomBoat(3, "S3", FLAGS.S);
        // Generate four Patrol boats
        generateRandomBoat(2, "P1", FLAGS.P);
        generateRandomBoat(2, "P2", FLAGS.P);
        generateRandomBoat(2, "P3", FLAGS.P);
        generateRandomBoat(2, "P4", FLAGS.P);

    }

    // method for generate correct boat and set grid to every location of boat
    // input length, boat name and flag
    // length 6 for carrier, 4 for battleship, 3 for submarines, 2 for Patrol boat
    public void generateRandomBoat(int len, String boatName,FLAGS f) {
        int[] firstLoc = {0, 0};
        int[] finalLoc = {0, 1};
        int x, y; // x and y axis value
        // Init the direction of boat
        // value 0 for goes up, 1 for right, 2 for down, 3 for right
        int dir = 0;
        boolean b = true;
        Random r = new Random();
        while (b) {
            x = r.nextInt(10);
            y = r.nextInt(10);
            firstLoc = new int[]{x, y};
            if (grids[x][y].getFlag().isEmpty()) {
                continue;
            }
            dir = r.nextInt(4);
            switch (dir) {
                case 0:
                    y = y - (len - 1);
                    finalLoc = new int[]{x, y};
                    break;
                case 1:
                    x = x + (len - 1);
                    finalLoc = new int[]{x, y};
                    break;
                case 2:
                    y = y + (len - 1);
                    finalLoc = new int[]{x, y};
                    break;
                case 3:
                    x = x - (len - 1);
                    finalLoc = new int[]{x, y};
                    break;
            }
            // check if the boat is in right bound and has no ship overlap
            if (finalLoc[0] < 10 && finalLoc[1] < 10 && finalLoc[0] > -1 && finalLoc[1] > -1) {
                if(isOverlap(firstLoc,len,dir))
                    b = false; //correct, jump out of the loop and keep setting grid
                else
                    continue; //false, overlap occurs, reproduce the random number
                // set grid
                switch (dir) {
                    case 0:
                        for (int i = 0; i < len; i++) {
                            setGrid(firstLoc, Optional.of(f), Optional.of(boatName), false);
                            firstLoc[1] = firstLoc[1] - 1;
                        }
                        break;
                    case 1:
                        for (int i = 0; i < len; i++) {
                            setGrid(firstLoc, Optional.of(f), Optional.of(boatName), false);
                            firstLoc[0] = firstLoc[0] + 1;
                        }
                        break;
                    case 2:
                        for (int i = 0; i < len; i++) {
                            setGrid(firstLoc, Optional.of(f), Optional.of(boatName), false);
                            firstLoc[1] = firstLoc[1] + 1;
                        }
                        break;
                    case 3:
                        for (int i = 0; i < len; i++) {
                            setGrid(firstLoc, Optional.of(f), Optional.of(boatName), false);
                            firstLoc[0] = firstLoc[0] - 1;
                        }
                        break;
                }

            }
        }
    }

    // method for check the whole boat if it is overlap
    // return true for overlap occurs
    // return false for no overlap
    public boolean isOverlap(int[] firstLoc,int len, int dir){
        int x = firstLoc[0];
        int y = firstLoc[1];
        // first judge if it is in the right boundary
        if(x<10&&y<10&&x>-1&&y>-1){
            //if true, then check the whole boat if it is overlap with other existed boat
            for(int i=0;i<len;i++){
                if(grids[x][y].getFlag().isEmpty()){
                    switch (dir){
                        case 0:
                            y = y - 1;
                            break;
                        case 1:
                            x = x + 1;
                            break;
                        case 2:
                            y = y + 1;
                            break;
                        case 3:
                            x = x - 1;
                            break;
                    }
                    continue;
                }
                else
                    return true; // overlap occurs
            }
            // all location of the boat is clear, no overlap
            return false;
        }
        else
            return true; // overlap occurs
    }

    /**
     * Check if the boat is sunk or not
     *
     * @param name
     * @return true: sunk
     */
    public boolean isSunk(String name) {
        boolean result = false;
        System.out.println(name);

        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[0].length; j++) {
                if (grids[i][j].getName().isPresent()) {
                    if (grids[i][j].getName().get() == name) {
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
     *
     * @param name
     */
    public List<int[]> recoverFlag(String name) {
        List<int[]> locs = new ArrayList<>();

        FLAGS originalFlag = null;
        for (FLAGS f : FLAGS.values()) {
            if (f.name().equals(name.substring(0, 1))) {
                originalFlag = f;
                break;
            }
        }

        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[0].length; j++) {
                if (grids[i][j].getName().isPresent()) {
                    if (grids[i][j].getName().get() == name) {
                        grids[i][j].setFlag(Optional.of(originalFlag));
                        locs.add(new int[]{i, j});
                    }
                }
            }
        }

        return locs;
    }

    public int getSunkNum() {
        return sunkNum;
    }

    public void setSunkNum(int num) {
        this.sunkNum = num;
    }

    /**
     * Check if the game is finished
     *
     * @return
     */
    public boolean isFinish() {
        if (sunkNum == NUM) {
            return true;
        } else {
            return false;
        }

    }

}
