package jp.ac.tokaipc.mymapsv2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

public class MyMapsV2 extends Activity {
// implements OnSeekBarChangeListener{
	private GoogleMap mMap;
	
	private static final LatLng OV_POINT = new LatLng(35.492543,136.641759);
	private GroundOverlay mGroundOverlay;	//オーバーレイ変数
	
	//シークバーオブジェクト宣言と最大値設定
    	//private SeekBar mTransparencyBar;
    	//private static final int TRANSPARENCY_MAX = 100;
    
	static final CameraPosition SOFTOPIA =
            new CameraPosition.Builder().target(new LatLng(35.3676, 136.6396))
                    .zoom(15.5f)
                    .bearing(0)
                    .tilt(60)
                    .build();
	static final CameraPosition TOKAIPC =
            new CameraPosition.Builder().target(new LatLng(35.4927, 136.6438))
                    .zoom(15.5f)
                    .bearing(300)
                    .tilt(10)
                    .build();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_overlay);
		
		//シークバー生成
		//mTransparencyBar = (SeekBar) findViewById(R.id.transparencySeekBar);
        	//mTransparencyBar.setMax(TRANSPARENCY_MAX);
        	//mTransparencyBar.setProgress(0);
        
		setUpMapIfNeeded();
	}

	@Override
  	protected void onResume() {
        	super.onResume();
        	setUpMapIfNeeded();
    	}

	private void setUpMapIfNeeded() {
	        if (mMap == null) {
        		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                		.getMap();
            		if (mMap != null) {
            			setUpMap();
            		}
        	}
	}
	
	private void setUpMap() {
        	mMap.getUiSettings().setZoomControlsEnabled(false);

        	mMap.moveCamera(
                	CameraUpdateFactory.newLatLngZoom(new LatLng(35.4927, 136.6438),11.f));
        	//グランドオーバーレイ
        	mGroundOverlay = mMap.addGroundOverlay(new GroundOverlayOptions()
       	 		.image(BitmapDescriptorFactory.fromResource(R.drawable.schoolmap)).anchor(0, 1)
        		.position(OV_POINT, 2400f, 1800f));

		//シークバーリスナー登録
		//mTransparencyBar.setOnSeekBarChangeListener(this);
        }
	
	public void onGoToSoftopia(View view) {
       		mMap.animateCamera(CameraUpdateFactory.newCameraPosition(SOFTOPIA),null);
    	}
	public void onGoToTokaipc(View view) {
        	mMap.animateCamera(CameraUpdateFactory.newCameraPosition(TOKAIPC),null);
    	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_my_maps_v2, menu);
//		return true;
//	}

	//シークバーの基本メソド
//	@Override
//	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//		// 値の変化に応じて透過度を変化させる
//		 if (mGroundOverlay != null) {
//	            mGroundOverlay.setTransparency((float) progress / (float) TRANSPARENCY_MAX);
//	        }
//	}
//
//	@Override
//	public void onStartTrackingTouch(SeekBar arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onStopTrackingTouch(SeekBar arg0) {
//		// TODO Auto-generated method stub
//		
//	}

}
