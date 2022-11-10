package com.zimug.dongbb.server.jwt.demo.upload;

import com.zimug.commons.exception.AjaxResponse;
import com.zimug.commons.exception.CustomExceptionType;
import com.zimug.dongbb.oss.service.IFileAccessService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/file")
public class FileAccessController {

    @Resource
    private IFileAccessService fileAccessService;

    /**
     * 文件上传请求
     */
    @PostMapping("/upload")
    public @ResponseBody AjaxResponse upload(MultipartFile uploadFile,
                                             HttpServletRequest request)  {
      try {
        //上传并返回访问地址
        return AjaxResponse.success(fileAccessService.uploadFile(uploadFile,request));
      } catch (Exception e){
        e.printStackTrace();
        return AjaxResponse.error(CustomExceptionType.SYSTEM_ERROR,
          "文件上传失败,请检查程序或者联系管理员!");
      }
    }
}