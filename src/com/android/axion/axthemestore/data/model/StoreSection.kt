/*
 * Copyright (C) 2026 crDroid Android Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.axion.axthemestore.data.model

import android.content.Intent
import java.util.Locale

enum class StoreSection {
    NetworkIcons,
    BatteryStyles,
    BackGesture,
    ChargingAnimation,
    StatusBarCustomization,
    All;

    fun customizationOmsCategoriesToDiscover(): Set<String>? = when (this) {
        NetworkIcons -> null
        All, StatusBarCustomization -> CustomizationOmsAll
        BatteryStyles -> setOf(OMS_BATTERY_STYLE)
        BackGesture -> setOf(OMS_BACK_GESTURE)
        ChargingAnimation -> setOf(OMS_CHARGING_ANIMATION)
    }

    fun isRelevantCategoryKey(key: String): Boolean = when (this) {
        All -> true
        NetworkIcons -> key in NetworkCategoryKeys
        StatusBarCustomization -> key in CustomizationCategoryKeys
        BatteryStyles -> key in BatteryCategoryKeys
        BackGesture -> key in BackGestureCategoryKeys
        ChargingAnimation -> key in ChargingAnimationCategoryKeys
    }

    companion object {
        const val EXTRA_STORE_SECTION =
            "com.android.axion.axthemestore.extra.STORE_SECTION"

        private const val OMS_BATTERY_STYLE =
            "android.theme.customization.battery_style"
        private const val OMS_BACK_GESTURE =
            "android.theme.customization.back_gesture"
        private const val OMS_CHARGING_ANIMATION =
            "android.theme.customization.charging_animation"
        private const val OMS_WIFI_ICON =
            "android.theme.customization.wifi_icon"
        private const val OMS_SIGNAL_ICON =
            "android.theme.customization.signal_icon"

        private val CustomizationOmsAll = setOf(
            OMS_BATTERY_STYLE,
            OMS_CHARGING_ANIMATION,
            OMS_BACK_GESTURE,
        )

        private val NetworkCategoryKeys = setOf(
            OMS_WIFI_ICON,
            OMS_SIGNAL_ICON,
            "wifi_icons",
            "signal_icons",
            "wifi",
            "signal",
            "statusbar_wifi",
            "statusbar_signal",
            "icon_packs",
        )

        private val BatteryCategoryKeys = setOf(
            OMS_BATTERY_STYLE,
            "battery_style",
        )

        private val BackGestureCategoryKeys = setOf(
            OMS_BACK_GESTURE,
            "back_gesture",
        )

        private val ChargingAnimationCategoryKeys = setOf(
            OMS_CHARGING_ANIMATION,
            "charging_animation",
        )

        private val CustomizationCategoryKeys =
            BatteryCategoryKeys + BackGestureCategoryKeys + ChargingAnimationCategoryKeys

        fun fromIntent(intent: Intent?): StoreSection {
            val raw = intent?.getStringExtra(EXTRA_STORE_SECTION) ?: return All
            return when (raw.lowercase(Locale.ROOT)) {
                "network_icons" -> NetworkIcons
                "battery_style" -> BatteryStyles
                "back_gesture" -> BackGesture
                "charging_animation" -> ChargingAnimation
                "status_bar_customization" -> StatusBarCustomization
                "all" -> All
                else -> All
            }
        }
    }
}
