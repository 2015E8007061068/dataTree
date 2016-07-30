package rscloudenviroment;

//import java.io.IOException;

//import org.dom4j.DocumentException;

public interface VEStudio {

	//解析XML文件，将参数形式转换成openstack API所需的参数形式
	public abstract void parsingXMl(VERequest verequest) throws Exception;
	//权限认证、用户认证
	public abstract boolean validation(String username,String password);
	//检查镜像库
	public abstract boolean checkImage();
	//创建基础镜像的云主机
	public abstract String buildBaseVE();
	//安装指定软件
	public abstract void installSoftware();
	//创建模板云主机
	public abstract String buildVE();
	//创建云存储
	public abstract String buildStorage();
	//打包镜像
	public abstract String packageVEImage();
	//上传镜像
	public abstract void registerVEImage();
	//得到创建云主机的信息
	public abstract String getVEID();
	//得到创建的云主机的url
	public abstract String getVEURL();
	

	//账户下创建容器
	public abstract void buildSwiftStorage(String StorageName);
	
}
