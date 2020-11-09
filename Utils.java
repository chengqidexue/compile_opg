import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    /**
     * 优先关系矩阵
     * none 0
     * > 1
     * < 2
     * = 3
     */
    public static int [][] operatorPrecedenceMatrix = {
            {1, 2,  2, 2, 1, 1},
            {1, 1, 2, 2, 1, 1},
            {1, 1, 0, 0, 1, 1},
            {2, 2, 2, 2, 3, 0},
            {1, 1, 0, 0, 1, 1},
            {2, 2, 2, 2, 0, 0}
    };

    public static Map<Character, Integer> operatorPrecedenceMatrixMap = new HashMap<>();
    static {
        operatorPrecedenceMatrixMap.put('+', 0);
        operatorPrecedenceMatrixMap.put('*', 1);
        operatorPrecedenceMatrixMap.put('i', 2);
        operatorPrecedenceMatrixMap.put('(', 3);
        operatorPrecedenceMatrixMap.put(')', 4);
        operatorPrecedenceMatrixMap.put('#', 5);
    }

    public static String readFile(String path) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String s = null;
            while((s = br.readLine())!=null){
                result.append(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 判断栈内符号是否比栈外符号优先级高
     * @param inStackChar   栈内终结符
     * @param outStackChar  栈外终结符
     * @return  1: > ; 2 <= ; 0: none
     */
    public static int operatorMatrix(char inStackChar, char outStackChar) {
        if (inStackChar == '#' && outStackChar == '#') return 2;
        return operatorPrecedenceMatrix[operatorPrecedenceMatrixMap.get(inStackChar)][operatorPrecedenceMatrixMap.get(outStackChar)];
    }
}