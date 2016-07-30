package rscloudenviroment;
/*
 * 云主机类
 * 
 */
public class RSHost {
	
	public String VEid;
	public String VEip;
	public String VEmessage;
	public String VEname;
	public String VEstatus;
	public String VEurl;
	//云主机拥有者-----数据库标识---------根据登录的用户判断显示的主机
	public String VEowner;

	public RSHost() {
		 this.VEid = "";
		 this.VEip = "";
		 this.VEmessage = "";
		 this.VEname = "";
		 this.VEstatus = "";
		 this.VEurl = "";
		 this.VEowner = "";
		
	}



	@Override
	public String toString() {
		return "RSHost [VEid=" + VEid + ", VEip=" + VEip + ", VEmessage=" + VEmessage + ", VEname=" + VEname
				+ ", VEstatus=" + VEstatus + ", VEurl=" + VEurl + ", VEowner=" + VEowner + "]";
	}



	public String getVEowner() {
		return VEowner;
	}



	public void setVEowner(String vEowner) {
		VEowner = vEowner;
	}



	public String getVEid() {
		System.out.println("RSHost.getVEid()");
		return VEid;
	}

	public void setVEid(String vEid) {
		System.out.println("RSHost.setVEid()");
		this.VEid = vEid;
	}

	public String getVEip() {
		System.out.println("RSHost.getVEip()");
		return VEip;
	}

	public void setVEip(String vEip) {
		System.out.println("RSHost.setVEip()");
		this.VEip = vEip;
	}

	public String getVEmessage() {
		System.out.println("RSHost.getVEmessage()");
		return VEmessage;
	}

	public void setVEmessage(String vEmessage) {
		System.out.println("RSHost.setVEmessage()");
		this.VEmessage = vEmessage;
	}

	public String getVEname() {
		System.out.println("RSHost.getVEname()");
		return VEname;
	}

	public void setVEname(String vEname) {
		System.out.println("RSHost.setVEname()");
		this.VEname = vEname;
	}

	public String getVEstatus() {
		System.out.println("RSHost.getVEstatus()");
		return VEstatus;
	}

	public void setVEstatus(String vEstatus) {
		System.out.println("RSHost.setVEstatus()");
		this.VEstatus = vEstatus;
	}

	public String getVEurl() {
		System.out.println("RSHost.getVEurl()");
		return VEurl;
	}

	public void setVEurl(String vEurl) {
		System.out.println("RSHost.setVEurl()");
		this.VEurl = vEurl;
	}


}
