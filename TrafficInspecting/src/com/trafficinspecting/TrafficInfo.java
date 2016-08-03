package com.trafficinspecting;

import android.graphics.drawable.Drawable;

//定义一个类TrafficInfo表示应用的各项信息
public class TrafficInfo {
	private String packname;
	private String appname;
	private long tx;
	private long rx;
	private Drawable icon;
	
	public String getPackName(){
		return packname;
	}
	public void setPackName(String packname){
		this.packname = packname;
	}
	public String getAppName(){
		return appname;
	}
	public void setAppName(String appname){
		this.appname = appname;
	}
	public long getTx(){
		return tx;
	}
	public void setTx(long tx){
		this.tx = tx;
	}
	public long getRx(){
		return rx;
	}
	public void setRx(long rx){
		this.rx = rx;
	}
	public Drawable getIcon(){
		return icon;
	}
	public void setIcon(Drawable icon){
		this.icon = icon;
	}
}
