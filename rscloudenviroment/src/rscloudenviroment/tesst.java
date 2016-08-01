package rscloudenviroment;


/**
 * <p>Title: </p>
 * <p>Description: 测试提取swift原数据  ，以及新建虚拟目录</p>
 * @author  xudong
 * @date 2016/7/27
 */
public class tesst {

	public static void main(String[] args) {
		
		//工厂方法
		VEStudio rSVeStudio = StudioFactory.creator("RSVE");
		
		
		boolean er = rSVeStudio.validation("pipsCloud","pipsCloudTeam");
		if(er == true){
			System.out.println("=====进入openstack成功====");
		}else{
			System.out.println("=====进入openstack失败====");
			return;
		}
		
		
		/*
		 * to-do
		 * 创建一个新容器 xdtest
		 */
		
		rSVeStudio.buildSwiftStorage("xd");
		
		//取出容器的原数据
		String lizi = "/home/HJ1/sensor1/guangzhou/20150717/file";
		String[] ll = lizi. split("\\/");
		
		for(int i =0;i<ll.length;i++){
			System.out.println(ll[i]+"长度是"+i);
		}
		
		
		
		/*
		 * 
		 * 
		 */

	}

}
