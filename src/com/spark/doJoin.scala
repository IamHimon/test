package com.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by HM on 2015/9/25.
 */
object doJoin {
  def main(args: Array[String]) {
    val start = System.currentTimeMillis()

    if (args.length != 2) {
      println("read file:<companyName><news>")
      return
    }

    val conf = new SparkConf().setAppName("doJoin")
    val sc = new SparkContext(conf)

//    val rdd1 = sc.textFile(args(0)) //read companyName
    val rdd2 = sc.textFile(args(1)) //read news

//    val list1 = rdd1.toArray()
    val list2 = rdd2.toArray()

    //    for (l <- list1) {
    //      println(l.split(",")(1))
    //    }

        for(s <- list2){
          println(s.split("::")(1))
        }
//
//    for (l <- list1) {
//      for (s <- list2) {
//        if (s.split("::")(1).contains(l.split(",")(1))) {
//          println(l.split(",")(0) + "::" + s.split("::")(0))
//        }
//      }
//    }

    val end = System.currentTimeMillis()
    println("consume:" + ((end - start) / 1000).toDouble + "second")
  }
}
