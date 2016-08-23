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
package com.esri.android.nearbyplaces.mapplace;

import com.esri.android.nearbyplaces.map.MapContract;
import com.esri.android.nearbyplaces.places.PlacesContract;

/**
 * Created by sand8529 on 7/6/16.
 */
public class MapPlaceMediator implements MapPlaceContract {

  private PlacesContract.Presenter mPlacePresenter;
  private MapContract.Presenter mMapPresenter;

  public MapPlaceMediator(){}

  @Override public MapContract.Presenter getMapPresenter() {
    return mMapPresenter;
  }

  @Override public PlacesContract.Presenter getPlacePresenter() {
    return mPlacePresenter;
  }

  @Override public void registerMapPresenter(MapContract.Presenter presenter) {
    mMapPresenter = presenter;
  }

  @Override public void registerPlacePresenter(PlacesContract.Presenter presenter) {
    mPlacePresenter = presenter;
  }
}