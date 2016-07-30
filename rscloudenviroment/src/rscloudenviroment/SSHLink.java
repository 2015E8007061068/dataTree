package rscloudenviroment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHLink {

	public String exec(String host,String user,String psw,int port,String command){
	    String result="";
	    Session session =null;
	    ChannelExec openChannel =null;
	    try {
	      JSch jsch=new JSch();
	      session = jsch.getSession(user, host, port);
	      java.util.Properties config = new java.util.Properties();
	      config.put("StrictHostKeyChecking", "no");
	      session.setConfig(config);
	      session.setPassword(psw);
	      session.connect();
	      openChannel = (ChannelExec) session.openChannel("exec");
	      openChannel.setCommand(command);
	      int exitStatus = openChannel.getExitStatus();
	      System.out.println("����ִ���У��� �Ժ�........");
	      openChannel.connect();  
	            InputStream in = openChannel.getInputStream();  
	            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
	            String buf = null;
	            while ((buf = reader.readLine()) != null) {
	            	//result+= new String(buf.getBytes("gbk"),"UTF-8")+"    <br>\r\n";
	            	result+= new String(buf.getBytes("gbk"),"UTF-8")+"    \r\n";
	            }  
	    } catch (JSchException | IOException e) {
	      result+=e.getMessage();
	    }finally{
	      if(openChannel!=null&&!openChannel.isClosed()){
	        openChannel.disconnect();
	      }
	      if(session!=null&&session.isConnected()){
	        session.disconnect();
	      }
	    }
	    return result;
	  }
	
	//�����ƽڵ� ip  root ����ip�б����ʹ�� �����exec������RSVEStudio���commd������commd�������ص���saltstack��һ������
	public String command(String cmd){
	//	cmd = "salt 'xcat1' state.sls vim";
		String result =exec("10.3.1.123","root","xFMRBwh9",22,cmd);
	
		if(result.equals("")){
			System.out.println("�������1:"+result);
		}else
		System.out.println("ִ�н����ʾ1:"+result);
		return result;
	}
	
	public static void main(String[] args){
		String cmd = "salt "+"'192.168.17.137'"+" state.sls vim;";
  //   String result =exec("10.3.1.123","root","xFMRBwh9",22,cmd);
		SSHLink ss = new SSHLink();
		String result=ss.command(cmd);
		if(result.equals("")){
			System.out.println("�������1:"+result);
		}else
		System.out.println("ִ�н����ʾ1:"+result);
		 boolean s = result.contains("Succeeded:");
		 System.out.println(s);
		
		
	}
}
