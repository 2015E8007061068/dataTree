package model;

import java.sql.Date;

public class MetadataModel {
	
	//文件路径
	private String filepath;
	
	//所属卫星
	private String statellite;
	
	//所属传感器
	private String sensor;
	
	//拍摄地区
	private String region;
	
	//拍摄日期
	private Date times;
	
	//文件权限
	private String authority;
	
	//文件大小
	private String size;

	public String getFileUrl() {
		return filepath;
	}

	public void setFileUrl(String fileUrl) {
		this.filepath = fileUrl;
	}

	public String getStatellite() {
		return statellite;
	}

	public void setStatellite(String statellite) {
		this.statellite = statellite;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getDate() {
		return times;
	}

	public void setDate(Date date) {
		this.times = date;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	
}
