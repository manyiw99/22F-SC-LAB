enum FLAGS{
    X,O,C,B,S,P
}

public class Grid {
    private int[] loc;
    private FLAGS flag;
    private String name;
    private boolean guess;  // true: guessed before

    public Grid(int[] loc,FLAGS flag, String name, boolean guess){
        this.loc=loc;
        this.flag=flag;
        this.name = name;
        this.guess=guess;
    }

    // Cannot set loc, only can get loc
    public int[] getLoc(){
        return loc;
    }

    public void setFlag(FLAGS flag){
        // System.out.println("in grid class:"+flag);
        this.flag=flag;
    }

    public FLAGS getFlag(){
        return flag;
    }

    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setGuess(boolean status){
        this.guess=status;
    }

    public boolean getGuess(){
        return guess;
    }

}
