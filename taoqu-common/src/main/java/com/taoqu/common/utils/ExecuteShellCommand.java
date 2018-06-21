/**
 * 
 */
package com.taoqu.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * 2018年5月18日
 * DeleteImageUtils.java
 * @author xushaoqun
 * desc:删除服务器上的图片
 */
public class ExecuteShellCommand {

	/*
	 * 远程执行ssh命令,命令执行成功为0，否则为1
	 */
	public static String executeCommand(int port,String host,String username,String password,final String command) {

		 String result = "";
		 Session session = null;
		 ChannelExec openChannel = null;
		 try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(password);
			session.setUserInfo(new MyUserInfo());
			session.connect();
			
			 //打开通道，设置通道类型，和执行的命令
		    openChannel = (ChannelExec) session.openChannel("exec");  
		    openChannel.setCommand(command);  
		    
		    int exitStatus = openChannel.getExitStatus();              
		    openChannel.connect(); 
		    System.out.println(exitStatus);
            InputStream in = openChannel.getInputStream();    
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));    
            String buf = null;  
            while ((buf = reader.readLine()) != null) {  
                result+= new String(buf.getBytes("gbk"),"UTF-8")+"    <br>\r\n";    
            } 
		} catch (JSchException | IOException e) {
			// TODO Auto-generated catch block
			 result+=e.getMessage(); 
		} finally {
	        if(openChannel!=null&&!openChannel.isClosed()){  
	           openChannel.disconnect();  
	        }  
	        if(session!=null&&session.isConnected()){  
	           session.disconnect();  
	        }  
        }
	    return result;
	}
	
	   private static class MyUserInfo implements UserInfo{
	        @Override
	        public String getPassphrase() {
	            System.out.println("getPassphrase");
	            return null;
	        }
	        @Override
	        public String getPassword() {
	            System.out.println("getPassword");
	            return null;
	        }
	        @Override
	        public boolean promptPassword(String s) {
	            System.out.println("promptPassword:"+s);
	            return false;
	        }
	        @Override
	        public boolean promptPassphrase(String s) {
	            System.out.println("promptPassphrase:"+s);
	            return false;
	        }
	        @Override
	        public boolean promptYesNo(String s) {
	            System.out.println("promptYesNo:"+s);
	            return true;//notice here!
	        }
	        @Override
	        public void showMessage(String s) {
	            System.out.println("showMessage:"+s);
	        }
	    }

}
