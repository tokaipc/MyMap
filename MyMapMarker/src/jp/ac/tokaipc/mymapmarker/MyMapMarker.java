package jp.ac.tokaipc.mymapmarker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyMapMarker extends Activity {
	// �}�[�J�[�p�̈ܓx�o�x
    private static final LatLng TOKAIPC = new LatLng(35.4927, 136.6438);
    private static final LatLng SOFTOPIA = new LatLng(35.3676, 136.6396);
    private static final LatLng OGAKI = new LatLng(35.3667, 136.6170);

    private GoogleMap mMap;
    // �}�[�J�[�錾
    private Marker mTokaipc;
    private Marker mSoftopia;
    private Marker mOhgaki;
    
    private TextView mTopText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_map_marker);
		
		setUpMapIfNeeded();
	}
	
    private void setUpMapIfNeeded() {
    	if(mMap == null){
            mMap =  ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
            		.getMap();
            
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    
    private void setUpMap(){
    	addMarkersToMap();	// �}�[�J�ǉ����\�h
    	//�}�b�v�t���O�����g����View�𐶐����AView�̕ω������X�i�[�Ŏ擾�܂��͒ʒm����
    	 final View mapView = getFragmentManager().findFragmentById(R.id.map).getView();
         if (mapView.getViewTreeObserver().isAlive()) {
             mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
         
                 @Override
                 public void onGlobalLayout() {
                	 //LatLngBounds�͈ܓx�o�x�ɕ��s�ɋ�؂�ꂽ���
                     LatLngBounds bounds = new LatLngBounds.Builder()
                             .include(OGAKI)
                             .include(TOKAIPC)
                             .include(SOFTOPIA)
                             .build();
                   
                     mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                     //�����}�[�J�[���\���ł���T�C�Y�ɃY�[���C������
                     mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50)); // move and zoom(greatest)
                 }
             });
         }
    	
    }
    
    private void addMarkersToMap() {
        // �}�[�J�[�����_�����O�i�J���[�A�C�R���j
        mSoftopia = mMap.addMarker(new MarkerOptions()
                .position(SOFTOPIA)
                .title("�\�t�g�s�A")
                .snippet("Softpia")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // ���摜
        mTokaipc = mMap.addMarker(new MarkerOptions()
        		.position(TOKAIPC)
			    .title("���C�\�J��")
			    .snippet("PolytechCollegeTokai")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow)));
        // �W���̃A�C�R��
        mOhgaki = mMap.addMarker(new MarkerOptions()
        		.position(OGAKI)
        		.title("��_�w")
        		.snippet("Ogaki Station"));
      
    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_my_map_marker, menu);
//		return true;
//	}

}
