package com.ada

import java.io.{IOException, BufferedWriter, FileWriter, File}

import com.file.FileCompressed
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.immutable.HashMap
import scala.collection.mutable.{ListBuffer, LinkedHashMap, Map}
import scala.io.Source
import scala.util.control.Breaks

/**
 * Created by HM on 2015/9/19.
 */
object doJoin {


  def storeLastMap(map: HashMap[String, List[String]]): Unit = {

    var fw: FileWriter = null
    var writer: BufferedWriter = null

    try {
      fw = new FileWriter(new File("D://E/files/resultMap.txt"))
      //      fw = new FileWriter(new File("hdfs://node1:9000/user/humeng/test/resultMap.txt"))  //spark????§Ö????
      writer = new BufferedWriter(fw)

      map.foreach(e => {
        val (k, v) = e
        println(k)
        writer.write(k + ",")

        val list = map(k) //????ArrayList????
        for (l <- list) {
          println(l)
          if (l != list.last) {
            writer.write(l + ",")
          } else {
            writer.write(l)
          }
        }
        writer.newLine()
        writer.flush()
      }
      )

    } catch {
      case ex: IOException => {
        println("IO Exception")
      }

    } finally {
      fw.close()
      writer.close()
    }


  }


  def main(args: Array[String]) {


    //    print(getSimpNews("D:\\E\\files\\mydata\\crawl\\text\\sina.com.cn\\finance\\20150907\\c1004-27549149l.txt"))
    //    getSimpNews("D:\\E\\files\\mydata\\crawl\\text\\sina.com.cn\\finance\\20150907\\c1004-27549149l.txt")

    //
    //    var map = new HashMap[String, List[String]]() //??§Ö?map(???ID??list)??list??????join?????????path
    //
    //
    //
    //    val conf = new SparkConf().setAppName("readCSVFile")
    //    val sc = new SparkContext(conf)
    //
    //    val rdd = sc.textFile("hdfs://node1:9000/user/humeng/ada/mapCSV.txt")
    //    val list = rdd.toArray()
    //    for(l <- list){
    //      println(l.split(",")(1))
    //
    //      val result = getRelatedNewsPathList(l.split(",")(1))
    //        if (result.size != 0) {
    //          map += (l.split(",")(0) -> result) //?????map
    //          println(map)
    //          storeLastMap(map) //?›¥map
    //        }
    //
    //    }


    //    val rdd = sc.textFile("hdfs://node1:9000/user/humeng/fileShen/shen.csv")
    //    val list = rdd.toArray()
    //    for(l <- list){
    //      println(l.split(",")(1))
    //
    //    }


//    var map = new HashMap[String, List[String]]()
//
//    val lines = Source.fromFile("D:/E/files/step2/result111.txt").getLines().toList
//    for (list <- lines) {
//      val arr = list.split(",")
//      println(arr(1))
//
//      val result = getRelatedNewsPathList(arr(1))
//      if (result.size != 0) {
//        map += (arr(0) -> result)
//        println(map)
//        storeLastMap(map)
//        //                map.toArray
//      }
//    }
  }
}
