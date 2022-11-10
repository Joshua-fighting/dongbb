package com.zimug.dongbb.oss.service;

import com.zimug.dongbb.oss.model.UploadResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Objects;

/**
 * @创建人 字母哥
 * @创建时间 2021/3/18
 **/
@Service
@ConditionalOnProperty(name = "file.local.enabled", havingValue = "true")
public class LocalFileAccessServiceImpl implements IFileAccessService {

  //绑定文件上传路径到uploadPath
  @Value("${file.local.upload-path}")
  private String uploadPath;



  @Override
  public UploadResult uploadFile(MultipartFile uploadFile,
                                 HttpServletRequest request) throws Exception {
    // 在 uploadPath 文件夹中通过日期对上传的文件归类保存
    // 比如：/2019/06/06/cf13891e-4b95-4000-81eb-b6d70ae44930.png
    UploadResult uploadResult = new UploadResult(
      request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() ,
      Objects.requireNonNull(uploadFile.getOriginalFilename())
    );
    File folder = new File(uploadPath + uploadResult.getFileStorePath());
    if (!folder.isDirectory()) {
      folder.mkdirs();
    }

    // 文件保存
    uploadFile.transferTo(new File(folder, uploadResult.getNewFileName()));

    // 返回上传文件的访问路径,如：2020/10/18/a9a05df4-6615-4bb5-b859-a3f9bf4bfae0.jpg
    // 前面加上httpDomain就可以访问,https://localhost:8888/2020/10/18/a9a05df4-6615-4bb5-b859-a3f9bf4bfae0.jpg
    return  uploadResult;
  }

}
