package jackzheng.study.com.regulartest.regular;

import android.text.TextUtils;

import java.util.ArrayList;

public class RegularUtils2 {
    private static final char[] SPILE_SIGN_LIST = {',','，','.','。','/','、',' '};
    private static char NEW_SPLIE_CHAR = '死';
    private static final char[] ALL_SIGN_LIST = {',','，','.','。','/','、',' ','-','_','—','=','+',NEW_SPLIE_CHAR};
    private static String  NEW_SPILE_SIGN = "死";


    //特殊字符替代
    private String repalace(String str){
        return null;
    }


    //剔除字符串中连续出现的符号和首尾的空格
    private String rejectNouserChar(String str){
        char[] list = str.toCharArray();
        int start = 0;
        int end = list.length -1;
        StringBuilder build = new StringBuilder();
        for(;start <= end ;start ++){
            if(list[start] != ' ' && list[start] != '\t'){
                break;
            }
        }
        if(start == end){
            return null;
        }
        for(;start <= end ;end --){
            if(list[end] != ' ' && list[end] != '\t'){
                break;
            }
        }
        if(start == end){
            return null;
        }
        for(;start<=end;){
            if(isSpileChar(list[start])){
                build.append(NEW_SPLIE_CHAR);
                while (isSpileChar(list[start])){
                    start++;
                }
            }else{
                build.append(list[start]);
                start++;
            }
        }
        return build.toString();
    }

    //字符串的分割
    private static ArrayList<String> spileString(String str){
        char[] cs = str.toCharArray();
        boolean isHavaSpoleSign ;
        StringBuilder build = new StringBuilder();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> value = new ArrayList<>();

        for(char c :cs){
            if (c == '\n') {
                if(build.length() <1){
                    continue;
                }
                String str2 = build.toString();
                build = new StringBuilder();
                list.add(str2);
            }else{
                build.append(c);
            }
        }

        if(build.length() > 0 ){
            String str2 = build.toString();
            list.add(str2);
        }
        //单行分割
       for(String s: list){
            if(haveNumCount(s) >3){
                ArrayList<String> tmp = sigleLineSpile(s);
                String end = tmp.get(tmp.size() - 1);
                int nuumcount = haveNumCount(end);
                if(nuumcount== 1 || nuumcount == 0){
                    ArrayList<String> tmp2 = new ArrayList<>();
                    for(int ii = 0 ; ii< tmp.size() -1;ii++){
                        tmp2.add(tmp.get(ii)+NEW_SPILE_SIGN+end);
                    }
                    value.addAll(tmp2);
                }else{
                    value.addAll(tmp);
                }
            }else{
                value.add(s);
            }
       }

        return  value;
    }
    private static ArrayList<String> sigleLineSpile(String str){
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Character> noSplieChars = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        char[] cs = str.toCharArray();
        int numCount = 0;
        for(int i = 0 ; i< cs.length ;){
            if(isNumber(cs[i])){
                numCount ++;
                while (isNumber(cs[i])){
                    builder.append(cs[i]);
                    i++;
                }
            }else if(isSpileChar(cs[i]) && numCount >= 2 && isNoSpileChar(cs[i],noSplieChars)){
                ArrayList<String> list2 = new ArrayList<>();
                boolean isSuccess =sigleLineSpileLoop(list2,cs,i+1,cs[i]);
                if(isSuccess){
                    return list2;
                }else{
                    noSplieChars.add(cs[i]);
                    builder.append(cs[i]);
                    i++;
                }
            }else{
                builder.append(cs[i]);
                i ++;
            }
        }
        return null;
    }
    private static int haveNumCount(String s){
        char[] cs = s.toCharArray();
        int count = 0;
        for(int i= 0; i< cs.length ;){
            count ++;
            if(isNumber(cs[i])){
                while (isNumber(cs[i])){
                    i++;
                }
            }else{
                i++;
            }
        }
        return count;
    }

    private static boolean isNoSpileChar(char c, ArrayList<Character> list){
        if(list.size() == 0){
            return false;
        }else{
            for(Character cc : list){
                if(c == cc){
                    return true;
                }
            }
            return false;
        }
    }
    private static boolean sigleLineSpileLoop( ArrayList<String> list ,char[] cs ,int index , char spileChar){
        StringBuilder builder = new StringBuilder();
        int numCount = 0;
        for(;index < cs.length ;){
            if(isNumber(cs[index])){
                numCount ++;
                while (isNumber(cs[index])){
                    builder.append(cs[index]);
                    index++;
                }
            }else if(spileChar ==  spileChar ){
                if(numCount >= 2){
                    list.add(builder.toString());
                    return sigleLineSpileLoop(list,cs,index,spileChar);
                }else{
                    return false;
                }
            }else{
                builder.append(cs[index]);
                index++;
            }
        }
        if(builder.length() >0){
            list.add(builder.toString());
        }
        return  true;
    }


    private static boolean isNumber(char c){
        if(c>= '0' && c <= '9'){
            return true;
        }
        return false;
    }
    private static boolean isSpileChar(char c){
        for(char c2 : ALL_SIGN_LIST){
            if(c == c2){
                return true;
            }
        }
        return false;
    }
}
