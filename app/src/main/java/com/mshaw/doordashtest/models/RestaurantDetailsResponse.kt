package com.mshaw.doordashtest.models
import android.telephony.PhoneNumberUtils
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json
import java.util.*

@JsonClass(generateAdapter = true)
data class RestaurantDetailsResponse(
    @Json(name = "address")
    val address: Address?,
    @Json(name = "asap_time")
    val asapTime: Int?,
    @Json(name = "average_rating")
    val averageRating: Double?,
    @Json(name = "business")
    val business: Business?,
    @Json(name = "business_id")
    val businessId: Int?,
    @Json(name = "composite_score")
    val compositeScore: Int?,
    @Json(name = "cover_img_url")
    val coverImgUrl: String?,
    @Json(name = "delivery_fee")
    val deliveryFee: Int?,
    @Json(name = "delivery_fee_details")
    val deliveryFeeDetails: DeliveryFeeDetails?,
    @Json(name = "delivery_radius")
    val deliveryRadius: Int?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "extra_sos_delivery_fee")
    val extraSosDeliveryFee: Int?,
    @Json(name = "fulfills_own_deliveries")
    val fulfillsOwnDeliveries: Boolean?,
    @Json(name = "header_image_url")
    val headerImageUrl: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "inflation_rate")
    val inflationRate: Double?,
    @Json(name = "is_consumer_subscription_eligible")
    val isConsumerSubscriptionEligible: Boolean?,
    @Json(name = "is_good_for_group_orders")
    val isGoodForGroupOrders: Boolean?,
    @Json(name = "is_newly_added")
    val isNewlyAdded: Boolean?,
    @Json(name = "is_only_catering")
    val isOnlyCatering: Boolean?,
    @Json(name = "max_composite_score")
    val maxCompositeScore: Int?,
    @Json(name = "max_order_size")
    val maxOrderSize: Int?,
    @Json(name = "menus")
    val menus: List<DetailsMenu> = emptyList(),
    @Json(name = "merchant_promotions")
    val merchantPromotions: List<DetailsMerchantPromotion> = emptyList(),
    @Json(name = "name")
    val name: String?,
    @Json(name = "number_of_ratings")
    val numberOfRatings: Int?,
    @Json(name = "object_type")
    val objectType: String?,
    @Json(name = "offers_delivery")
    val offersDelivery: Boolean?,
    @Json(name = "offers_pickup")
    val offersPickup: Boolean?,
    @Json(name = "phone_number")
    val phoneNumber: String?,
    @Json(name = "price_range")
    val priceRange: Int?,
    @Json(name = "provides_external_courier_tracking")
    val providesExternalCourierTracking: Boolean?,
    @Json(name = "service_rate")
    val serviceRate: Double?,
    @Json(name = "should_show_store_logo")
    val shouldShowStoreLogo: Boolean?,
    @Json(name = "show_store_menu_header_photo")
    val showStoreMenuHeaderPhoto: Boolean?,
    @Json(name = "show_suggested_items")
    val showSuggestedItems: Boolean?,
    @Json(name = "slug")
    val slug: String?,
    @Json(name = "special_instructions_max_length")
    val specialInstructionsMaxLength: Int?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "status_type")
    val statusType: String?,
    @Json(name = "tags")
    val tags: List<String>?,
    @Json(name = "yelp_biz_id")
    val yelpBizId: String?,
    @Json(name = "yelp_rating")
    val yelpRating: Double?,
    @Json(name = "yelp_review_count")
    val yelpReviewCount: Int?
) {
    val addressFormatted: String
        get() {
            return """
                    ${address?.street}
                    ${address?.city}, ${address?.state} ${address?.zipCode}
                """.trimIndent()
        }

    val phoneNumberFormatted: String
        get() {
            return PhoneNumberUtils.formatNumber(phoneNumber?.replace("+1", ""), Locale.getDefault().country)
        }

    val deliveryFeeFormatted: String
        get() {
            val deliveryFee = deliveryFeeDetails?.originalFee?.displayString
            return "Delivery fee: $deliveryFee"
        }

    val deliveryTimeFormatted: String
        get() = "Delivery time: $status"
}

@JsonClass(generateAdapter = true)
data class Address(
    @Json(name = "city")
    val city: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "lat")
    val lat: Double?,
    @Json(name = "lng")
    val lng: Double?,
    @Json(name = "printable_address")
    val printableAddress: String?,
    @Json(name = "shortname")
    val shortname: String?,
    @Json(name = "state")
    val state: String?,
    @Json(name = "street")
    val street: String?,
    @Json(name = "subpremise")
    val subpremise: String?,
    @Json(name = "zip_code")
    val zipCode: String?
)

@JsonClass(generateAdapter = true)
data class Business(
    @Json(name = "business_vertical")
    val businessVertical: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?
)

@JsonClass(generateAdapter = true)
data class DeliveryFeeDetails(
    @Json(name = "discount")
    val discount: Discount?,
    @Json(name = "final_fee")
    val finalFee: FinalFee?,
    @Json(name = "original_fee")
    val originalFee: OriginalFee?,
    @Json(name = "surge_fee")
    val surgeFee: SurgeFee?
)

@JsonClass(generateAdapter = true)
data class DetailsMenu(
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_business_enabled")
    val isBusinessEnabled: Any?,
    @Json(name = "is_catering")
    val isCatering: Boolean,
    @Json(name = "menu_version")
    val menuVersion: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "open_hours")
    val openHours: List<List<OpenHour>>,
    @Json(name = "status")
    val status: String,
    @Json(name = "status_type")
    val statusType: String,
    @Json(name = "subtitle")
    val subtitle: String
)

@JsonClass(generateAdapter = true)
data class DetailsMerchantPromotion(
    @Json(name = "category_id")
    val categoryId: Int?,
    @Json(name = "delivery_fee")
    val deliveryFee: Int?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "minimum_order_cart_subtotal")
    val minimumOrderCartSubtotal: Int?,
    @Json(name = "new_store_customers_only")
    val newStoreCustomersOnly: Boolean?,
    @Json(name = "sort_order")
    val sortOrder: Int?,
    @Json(name = "title")
    val title: String?
)

@JsonClass(generateAdapter = true)
data class Discount(
    @Json(name = "amount")
    val amount: Amount?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "discount_type")
    val discountType: String?,
    @Json(name = "min_subtotal")
    val minSubtotal: MinSubtotal?,
    @Json(name = "source_type")
    val sourceType: String?,
    @Json(name = "text")
    val text: String?
)

@JsonClass(generateAdapter = true)
data class FinalFee(
    @Json(name = "display_string")
    val displayString: String?,
    @Json(name = "unit_amount")
    val unitAmount: Int?
)

@JsonClass(generateAdapter = true)
data class OriginalFee(
    @Json(name = "display_string")
    val displayString: String?,
    @Json(name = "unit_amount")
    val unitAmount: Int?
)

@JsonClass(generateAdapter = true)
data class SurgeFee(
    @Json(name = "display_string")
    val displayString: String?,
    @Json(name = "unit_amount")
    val unitAmount: Int?
)

@JsonClass(generateAdapter = true)
data class Amount(
    @Json(name = "currency")
    val currency: String?,
    @Json(name = "display_string")
    val displayString: String?,
    @Json(name = "unit_amount")
    val unitAmount: Int?
)

@JsonClass(generateAdapter = true)
data class MinSubtotal(
    @Json(name = "currency")
    val currency: String?,
    @Json(name = "display_string")
    val displayString: String?,
    @Json(name = "unit_amount")
    val unitAmount: Int?
)

@JsonClass(generateAdapter = true)
data class OpenHour(
    @Json(name = "hour")
    val hour: Int?,
    @Json(name = "minute")
    val minute: Int?
)