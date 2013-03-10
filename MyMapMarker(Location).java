package jp.ac.tokaipc.mymapmarker;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//位置情報取得はLocationListenerとLocationSourceをインプリメント
public class MyMapMarker extends Activity 
implements LocationListener, LocationSource{ 
	// マーカー用の緯度経度
    private static final LatLng TOKAIPC = new LatLng(35.4927, 136.6438);
    private static final LatLng SOFTOPIA = new LatLng(35.3676, 136.6396);
    private static final LatLng OGAKI = new LatLng(35.3667, 136.6170);

    private GoogleMap mMap;
    //ロケーションリスナーとロケーションマネージャの宣言
    private OnLocationChangedListener mListener;
    private LocationManager locationManager;
    
    // マーカー宣言
    private Marker mTokaipc;
    private Marker mSoftopia;
    private Marker mOhgaki;
    private Marker mCurrent;
    
    private TextView mTopText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_map_marker);
		mTopText = (TextView)findViewById(R.id.top_text);
		// LocationManager生成
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		if(locationManager != null){
			boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if(gpsIsEnabled){	//LocationListener登録
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, 5000L, 10F, this);//プロバイダ、間隔（ミリ秒）、精度（ｍ）、リスナー
			}else if(networkIsEnabled){
				locationManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER, 5000L, 10F, this);
			}
		}
		
		setUpMapIfNeeded();
	}
	
	@Override
	public void onPause(){
		if(locationManager != null){
			locationManager.removeUpdates(this);	//LocationListener解放
		}
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		setUpMapIfNeeded();
		if(locationManager != null){
			mMap.setMyLocationEnabled(true);	//現在位置を反映
		}
	}
	
    private void setUpMapIfNeeded() {
    	if(mMap == null){
            mMap =  ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
            		.getMap();
            
            if (mMap != null) {
                setUpMap();
            }
            mMap.setLocationSource(this);	//LocationSource有効（置換え）
        }
    }
    
    private void setUpMap(){
    	addMarkersToMap();	// マーカ追加メソド
    	//マップフラグメントからViewを生成し、Viewの変化をリスナーで取得または通知する
    	 final View mapView = getFragmentManager().findFragmentById(R.id.map).getView();
         if (mapView.getViewTreeObserver().isAlive()) {
             mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
         
                 @Override
                 public void onGlobalLayout() {
                	 //LatLngBoundsは緯度経度に平行に区切られた区画
                     LatLngBounds bounds = new LatLngBounds.Builder()
                             .include(OGAKI)
                            // .include(TOKAIPC)
                             .include(SOFTOPIA)
                             .build();
                   
                     mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                     //複数マーカーが表示できるサイズにズームインする
                     mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50)); // move and zoom(greatest)
                     //マップの中心座標の表示
//                     mTopText.setText("" +
//             				mMap.getCameraPosition().target.latitude + ", " +
//             				mMap.getCameraPosition().target.longitude);
                 }
             });
         }
    	mMap.setMyLocationEnabled(true);
    }
    
    private void addMarkersToMap() {
        // マーカーレンダリング（カラーアイコン）
//        mSoftopia = mMap.addMarker(new MarkerOptions()
//                .position(SOFTOPIA)
//                .title("ソフトピア")
//                .snippet("Softpia")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // 矢印画像
        mTokaipc = mMap.addMarker(new MarkerOptions()
        		.position(TOKAIPC)
			    .title("東海能開大")
			    .snippet("PolytechCollegeTokai")
			    .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow)));
        // 標準のアイコン
        mOhgaki = mMap.addMarker(new MarkerOptions()
        		.position(OGAKI)
        		.title("大垣駅")
        		.snippet("Ogaki Station"));
        
        mCurrent = mMap.addMarker(new MarkerOptions()
				.position(SOFTOPIA)
				.title("現在地")
				.snippet("Current Possition")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
      
    }

	@Override
	public void activate(OnLocationChangedListener listener) {
		mListener = listener;
	}

	@Override
	public void deactivate() {
		mListener = null;
	}
	//位置情報が更新されたときに呼ばれる
	@Override
	public void onLocationChanged(Location location) {
		if(mListener != null){
			mListener.onLocationChanged(location);//新しい位置情報の取得
			mMap.animateCamera(CameraUpdateFactory.newLatLng(
					new LatLng(location.getLatitude(), location.getLongitude())));
			//マーカーの位置情報を更新
			mCurrent.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
		}
//		mTopText.setText("" +
//				mMap.getCameraPosition().target.latitude + ", " +
//				mMap.getCameraPosition().target.longitude);
			
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Toast.makeText(this, "onProviderDisabled", Toast.LENGTH_SHORT).show();		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		Toast.makeText(this, "onProviderEnabled", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Toast.makeText(this, "onStatusChanged", Toast.LENGTH_SHORT).show();
		
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_my_map_marker, menu);
//		return true;
//	}

}
