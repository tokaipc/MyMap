package jp.ac.tokaipc.mymapsv2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MyMapsV2 extends Activity {
	private GoogleMap mMap;
	//マップの表示ポイントをCameraPositionで指定する
	//緯度経度、ズーム、回転、チルト
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
		setContentView(R.layout.activity_main);
		setUpMapIfNeeded();	//マップ取得メソド
	}

	private void setUpMapIfNeeded() {
       		//　マップが用意できているかチェック
        	if (mMap == null) {
            	//　MapFragment( SupportMapFragment)からマップオブジェクトを生成
        		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    	.getMap();
            	//マップ生成したところで各種設定
            		if (mMap != null) {
            			//mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);	//航空写真
            			setUpMap();
            		}
        	}
	}
	
	private void setUpMap() {
        // ズームコントロール表示・非表示
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // カメラ移動によりマップ表示ポイントを移動
        mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(new LatLng(35.4927, 136.6438),10.f));
    	}
	
	//ボタンクリックで表示ポイント切替え
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

}
