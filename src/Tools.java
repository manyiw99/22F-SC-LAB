import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Tools {
    /**
     * Get user input from terminal
     * @return
     */
    public String readUser(){
        String inputLine = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            inputLine = in.readLine();
            if (inputLine.length() == 0)
                return null;
        }catch ( IOException e){
            System.out.println("IOException: "+e);
        }

        return inputLine;
    }

    public int convertLetterToInt(char L){
        if (L=='A'){
            return 0;
        }else if(L=='B'){
            return 1;
        }else if(L=='C') {
            return 2;
        }else if(L=='D'){
            return 3;
        }else if(L=='E'){
            return 4;
        }else if(L=='F'){
            return 5;
        }else if(L=='G'){
            return 6;
        }else if(L=='H'){
            return 7;
        }else if(L=='I'){
            return 8;
        }else if(L=='J'){
            return 9;
        }

        return 10;
    }

    public boolean guessValidation(String inputLine) {
        if (inputLine == null || inputLine.equals("  ") || inputLine.length() != 2) {
            return false;
        }
        int c = convertLetterToInt(inputLine.charAt(0));

        if(!Character.isDigit(inputLine.charAt(1))){
            return false;
        }

        int i = Integer.parseInt(inputLine.substring(1));
        if (c < 0 || c > 9 || i < 0 || i > 9) {
//            System.out.println("Sorry, your input is invalid. Please try again.");
            return false;
        }
        return true;
    }

    public boolean placementValidation(String inputLine, int length) {
        if(inputLine == null){
            return false;
        }else{
            if(inputLine.length() != 5) {
                return false;
            }
        }

        String[] pos = inputLine.split(",");
        if (!guessValidation(pos[0]) || !guessValidation(pos[1])) {
            return false;
        }
        char start1 = pos[0].charAt(0);
        char start2 = pos[1].charAt(0);
        HashSet<String> set = new HashSet<String>();
        set.add("A");
        set.add("B");
        set.add("C");
        set.add("D");
        set.add("E");
        set.add("F");
        set.add("G");
        set.add("H");
        set.add("I");
        set.add("J");
        if (!set.contains(pos[0].substring(0,1)) || !set.contains(pos[1].substring(0,1))) {
            return false;
        }
        int pos1 = Integer.parseInt(pos[0].substring(1));
        int pos2 = Integer.parseInt(pos[1].substring(1));
        int dif1 = Math.abs(start2 - start1);
        int dif2 = Math.abs(pos2 - pos1);
        // they are not in the same column or row
        if (dif1 != 0 && dif2 != 0) {
            return false;
        }
        // they are in the same column but the length is incorrect
        if (dif1 == 0 && dif2 != length - 1) {
            return false;
        }
        // they are in the same row but the length is incorrect
        if (dif2 == 0 && dif1 != length - 1) {
            return false;
        }
        return true;
    }


    /**
     * 1. Input format check
     * 2. Transform input in the terminal into List<int[]>
     * @param length
     *        length = 0 means user is guessing (not place boats), place the return value in the first element of List<int[]>
     * @return List<int[]> means all the cells user chose
     */
    public List<int[]> formatInput(int length, String inputLine){
//        String inputLine = readUser();
        // Convert string to List<String>, split by comma
        List<String> locations = Arrays.asList(inputLine.split("\\s*,\\s*"));
        List<int[]> result = new ArrayList<>();

        if(length==0){
            int oneInput = convertLetterToInt(locations.get(0).charAt(0));
            int startPoint[] = {Character.getNumericValue(locations.get(0).charAt(1)), oneInput};
            // System.out.println(Arrays.toString(startPoint));

            result.add(startPoint);
        }else{
            for(int i =0; i<locations.size();i++){
                int oneInput = convertLetterToInt(locations.get(i).charAt(0));
                int startPoint[] = {Character.getNumericValue(locations.get(i).charAt(1)), oneInput};
                // System.out.println(Arrays.toString(startPoint));

                result.add(startPoint);
            }
        }

        // Input validation


        // 需要将locations格式转换为坐标， 现在为[A1,A3]，A代表0，需要转换成 [1,0],[3,0]格式
        // 最后返回的list包含这个船占据的所有格子的坐标

        return result;
    }

}
