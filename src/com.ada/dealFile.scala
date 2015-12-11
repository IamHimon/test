package com.ada

import scala.collection.immutable.HashSet
import scala.collection.mutable
import scala.io.Source

/**
 * Created by HM on 2015/9/23.
 */
object dealFile {
  def main(args: Array[String]) {
    var i = 0
    var set = HashSet[String]()
    val str = mutable.StringBuilder
    var set1 = mutable.LinkedHashSet()
    var set2 = mutable.TreeSet[String]()

    val lines = Source.fromFile("sourceFiles/dic.txt").getLines()
    for(line <- lines){
      val arr = line.split(",")
//      println(arr(0))
//      set += arr(0)
//      set1.addString(arr(0).toString)
      set2.+=(arr(0))
      i += 1
    }

//    Source.fromFile("D:/E/files/mapCSV.txt").foreach({
//      print
//    })

//    println(set.size)
    set2.foreach({
      println
    })
//    println(i)
  }
}
