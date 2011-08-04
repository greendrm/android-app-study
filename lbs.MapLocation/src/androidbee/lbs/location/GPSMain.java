package androidbee.lbs.location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

/**
 * 
 */
public class GPSMain extends MapActivity {
	private final String TAG = "androidbee";
	private Button gpsButton = null;
	private Button addrButton = null;
	// private TextView gpsText = null;
	// private boolean isLoadGps = false;
	private Context context;
	
    private MapView map = null;
	private LocationManager locationManager;
	private String provider;
	private double m_lat, m_lon;
	
	private GpsLocationListener listener = null;
	private MyLocationOverlay me;
	private boolean isLoadGps;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = this.getBaseContext();
		setContentView(R.layout.main);

		map = (MapView) findViewById(R.id.map);

        map.getController().setCenter(getGeoPoint( m_lat, m_lon));
        map.getController().setZoom(16);

        map.setBuiltInZoomControls(true);

        map.setSatellite(false);
        
        Drawable marker;
        marker = getResources().getDrawable(R.drawable.icon);
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());

        map.getOverlays().add(new CircleOverlay());
        
        map.getOverlays().add(new SitesOverlay(marker));

        // 현재 위치를 맵뷰에서 보여주게 고안된 클래
        me = new MyLocationOverlay(this, map);
        map.getOverlays().add(me);
        // 나침반을 보여준다.
        me.enableCompass();
        // 내 위치의 마커(기본)을 보여준다.
        me.enableMyLocation();
        
		loadGps();

		// new Thread( timerThread ).start();
		gpsButton = (Button) findViewById(R.id.btn_gps_search);
		addrButton = (Button) findViewById(R.id.btn_addr_search);

		gpsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.w(TAG, "onStatusChanged");
//				Intent in = new Intent(context, RestaurantList.class);
				Location loc = null;

				loc = getLocation();

				if (loc == null) {
					Log.w(TAG, "location is null");

					AlertDialog.Builder adb = new AlertDialog.Builder(
							GPSMain.this);
					adb.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							});
					adb.setTitle(R.string.alert_title);
					adb.setMessage(R.string.alert_message);
					adb.show();
				} else {
//					in.putExtra("MY CURRENT LOCATION", new Location(loc));
//					startActivity(in);
				}
			}

			private Location getLocation() {

				Location location = locationManager
						.getLastKnownLocation(provider);
				if (location == null) {
					Log.w(TAG, "get Location From GPS Fail !!!!!");
					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				}
				return location;
			}
		});

		addrButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 주소 입력 화면으로 옮겨주자..
//				Intent in = new Intent(context, InputAddress.class);
//				startActivity(in);
			}
		});
	}

	

    private String getMyPosAddress(double dbLat, double dbLon) {
        String addressString = "No address found";
        Geocoder gc = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = gc.getFromLocation(dbLat, dbLon, 1);

            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                addressString = address.getAddressLine(0);
                addressString = addressString.substring(addressString.indexOf(" ") + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressString;
    }


    private GeoPoint getGeoPoint(Location loc) {
        return getGeoPoint(loc.getLatitude(), loc.getLongitude());
    }

    private GeoPoint getGeoPoint(double lat, double lon) {
        return (new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6)));
    }

    
    private class SitesOverlay extends ItemizedOverlay<OverlayItem> {
        private List<OverlayItem> items = new ArrayList<OverlayItem>();
        Resources mRes;
        private Drawable marker = null;

        public SitesOverlay(Drawable marker) {
            super(marker);

            this.marker = marker;
            mRes = getResources();
            
//            for (int i = 0; i < Commons.astrLat.length; i++) {
//                Double dbLat, dbLon;
//                dbLat = Double.parseDouble(Commons.astrLat[i]);
//                dbLon = Double.parseDouble(Commons.astrLon[i]);
//                OverlayItem overlay = new OverlayItem(getGeoPoint(dbLat, dbLon), Commons.ThingsName[i], getMyPosAddress(dbLat, dbLon));
//                overlay.setMarker(mRes.getDrawable(Commons.Things[i]));
//                items.add(overlay);
//            }
            
            populate();
        }

        @Override
        protected OverlayItem createItem(int i) {
            return (items.get(i));
        }

        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
            // //////////////////////////////////////////////////////////////////////////
            // Get the current location
            for (int i = 0; i < items.size(); i++) {
//                this.marker =  mRes.getDrawable( Commons.Things[i]);
                if (i == 0)
                    drawItem(canvas, mapView, shadow, new GeoPoint(0, 0), items.get(i).getPoint(),
                            items.get(i).getTitle());
                else
                    drawItem(canvas, mapView, shadow, items.get(i - 1).getPoint(), items.get(i)
                            .getPoint(),items.get(i).getTitle());
            }
        }

        @Override
        protected boolean onTap(int i) {
//            Toast.makeText(MyMapViewActivity.this, items.get(i).getSnippet(), Toast.LENGTH_SHORT)
//                    .show();
//            Intent intent = new Intent(getBaseContext(), MyDetailActivity.class);
//            startActivity(intent);
            return (true);
        }

        @Override
        public int size() {
            return (items.size());
        }

        public void drawItem(Canvas canvas, MapView mapView, boolean shadow,
                GeoPoint geoStartPoint, GeoPoint geoPoint, String markerName) {
            int mRadius = 5;

            if (shadow == false) {
                // Convert the location to screen pixels
                Point point = new Point();
                Projection projection = mapView.getProjection();
                projection.toPixels(geoPoint, point);

                // Setup the paint
                Paint paint = new Paint();
                paint.setARGB(255, 255, 255, 255);
                paint.setAntiAlias(true);
                paint.setFakeBoldText(true);

                Paint backPaint = new Paint();
                backPaint.setARGB(180, 50, 50, 50);
                backPaint.setAntiAlias(true);

                int letterWidth;
                int ratio;

                if (markerName.contains(" "))
                    ratio = 13;
                else
                    ratio = 14;

                letterWidth = markerName.length() * ratio;

                RectF backRect = new RectF(point.x + 2 + mRadius, point.y - 3 * mRadius, point.x
                        + letterWidth, point.y + mRadius);

                // Draw the marker
                canvas.drawRoundRect(backRect, 5, 5, backPaint);
                canvas.drawText(markerName, point.x + 2 * mRadius, point.y, paint);

                // gtko: 새로운 마커를 그린다...
                int xx = 20;
                int yy = 30;
                this.marker.setBounds(point.x+xx, point.y-yy, point.x+xx+marker.getIntrinsicWidth(), point.y-yy+ marker.getIntrinsicHeight());
                this.marker.draw(canvas);
                
                /*
                 * Point startPosition = new Point();
                 * projection.toPixels(geoStartPoint, startPosition);
                 * if(geoStartPoint.getLatitudeE6() != 0) { // draw 메서드에서
                 * drawLine()을 이용해 선을 그을것입니다. Paint paintLine = new Paint(); //
                 * 선을 긋기 위한 페인트 생성 paintLine.setARGB(180,0, 0, 255);
                 * paintLine.setStrokeWidth(3); // 굵기
                 * paintLine.setAntiAlias(true); // 부드럽게 처리. //선을 긋습니다.
                 * canvas.drawLine(point.x, point.y, startPosition.x,
                 * startPosition.y, paintLine); }
                 */
            }

            super.draw(canvas, mapView, shadow);

            boundCenterBottom(marker);
        }
    }
    
    public class CircleOverlay extends Overlay {
        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
            if (shadow == false) {
                // Convert the location to screen pixels
                Point point = new Point();
                Projection projection = mapView.getProjection();
                projection.toPixels( getGeoPoint( m_lat, m_lon), point);

                // 타원을 그린다.
                Paint paintA = new Paint();
                paintA.setARGB( 100, 255, 0, 0);
                paintA.setAntiAlias(true);
                canvas.drawCircle(point.x, point.y, 100, paintA);
                
                // 원을 그린다
//                Paint paint = new Paint();
//                paint.setARGB( 250, 255, 0, 0);
//                paint.setAntiAlias(true);
//                int rad = 100;
//                RectF oval = new RectF(point.x - rad, point.y - rad, point.x + rad, point.y + rad);
//                Paint paintC = new Paint();
//                paintC.setARGB( 250, 23, 45, 0);
//                paintC.setAntiAlias(true);
////                canvas.drawOval(oval, paint);
//                canvas.drawArc(oval, 0, 360, false, paintC);
            }
        }

    }
	// Handler msgHandle = new Handler( ){
	// public void handleMessage(Message message) {
	// switch( message.what ){
	// case R.id.message_timer_end:
	// alertUsingGps();
	// break;
	// }
	// }
	// };
	//
	// /**
	// *
	// */
	// Runnable timerThread = new Runnable(){
	// public void run(){
	// try{
	// Thread.sleep( SLEEP_TIME );
	// msgHandle.sendEmptyMessage( R.id.message_timer_end );
	// }catch( Exception e ){
	// Log.w( TAG , e );
	// }
	// }
	// };
	//
	// public void alertUsingGps() {
	//
	// AlertDialog.Builder adb = new AlertDialog.Builder(this);
	// adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	//
	// // @Override
	// public void onClick(DialogInterface dialog, int which) {
	// loadGps();
	// }
	// });
	// adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
	// // @Override
	// public void onClick(DialogInterface dialog, int which) {
	// }
	// });
	// adb.setTitle( R.string.alert_title );
	// adb.setMessage(R.string.alert_message);
	// adb.show();
	// }

	public void loadGps() {
		Log.w(TAG, "loadGps");
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if( isLoadGps )
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 
					2000, 10, new GpsLocationListener());
		 isLoadGps = true;

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 정확도
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 전원 소비량
		criteria.setAltitudeRequired(false); // 고도, 높이 값을 얻어 올지를 결정
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false); // 속도
		criteria.setCostAllowed(true); // 위치 정보를 얻어 오는데 들어가는 금전적 비용
		provider = locationManager.getBestProvider(criteria, true);
		listener = new GpsLocationListener();
		locationManager.requestLocationUpdates(provider, 1000, 5, listener);
	}

	private class GpsLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			Log.w(TAG, "onLocationChanged");
		}

		public void onProviderDisabled(String provider) {
			Log.w(TAG, "onProviderDisabled");
		}

		public void onProviderEnabled(String provider) {
			Log.w(TAG, "onProviderEnabled");
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.w(TAG, "onStatusChanged");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (listener == null) {
			listener = new GpsLocationListener();
			locationManager.requestLocationUpdates(provider, 1000, 5, listener);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(listener);
		listener = null;
	}



	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
