package rscloudenviroment;

//import java.io.IOException;

//import org.dom4j.DocumentException;

public interface VEStudio {

	//����XML�ļ�����������ʽת����openstack API����Ĳ�����ʽ
	public abstract void parsingXMl(VERequest verequest) throws Exception;
	//Ȩ����֤���û���֤
	public abstract boolean validation(String username,String password);
	//��龵���
	public abstract boolean checkImage();
	//�������������������
	public abstract String buildBaseVE();
	//��װָ�����
	public abstract void installSoftware();
	//����ģ��������
	public abstract String buildVE();
	//�����ƴ洢
	public abstract String buildStorage();
	//�������
	public abstract String packageVEImage();
	//�ϴ�����
	public abstract void registerVEImage();
	//�õ���������������Ϣ
	public abstract String getVEID();
	//�õ���������������url
	public abstract String getVEURL();
	

	//�˻��´�������
	public abstract void buildSwiftStorage(String StorageName);
	
}
