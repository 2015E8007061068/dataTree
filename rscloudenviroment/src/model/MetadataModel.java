package model;

import java.sql.Date;

public class MetadataModel {
	
	//�ļ�·��
	private String filepath;
	
	//��������
	private String statellite;
	
	//����������
	private String sensor;
	
	//�������
	private String region;
	
	//��������
	private Date times;
	
	//�ļ�Ȩ��
	private String authority;
	
	//�ļ���С
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
