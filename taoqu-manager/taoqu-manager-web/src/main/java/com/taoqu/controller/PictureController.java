/**
 * 
 */
package com.taoqu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taoqu.common.utils.JsonUtils;
import com.taoqu.service.PictureService;

/**
 * 2018年5月4日
 * PictureController.java
 * @author xushaoqun
 * desc:上传文件处理
 */
@Controller
public class PictureController {

	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile) {
		Map result = pictureService.uploadPicture(uploadFile);
		//对于chrome，我们可以直接返回一个Map,但是火狐不行，火狐只能识别字符串
		//为了保证功能的兼容性，需要把result转换成json格式的字符串
		String json = JsonUtils.objectToJson(result);
		return json;
	} 
	
}
