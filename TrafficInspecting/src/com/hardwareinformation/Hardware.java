package com.hardwareinformation;

import com.trafficinspecting.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class Hardware extends ActionBarActivity{
	
	private TextView phone_num;
	private TextView imei;
	private TextView operator;
	private TextView sim_num;
	private TextView imsi;
	private TextView iso;
	private TextView operator_num;
	private TelephonyManager tm;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hardware_information);
		
		phone_num = (TextView) findViewById(R.id.phone_num);
		imei = (TextView) findViewById(R.id.imei);
		operator = (TextView) findViewById(R.id.operator);
		sim_num = (TextView) findViewById(R.id.sim_num);
		imsi = (TextView) findViewById(R.id.imsi);
		iso = (TextView) findViewById(R.id.iso);
		operator_num = (TextView) findViewById(R.id.operator_num);
		
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		phone_num.setText(tm.getLine1Number());
		imei.setText(tm.getDeviceId());
		operator.setText(tm.getNetworkOperatorName());
		sim_num.setText(tm.getSimSerialNumber());
		imsi.setText(tm.getSubscriberId());
		iso.setText(tm.getNetworkCountryIso());
		operator_num.setText(tm.getNetworkOperator());
		
	}
}
