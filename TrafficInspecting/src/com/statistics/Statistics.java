package com.statistics;



import java.util.List;
import com.trafficinspecting.R;
import com.trafficinspecting.TrafficInfo;
import com.trafficinspecting.TrafficInfoProvider;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Statistics extends ActionBarActivity {
	private ListView lv; //展现数据列表
	private TrafficInfoProvider provider; //获取所有具有INTERNE权限的应用的流量信息
	private List<TrafficInfo> trafficInfos;//封装单个具有INTERNET权限的应用的流量信息
    private TrafficAdapter tadapter =new TrafficAdapter();
	/*Handler初识：
	 * 当应用程序启动时，Android首先会开启一个主线程（UI线程），主线程为管理界面中的UI控件，进行事件分发
	 * 定义:主要接受子线程发送的数据，并用此数据配合主线程更新UI。它与子线程通过Message对象传递数据（子线程用sendMessage()方法传递）。
	 * */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg){
			lv.setAdapter(tadapter);
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.statistics);
		
		 lv=(ListView) findViewById(R.id.listview);
		 provider = new TrafficInfoProvider(this);
	       new Thread(){
	    	   public void run(){
	    		   trafficInfos = provider.getTrafficInfos();     //trafficInfos中包含了所有返回的具有INTETNET访问权限的应用
	    		   //向主线程发送一个空消息，用来通知主线程更新数据
	    		   handler.sendEmptyMessage(0);
	    	   };
	       }.start();
	       
	    }
	/*关于自定义adapter
     * 继承自SimpleAdapter,具体复写方法是如下4种：
     * getCount():返回ListView中要显示的子view数量
     * getItem():返回一个子view条目，也可以自定义返回你想要的信息
     * getItemId():根据ListView中的位置返回id
     * getView():返回这个条目的整个信息，是一个单独的布局文件
     * 
     */

	
	
	public class TrafficAdapter extends BaseAdapter {

		@Override
		//得到子view的条目数
		public int getCount() {
			return trafficInfos != null? trafficInfos.size():0;
		}
		//根据ListView位置返回View
		@Override
		public Object getItem(int arg0) {
		 return trafficInfos.get(arg0);

		}
		//根据listview位置得到list中的ID
		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		@Override
		//根据位置得到View对象，ListView中多少个Item就调用多少次
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder = new ViewHolder();
			TrafficInfo info = trafficInfos.get(position);
			//复用缓存的view
			if(convertView == null){
				/*inflate方法：
				 *  inflate()作用是将xml定义的一个布局找出来形成一个view对象，但是隐藏的，有需要时用setContentView(View)显示出来； 
				 * */
				view = View.inflate(getApplicationContext(),R.layout.item, null);
				holder.iv_icon = (ImageView) view.findViewById(R.id.icon);
				holder.tv_name = (TextView) view.findViewById(R.id.appname);
				holder.tv_rx = (TextView) view.findViewById(R.id.downtext);
				holder.tv_tx = (TextView) view.findViewById(R.id.uptext);
				holder.tv_total = (TextView) view.findViewById(R.id.total);
				view.setTag(holder);
				/*View中的setTag(Object)表示给View添加一个格外的数据，以后要用可以用getTag()将这个数据去取出来
				 */
			}else{
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.iv_icon.setImageDrawable(info.getIcon());
			holder.tv_name.setText(info.getAppName());
			long rx = info.getRx();
			long tx = info.getTx();
			//增强程序的健壮性
			if(rx<0) rx = 0;
			if(tx<0) tx = 0;
			//当数据较大时单位转换成M,G
			holder.tv_rx.setText(Formatter.formatFileSize(getApplicationContext(),rx));
			holder.tv_tx.setText(Formatter.formatFileSize(getApplicationContext(), tx));
			long total = tx + rx;
			holder.tv_total.setText(Formatter.formatFileSize(getApplicationContext(), total));
		
			return view;
		}

	}
	static class ViewHolder{
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_tx;
		TextView tv_rx;
		TextView tv_total;
	}

	}
	

