import java.util.Optional;

enum FLAGS{
    X,O,C,B,S,P
}

public class Grid {
    private int[] loc;
    private Optional<FLAGS> flag;
    private Optional<String> name;
    private boolean guess;  // true: guessed before

    public Grid(int[] loc,Optional<FLAGS> flag, Optional<String> name, boolean guess){
        this.loc=loc;
        this.flag=flag;
        this.name = name;
        this.guess=guess;
    }

    // Cannot set loc, only can get loc
    public int[] getLoc(){
        return loc;
    }

    public void setFlag(Optional<FLAGS> flag){
        // System.out.println("in grid class:"+flag);
        this.flag=flag;
    }

    public Optional<FLAGS> getFlag(){
        return flag;
    }

    public void setName(Optional<String> name){
        this.name=name;
    }

    public Optional<String> getName(){
        return name;
    }

    public void setGuess(boolean status){
        this.guess=status;
    }

    public boolean getGuess(){
        return guess;
    }

}
