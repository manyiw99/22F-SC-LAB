import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
        }
        // 补充其余字母

        return 0;
    }

    /**
     * 1. Input format check
     * 2. Transform input in the terminal into List<int[]>
     * @param length
     *        length = 0 means user is guessing (not place boats), place the return value in the first element of List<int[]>
     * @return List<int[]> means all the cells user chose
     */
    public List<int[]> FormatInput(int length){
        String inputLine = readUser();
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
