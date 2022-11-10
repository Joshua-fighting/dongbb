package com.zimug.dongbb.oss.props;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "file.minio")
@ConditionalOnProperty(
  name = "file.minio.enabled",
  havingValue = "true"
)
public class MinioProperties {

  //是否启用minio作为文件存储
  private Boolean enabled = false;
  //minio的http服务根路径
  private String endpoint;
  //minio accessKey
  private String accessKey;
  //minio secretKey
  private String secretKey;
  //minio bucketName
  private String bucketName;

  @Bean
  public MinioClient minioClient() {
    return MinioClient.builder()
      .endpoint(endpoint)
      .credentials(accessKey, secretKey)
      .build();
  }
}