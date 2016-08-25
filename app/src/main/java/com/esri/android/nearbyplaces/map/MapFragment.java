/* Copyright 2016 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For additional information, contact:
 * Environmental Systems Research Institute, Inc.
 * Attn: Contracts Dept
 * 380 New York Street
 * Redlands, California, USA 92373
 *
 * email: contracts@esri.com
 *
 */
package com.esri.android.nearbyplaces.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.esri.android.nearbyplaces.PlaceListener;
import com.esri.android.nearbyplaces.R;
import com.esri.android.nearbyplaces.data.CategoryHelper;
import com.esri.android.nearbyplaces.data.Place;
import com.esri.android.nearbyplaces.filter.FilterDialogFragment;
import com.esri.android.nearbyplaces.filter.FilterPresenter;
import com.esri.android.nearbyplaces.places.PlacesActivity;
import com.esri.android.nearbyplaces.route.RouteDirectionsFragment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.geometry.*;
import com.esri.arcgisruntime.layers.ArcGISVectorTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.*;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.tasks.route.DirectionManeuver;
import com.esri.arcgisruntime.tasks.route.Route;
import com.esri.arcgisruntime.tasks.route.RouteResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by sand8529 on 6/24/16.
 */
public class MapFragment extends Fragment implements  MapContract.View, PlaceListener {

  private MapContract.Presenter mPresenter;

  private CoordinatorLayout mMapLayout;

  private MapView mMapView;

  private LocationDisplay mLocationDisplay;

  private GraphicsOverlay mGraphicOverlay, mRouteOverlay;


  private boolean initialLocationLoaded =false;

  private boolean mCenteringOnPlace = false;

  private Graphic mCenteredGraphic = null;

  private Place mCenteredPlace = null;

  private NavigationCompletedListener mNavigationCompletedListener;

  private final static String TAG = MapFragment.class.getSimpleName();

  private long mStartTime;

  private String centeredPlaceName;


  private BottomSheetBehavior bottomSheetBehavior;

  private FrameLayout mBottomSheet;

  private boolean mShowSnackbar = false;

  private boolean mRoutingState = false;

  private List<DirectionManeuver> mRouteDirections;

  public MapFragment(){}

  public static MapFragment newInstance(){
    return new MapFragment();
  }

  @Override
  public void onCreate(@NonNull Bundle savedInstance){

    super.onCreate(savedInstance);
    // retain this fragment
    setRetainInstance(true);

    // Set up the toolbar
    setUpToolbar();

    // Set toolbar to transparent on start of activity
    setToolbarTransparent();

    setHasOptionsMenu(true);/// allows invalidateOptionsMenu to work
    mMapLayout = (CoordinatorLayout) getActivity().findViewById(R.id.map_coordinator_layout);

    //Set up behavior for the bottom sheet
    setUpBottomSheet();
  }

  @Override
  @Nullable
  public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
      Bundle savedInstance){
    mStartTime = Calendar.getInstance().getTimeInMillis();
    Log.i("MapFragment", "Start_ON_CREATE_VIEW");
    View root = layoutInflater.inflate(R.layout.map_fragment, container,false);
    setUpMapView(root);
    Log.i("MapFragment", "End_ON_CREATE_VIEW");

    // If any extra data was sent, store it.
    if (getActivity().getIntent().getSerializableExtra("PLACE_DETAIL") != null){
      centeredPlaceName = getActivity().getIntent().getStringExtra("PLACE_DETAIL");
    }

    return root;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    // Inflate the menu items for use in the action bar
    inflater.inflate(R.menu.map_menu, menu);
  }

  /**
   * When activity first starts, the action bar
   * shows the back arrow for navigating back
   * to parent activity.  Menu item click listener
   * responds in one of the following ways:
   * 1) navigating back to list of places,
   * 2) showing the filter UI
   * 3) showing the route to selected place in map
   */
  private void setUpToolbar(){
    Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.map_toolbar);
    ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    toolbar.setTitle("");
    final ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);

    ab.setHomeAsUpIndicator(0); // Use default home icon
    // Menu items change depending on presence/absence of bottom sheet
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
      @Override public boolean onMenuItemClick(MenuItem item) {
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.list_view))){
          // Show the list of places
          showList();
        }
        if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.filter))){
          FilterDialogFragment dialogFragment = new FilterDialogFragment();
          FilterPresenter filterPresenter = new FilterPresenter();
          dialogFragment.setPresenter(filterPresenter);
          dialogFragment.show(getActivity().getFragmentManager(),"dialog_fragment");

        }
          if (item.getTitle().toString().equalsIgnoreCase("Route")){
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final View routeHeaderView = inflater.inflate(R.layout.route_header,null);
            TextView tv = (TextView) routeHeaderView.findViewById(R.id.route_bar_title);
            tv.setText(mCenteredPlace.getName());
            tv.setTextColor(Color.WHITE);
            ImageView btnClose = (ImageView) routeHeaderView.findViewById(R.id.btnClose);
            ImageView btnDirections = (ImageView) routeHeaderView.findViewById(R.id.btnDirections);
            final ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
            ab.hide();
            mMapView.addView(routeHeaderView, layout);
            btnClose.setOnClickListener(new View.OnClickListener() {
              @Override public void onClick(View v) {
                mMapView.removeView(routeHeaderView);
                ab.show();
              }
            });
            btnDirections.setOnClickListener(new View.OnClickListener() {
              @Override public void onClick(View v) {
                // show directions fragment
                RouteDirectionsFragment routeDirectionsFragment = new RouteDirectionsFragment();
                routeDirectionsFragment.show(getActivity().getFragmentManager(),"route_directions_fragment");
                routeDirectionsFragment.setRoutingDirections(mRouteDirections);
              }
            });
          mPresenter.getRoute();

        }
        return false;
      }
    });
  }
  private void showList(){
    Intent intent = new Intent(getActivity(), PlacesActivity.class);
    startActivity(intent);
  }
  private void setToolbarTransparent(){
    // Sets the outline of the toolbar shadow to the background color
    // of the layout (transparent, in this case)
    AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.map_appbar);
    appBarLayout.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
  }

  /**
   * Add the map to the view and set up location display
   * @param root View
   */
  private void setUpMapView(View root){
    Log.i("MapFragment", "Start_SET_UP_MAP_VIEW");

    mMapView = (MapView) root.findViewById(R.id.map);
    Log.i("MapFragment", "Find_map_view");

//    Basemap basemap = new Basemap(new ArcGISVectorTiledLayer(
//        getResources().getString(R.string.navigation_url)));
    Basemap basemap = Basemap.createStreets();
    Log.i("MapFragment", "Basemap_created");

    ArcGISMap map = new ArcGISMap(basemap);
    mMapView.setMap(map);
    Log.i("MapFragment", "Map_attached_to_map_view");

    // Add graphics overlay for map markers
    mGraphicOverlay  = new GraphicsOverlay();
    mMapView.getGraphicsOverlays().add(mGraphicOverlay);
    Log.i("MapFragment", "Graphics_overlay_added");

    mLocationDisplay = mMapView.getLocationDisplay();
    Log.i("MapFragment", "Get_location_display");

    mLocationDisplay.startAsync();
    Log.i("MapFragment", "Start_async_loc_display");


    mMapView.addDrawStatusChangedListener(new DrawStatusChangedListener() {
      @Override public void drawStatusChanged(DrawStatusChangedEvent drawStatusChangedEvent) {
        if (drawStatusChangedEvent.getDrawStatus() == DrawStatus.COMPLETED){
          long elapsedTime = (Calendar.getInstance().getTimeInMillis() - mStartTime);
          Log.i("MapFragment", "***Time taken*** = " + Long.toString(elapsedTime));
          mPresenter.start();
          mMapView.removeDrawStatusChangedListener(this);
        }
      }
    });

    // Setup OnTouchListener to detect and act on long-press
    mMapView.setOnTouchListener(new MapTouchListener(getActivity().getApplicationContext(), mMapView));
    Log.i("MapFragment", "End_SET_UP_MAP_VIEW");
  }

  private void setUpBottomSheet(){
    bottomSheetBehavior = BottomSheetBehavior.from(getActivity().findViewById(R.id.bottom_card_view));

    bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
      @Override
      public void onStateChanged(View bottomSheet, int newState) {
        getActivity().invalidateOptionsMenu();
        if (newState == BottomSheetBehavior.STATE_COLLAPSED && mShowSnackbar) {
          showSearchSnackbar();
          mShowSnackbar = false;
        }
      }

      @Override
      public void onSlide(View bottomSheet, float slideOffset) {
      }
    });

    mBottomSheet = (FrameLayout) getActivity().findViewById(R.id.bottom_card_view);
  }


  /**
   * Set the menu options based on
   * the bottom sheet state
   *
   */
  @Override
  public void onPrepareOptionsMenu(Menu menu){
    MenuItem listItem = menu.findItem(R.id.list_action);
    MenuItem routeItem = menu.findItem(R.id.route_action);
    MenuItem directionItem = menu.findItem(R.id.walking_directions);
    MenuItem filterItem = menu.findItem(R.id.filter_in_map);

    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED  && !mRoutingState){
      Log.i("MapFragment", "Routing state false & bottom sheet collapsed, showing list & filter icons...");
      listItem.setVisible(true);
      filterItem.setVisible(true);
      routeItem.setVisible(false);
    }else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED && !mRoutingState){
      Log.i("MapFragment", "Routing state false & bottom sheet expanded, showing route & filter icons...");
      listItem.setVisible(false);
      filterItem.setVisible(true);
      routeItem.setVisible(true);
    }
    if (mRoutingState){
      Log.i("MapFragment", "Routing state true, setting menu items to invisible");
      listItem.setVisible(false);
      filterItem.setVisible(false);
      routeItem.setVisible(false);
      directionItem.setVisible(true);
    }else{
      directionItem.setVisible(false);
    }
  }



  private void showSearchSnackbar(){
    // Show snackbar prompting user about
    // scanning for new locations
    Snackbar snackbar = Snackbar
        .make(mMapLayout, "Search for places?", Snackbar.LENGTH_LONG)
        .setAction("SEARCH", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (mRouteOverlay != null){
              mRouteOverlay.getGraphics().clear();
            }

            mPresenter.findPlacesNearby();
          }
        });

    snackbar.show();
  }
  /**
   * Attach the viewpoint change listener
   * so that POIs get updated as map's
   * visible area is changed.
   */
  private void setNavigationCompletedListener(){
    mNavigationCompletedListener = new NavigationCompletedListener() {
      @Override public void navigationCompleted(NavigationCompletedEvent navigationCompletedEvent) {
          onMapScroll();
      }
    };
    mMapView.addNavigationCompletedListener(mNavigationCompletedListener);
    Log.i(TAG, "Navigation complete handler ON");
  }

  private void removeNavigationCompletedListener(){
    if (mNavigationCompletedListener != null){
      mMapView.removeNavigationCompletedListener(mNavigationCompletedListener);
      mNavigationCompletedListener = null;
    }
    Log.i(TAG, "Navigation complete handler OFF");
  }

  @Override
  public void onResume(){
    super.onResume();
    mMapView.resume();
   if (!mLocationDisplay.isStarted()){
      mLocationDisplay.startAsync();
    }
  }

  @Override
  public void onPause(){
    super.onPause();
    mMapView.pause();
   if (mLocationDisplay.isStarted()){
      mLocationDisplay.stop();
    }
  }

  /**
   * If any places are found,
   * add them to the map as graphics.
   * @param places List of Place items
   */
  @Override public void showNearbyPlaces(List<Place> places) {
    if (!initialLocationLoaded){
      setNavigationCompletedListener();
    }
    initialLocationLoaded = true;
    if (places.isEmpty()){
      Toast.makeText(getContext(),getString(R.string.no_places_found),Toast.LENGTH_SHORT).show();
      return;
    }
    // Clear out any existing graphics
    mGraphicOverlay.getGraphics().clear();

    // Create a graphic for every place
    // and create a list of points
    List<Point> points = new ArrayList<>();
    for (Place place : places){
      BitmapDrawable pin = (BitmapDrawable) ContextCompat.getDrawable(getActivity(),getDrawableForPlace(place)) ;
      final PictureMarkerSymbol pinSymbol = new PictureMarkerSymbol(pin);
      Point graphicPoint = place.getLocation();
      Graphic graphic = new Graphic(graphicPoint, pinSymbol);
      points.add(graphicPoint);
      mGraphicOverlay.getGraphics().add(graphic);
    }
    Envelope env = getResultEnveope(points);
    mMapView.setViewpoint(new Viewpoint(env));

    // If a centered place name is not null,
    // show detail view
    if (centeredPlaceName != null){
      for (Place p: places){
        if (p.getName().equalsIgnoreCase(centeredPlaceName)){
          showDetail(p);
          centeredPlaceName = null;
          break;
        }
      }
    }
  }
  /**
   * @param place
   */
  @Override public void showDetail(Place place) {
    TextView txtName = (TextView) mBottomSheet.findViewById(R.id.placeName);
    txtName.setText(place.getName());
    String address = place.getAddress();
    String[] splitStrs = TextUtils.split(address, ",");
    if (splitStrs.length>0)                                   {
      address = splitStrs[0];
    }
    TextView txtAddress = (TextView) mBottomSheet.findViewById(R.id.placeAddress) ;
    txtAddress.setText(address);
    TextView txtPhone  = (TextView) mBottomSheet.findViewById(R.id.placePhone) ;
    txtPhone.setText(place.getPhone());
    TextView txtUrl = (TextView) mBottomSheet.findViewById(R.id.placeUrl);
    txtUrl.setText(place.getURL());
    TextView txtType = (TextView) mBottomSheet.findViewById(R.id.placeType) ;
    txtType.setText(place.getType());

    // Assign the appropriate icon
    Drawable d =   CategoryHelper.getDrawableForPlace(place, getActivity()) ;
    ImageView icon = (ImageView) getActivity().findViewById(R.id.TypeIcon);
    icon.setImageDrawable(d);
    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    // Center map on selected place
    mPresenter.centerOnPlace(place);
    mShowSnackbar = false;
    centeredPlaceName = place.getName();
  }

  @Override public void onMapScroll() {
    //Dismiss bottom sheet
    mShowSnackbar = true;
    if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){ // show snackbar prompting for re-doing search
      showSearchSnackbar();
    }else{
      bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

  }
  private Envelope getResultEnveope(List<Point> points){
    Multipoint mp = new Multipoint(points);
    return GeometryEngine.buffer(mp,0.0007).getExtent();
  }
  /**
   * Assign appropriate drawable given place type
   * @param p - Place
   * @return - Drawable
   */
  private int getDrawableForPlace(Place p){
    int d = CategoryHelper.getPinForPlace(p);
    return d;
  }
  private int getPinForCenterPlace(Place p){
    return CategoryHelper.getPinForCenterPlace(p);
  }

  @Override public MapView getMapView() {
    return mMapView;
  }


  /**
   * Center the selected place and change the pin
   * color to blue.
   * @param p
   */
  @Override public void centerOnPlace(Place p) {
    // Stop listening to navigation changes
    // while place is centered in map.
    removeNavigationCompletedListener();
    mCenteringOnPlace = true;
    ListenableFuture<Boolean>  viewCentered = mMapView.setViewpointCenterAsync(p.getLocation());
    viewCentered.addDoneListener(new Runnable() {
      @Override public void run() {
        // Once we've centered on a place, listen
        // for changes in viewpoint.
        if (mNavigationCompletedListener == null){
          Log.i(TAG, "Done centering");
          setNavigationCompletedListener();
        }
      }
    });
    // Change the pin icon
    if (mCenteredGraphic != null){
      BitmapDrawable oldPin = (BitmapDrawable) ContextCompat.getDrawable(getActivity(),getDrawableForPlace(mCenteredPlace)) ;
      mCenteredGraphic.setSymbol(new PictureMarkerSymbol(oldPin));
    }
    List<Graphic> graphics = mGraphicOverlay.getGraphics();
    for (Graphic g : graphics){
      if (g.getGeometry().equals(p.getLocation())){
        mCenteredGraphic = g;
        BitmapDrawable pin = (BitmapDrawable) ContextCompat.getDrawable(getActivity(),getPinForCenterPlace(p)) ;
        final PictureMarkerSymbol pinSymbol = new PictureMarkerSymbol(pin);
        g.setSymbol(pinSymbol);
        break;
      }
    }
    // Keep track of centered place since
    // it will be needed to reset
    // the graphic if another place
    // is centered.
    mCenteredPlace = p;
  }

  @Override public void showRoute(RouteResult routeResult, Point startPoint, Point endPoint) {
    Route route;
    try {
      route = routeResult.getRoutes().get(0);
      if (route.getTotalLength() == 0.0) {
        throw new Exception("Can not find the Route");
      }
    } catch (Exception e) {
      Toast.makeText(getActivity(),
          "We are sorry, we couldn't find the route. Please make "
              + "sure the Source and Destination are different or are connected by road",
          Toast.LENGTH_LONG).show();
      Log.e(TAG, e.getMessage());
      return;
    }

    if (mRouteOverlay == null) {
      mRouteOverlay = new GraphicsOverlay();
    }else{
      mRouteOverlay.getGraphics().clear();
    }
    // Create polyline graphic of the full route
    SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.RED, 4);
    Graphic routeGraphic = new Graphic(route.getRouteGeometry(), lineSymbol);

    // Add the route graphic to the route layer
    mRouteOverlay.getGraphics().add(routeGraphic);
    mMapView.getGraphicsOverlays().add(mRouteOverlay);

    // Zoom to the extent of the entire route with a padding
    Geometry shape = routeGraphic.getGeometry();
    mMapView.setViewpointGeometryWithPaddingAsync(shape, 400);

    // Get routing directions
    mRouteDirections = route.getDirectionManeuvers();
  }

  /**
   *
   * @param presenter
   */
  @Override public void setPresenter(MapContract.Presenter presenter) {
    mPresenter = presenter;
  }

  /**
   * Given a map point, find the associated Place
   */
  private Place getPlaceForPoint(Point p){
    Place foundPlace = mPresenter.findPlaceForPoint(p);
    return foundPlace;
  }
  private class MapTouchListener extends DefaultMapViewOnTouchListener {
    /**
     * Instantiates a new DrawingMapViewOnTouchListener with the specified
     * context and MapView.
     *
     * @param context the application context from which to get the display
     *                metrics
     * @param mapView the MapView on which to control touch events
     */
    public MapTouchListener(Context context, MapView mapView) {
      super(context, mapView);
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
      removeNavigationCompletedListener();
      android.graphics.Point screenPoint = new android.graphics.Point(
          (int) motionEvent.getX(),
          (int) motionEvent.getY());
      // identify graphics on the graphics overlay
      final ListenableFuture<List<Graphic>> identifyGraphic = mMapView
          .identifyGraphicsOverlayAsync(mGraphicOverlay, screenPoint, 10, 2);

      identifyGraphic.addDoneListener(new Runnable() {
        @Override
        public void run() {
          try {
            // get the list of graphics returned by identify
            List<Graphic> graphic = identifyGraphic.get();

            // get size of list in results
            int identifyResultSize = graphic.size();
            if (identifyResultSize > 0){
              Graphic foundGraphic = graphic.get(0);
              Place foundPlace = getPlaceForPoint((Point)foundGraphic.getGeometry());
              if (foundPlace != null){
                showDetail(foundPlace);
              }
            }
          } catch (InterruptedException | ExecutionException ie) {
            ie.printStackTrace();
          }
        }

      });
      return true;
    }
  }

}
