package com.yhh.oss.controller.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {


    String uploadFileAvatar(MultipartFile file);

}
