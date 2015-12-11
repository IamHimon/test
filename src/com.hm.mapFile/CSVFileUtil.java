package com.hm.mapFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HM on 2015/9/18.
 */
public class CSVFileUtil {
    public static final String ENCODE = "UTF-8";
    public static final String ENCODE1 = "GBK";

    private FileInputStream fis = null;
    private InputStreamReader isw = null;
    private BufferedReader br = null;


    public CSVFileUtil(String filename) throws Exception {
        fis = new FileInputStream(filename);
        isw = new InputStreamReader(fis, ENCODE1);
        br = new BufferedReader(isw);
    }


    /*
    * read all lines from bufferedReader
    * */

    public void readAllLines() throws Exception {
        String stemp;
        while ((stemp = br.readLine()) != null) {
            System.out.println(stemp);

        }
    }

    /*
    *市，县级存储两个
    * */
    public void fixDic2() throws Exception {
        String stemp;
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(new File("D://E/fixed.txt"));
            writer = new BufferedWriter(fw);

//            System.out.println(Charset.defaultCharset().displayName()); //查看编码
            while ((stemp = br.readLine()) != null) {
                String temp = stemp.substring(stemp.length() - 1);

                //System.out.println(temp.length());
//                System.out.println(stemp.substring(stemp.length()-1).equals("市"));
                if (temp.endsWith("市")) {
                    System.out.println(stemp);
//                    System.out.println("test");
                    writer.write(stemp.substring(0, stemp.length() - 1));
                    writer.newLine();
                }
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fw.close();
            writer.close();
        }

    }

    /*
    * confirm all name's length is longer than 1
    *
    * */

    public void fixDic() throws Exception {
        String stemp;
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(new File("D://E/fixed.txt"));
            writer = new BufferedWriter(fw);

            while ((stemp = br.readLine()) != null) {
                if (stemp.length() != 1) {
                    System.out.println(stemp);
                    writer.write(stemp);
                    writer.newLine();
                }
                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fw.close();
            writer.close();
        }

    }

    /*
    * build csv(.txt) file (company's ID,simplified company's name)
    * */

    public void buildMapCSV(String fileName) throws Exception {
        String stemp;
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(new File("D://E/files/step2/" + fileName));
            writer = new BufferedWriter(fw);
            String name = null;
            String id = null;

            while ((stemp = br.readLine()) != null) {
                if (stemp.length() != 1) {
                    String[] s = stemp.split(",");

                    nameCut nc = new nameCut();

                    name = s[1].toString();
                    id = s[0].toString();
                    if ((name.length() > 1) && (id.length() > 0)) {
                        if (nc.rebuildName2(nc.startSeg(name)).size() > 1) {
                            System.out.println(s[0] + "," +name+"," + nc.rebuildName2(nc.startSeg(name)).get(1) + "," + nc.rebuildName2(nc.startSeg(name)).get(0));
                            writer.write(s[0] + "," +name+","+ nc.rebuildName2(nc.startSeg(name)).get(1) + "," + nc.rebuildName2(nc.startSeg(name)).get(0));
                            writer.newLine();
                        } else {
                            writer.write(s[0] + "," + name+","+"k" + "," + nc.rebuildName2(nc.startSeg(name)).get(0));
                            writer.newLine();
                        }
                    }
                }

                writer.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fw.close();
            writer.close();
        }
    }

    //make sure the name is not covered by any other name. If it is covered, then we put the nearest removed name into the company name.

    public void checkNameWithOtherNames(String path) throws Exception {
        FileWriter fw = null;
        BufferedWriter writer = null;
        List<String> name = new ArrayList<>();
        String stemp;
        String[] str1;
        String[] str2;
        String temp1 = null;
        String temp2 = null;
        try {
            fw = new FileWriter(new File("D://E/files/step2/" + path));
            writer = new BufferedWriter(fw);
            while ((stemp = br.readLine()) != null) {
                name.add(stemp);
            }
            int i,j;
            flagB:
            for (i = 0; i < name.size(); i++) {
                str1 = name.get(i).split(",");
                temp1 = str1[3].toString();     //temp1:company's name will be write into file.
                flagA:
                for (j = 0; j < name.size(); j++) {
                    str2 = name.get(j).split(",");
                    temp2 = str2[3].toString(); //temp2:company's used to judge if it contains temp1,if so,write temp1,if not continue the loop.

                    if (temp2.contains(temp1) && (j != i)) {
//                        System.out.println("write:" + temp2 + "contains:" + temp1+"  nearest:" +str1[2].toString()+"  length:"+ str1[2].toString().length());
                        if (str1[2].toString().length() != 1) {
                            temp1 = (str1[2].toString()).concat(temp1);
                        }

//                        System.out.println(str1[2].toString() + "contains:::" + temp1);
                        System.out.println(str1[0].toString() + "," + temp1);
                        writer.write(str1[0].toString() + ","+str1[1].toString()+"," + temp1);
                        writer.newLine();
                        continue flagB;
                    }

                }
                    if(i != j) {
                        writer.write(str1[0].toString() + "," + str1[1].toString() + "," + temp1);
                        writer.newLine();
                        writer.flush();
                    }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fw.close();
            writer.close();
        }

    }



    /*
    * ???????csv??????????????map(String,List)
    * */
    public static void buildMapListCSV(Map<String, ArrayList<String>> map) throws Exception {
        String stemp;
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(new File("D://E/files/mapCSV4.txt"));
            writer = new BufferedWriter(fw);

            stemp = map.toString();

            System.out.println(stemp);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fw.close();
            writer.close();
        }
    }


    /*
    * list to String
    *
    * */

    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return sb.toString().substring(0, sb.toString().length());
    }


    /**
     * ??CSV??????ж?????CSV??
     *
     * @throws Exception
     */
    public String readLine() throws Exception {

        StringBuffer readLine = new StringBuffer();
        boolean bReadNext = true;

        while (bReadNext) {

            if (readLine.length() > 0) {
                readLine.append("\r\n");
            }
            //???
            String strReadLine = br.readLine();

            // readLine is Null
            if (strReadLine == null) {
                return null;
            }
            readLine.append(strReadLine);

            //????????????????????????????????л??е????????
            if (countChar(readLine.toString(), '"', 0) % 2 == 1) {
                bReadNext = true;
            } else {
                bReadNext = false;
            }
        }
        return readLine.toString();
    }

    /**
     * ??CSV?????????????????????顣??????鳤????????????????????null
     */
    public static String[] fromCSVLine(String source, int size) {
        ArrayList tmpArray = fromCSVLinetoArray(source);
        if (size < tmpArray.size()) {
            size = tmpArray.size();
        }
        String[] rtnArray = new String[size];
        tmpArray.toArray(rtnArray);
        return rtnArray;
    }

    /**
     * ??CSV?????????????????????顣????????鳤???
     */
    public static ArrayList fromCSVLinetoArray(String source) {
        if (source == null || source.length() == 0) {
            return new ArrayList();
        }
        int currentPosition = 0;
        int maxPosition = source.length();
        int nextComma = 0;
        ArrayList rtnArray = new ArrayList();
        while (currentPosition < maxPosition) {
            nextComma = nextComma(source, currentPosition);
            rtnArray.add(nextToken(source, currentPosition, nextComma));
            currentPosition = nextComma + 1;
            if (currentPosition == maxPosition) {
                rtnArray.add("");
            }
        }
        return rtnArray;
    }


    /**
     * ????????????????????????CSV?С??????CSV???????????
     */
    public static String toCSVLine(String[] strArray) {
        if (strArray == null) {
            return "";
        }
        StringBuffer cvsLine = new StringBuffer();
        for (int idx = 0; idx < strArray.length; idx++) {
            String item = addQuote(strArray[idx]);
            cvsLine.append(item);
            if (strArray.length - 1 != idx) {
                cvsLine.append(',');
            }
        }
        return cvsLine.toString();
    }

    /**
     * ??????????List????????CSV?С??????CSV???????????
     */
    public static String listToCSVLine(ArrayList strArrList) {
        if (strArrList == null) {
            return "";
        }
        String[] strArray = new String[strArrList.size()];
        for (int idx = 0; idx < strArrList.size(); idx++) {
            strArray[idx] = (String) strArrList.get(idx);
        }
        return toCSVLine(strArray);
    }

    // ==========????????????????==============================

    /**
     * ?????????????????
     *
     * @param str   ??????
     * @param c     ????
     * @param start ???λ??
     * @return ????
     */
    private int countChar(String str, char c, int start) {
        int i = 0;
        int index = str.indexOf(c, start);
        return index == -1 ? i : countChar(str, c, index + 1) + 1;
    }

    /**
     * ?????????????λ?á?
     *
     * @param source ??????
     * @param st     ???????λ??
     * @return ??????????λ?á?
     */
    private static int nextComma(String source, int st) {
        int maxPosition = source.length();
        boolean inquote = false;
        while (st < maxPosition) {
            char ch = source.charAt(st);
            if (!inquote && ch == ',') {
                break;
            } else if ('"' == ch) {
                inquote = !inquote;
            }
            st++;
        }
        return st;
    }

    /**
     * ?????????????
     */
    private static String nextToken(String source, int st, int nextComma) {
        StringBuffer strb = new StringBuffer();
        int next = st;
        while (next < nextComma) {
            char ch = source.charAt(next++);
            if (ch == '"') {
                if ((st + 1 < next && next < nextComma) && (source.charAt(next) == '"')) {
                    strb.append(ch);
                    next++;
                }
            } else {
                strb.append(ch);
            }
        }
        return strb.toString();
    }

    /**
     * ???????????????????????????????????????????????"?????""??
     *
     * @param item ?????
     * @return ????????????
     */
    private static String addQuote(String item) {
        if (item == null || item.length() == 0) {
            return "\"\"";
        }
        StringBuffer sb = new StringBuffer();
        sb.append('"');
        for (int idx = 0; idx < item.length(); idx++) {
            char ch = item.charAt(idx);
            if ('"' == ch) {
                sb.append("\"\"");
            } else {
                sb.append(ch);
            }
        }
        sb.append('"');
        return sb.toString();
    }
}
