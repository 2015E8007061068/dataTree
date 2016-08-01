package virtualdirectory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.LogManager;

import rscloudenviroment.StudioFactory;
import rscloudenviroment.VEStudio;

public class Test1 {



	/*
	 * �������container��ԭ����
	 */
	public static void printMetaData(Map<String, String> metaData) {

		String key = null;
		String value = null;
		Iterator iter = metaData.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			key = (String) entry.getKey();
			value = (String) entry.getValue();
			System.out.println("Ԫ���ݵ�keyΪ��" + key + " Ԫ���ݵ�valueΪ��" + value);

		}
	}
	
	 

	public static void main(String[] args) {
		
		// VEStudio rSVeStudio = StudioFactory.creator("RSVE");

		// ������Ҫ�ĳɹ�������
		 RSVEStudioChild rSVeStudio = new RSVEStudioChild();

		boolean er = rSVeStudio.validation("pipsCloud", "pipsCloudTeam");
		if (er == true) {
			System.out.println("=====����openstack�ɹ�====");
		} else {
			System.out.println("=====����openstackʧ��====");
			return;
		}

		/*
		 * to-do ����һ�������� xdtest
		 */

		 //rSVeStudio.buildSwiftStorage("xdcopy1");

		// ȡ��������ԭ����
		Map<String, String> metaData = new HashMap<String, String>();
		metaData = rSVeStudio.getMetadata("xd");
		printMetaData(metaData);

		// �����������������Ŀ¼
		//LogManager.getLogManager().reset();
		//rSVeStudio.createfilepath("xd", "����Ŀ¼�ļ�","/home/"+"beijing");
		
		//��һ����������������һ������
		rSVeStudio.copyObjectDuringContainers("home", "HJ1/sensor1/beijing/20150707/faaa", "xd", "sss","/home");
	}

}
