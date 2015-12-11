package com.file

/**
 * Created by HM on 2015/9/19.
 */
import java.io.File
import scala.util.control._

object ConstructFileIndex {
  val loop = new Breaks
  /**
   * ���ݵ�ǰ���ļ�����ȡ���ļ������Ŀ¼��·��
   * */
  def getFileIndex(path: String, rootPath: String): String= {
    var fileIndex = new File(path).getName()
    var parentFile = new File(path).getParentFile()
    var name = parentFile.getName()
    loop.breakable{
      while (true){
        if(name != rootPath){
          fileIndex += ("-"+name)
          parentFile = parentFile.getParentFile()
          name = parentFile.getName()
        }else{
          fileIndex += ("-"+name)
          loop.break()
        }
      }
    }
    return fileIndex
  }
//  def main(args: Array[String]): Unit = {
//    println(getFileIndex("D:/E/files/mydata/crawl/text/sina.com.cn/finance/20150906", "crawl"))
//  }
}
