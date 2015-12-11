package com.file

import java.io.File
import scala.util.control._
import scala.collection.mutable.Map
import scala.collection.mutable.LinkedHashMap
import scala.io.Source
import scala.io.BufferedSource

/**
 * @author ada
 */
object FileOperation {
  var news: Map[String, Int] = Map()
  var newMap: LinkedHashMap[String, Int] = LinkedHashMap()
  val loop = new Breaks
  /**
   * 遍历指定目录下的所有文件
   * */
 def walk(file:File){
   if(file.isFile()){
       news += (file.getPath() -> file.getParentFile().getName().toInt)
//       println("文件："+file.getName())
   }
   else{
//      println("文件夹："+file.getName)
      file.listFiles().foreach(walk)
   }
  }
 /**
   * 将Map根据value进行排序
   * */
 def printSortValues(map : Map[String, Int]){        
     map.toList sortBy ( _._2 ) foreach {
     case (key,value) =>
       newMap += (key -> value)
     }    
 }
 /**
 * 将文件读入内存
 * */
 def readFileByTime(map: Map[String, Int]){
     val mapSize = map.size
     var files = new Array[BufferedSource](mapSize+1)
     try{
       var i = 1
       map.foreach(e => {
       val (k,v) = e
       files(i) = Source.fromFile(k)
              
       val contents = files(i).mkString
       
//       println(i+ "---------" +contents)

       i += 1
       }
      )
     }
     catch{
       case e: Exception=>{
         println("文件打开异常"+e.toString())
       }
     }
     finally{
       for(i <- 1 to mapSize){
         files(i).close()
       }
     }
 }

 def main(args: Array[String]): Unit = {
      walk(new File("D:/E/files/mydata"))
      printSortValues(news)

//      println(newMap)
      println()

   //遍历news，newMap
//      news.foreach(e => {
//      val (k,v) = e
//      println(k+"--------"+v)
//   })
   println()
   newMap.foreach(e => {
      val (k,v) = e
      println(k+":"+v)
   })

      readFileByTime(newMap)
  }
}