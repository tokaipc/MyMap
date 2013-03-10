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

//�ʒu���擾��LocationListener��LocationSource���C���v�������g
public class MyMapMarker extends Activity 
implements LocationListener, LocationSource{ 
	// �}�[�J�[�p�̈ܓx�o�x
    private static final LatLng TOKAIPC = new LatLng(35.4927, 136.6438);
    private static final LatLng SOFTOPIA = new LatLng(35.3676, 136.6396);
    private static final LatLng OGAKI = new LatLng(35.3667, 136.6170);

    private GoogleMap mMap;
    //���P�[�V�������X�i�[�ƃ��P�[�V�����}�l�[�W���̐錾
    private OnLocationChangedListener mListener;
    private LocationManager locationManager;
    
    // �}�[�J�[�錾
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
		// LocationManager����
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		if(locationManager != null){
			boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if(gpsIsEnabled){	//LocationListener�o�^
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, 5000L, 10F, this);//�v���o�C�_�A�Ԋu�i�~���b�j�A���x�i���j�A���X�i�[
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
			locationManager.removeUpdates(this);	//LocationListener���
		}
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		setUpMapIfNeeded();
		if(locationManager != null){
			mMap.setMyLocationEnabled(true);	//���݈ʒu�𔽉f
		}
	}
	
    private void setUpMapIfNeeded() {
    	if(mMap == null){
            mMap =  ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
            		.getMap();
            
            if (mMap != null) {
                setUpMap();
            }
            mMap.setLocationSource(this);	//LocationSource�L���i�u�����j
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
                            // .include(TOKAIPC)
                             .include(SOFTOPIA)
                             .build();
                   
                     mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                     //�����}�[�J�[���\���ł���T�C�Y�ɃY�[���C������
                     mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50)); // move and zoom(greatest)
                     //�}�b�v�̒��S���W�̕\��
//                     mTopText.setText("" +
//             				mMap.getCameraPosition().target.latitude + ", " +
//             				mMap.getCameraPosition().target.longitude);
                 }
             });
         }
    	mMap.setMyLocationEnabled(true);
    }
    
    private void addMarkersToMap() {
        // �}�[�J�[�����_�����O�i�J���[�A�C�R���j
//        mSoftopia = mMap.addMarker(new MarkerOptions()
//                .position(SOFTOPIA)
//                .title("�\�t�g�s�A")
//                .snippet("Softpia")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

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
        
        mCurrent = mMap.addMarker(new MarkerOptions()
				.position(SOFTOPIA)
				.title("���ݒn")
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
	//�ʒu��񂪍X�V���ꂽ�Ƃ��ɌĂ΂��
	@Override
	public void onLocationChanged(Location location) {
		if(mListener != null){
			mListener.onLocationChanged(location);//�V�����ʒu���̎擾
			mMap.animateCamera(CameraUpdateFactory.newLatLng(
					new LatLng(location.getLatitude(), location.getLongitude())));
			//�}�[�J�[�̈ʒu�����X�V
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
