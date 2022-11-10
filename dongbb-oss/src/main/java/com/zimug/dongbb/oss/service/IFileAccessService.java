package com.zimug.dongbb.oss.service;

import com.zimug.dongbb.oss.model.UploadResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @创建人 字母哥
 * @创建时间 2021/3/18
 * @描述
 **/
public interface IFileAccessService {

  /**
   * 文件上传接口
   *
   * @param file 上传的文件
   * @return 访问地址
   * @throws Exception
   */
  public UploadResult uploadFile(MultipartFile file,
                                 HttpServletRequest request) throws Exception;


}
