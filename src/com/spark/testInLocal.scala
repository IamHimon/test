package com.spark

import scala.io.Source

/**
 * Created by HM on 2015/10/8.
 */
object testInLocal {
  def main(args: Array[String]) {
    val start = System.currentTimeMillis()
    val lines = Source.fromFile("D:\\E\\files\\copys\\result1.txt").getLines()
    for(line <- lines) {
      println(line.split(",")(1))
    }
    val end = System.currentTimeMillis()
    println("condume:"+(end-start)+"millisecond")
  }
}
