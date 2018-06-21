/**
 * 
 */
package com.taoqu.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 2018年5月4日
 * PictureService.java
 * @author xushaoqun
 * desc:用于图片上传
 */
public interface PictureService {
	Map uploadPicture(MultipartFile uploadFile) ;
}
