/**
 * 
 */
package com.taoqu.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taoqu.common.utils.FtpUtil;
import com.taoqu.common.utils.IDUtils;
import com.taoqu.service.PictureService;

/**
 * 2018年5月4日
 * PictureServiceImpl.java
 * @author xushaoqun
 * desc:用于文件上传
 */
@Service
public class PictureServiceImpl implements PictureService {
    
	//利用spring中的value注解，从.properties文件中直接获取相应属性的值，赋给私有变量
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASEPATH}")
	private String FTP_BASEPATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	@Override
	public Map uploadPicture(MultipartFile uploadFile) {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap();
		try {
			//取得上传上来的原始图片文件名
			String oldName = uploadFile.getOriginalFilename();

			//生成新的文件名，以这个新文件名保存在FTP服务器上。这么做是因为避免oldName会有重复的情况
			String newName = IDUtils.genImageName();
			//从oldName中截取后缀名，添加到新的文件名中，形成最终的全新文件名
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			//正式上传图片
			String  imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD,
					FTP_BASEPATH,imagePath, newName, uploadFile.getInputStream());
			//返回结果
			if(!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");
				return resultMap;
			}
			resultMap.put("error", 0);
			//形成完整的URL，例如http://192.168.195.178/images/2015/12/12/xxxxxxx.jpg
			resultMap.put("url", IMAGE_BASE_URL + imagePath + "/" + newName);
			return resultMap;
		} catch (IOException e) {
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");
			return resultMap;
		}
	}

	/**
	 * @return the fTP_ADDRESS
	 */
	public String getFTP_ADDRESS() {
		return FTP_ADDRESS;
	}

	/**
	 * @param fTP_ADDRESS the fTP_ADDRESS to set
	 */
	public void setFTP_ADDRESS(String fTP_ADDRESS) {
		FTP_ADDRESS = fTP_ADDRESS;
	}


	/**
	 * @return the fTP_PORT
	 */
	public Integer getFTP_PORT() {
		return FTP_PORT;
	}

	/**
	 * @param fTP_PORT the fTP_PORT to set
	 */
	public void setFTP_PORT(Integer fTP_PORT) {
		FTP_PORT = fTP_PORT;
	}

	/**
	 * @return the fTO_USERNAME
	 */
	public String getFTP_USERNAME() {
		return FTP_USERNAME;
	}

	/**
	 * @param fTO_USERNAME the fTO_USERNAME to set
	 */
	public void setFTP_USERNAME(String fTP_USERNAME) {
		FTP_USERNAME = fTP_USERNAME;
	}

	/**
	 * @return the fTP_PASSWORD
	 */
	public String getFTP_PASSWORD() {
		return FTP_PASSWORD;
	}

	/**
	 * @param fTP_PASSWORD the fTP_PASSWORD to set
	 */
	public void setFTP_PASSWORD(String fTP_PASSWORD) {
		FTP_PASSWORD = fTP_PASSWORD;
	}

	/**
	 * @return the fTP_BASEPATH
	 */
	public String getFTP_BASEPATH() {
		return FTP_BASEPATH;
	}

	/**
	 * @param fTP_BASEPATH the fTP_BASEPATH to set
	 */
	public void setFTP_BASEPATH(String fTP_BASEPATH) {
		FTP_BASEPATH = fTP_BASEPATH;
	}

	/**
	 * @return the iMAGE_BASE_URL
	 */
	public String getIMAGE_BASE_URL() {
		return IMAGE_BASE_URL;
	}

	/**
	 * @param iMAGE_BASE_URL the iMAGE_BASE_URL to set
	 */
	public void setIMAGE_BASE_URL(String iMAGE_BASE_URL) {
		IMAGE_BASE_URL = iMAGE_BASE_URL;
	}
	
	
	
}
