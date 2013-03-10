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
	//�}�b�v�̕\���|�C���g��CameraPosition�Ŏw�肷��
	//�ܓx�o�x�A�Y�[���A��]�A�`���g
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
		setUpMapIfNeeded();	//�}�b�v�擾���\�h
	}

	private void setUpMapIfNeeded() {
       		//�@�}�b�v���p�ӂł��Ă��邩�`�F�b�N
        	if (mMap == null) {
            	//�@MapFragment( SupportMapFragment)����}�b�v�I�u�W�F�N�g�𐶐�
        		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    	.getMap();
            	//�}�b�v���������Ƃ���Ŋe��ݒ�
            		if (mMap != null) {
            			//mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);	//�q��ʐ^
            			setUpMap();
            		}
        	}
	}
	
	private void setUpMap() {
        // �Y�[���R���g���[���\���E��\��
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // �J�����ړ��ɂ��}�b�v�\���|�C���g���ړ�
        mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(new LatLng(35.4927, 136.6438),10.f));
    	}
	
	//�{�^���N���b�N�ŕ\���|�C���g�ؑւ�
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
