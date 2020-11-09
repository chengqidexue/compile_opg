import java.io.FileNotFoundException;

/**
 * 编程实现以下文法 G[E] 的算符优先分析法：
 * E -> E '+' T | T
 * T -> T '*' F | F
 * F -> '(' E ')' | 'i'
 */

public class Opg {

    /**
     * 符号栈，默认首先入栈一个#作为
     */
    public static char [] listStack = new char[1024];
    static {
        listStack[0] = '#';
    }

    public static void main(String[] args) {

        if(args.length != 1) {
            System.out.println("请输入input文件");
            return;
        }
        String input = "";
        try {
            input = Utils.readFile(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("输入文件不存在");
        }
//        System.out.println(input.length());
        //形式：i+i...#\0
        char [] inputChars = new char[1024];
        for (int i = 0; i < input.length(); i++) {
            inputChars[i] = input.charAt(i);
        }
        inputChars[input.length()] = '#';
        inputChars[input.length()+1] = '\0';
//        System.out.println(inputChars);

        /**
         * 算符优先法分析过程
         * 非终结符：N
         * + * i ( )
         * > 1
         * < 2
         * = 2
         */
        int h = 0;      //输入字符串上的index
        int k = 0;      //栈上的index,每次循环开始时指向栈顶
        int j = 0;
        char ch = '@';  //读入的字符
        while(true){
            ch = inputChars[h];
            if (ch == '\0') break;

            if (listStack[k] != 'N') {
                j = k;
            } else {
                j = k - 1;
            }

            int count = Utils.operatorMatrix(listStack[j], ch);
            //移进
            if (count == 2 || count == 3) {
                k = k + 1;
                h = h + 1;
                listStack[k] = ch;
                if (ch != '#')
                    System.out.println("I"+ ch);
            }

            //规约
            else if(count == 1) {
                char q = '@';
                do{
                    q = listStack[j];
                    if (listStack[j-1] != 'N') {
                        j = j - 1;
                    } else {
                        j = j - 2;
                    }
                }while (Utils.operatorMatrix(listStack[j],q) != 2);
                //规约s[j+1]...s[k]
                int flag = 0;
                char[] temp = new char[100];
                int ti = 0;
                for (int i = j + 1; i <= k; i++) {
                    temp[ti++] = listStack[i];
                }
                if (ti == 1 && temp[0] == 'i') flag = 1;
                if (ti == 3 && (temp[0] == '(' && temp[1] == 'N' && temp[2] == ')')) flag = 1;
                if (ti == 3 && (temp[0] == 'N' && temp[1] == '+' && temp[2] == 'N')) flag = 1;
                if (ti == 3 && (temp[0] == 'N' && temp[1] == '*' && temp[2] == 'N')) flag = 1;
                if (flag == 0) {
                    System.out.println("RE");
                    break;
                }

                k = j + 1;
                listStack[k] = 'N';
                System.out.println("R");
            } else {
                System.out.println("E");
                break;
            }
        }
    }
}