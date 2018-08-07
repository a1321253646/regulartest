package jackzheng.study.com.regulartest;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jackzheng.study.com.regulartest.regular.StringDealFactory;


public class HtmlParse {

    public  static MaxIndexResult parse(){
        Log.d("zsbin","HtmlParse MaxIndexResult = ");
        MaxIndexResult result = new MaxIndexResult();
        try {
            Document doc = Jsoup.connect("https://csj.1396j.com/shishicai/?utm=new_csj").get();
            Elements select = doc.select("table#history.lot-table");
          //  Log.d("zsbin","select ="+select.toString());
            for(Element element5 :select){
                String text = element5.text();
                int index = text.indexOf("20180807", 0);
                Log.d("zsbin","index = "+index);
                char[] chars = text.toCharArray();
                StringBuilder build = new StringBuilder();
                String qishu=null,haoma=null;
                int count = 0;
                for(int i =index ;i<chars.length; ){
                    if(qishu == null &&chars[i]== '-' && i<chars.length -1 && StringDealFactory.isNumber(chars[i+1])){
                        i++;
                        while (StringDealFactory.isNumber(chars[i])){
                            build.append(chars[i]);
                            i++;
                        }
                        qishu = build.toString();
                        build = new StringBuilder();
                    }else if(chars[i] == ' '){
                        count++;
                        i++;
                    }else if(count == 2 && StringDealFactory.isNumber(chars[i])){
                        for(int ii =0; ii<5 ;ii++ ){
                            build.append(chars[i]);
                            if(i < chars.length -2 && chars[i+1] == ' ' && StringDealFactory.isNumber(chars[i+2])){
                                i= i+2;
                            }else{
                                return null;
                            }
                        }
                        haoma = build.toString();
                        break;
                    }else{
                        i++;
                    }

                }
                result.index = Integer.parseInt(qishu);
                result.str = haoma;
                Log.d("zsbin","qishu ="+ result.index + " haoma = "+ result.str);
                return  result;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static class MaxIndexResult{
        public int index = 0;
        public String str;
    }
}
