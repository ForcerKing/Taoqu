/**
 * 
 */
package com.taoqu.common.test;

import org.junit.Test;

import com.taoqu.common.utils.ExecuteShellCommand;
import com.taoqu.common.utils.JedisUtils;

/**
 * 2018年5月18日
 * testExecuteShellCommand.java
 * @author xushaoqun
 * desc:单元测试用例
 */
public class TestUtils {
	
	@Test
	public void testExecuteShellCommand() {
		String command = "rm /home/ftpuser/www/images/2018/05/10/file.png" ;

		String result = ExecuteShellCommand.executeCommand(22,"192.168.195.178", "ftpuser", "sd19900224", command);
	
	    System.out.println("删除结果为："+ result);
	}

	@Test
	public void testJedisUtils() {
		
		//JedisUtils.getJedis().set("12", "12");
		JedisUtils.getJedisCluster().set("12", "12");
	}
}
