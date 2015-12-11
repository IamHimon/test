package com.spark

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by HM on 2015/10/8.
 */
object readFile {
  def main(args: Array[String]) {
    val start = System.currentTimeMillis()
    if(args.length != 1){
      println("read file:<companyName>")
      return
    }

    val conf = new SparkConf().setAppName("readFile")
    val sc = new SparkContext(conf)

    val rdd1 = sc.textFile(args(0))

    val list1 = rdd1.toArray()

    println("companyName:")
    for(l <- list1){
      println(l.split(",")(1))
    }

    val end = System.currentTimeMillis()
    println("condume:"+(end - start) / 1000+"second")



  }

}
