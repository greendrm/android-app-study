
package com.sgap.exam.camera2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MyMapViewActivity extends MapActivity {
    private MapView map = null;

    private MyLocationOverlay me = null;

    private double lat, lon;

    boolean drawMyPos = false;

    LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);

        // astrTime = getIntent().getStringArrayExtra("time");
        // astrLat = getIntent().getStringArrayExtra("lat");
        // astrLon = getIntent().getStringArrayExtra("lon");

        lat = Double.parseDouble(Commons.astrLat[2]);
        lon = Double.parseDouble(Commons.astrLon[2]);

        Button btn_map = (Button)findViewById(R.id.btn_map);
        btn_map.setEnabled(false);
        
        Button btn_camera = (Button)findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
               Intent intent = new Intent(getBaseContext(), CameraActivity.class);
               startActivity(intent);
            }
        });            
        map = (MapView) findViewById(R.id.map);

        map.getController().setCenter(getGeoPoint(lat, lon));
        map.getController().setZoom(16);

        map.setBuiltInZoomControls(true);

        map.setSatellite(false);

        Drawable marker;
        marker = getResources().getDrawable(R.drawable.map_view_pin01);
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
        
    }

    @Override
    public void onResume() {
        super.onResume();

        me.enableCompass();
        me.enableMyLocation();
    }

    @Override
    public void onPause() {
        super.onPause();

        me.disableMyLocation();
        me.disableCompass();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return (false);
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
            
            for (int i = 0; i < Commons.astrLat.length; i++) {
                Double dbLat, dbLon;
                dbLat = Double.parseDouble(Commons.astrLat[i]);
                dbLon = Double.parseDouble(Commons.astrLon[i]);
                OverlayItem overlay = new OverlayItem(getGeoPoint(dbLat, dbLon), Commons.ThingsName[i], getMyPosAddress(dbLat, dbLon));
                overlay.setMarker(mRes.getDrawable(Commons.Things[i]));
                items.add(overlay);
            }
            
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
                this.marker =  mRes.getDrawable( Commons.Things[i]);
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
            Intent intent = new Intent(getBaseContext(), MyDetailActivity.class);
            startActivity(intent);
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
                projection.toPixels( getGeoPoint(lat, lon), point);

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
}
