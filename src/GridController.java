import java.util.ArrayList;
import java.util.Arrays;

public class GridController {
    String gridName;
    ArrayList<Grid> grids;
    int sunkNum;
    int NUM=10;

    public GridController(String gridName){
        this.gridName=gridName;
        sunkNum=0;

        grids = new ArrayList<Grid>();

        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                int[] loc={i,j};
                grids.add(new Grid(loc,null,null,false));
            }
        }
    }

    public void drawGrid(){
        System.out.println("===" + gridName + "===");
        System.out.println("A---------------J");

        //补充标题之类 --> 将页面显示完整 (隐藏逻辑)

        //可以访问每个grid的loc与Flag,画图可以转成二维数组，也可以直接用getLoc()与getFlag()获得每个格子的信息来画

        FLAGS[][] flags = new FLAGS[10][10];

        // Construct 2-dimensional array
        for (int i = 0; i < grids.size(); i++) {
            int[] loc = grids.get(i).getLoc();
            flags[loc[0]][loc[1]] = grids.get(i).getFlag();
        }
        System.out.println(Arrays.deepToString(flags).replace("], ", "]\n"));
    }

    public void setGrid(int[] loc, FLAGS f, String name, boolean guess){
        //System.out.println("In gridController class:"+ Arrays.toString(loc) +" "+f +" name: "+name); //ok
        for(int i = 0;i < grids.size(); i++){
            if( Arrays.equals(grids.get(i).getLoc(),loc)){
                grids.get(i).setFlag(f);
                grids.get(i).setName(name);
                grids.get(i).setGuess(guess);
                break;
            }
        }

    }

    public FLAGS retrieveFlag(int[] loc){
        FLAGS result = null;
        for(int i = 0;i < grids.size(); i++){
            if( Arrays.equals(grids.get(i).getLoc(),loc)){
                result = grids.get(i).getFlag();
                break;
            }
        }
        
        return result;
    }

    public String retrieveName(int[] loc){
        String result = null;
        for(int i = 0;i < grids.size(); i++){
            if( Arrays.equals(grids.get(i).getLoc(),loc)){
                result = grids.get(i).getName();
                break;
            }
        }

        return result;
    }

    public boolean retrieveGuess(int[] loc){
        boolean result = false;
        for(int i = 0;i < grids.size(); i++){
            if( Arrays.equals(grids.get(i).getLoc(),loc)){
                result = grids.get(i).getGuess();
                break;
            }
        }

        return result;
    }


    public void generateRandomGrid(){

        // 随机生成坐标， eg.[1,2],[1,3]
        int[] randomLoc = {1,1};


        setGrid(randomLoc,FLAGS.B,"B3",false);
    }

    /**
     * Check if the boat is sunk or not
     * @param name
     * @return true: sunk
     */
    public boolean isSunk(String name){
        boolean result=false;

        for(int i = 0;i < grids.size(); i++){
            if( grids.get(i).getName() == name){
                if (grids.get(i).getFlag() != FLAGS.X) {
                    result = false;
                    break;
                }
                else{
                    result=true;

                }
            }
        }

        return result;
    }

    public void recoverFlag(String name){
        FLAGS originalFlag = null;
        for(FLAGS f: FLAGS.values()){
            if(f.name()==name.substring(0,1)){
                originalFlag = f;
                break;
            }
        }

        for(int i = 0;i < grids.size(); i++){
            if( grids.get(i).getName() == name){
                grids.get(i).setFlag(originalFlag);
            }
        }

    }

    public int getSunkNum(){
        return sunkNum;
    }
    public void setSunkNum(int num){
        this.sunkNum=num;
    }

    public boolean isFinish(){
        if(sunkNum==NUM){
            return true;
        }else{
            return false;
        }

    }


}
