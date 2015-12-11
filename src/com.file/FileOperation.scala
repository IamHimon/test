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
   * ����ָ��Ŀ¼�µ������ļ�
   * */
 def walk(file:File){
   if(file.isFile()){
       news += (file.getPath() -> file.getParentFile().getName().toInt)
//       println("�ļ���"+file.getName())
   }
   else{
//      println("�ļ��У�"+file.getName)
      file.listFiles().foreach(walk)
   }
  }
 /**
   * ��Map����value��������
   * */
 def printSortValues(map : Map[String, Int]){        
     map.toList sortBy ( _._2 ) foreach {
     case (key,value) =>
       newMap += (key -> value)
     }    
 }
 /**
 * ���ļ������ڴ�
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
         println("�ļ����쳣"+e.toString())
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

   //����news��newMap
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