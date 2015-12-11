package com.hm.mapFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HM on 2015/9/18.
 */
public class nameSeg {
    private static final List<String> DIC1 = new ArrayList<>();

    private static int MAX_LENGTH;
    static{
        try {
            int max=1;
            int count=0;
            List<String> lines = Files.readAllLines(Paths.get("sourceFiles/fixedDic2.txt"), Charset.forName("utf-8"));
            for(String line : lines){
                DIC1.add(line);
                count++;
                if(line.length()>max){
                    max=line.length();
                }
            }
            MAX_LENGTH = max;
//            System.out.println("?????????????????"+count);
//            System.out.println("?????????"+MAX_LENGTH);
        } catch (IOException ex) {
            System.err.println("?????????:"+ex.getMessage());
        }

    }
    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return sb.toString().substring(0,sb.toString().length());
    }

    public static List<String> seg(String text){
        List<String> result = new ArrayList<>();
        while(text.length()>0){
            int len=MAX_LENGTH;
            if(text.length()<len){
                len=text.length();
            }
            //?????????????????????????
            String tryWord = text.substring(0,0+len);
            while(!DIC1.contains(tryWord)){
                //?????????????????未??????????????蟹?
                if(tryWord.length()==1) break;
                //??????????????????????
                tryWord=tryWord.substring(0, tryWord.length()-1);
            }
            if(!DIC1.contains(tryWord)){
                result.add(tryWord);
            }
//            result.add(tryWord);
            //????????????????????????
            text=text.substring(tryWord.length());
        }
        return result;
    }

    public static void main(String[] args){
        String text = "哈尔滨德士达科技有限公司";
        System.out.println(listToString(seg(text)));
        System.out.println(seg(text));

    }
}
