package com.hm.mapFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HM on 2015/9/22.
 */
public class test {
    public static void main(String[] args) throws IOException {
//
//      List<String> DIC = new ArrayList<>();
//
//        List<String> lines1 = Files.readAllLines(Paths.get("sourceFiles/fixedDic2.txt"), Charset.forName("utf-8"));
//        for (String line : lines1) {
//            DIC.add(line);
//        }
//        System.out.println(DIC);

        String test = "齐鲁商品混凝土";
        String str = "混凝土";
        String k = "k";
//        String[] s = test.split(",");
//
//        System.out.println(s[2].toString());
//
//        String str1 = "你好";
//        String str2 = "吗？";
//        str1 = str1.concat(str2);
//        System.out.println(str1);

//        System.out.println(test.contains(str));
        System.out.println(test.length());





    }
}
