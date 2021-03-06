package com.example.thirdsdk;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.thirdsdk.util.MapBaiduUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ouyangshen on 2017/12/18.
 */
public class MapBaiduActivity extends AppCompatActivity implements OnClickListener,
        OnMapClickListener, OnGetPoiSearchResultListener,
        OnGetSuggestionResultListener {
    private static final String TAG = "MapBaiduActivity";
    private TextView tv_scope_desc, tv_loc_position;
    private int search_method;
    private String[] searchArray = {"???????????????", "???????????????"};
    private int SEARCH_CITY = 0;
    private int SEARCH_NEARBY = 1;
    private boolean isPaused = false;

    private void setMethodSpinner(Context context, int spinner_id, int seq) {
        Spinner sp_poi_method = findViewById(spinner_id);
        ArrayAdapter<String> county_adapter;
        county_adapter = new ArrayAdapter<String>(context,
                R.layout.item_select, searchArray);
        county_adapter.setDropDownViewResource(R.layout.item_select);
        // setPrompt?????????????????????????????????
        sp_poi_method.setPrompt("?????????POI????????????");
        sp_poi_method.setAdapter(county_adapter);
        sp_poi_method.setOnItemSelectedListener(new SpinnerSelectedListenerOrder());
        if (seq >= 0) {
            sp_poi_method.setSelection(seq, true);
        } else {
            sp_poi_method.setFocusable(false);
        }
    }

    class SpinnerSelectedListenerOrder implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            search_method = arg2;
            if (search_method == SEARCH_CITY) {
                tv_scope_desc.setText("?????????");
            } else if (search_method == SEARCH_NEARBY) {
                tv_scope_desc.setText("?????????");
            }
            et_city.setText("");
            et_searchkey.setText("");
        }

        public void onNothingSelected(AdapterView<?> arg0) {}
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ?????????????????????setContentView??????????????????
        // ?????????????????????SDK
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map_baidu);
        tv_scope_desc = findViewById(R.id.tv_scope_desc);
        tv_loc_position = findViewById(R.id.tv_loc_position);
        setMethodSpinner(this, R.id.sp_poi_method, SEARCH_CITY);
        initLocation();
        initMap();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
            searchButtonProcess(v);
        } else if (v.getId() == R.id.btn_next_data) {
            goToNextPage(v);
        } else if (v.getId() == R.id.btn_clear_data) {
            et_city.setText("");
            et_searchkey.setText("");
            // ??????????????????
            mMapView.getMap().clear();
            posArray.clear();
            isPolygon = false;
        }
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        isPaused = true;
        super.onPause();
    }

    @Override
    public void onResume() {
        if (isPaused) {
            mMapView.onResume();
            isPaused = false;
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // ?????????????????????
        if (null != mLocClient) {
            mLocClient.stop();
            mLocClient = null;
        }
        // ??????????????????
        if (mMapLayer != null) {
            mMapLayer.setMyLocationEnabled(false);
        }
        mMapView.onDestroy();
        mMapView = null;
        mPoiSearch.destroy();
        mSuggestionSearch.destroy();
        super.onDestroy();
    }

    private double mLatitude;
    private double mLongitude;

    // ???????????????POI?????????????????????
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private AutoCompleteTextView et_searchkey = null;
    private EditText et_city = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int load_Index = 0;

    private void initMap() {
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        et_city = findViewById(R.id.et_city);
        et_searchkey = findViewById(R.id.et_searchkey);
        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btn_next_data).setOnClickListener(this);
        findViewById(R.id.btn_clear_data).setOnClickListener(this);
        sugAdapter = new ArrayAdapter<String>(this, R.layout.item_select);
        et_searchkey.setAdapter(sugAdapter);

        // ??????????????????????????????????????????????????????
        et_searchkey.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {}

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                if (cs.length() <= 0) {
                    return;
                }
                String city = et_city.getText().toString();
                // ??????????????????????????????????????????????????????onGetSuggestionResult?????????
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).city(city));
            }
        });
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res != null && res.getAllSuggestions() != null) {
            sugAdapter.clear();
            for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
                if (info.key != null) {
                    sugAdapter.add(info.key);
                }
            }
            sugAdapter.notifyDataSetChanged();
        }
    }

    // ??????????????????????????????
    public void searchButtonProcess(View v) {
        Log.d(TAG, "editCity=" + et_city.getText().toString()
                + ", editSearchKey=" + et_searchkey.getText().toString()
                + ", load_Index=" + load_Index);
        String keyword = et_searchkey.getText().toString();
        if (search_method == SEARCH_CITY) {
            String city = et_city.getText().toString();
            mPoiSearch.searchInCity((new PoiCitySearchOption()).city(city)
                    .keyword(keyword).pageNum(load_Index));
        } else if (search_method == SEARCH_NEARBY) {
            LatLng position = new LatLng(mLatitude, mLongitude);
            int radius = Integer.parseInt(et_city.getText().toString());
            mPoiSearch.searchNearby((new PoiNearbySearchOption())
                    .location(position).keyword(keyword).radius(radius)
                    .pageNum(load_Index));
        }
    }

    public void goToNextPage(View v) {
        load_Index++;
        searchButtonProcess(null);
    }

    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(this, "???????????????", Toast.LENGTH_LONG).show();
        } else if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mMapLayer.clear();
            PoiOverlay overlay = new MyPoiOverlay(mMapLayer);
            mMapLayer.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
        } else if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            String strInfo = "???";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city + ",";
            }
            strInfo += "????????????";
            Toast.makeText(this, strInfo, Toast.LENGTH_LONG).show();
        }
    }

    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
        } else {
            Log.d(TAG,
                    "name=" + result.getName() + ",address="
                            + result.getAddress() + ",detail_url="
                            + result.getDetailUrl() + ",shop_hours="
                            + result.getShopHours() + ",telephone="
                            + result.getTelephone() + ",price="
                            + result.getPrice() + ",type=" + result.getType()
                            + ",tag=" + result.getTag());
            Toast.makeText(this, result.getName() + ": " + result.getAddress(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult result) {}

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
            return true;
        }
    }

    // ????????????????????????????????????
    private MapView mMapView; // ??????????????????????????????
    private BaiduMap mMapLayer; // ??????????????????????????????
    private LocationClient mLocClient; // ?????????????????????????????????
    private boolean isFirstLoc = true; // ??????????????????

    // ?????????????????????
    private void initLocation() {
        // ??????????????????????????????bmapView???????????????
        mMapView = findViewById(R.id.bmapView);
        // ??????????????????????????????????????????????????????
        mMapView.setVisibility(View.INVISIBLE);
        mMapLayer = mMapView.getMap(); // ????????????????????????????????????
        mMapLayer.setOnMapClickListener(this); // ??????????????????????????????????????????
        mMapLayer.setMyLocationEnabled(true); // ??????????????????
        mLocClient = new LocationClient(this); // ???????????????????????????
        mLocClient.registerLocationListener(new MyLocationListenner()); // ?????????????????????
        LocationClientOption option = new LocationClientOption(); // ????????????????????????
        option.setOpenGps(true); // ??????GPS
        option.setCoorType("bd09ll"); // ??????????????????
        option.setScanSpan(1000); // ???????????????????????????
        option.setIsNeedAddress(true); // ??????true?????????????????????????????????
        mLocClient.setLocOption(option); // ????????????????????????????????????
        mLocClient.start(); // ?????????????????????????????????
        // ???????????????????????????
        // mLocClient.getLastKnownLocation();
    }

    // ???????????????????????????
    public class MyLocationListenner implements BDLocationListener {

        // ?????????????????????????????????
        public void onReceiveLocation(BDLocation location) {
            // ??????????????????????????????????????????????????????????????????
            if (location == null || mMapView == null) {
                Log.d(TAG, "location is null or mMapView is null");
                return;
            }
            mLatitude = location.getLatitude(); // ????????????????????????
            mLongitude = location.getLongitude(); // ????????????????????????
            String position = String.format("???????????????%s|%s|%s|%s|%s|%s|%s",
                    location.getProvince(), location.getCity(),
                    location.getDistrict(), location.getStreet(),
                    location.getStreetNumber(), location.getAddrStr(),
                    location.getTime());
            tv_loc_position.setText(position);
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // ?????????????????????????????????????????????????????????0-360
                    .direction(100).latitude(mLatitude).longitude(mLongitude)
                    .build();
            mMapLayer.setMyLocationData(locData); // ?????????????????????????????????
            if (isFirstLoc) { // ????????????
                isFirstLoc = false;
                LatLng ll = new LatLng(mLatitude, mLongitude); // ???????????????????????????
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll, 14);
                mMapLayer.animateMapStatus(update); // ????????????????????????????????????????????????
                mMapView.setVisibility(View.VISIBLE); // ???????????????????????????????????????
            }
        }
    }

    // ???????????????????????????????????????
    private static int lineColor = 0x55FF0000;
    private static int arcColor = 0xbb00FFFF;
    private static int textColor = 0x990000FF;
    private static int polygonColor = 0x77FFFF00;
    private static int radius = 100;
    private ArrayList<LatLng> posArray = new ArrayList<LatLng>();
    boolean isPolygon = false;

    private void addDot(LatLng pos) {
        if (isPolygon && posArray.size() > 1 && MapBaiduUtil.isInsidePolygon(pos, posArray)) {
            Log.d(TAG, "isInsidePolygon");
            LatLng centerPos = MapBaiduUtil.getCenterPos(posArray);
            OverlayOptions ooText = new TextOptions().bgColor(0x00ffffff)
                    .fontSize(26).fontColor(textColor).text("??????")// .rotate(-30)
                    .position(centerPos);
            mMapLayer.addOverlay(ooText);
            return;
        }
        if (isPolygon) {
            Log.d(TAG, "isPolygon == true");
            posArray.clear();
            isPolygon = false;
        }
        boolean is_first = false;
        LatLng thisPos = pos;
        if (posArray.size() > 0) {
            LatLng firstPos = posArray.get(0);
            int distance = (int) Math.round(MapBaiduUtil.getShortDistance(
                    thisPos.longitude, thisPos.latitude, firstPos.longitude,
                    firstPos.latitude));
            // ?????????????????????????????????
            if (posArray.size() == 1 && distance <= 0) {
                return;
            } else if (posArray.size() > 1) {
                LatLng lastPos = posArray.get(posArray.size() - 1);
                int lastDistance = (int) Math.round(MapBaiduUtil.getShortDistance(
                        thisPos.longitude, thisPos.latitude, lastPos.longitude,
                        lastPos.latitude));
                // ????????????????????????????????????????????????
                if (lastDistance <= 0) {
                    return;
                }
            }
            if (distance < radius * 2) {
                thisPos = firstPos;
                is_first = true;
            }
            Log.d(TAG, "distance=" + distance + ", radius=" + radius + ", is_first=" + is_first);

            // ?????????
            LatLng lastPos = posArray.get(posArray.size() - 1);
            List<LatLng> points = new ArrayList<LatLng>();
            points.add(lastPos);
            points.add(thisPos);
            OverlayOptions ooPolyline = new PolylineOptions().width(2)
                    .color(lineColor).points(points);
            mMapLayer.addOverlay(ooPolyline);

            // ??????????????????????????????
            distance = (int) Math.round(MapBaiduUtil.getShortDistance(
                    thisPos.longitude, thisPos.latitude, lastPos.longitude,
                    lastPos.latitude));
            String disText;
            if (distance > 1000) {
                disText = Math.round(distance * 10 / 1000) / 10d + "??????";
            } else {
                disText = distance + "???";
            }
            LatLng llText = new LatLng(
                    (thisPos.latitude + lastPos.latitude) / 2,
                    (thisPos.longitude + lastPos.longitude) / 2);
            OverlayOptions ooText = new TextOptions().bgColor(0x00ffffff)
                    .fontSize(24).fontColor(textColor).text(disText)// .rotate(-30)
                    .position(llText);
            mMapLayer.addOverlay(ooText);
        }
        if (!is_first) {
//			// ?????????
//			OverlayOptions ooCircle = new CircleOptions().fillColor(lineColor)
//					.center(thisPos).stroke(new Stroke(2, 0xAAFF0000))
//					.radius(radius);
//			mMapLayer.addOverlay(ooCircle);
            // ???????????????
            BitmapDescriptor bitmapDesc = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_geo);
            OverlayOptions ooMarker = new MarkerOptions().draggable(false)
                    .visible(true).icon(bitmapDesc).position(thisPos);
            mMapLayer.addOverlay(ooMarker);
            mMapLayer.setOnMarkerClickListener(new OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    LatLng markPos = marker.getPosition();
                    addDot(markPos);
                    return true;
                }
            });
        } else {
            Log.d(TAG, "posArray.size()=" + posArray.size());
            // ????????????????????????????????????????????????????????????
            if (posArray.size() < 3) {
                posArray.clear();
                isPolygon = false;
                return;
            }
            // ????????????
            OverlayOptions ooPolygon = new PolygonOptions().points(posArray)
                    .stroke(new Stroke(1, 0xFF00FF00)).fillColor(polygonColor);
            mMapLayer.addOverlay(ooPolygon);
            isPolygon = true;

            // ??????????????????????????????
            LatLng centerPos = MapBaiduUtil.getCenterPos(posArray);
            double area = Math.round(MapBaiduUtil.getArea(posArray));
            String areaText;
            if (area > 1000000) {
                areaText = Math.round(area * 100 / 1000000) / 100d + "????????????";
            } else {
                areaText = (int) area + "?????????";
            }
            OverlayOptions ooText = new TextOptions().bgColor(0x00ffffff)
                    .fontSize(26).fontColor(textColor).text(areaText)// .rotate(-30)
                    .position(centerPos);
            mMapLayer.addOverlay(ooText);
        }
        posArray.add(thisPos);
        if (posArray.size() >= 3) {
            // ?????????
            OverlayOptions ooArc = new ArcOptions()
                    .color(arcColor).width(2)
                    .points(posArray.get(posArray.size() - 1),
                            posArray.get(posArray.size() - 2),
                            posArray.get(posArray.size() - 3));
            mMapLayer.addOverlay(ooArc);
        }
    }

    @Override
    public void onMapClick(LatLng arg0) {
        addDot(arg0);
    }

    @Override
    public boolean onMapPoiClick(MapPoi arg0) {
        addDot(arg0.getPosition());
        return false;
    }

}
