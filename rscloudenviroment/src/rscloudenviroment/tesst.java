package rscloudenviroment;


/**
 * <p>Title: </p>
 * <p>Description: ������ȡswiftԭ����  ���Լ��½�����Ŀ¼</p>
 * @author  xudong
 * @date 2016/7/27
 */
public class tesst {

	public static void main(String[] args) {
		
		//��������
		VEStudio rSVeStudio = StudioFactory.creator("RSVE");
		
		
		boolean er = rSVeStudio.validation("pipsCloud","pipsCloudTeam");
		if(er == true){
			System.out.println("=====����openstack�ɹ�====");
		}else{
			System.out.println("=====����openstackʧ��====");
			return;
		}
		
		
		/*
		 * to-do
		 * ����һ�������� xdtest
		 */
		
		rSVeStudio.buildSwiftStorage("xd");
		
		//ȡ��������ԭ����
		
		
		
		
		
		
		/*
		 * 
		 * 
		 */

	}

}
