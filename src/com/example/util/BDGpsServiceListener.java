package com.example.util;

import java.util.HashMap;
import java.util.Map;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BDGpsServiceListener implements BDLocationListener {

	private Context context;
	
	public BDGpsServiceListener(){
		super();
	}
	public BDGpsServiceListener(Context context){
		super();
		this.context = context;
	}

	//���͹㲥����ʾ���½���
	private void sendToActivity(String str){
		Intent intent = new Intent();
		intent.putExtra("newLoca", str);
		intent.setAction("NEW LOCATION SENT");
		context.sendBroadcast(intent);
	}
	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		Log.i("Listener", "********BDGpsServiceListener onReceiveLocation*******");
		if(location == null){return;}
		StringBuffer sb = new StringBuffer();
		sb.append("����=").append(location.getLongitude());
		sb.append("\nγ��=").append(location.getLatitude());
		sb.append("\nʱ��=").append(location.getTime());
		sb.append("\nERR Code=").append(location.getLocType());
		if (location.hasRadius()){
			sb.append("\n��λ����=").append(location.getRadius());
		}
		if (location.getLocType() == BDLocation.TypeGpsLocation){
			sb.append("\n�ٶ�=");
			sb.append(location.getSpeed());
			sb.append("\n����=");
			sb.append(location.getSatelliteNumber());
		} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
			sb.append("\nλ��=").append(location.getAddrStr());
			sb.append("\nʡ=").append(location.getProvince());
			sb.append("\n��=").append(location.getCity());
			sb.append("\n����=").append(location.getDistrict());
		}
		
		sendToActivity(sb.toString());
		
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("longi", location.getLongitude()+"");
			map.put("lati", location.getLatitude()+"");
			map.put("time", location.getTime());
			String url = HttpUtil.BASE_URL+"coords.do?method=addCoords";
			HttpUtil.postRequest(url, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
