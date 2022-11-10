package com.zimug.dongbb.oss.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class UploadResult {
  //上传文件HTTP访问根路径
  private String endpoint;
  //上传文件原始文件名
  private String originalFileName;
  //上传文件存储路径
  private String fileStorePath;
  //上传文件新命名文件名，保证唯一性
  private String newFileName;
  //上传文件Http访问地址
  private String accessPath;

  public UploadResult(String endpoint, String originalFileName) {
    this.endpoint = endpoint;
    this.originalFileName = originalFileName;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    this.fileStorePath = sdf.format(new Date());
    this.newFileName = UUID.randomUUID().toString()
      + originalFileName.substring(originalFileName.lastIndexOf("."));
    this.accessPath = endpoint + "/" + this.fileStorePath + this.newFileName;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public String getFileStorePath() {
    return fileStorePath;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public String getNewFileName() {
    return newFileName;
  }

  public String getAccessPath() {
    return accessPath;
  }
}
