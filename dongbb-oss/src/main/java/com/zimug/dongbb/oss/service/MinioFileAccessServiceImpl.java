package com.zimug.dongbb.oss.service;

import com.zimug.dongbb.oss.props.MinioProperties;
import com.zimug.dongbb.oss.model.UploadResult;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
@ConditionalOnProperty(name = "file.minio.enabled", havingValue = "true")
public class MinioFileAccessServiceImpl implements IFileAccessService {

    @Resource
    private MinioProperties minioConfig;

    @Resource
    private MinioClient minioClient;


    @Override
    public UploadResult uploadFile(MultipartFile uploadFile,
                                   HttpServletRequest request) throws Exception {

      UploadResult uploadResult = new UploadResult(
        minioConfig.getEndpoint() + "/" + minioConfig.getBucketName(),
        Objects.requireNonNull(uploadFile.getOriginalFilename()));

      boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder()
                                          .bucket(minioConfig.getBucketName())
                                          .build());
      if(!isExist) {
        minioClient.makeBucket(MakeBucketArgs.builder()
          .bucket(minioConfig.getBucketName())
          .build());
      }

      PutObjectArgs args = PutObjectArgs.builder()
              .bucket(minioConfig.getBucketName())
              .object(uploadResult.getFileStorePath() + uploadResult.getNewFileName())
              .stream(uploadFile.getInputStream(), uploadFile.getSize(), -1)
              .contentType(uploadFile.getContentType())
              .build();

      minioClient.putObject(args);
      return uploadResult;
    }
}