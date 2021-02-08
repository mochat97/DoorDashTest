package com.mshaw.doordashtest.models
import android.os.Parcelable
import com.mshaw.doordashtest.util.extensions.round
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.util.*

@JsonClass(generateAdapter = true)
@Parcelize
data class RestaurantListResponse(
    @Json(name = "is_first_time_user")
    val isFirstTimeUser: Boolean,
    @Json(name = "next_offset")
    val nextOffset: Int,
    @Json(name = "num_results")
    val numResults: Int,
    @Json(name = "show_list_as_pickup")
    val showListAsPickup: Boolean,
    @Json(name = "sort_order")
    val sortOrder: String,
    @Json(name = "stores")
    val stores: List<Store>
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Store(
    @Json(name = "average_rating")
    val averageRating: Double,
    @Json(name = "business_id")
    val businessId: Int,
    @Json(name = "cover_img_url")
    val coverImgUrl: String,
    @Json(name = "delivery_fee")
    val deliveryFee: Int,
    @Json(name = "delivery_fee_monetary_fields")
    val deliveryFeeMonetaryFields: DeliveryFeeMonetaryFields,
    @Json(name = "description")
    val description: String,
    @Json(name = "display_delivery_fee")
    val displayDeliveryFee: String,
    @Json(name = "distance_from_consumer")
    val distanceFromConsumer: Double,
    @Json(name = "extra_sos_delivery_fee")
    val extraSosDeliveryFee: Int,
    @Json(name = "extra_sos_delivery_fee_monetary_fields")
    val extraSosDeliveryFeeMonetaryFields: ExtraSosDeliveryFeeMonetaryFields,
    @Json(name = "header_img_url")
    val headerImgUrl: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_consumer_subscription_eligible")
    val isConsumerSubscriptionEligible: Boolean,
    @Json(name = "is_newly_added")
    val isNewlyAdded: Boolean,
    @Json(name = "location")
    val location: Location,
    @Json(name = "menus")
    val menus: List<Menu>,
    @Json(name = "merchant_promotions")
    val merchantPromotions: List<MerchantPromotion>,
    @Json(name = "name")
    val name: String,
    @Json(name = "next_close_time")
    val nextCloseTime: Date,
    @Json(name = "next_open_time")
    val nextOpenTime: Date,
    @Json(name = "num_ratings")
    val numRatings: Int,
    @Json(name = "price_range")
    val priceRange: Int,
    @Json(name = "promotion_delivery_fee")
    val promotionDeliveryFee: Int,
    @Json(name = "service_rate")
    val serviceRate: String?,
    @Json(name = "status")
    val status: Status,
    @Json(name = "url")
    val url: String
): Parcelable {
    fun getDistanceFromConsumerFormatted(date: Date): String {
        return if (date.after(nextOpenTime) && date.before(nextCloseTime)) {
            "${distanceFromConsumer.round(0.5)} mi"
        } else {
            "Closed"
        }
    }
}

@JsonClass(generateAdapter = true)
@Parcelize
data class DeliveryFeeMonetaryFields(
    @Json(name = "currency")
    val currency: String,
    @Json(name = "decimal_places")
    val decimalPlaces: Int,
    @Json(name = "display_string")
    val displayString: String,
    @Json(name = "unit_amount")
    val unitAmount: Int?
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class ExtraSosDeliveryFeeMonetaryFields(
    @Json(name = "currency")
    val currency: String,
    @Json(name = "decimal_places")
    val decimalPlaces: Int,
    @Json(name = "display_string")
    val displayString: String,
    @Json(name = "unit_amount")
    val unitAmount: Int
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Location(
    @Json(name = "lat")
    val lat: Double,
    @Json(name = "lng")
    val lng: Double
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Menu(
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_catering")
    val isCatering: Boolean,
    @Json(name = "name")
    val name: String,
    @Json(name = "popular_items")
    val popularItems: List<PopularItem>,
    @Json(name = "subtitle")
    val subtitle: String
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class MerchantPromotion(
    @Json(name = "category_new_store_customers_only")
    val categoryNewStoreCustomersOnly: Boolean,
    @Json(name = "daypart_constraints")
    val daypartConstraints: List<String?>,
    @Json(name = "delivery_fee")
    val deliveryFee: Int?,
    @Json(name = "delivery_fee_monetary_fields")
    val deliveryFeeMonetaryFields: DeliveryFeeMonetaryFields,
    @Json(name = "id")
    val id: Int,
    @Json(name = "minimum_subtotal")
    val minimumSubtotal: Int?,
    @Json(name = "minimum_subtotal_monetary_fields")
    val minimumSubtotalMonetaryFields: MinimumSubtotalMonetaryFields
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Status(
    @Json(name = "asap_available")
    val asapAvailable: Boolean,
    @Json(name = "asap_minutes_range")
    val asapMinutesRange: List<Int>,
    @Json(name = "pickup_available")
    val pickupAvailable: Boolean,
    @Json(name = "scheduled_available")
    val scheduledAvailable: Boolean,
    @Json(name = "unavailable_reason")
    val unavailableReason: String?
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class PopularItem(
    @Json(name = "description")
    val description: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "img_url")
    val imgUrl: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "price")
    val price: Int
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class MinimumSubtotalMonetaryFields(
    @Json(name = "currency")
    val currency: String,
    @Json(name = "decimal_places")
    val decimalPlaces: Int,
    @Json(name = "display_string")
    val displayString: String,
    @Json(name = "unit_amount")
    val unitAmount: Int?
): Parcelable