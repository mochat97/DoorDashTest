package com.mshaw.doordashtest.util.extensions

import com.google.android.gms.maps.model.LatLng
import com.mshaw.doordashtest.models.Location

fun Location.toLatLng(): LatLng {
    return LatLng(lat, lng)
}