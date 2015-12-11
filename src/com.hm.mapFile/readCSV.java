package com.hm.mapFile;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by HM on 2015/9/18.
 */
public class readCSV {


    //processing name according dic
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


    //processing the original data file
    public static void processFile(String inputPath, String outputPath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("sourceFiles/dic.txt"), Charset.forName("utf-8"));
        File file1 = new File(inputPath);
        File file2 = new File(outputPath);
        BufferedWriter bw = null;
        BufferedReader br = null;
        String temp = null;
        try {
            br = new BufferedReader(new FileReader(file1));
            bw = new BufferedWriter(new FileWriter(file2));
            String result = null;
            while ((temp = br.readLine()) != null) {
                System.out.println(temp);


//                result = temp.replaceAll(aimWord,"");
                bw.write(result);
                bw.newLine();
            }
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ((br != null) && (bw != null)) {
                try {
                    br.close();
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }


    //original treatment
    public static void replaceTreatment(String inputPath, String outputPath, String aimWord) throws IOException {


        List<String> lines = Files.readAllLines(Paths.get("sourceFiles/dic.txt"), Charset.forName("utf-8"));
        File file1 = new File(inputPath);
        File file2 = new File(outputPath);
        BufferedWriter bw = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file1));
            bw = new BufferedWriter(new FileWriter(file2));
            String temp = null;
            String result = null;
            while ((temp = br.readLine()) != null) {
                System.out.println(temp);
                result = temp.replaceAll(aimWord, "");
                bw.write(result);
                bw.newLine();
            }
            bw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ((br != null) && (bw != null)) {
                try {
                    br.close();
                    bw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
//        return outputPath;
    }


    public static void makeNewFile() throws Exception {
        int i = 1;
        String inputFile = "D:\\E\\files\\replaceAllFiles\\maininfo" + 1 + ".txt";
        String outputFile = "D:\\E\\files\\replaceAllFiles\\maininfo" + 2 + ".txt";
        List<String> lines1 = Files.readAllLines(Paths.get("sourceFiles/dic.txt"), Charset.forName("utf-8"));
        for (String line : lines1) {
            System.out.println(line);
            replaceTreatment(inputFile, outputFile, line);
            new File(inputFile).delete();
            i++;
            inputFile = outputFile;
            outputFile = "D:\\E\\files\\replaceAllFiles\\maininfo" + (i + 1) + ".txt";
        }
        new File(inputFile).renameTo(new File("D:\\E\\files\\replaceAllFiles\\result.txt"));
        System.out.println(new File(outputFile).getName());

    }


    // make sure the name is not covered by any other name. If it is covered, then we put the nearest removed name into the company name.
    public static void checkNameWithOtherNames() {


    }


    public static void main(String[] args) throws Exception {

        long begin = System.currentTimeMillis();


        CSVFileUtil csvfu = new CSVFileUtil("D:\\E\\files\\shen\\maininfo1.txt");
        csvfu.buildMapCSV("1results.txt");
//
//        CSVFileUtil csvfu = new CSVFileUtil("D:\\E\\files\\shen\\maininfo2.txt");
//        csvfu.buildMapCSV("2results.txt");
//
//        CSVFileUtil csvfu = new CSVFileUtil("D:\\E\\files\\shen\\maininfo3.txt");
//        csvfu.buildMapCSV("3results.txt");

//        CSVFileUtil csvfu = new CSVFileUtil("D:\\E\\files\\step2\\1results.txt");
//        csvfu.checkNameWithOtherNames("result1111.txt");

//        CSVFileUtil csvfu = new CSVFileUtil("D:\\E\\files\\step2\\allResults.txt");
//        csvfu.checkNameWithOtherNames("lastResult.txt");

        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (double)((end - begin)/1000) + "秒");


    }


}
