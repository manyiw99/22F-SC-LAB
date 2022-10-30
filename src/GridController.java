import java.util.Arrays;
import java.util.Optional;

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
        System.out.println("===" + gridName + "===");
        System.out.println("A---------------J");

        FLAGS[][] flags = new FLAGS[10][10];
        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[0].length; j++) {
                flags[i][j] = grids[i][j].getFlag().orElse(null);
            }
        }
        System.out.println(Arrays.deepToString(flags).replace("], ", "]\n"));

        //补充标题之类 --> 将页面显示完整 (隐藏逻辑)
        //最开始初始化的时候显示空网格
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

        // 随机生成坐标， eg.[1,2],[1,3]
        int[] randomLoc = {1,1};


        setGrid(randomLoc,Optional.of(FLAGS.B),Optional.of("B3"),false);
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
    public void recoverFlag(String name){
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
                    }
                }
            }
        }

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
