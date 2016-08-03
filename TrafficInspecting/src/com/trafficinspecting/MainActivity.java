package com.trafficinspecting;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.slidingmenu.SlidingMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class MainActivity extends ActionBarActivity implements OnClickListener,OnItemClickListener{
	private ImageButton bt_switch; //�л����໬�˵��İ�ť 
	private SlidingMenu mMenu; //�໬�˵�
	private ListView interface_lv; //��ҳ���Listview�ؼ�
	private SimpleAdapter sadatper; //��simpleadapter������������ҳ���listview
	private List<Map<String,Object>>interface_dataList; //����װ����Դ��datalist
	private ListView slidemenu_lv1;
	private ListView slidemenu_lv2;
	private ListView slidemenu_lv3;
	private ArrayAdapter<String>arr_adapter1;
	private ArrayAdapter<String>arr_adapter2;
	private ArrayAdapter<String>arr_adapter3;
	String[] arr_data1 ={"Ӳ����Ϣ","�������"}; 
	String[] arr_data2 ={"��������","������������"};
	String[] arr_data3 ={"��������","��Ҫ����"};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	//͸��������
    	
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //͸��������
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); 
        
 
        bt_switch=(ImageButton) findViewById(R.id.toSideMenu);
        mMenu = (SlidingMenu) findViewById(R.id.leftmenu);
        interface_lv = (ListView) findViewById(R.id.interface_listview);
        slidemenu_lv1 = (ListView) findViewById(R.id.slidingmenu_lv1);
        slidemenu_lv2 = (ListView) findViewById(R.id.slidingmenu_lv2);
        slidemenu_lv3 = (ListView) findViewById(R.id.slidingmenu_lv3);
        
        interface_dataList = new ArrayList<Map<String,Object>>();
        arr_adapter1 = new ArrayAdapter<String>(this,R.layout.slidingmenu_listview_item,R.id.slidingmenu_tv,arr_data1);
        arr_adapter2 = new ArrayAdapter<String>(this,R.layout.slidingmenu_listview_item,R.id.slidingmenu_tv,arr_data2);
        arr_adapter3 = new ArrayAdapter<String>(this,R.layout.slidingmenu_listview_item,R.id.slidingmenu_tv,arr_data3);
        
       bt_switch.setOnClickListener(this);

       slidemenu_lv1.setAdapter(arr_adapter1);
       slidemenu_lv2.setAdapter(arr_adapter2);
       slidemenu_lv3.setAdapter(arr_adapter3);
       
       //simpleadapter������������
       sadatper = new SimpleAdapter(this,getData(),R.layout.interface_item,new String[]{"imageview","textview"},new int[]{R.id.interface_item_img,R.id.interface_item_tv});
       interface_lv.setAdapter(sadatper); //��listview����������
       interface_lv.setOnItemClickListener(this); //��listview��item���ü����¼�
       slidemenu_lv1.setOnItemClickListener(this);
       slidemenu_lv2.setOnItemClickListener(this);
       slidemenu_lv3.setOnItemClickListener(this);
    }
    
    //�����ݽ�interface_dataList
    private List<? extends Map<String,Object>> getData() {
		// TODO Auto-generated method stub
    	Map<String,Object>map1 = new HashMap<String, Object>();  //ÿ����һ�����ݶ�Ҫ��������HashMap
    	map1.put("imageview",R.drawable.jktj);
    	map1.put("textview","���ͳ��");
    	interface_dataList.add(map1);
    	
    	Map<String,Object>map2 = new HashMap<String, Object>();  //ÿ����һ�����ݶ�Ҫ��������HashMap
    	map2.put("imageview",R.drawable.sz);
    	map2.put("textview","����");
    	interface_dataList.add(map2);
    	
    	Map<String,Object>map3 = new HashMap<String, Object>();  //ÿ����һ�����ݶ�Ҫ��������HashMap
    	map3.put("imageview",R.drawable.rl);
    	map3.put("textview","ÿ���嵥");
    	interface_dataList.add(map3);
    	
		return interface_dataList;
	}


	//��ť�ļ����¼�
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		
		case R.id.toSideMenu:
			mMenu.toggle();
			break;
		}
		
	}
   //��ҳ��listview��item�ļ����¼�
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(parent.getId()==R.id.interface_listview){
		if(position == 0){
			Intent intent1 = new Intent(MainActivity.this,com.statistics.Statistics.class);
			startActivity(intent1);
		}
		
		}else if(parent.getId() == R.id.slidingmenu_lv1){
			if(position == 0){
				Intent intent2 = new Intent(MainActivity.this,com.hardwareinformation.Hardware.class);
				startActivity(intent2);
			}
			
		}else if(parent.getId() == R.id.slidingmenu_lv2){
			
		}else if(parent.getId() == R.id.slidingmenu_lv3){
		if(position == 0){
			Intent intent3 = new  Intent(MainActivity.this,com.aboutme.AboutMe.class);
			startActivity(intent3);
		}else if(position == 1){
			Intent intent4 = new Intent(MainActivity.this,com.feedback.Feedback.class);
			startActivity(intent4);
		}
		}else ;
	}
	
}