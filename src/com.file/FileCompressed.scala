package com.file

import scala.io.Source
import scala.collection.mutable._
import scala.util.matching._

/**
 * Created by HM on 2015/9/19.
 */

class FileCompressed {
  /**
   * 提取文件的关键内容
   **/
  def getKeyContentsByNews(news: String ): ListBuffer[String] = {
    var subContents: ListBuffer[String] = ListBuffer()
    var keywordsList: ListBuffer[String] = ListBuffer()
    for (line <- Source.fromFile("sourceFiles/keywordset.txt", "UTF-8").getLines) {
      keywordsList += line
    }

    for (i <- 0 to keywordsList.size - 1) {
      if (news.indexOf(keywordsList(i)) != -1) {
        var tempString = ""
        val regex = new Regex("(，|。)(.*?)" + keywordsList(i))
        //              val regex = """(\s)?(，|。)(.*?)+局""".r
        for (matchsting <- regex.findAllIn(news)) {
          if (matchsting.lastIndexOf("，") != -1) {
            tempString = matchsting.substring(matchsting.lastIndexOf("，") + 1, matchsting.indexOf(keywordsList(i)) + keywordsList(i).length)
            //                   println(matchsting.substring(matchsting.lastIndexOf("，")+1, matchsting.indexOf(keywordsList(i))+1))
          }
          else if (matchsting.lastIndexOf("。") != -1) {
            tempString = matchsting.substring(matchsting.lastIndexOf("。") + 1, matchsting.indexOf(keywordsList(i)) + keywordsList(i).length)
            //                  println(matchsting.substring(matchsting.lastIndexOf("。")+1, matchsting.indexOf(keywordsList(i))+1))
          }
          subContents += tempString
        }
      }
    }
    //     println(subContents)
    return subContents

  }


}