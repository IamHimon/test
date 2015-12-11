package com.hm.mapFile;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HM on 2015/9/22.
 */
public class nameCut {

    private static final List<String> DIC = new ArrayList<>();  //read fixDic2.txt
    private static final List<String> DIC1 = new ArrayList<>(); //read placeDic.txt where only contain all place name

    private static int MAX_LENGTH;

    static {
        try {
            int max = 1;
            int count = 0;
            List<String> lines = Files.readAllLines(Paths.get("sourceFiles/fixedDic2.txt"), Charset.forName("utf-8"));
            for (String line : lines) {
                DIC.add(line);
                count++;
                if (line.length() > max) {
                    max = line.length();
                }
            }

            //read all place name to memory
            List<String> lines1 = Files.readAllLines(Paths.get("sourceFiles/placeDic.txt"), Charset.forName("utf-8"));
            for (String line : lines1) {
                DIC1.add(line);
            }

            MAX_LENGTH = max;
//            System.out.println("dic count："+count);
//            System.out.println("max word length："+MAX_LENGTH);
        } catch (IOException ex) {
            System.err.println("read dic failed" + ex.getMessage());
        }

    }

    //list to string
    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return sb.toString().substring(0, sb.toString().length());
    }


    //processing name according dic originally
    public static String processNameAccordingDic(String originalName) throws IOException {
//        String originalName = "哈尔滨（美国）新威力汽车经销站有限责任公司";
        String temp = null;
        String result = null;
        List<String> lines = Files.readAllLines(Paths.get("sourceFiles/dic.txt"), Charset.forName("utf-8"));
        for (String line : lines) {
            result = originalName.replaceAll(line, "");
            temp = result;
            originalName = temp;
        }
        return result;
    }


    //    judge whether the company's name is start with place-name,if so,turn to seg(),if not,break the process then
    //   retain the complete name to the List.
    public static Map<String, List> startSeg(String text) throws IOException {
        String processedName = processNameAccordingDic(text);
        Map<String, List> map = new HashMap<>();
        List<String> retained = new ArrayList<>();        //retained words list
        List<String> removed = new ArrayList<>();       //removed words list

        //judge if the processed company's name is start with place name
        String temp = null;
        for (int i = 0; i <= processedName.length(); i++) {
            temp = processedName.substring(0, i);
            if (DIC1.contains(temp)) {
                break;
            }
        }
        retained.add(temp);
//        if (!temp.equals(text)){
        if (!temp.equals(processedName)) {
//            map = seg(processedName);
            map = seg2(processedName);
        } else {
            map.put("retained", retained);
        }
        return map;
    }


    //divide the processed company's name  (version 1)
    public static Map<String, List> seg1(String text) {

        Map<String, List> map = new HashMap<>();

        List<String> retained = new ArrayList<>();        //retained words list
        List<String> removed = new ArrayList<>();       //removed words list

        while (text.length() > 0) {         //redetermine the max_length
            int len = MAX_LENGTH;
            if (text.length() < len) {
                len = text.length();
            }
            String tryWord = text.substring(0, 0 + len);

            while (!DIC.contains(tryWord)) {
                //the ending judgement
                if (tryWord.length() == 1) {
                    break;
                }
                //move one position toward right
                tryWord = tryWord.substring(0, tryWord.length() - 1);
            }
            if (!DIC.contains(tryWord)) {
                retained.add(tryWord);
            } else {

//                System.out.println(tryWord);
                if (DIC1.contains(tryWord)) {
                    removed.add(tryWord);
                }
            }
//            result.add(tryWord);
            text = text.substring(tryWord.length());
        }

        map.put("retained", retained);
        map.put("removed", removed);
        return map;
    }


    //divide the processed company's name，retain these place-name that is inside the company's name(version 2)
    public static Map<String, List> seg2(String text) {

        int length = text.length();

        Map<String, List> map = new HashMap<>();

        List<String> retained = new ArrayList<>();        //retained words list
        List<String> removed = new ArrayList<>();       //removed words list

        String temp = null;
        String result = null;
        int i, j;
        labelA:
        for (i = 0; i < length - 1; i = j) {             //not i+=j,
            lableB:
            for (j = length; j >= i; j--) {
                temp = text.substring(i, j);
//                System.out.println("temp:"+temp);
                if (DIC1.contains(temp)) {
//                    System.out.println("removed:" + temp + "  substring:   i="+i+"    j="+j);
//                    System.out.println("retained::" + text.substring(j, length));
                    removed.add(temp);
                    break lableB;         //break : break from the current for-loop
                }
//                System.out.println( "i="+i+"    j="+j);
                if (i == j) {
                    result = text.substring(i, length);
//                    System.out.println("over: result="+result);
                    break labelA;
                }
            }
        }
        retained.add(result);
//        if(removed.size() != 0) {
//            retained.add(removed.get(removed.size() - 1));
//        }
        map.put("retained", retained);
        map.put("removed", removed);
        return map;
    }


    // make further processing of the map that got from startSeg ,then return the ultimate result.(version 1)
    public static String rebuildName1(Map<String, List> map) {
        String simpleName = null;
        List<String> removed = map.get("removed");
        List<String> retained = map.get("retained");

        if (!(removed == null)) {
            if (removed.size() > 1) {
                retained.add(0, removed.get(removed.size() - 1));
            }
            if (DIC1.contains(removed.get(0)) && DIC1.contains(removed.get(removed.size() - 1))
                    && (removed.get(0) != removed.get(removed.size() - 1)) && (removed.size() > 1)) {
                retained.add(0, removed.get(removed.size() - 1));
            } else if (retained.size() <= 3) {              //if the length of retained is less than 3,add the nearest place-name;
                retained.add(0, removed.get(removed.size() - 1));
            }
            simpleName = listToString(retained);
        } else {
            simpleName = listToString(map.get("retained"));
        }
        return simpleName;
    }

    // // make further processing of the map that got from startSeg ,then return the ultimate result<simplifiedName,nearestPlaceName> (version 2)
    public static List<String> rebuildName2(Map<String, List> map) {
        List<String> result = new ArrayList<>();
        String simpleName = null;
        List<String> removed = map.get("removed");
        List<String> retained = map.get("retained");

        if (removed != null) {
            //if the length of removed list is more than 1,add the nearest place-name to retained.
//            System.out.println(removed.size());
//            System.out.println("test:" + removed.get(removed.size() - 1));
            result.add(removed.get(0));
            if (removed.size() > 1) {
                retained.add(0, removed.get(removed.size() - 1));
                simpleName = listToString(retained);
            } else {
                simpleName = listToString(retained);
//                System.out.println(simpleName);
            }
        } else {
            simpleName = listToString(retained);
        }
//        System.out.println(simpleName);

        result.add(0,simpleName);
//        System.out.println(result.get(0)+","+result.get(1));
        return result;
    }


    public static String getNearestPlaceName(){
        String nearestPlaceName = null;


        return nearestPlaceName;
    }

    public static void main(String[] args) throws IOException {


//
//        String text9 = "北京市苏州市平江区万向德农股份有限公司";
//        String text9 = "七台河市国美电器有限责任公司";
//        String text4 = "七台河市军粮供应站";
//        String text5 = "中国银行股份有限公司信恒支行";
//        String text6 = "上海中兴纺织品有限公司哈尔滨分公司";
//        String text9 = "江苏省苏州市平江区中国人寿保险(美国)股份有限公司哈尔滨市南岗支公司";
//        String text9 = "齐齐哈尔市化工厂";
        String text9 = "江苏省苏州市平江区中国移动分公司";
//        String text10 = "黑龙江省苏州市平江区中国移动分公司";
//        String text8 = "万都（哈尔滨）汽车底盘系统有限公司";
//        String text9 = "加拿大七天快捷酒店管理(美国)有限公司哈尔滨中央大街分公司";
//        String text10 = "哈尔滨(美国)新威力汽车经销有限责任公司";
//        System.out.println(seg2(text9).get("removed"));
//        System.out.println(seg2(text10).get("removed"));

//        System.out.println(text1);
//        System.out.println(startSeg(text1).get("retained"));
//        System.out.println(startSeg(text1).get("removed"));
//        System.out.println(rebuildName(startSeg(text1)));
//
//        System.out.println("----------------------------");
//        System.out.println(text5);
//        System.out.println(startSeg(text5).get("retained"));
//        System.out.println(startSeg(text5).get("removed"));
//        System.out.println(rebuildName(startSeg(text5)));
//
//
//        System.out.println("---------------------------");
//        System.out.println(text6);
//        System.out.println(startSeg(text6).get("retained"));
//        System.out.println(startSeg(text6).get("removed"));
//        System.out.println(rebuildName(startSeg(text6)));
//
//
//
//        System.out.println("---------------------------");
//        System.out.println(text7);
//        System.out.println(startSeg(text7).get("retained"));
//        System.out.println(startSeg(text7).get("removed"));
//        System.out.println(rebuildName(startSeg(text7)));
//
//
//
//        System.out.println("---------------------------");
//        System.out.println(text8);
//        System.out.println(startSeg(text8).get("retained"));
//        System.out.println(startSeg(text8).get("removed"));
//        System.out.println(rebuildName(startSeg(text8)));

        System.out.println("---------------------------");
        System.out.println(text9);
        System.out.println(startSeg(processNameAccordingDic(text9)).get("retained"));
        System.out.println(startSeg(processNameAccordingDic(text9)).get("removed"));
        System.out.println(rebuildName2(startSeg(processNameAccordingDic(text9))).get(1));

//


    }
}
