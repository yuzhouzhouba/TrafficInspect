package com.trafficinspecting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;

public class TrafficInfoProvider {
	private PackageManager packagemanager;
	/*PackageManager主要是管理应用程序包，它可以获取应用程序的信息
	 * 用getPackageManager()可以获得一个PackageManager的对象
	 * 主要方法:(这里用到的)
	 * 1.getApplicationIcon(String packageName)--返回给定包名的图标，否则返回null
	 * 2.getApplicationInfo(String packageName,int flags):flags:该应用是此flags标记，通常直接赋0即可  --返回该applicationinfo对象
	 * 3.getInstalledApplications(int flags)flags一般为GET_UNINSTALLED_PACKAGES,此时会返回所有ApplicationInfo,我们可以对ApplicationInfo的flags过滤，得到我们需要的。 --返回给定条件的所有ApplicationInfo
	 * 4.getInstalledPackages(int flags)--返回给定条件的所有PackageInfo
	 * */
	private Context context;  //这里为什么要声明context
	public TrafficInfoProvider(Context context){
		this.context = context;
		packagemanager =  context.getPackageManager();
	}
	
	//返回所有的有互联网访问权限的应用程序的流量信息。
	public List<TrafficInfo> getTrafficInfos(){
		//获取配置权限信息的应用程序
		List<PackageInfo> packageinfos = packagemanager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		//存放具有INTERNET权限信息的应用
		List<TrafficInfo> trafficinfos = new ArrayList<TrafficInfo>();
		for(PackageInfo packageinfo : packageinfos){
			//获取该应用的所有权限信息
			String[] permissions = packageinfo.requestedPermissions;
			if(permissions != null&&permissions.length>0){
				for(String permission : permissions){
					//筛选出具有INTERNET权限的应用程序
					if("android.permission.INTERNET".equals(permission)){
						//封装具有Internet权限的应用程序信息
						TrafficInfo trafficinfo = new TrafficInfo();
						trafficinfo.setPackName(packageinfo.packageName);
						trafficinfo.setAppName(packageinfo.applicationInfo.loadLabel(packagemanager).toString());
						trafficinfo.setIcon(packageinfo.applicationInfo.loadIcon(packagemanager));
						//获取到应用的uid(user id)
						int uid = packageinfo.applicationInfo.uid;
						//TrafficStats对象通过应用的uid来获取应用的下载、上传流量信息  
                        trafficinfo.setRx(TrafficStats.getUidRxBytes(uid));  
                        trafficinfo.setTx(TrafficStats.getUidTxBytes(uid));  
                        trafficinfos.add(trafficinfo);  
                        trafficinfo = null;  
                        break;
					}
				}
			}
		}
		return trafficinfos;
	}
	
}
