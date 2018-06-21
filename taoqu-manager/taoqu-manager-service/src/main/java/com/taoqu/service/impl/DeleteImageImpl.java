/**
 * 
 */
package com.taoqu.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taoqu.common.utils.ExecuteShellCommand;
import com.taoqu.service.DeleteImageService;

/**
 * 2018年5月18日
 * DeleteImageImpl.java
 * @author xushaoqun
 * desc:删除服务器上的图片服务
 */
@Service
public class DeleteImageImpl implements DeleteImageService {
	
	@Value("${FTP_ADDRESS}")
	private String host;
	@Value("${SSH_PORT}")
	private Integer port;
	@Value("${FTP_USERNAME}")
	private String username;
	@Value("${FTP_PASSWORD}")
	private String password;
	@Value("${FTP_BASEPATH}")
	private String bashpath;
	
	@Override
	public String deleteImage(String url) {
		// 分割url
		String[] stringList = url.split("images");
		//获取字符串数组最后一个元素，即：/2018/x/xx/xxxx.jpg
		String urlPiece = stringList[stringList.length - 1];
		final String command = "rm " + bashpath  + urlPiece;
		String result = ExecuteShellCommand.executeCommand(port, host, username, password, command);
		System.out.println("删除结果为："+result);
		return result;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the bashpath
	 */
	public String getBashpath() {
		return bashpath;
	}

	/**
	 * @param bashpath the bashpath to set
	 */
	public void setBashpath(String bashpath) {
		this.bashpath = bashpath;
	}

	
}
