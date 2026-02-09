package com.michaelrmossman.docdatasets.ui.features

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.model.Feature
import com.michaelrmossman.docdatasets.util.IntegerUtils.getMaxLines
import com.michaelrmossman.docdatasets.util.TextUtils.getAathOrWaroRestrictionText
import com.michaelrmossman.docdatasets.util.TextUtils.getIsActiveText
import com.michaelrmossman.docdatasets.util.TextUtils.getPropertyText
import com.michaelrmossman.docdatasets.util.TextUtils.getUndefinedText
import com.michaelrmossman.docdatasets.util.formatWithCommas

@Composable
fun FeatureListItemContent(
    // dataset: DataSetEntity,
    feature: Feature,
    fullText: Boolean,
    /* Modifier used by all [Feature] text items */
    modifier: Modifier = Modifier
) {
    /* AATH Concessions */
    feature.properties.blockName?.let { name ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = name,
                stringId = R.string.prop_block_name
            )
        )
        val aathRestriction = getAathOrWaroRestrictionText(
            feature.properties.restriction1,
            feature.properties.restriction2
        )
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = aathRestriction,
                stringId = R.string.feature_aath_restriction
            )
        )
    }

    /* CMS Vis Management */
    feature.properties.cmsArea?.let { cmsArea ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = cmsArea,
                stringId = R.string.prop_cms_area
            )
        )
        feature.properties.vmzName?.let { vmzName ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = vmzName,
                    stringId = R.string.prop_vmz_name
                )
            )
        }
    }

    /* CMS Air Zones */
    // cmsArea already above
    feature.properties.cmsAirZone?.let { cmsAirZone ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = cmsAirZone,
                stringId = R.string.prop_cms_air_zone
            )
        )
    }

    /* Covenant Areas */
    feature.properties.name0?.let { name ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = name,
                stringId = R.string.prop_name
            )
        )
        feature.properties.recordedArea?.let { recordedArea ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = stringResource(
                        R.string.feature_hectares,
                        recordedArea.toInt().formatWithCommas()
                    ),
                    stringId = R.string.prop_recorded_area
                )
            )
        }
    }

    /* (Distrib Of) Species */
    feature.properties.office?.let { office ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = office,
                stringId = R.string.prop_office
            )
        )
        feature.properties.abundance?.let { abundance ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = when (abundance.isNotBlank()) {
                        true -> abundance
                        else -> getUndefinedText()
                    },
                    stringId = R.string.prop_abundance
                )
            )
        }
    }

    /* DoC Campsites */
    feature.properties.name?.let { name ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = name,
                stringId = R.string.prop_name
            )
        )
        feature.properties.place?.let { place ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = place,
                    stringId = R.string.prop_place
                )
            )
        }
    }

    /* Ecological Districts */
    feature.properties.ecologicalDist?.let { district ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = district,
                stringId = R.string.prop_ecological_dist
            )
        )
        feature.properties.ecologicalReg?.let { region ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = region,
                    stringId = R.string.prop_ecological_reg
                )
            )
        }
    }

    /* Freedom Camping */
    // Name already above
    feature.properties.siteCategoryType?.let { type ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = type,
                stringId = R.string.prop_site_category_type
            )
        )
    }

    /* Kea Habitat */
    feature.properties.keaPresence?.let { presence ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = presence,
                stringId = R.string.prop_kea_presence
            )
        )
        feature.properties.scroungeInfluenced?.let { scrounged ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = scrounged,
                    stringId = R.string.prop_scrounge_influenced
                )
            )
        }
    }

    /* Marine Reserves */
    // In this context, identical to Covenant Areas

    /* DoC MTB Locations */
    // Name already above
    feature.properties.introduction?.let { intro ->
        Text(
            maxLines = getMaxLines(fullText),
            overflow = TextOverflow.Ellipsis,
            modifier = modifier,
            text = getPropertyText(
                property = intro,
                stringId = R.string.prop_title
            )
        )
    }

    /* DoC MTB Routes */
    // identical to MTB Locations

    /* NonMigratory Freshwater Fish Dist */
    feature.properties.catchment?.let { catchment ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = catchment,
                stringId = R.string.prop_catchment
            )
        )
        feature.properties.docDistrict?.let { district ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = district,
                    stringId = R.string.prop_doc_district
                )
            )
        }
    }

    /* DOC Offices */
    // Name already above
    feature.properties.phoneNumber?.let { phone ->
        when (phone.isNotBlank()) {
            true -> Text(
                modifier = modifier,
                text = getPropertyText(
                    property = phone,
                    stringId = R.string.prop_phone_number
                )
            )
            else -> Text(
                modifier = modifier,
                text = getPropertyText(
                    property = getIsActiveText(
                        feature.properties.isActive
                    ),
                    stringId = R.string.prop_is_active
                )
            )
        }
        /* 19 of 118 email addies are empty strings,
           but we'll still go with the null check,
           just in case this changes in future */
        feature.properties.email?.let { email ->
            val emailAddie = when (email.length) {
                0 -> getUndefinedText()
                else -> email
            }
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = emailAddie,
                    stringId = R.string.prop_email
                )
            )
        }
    }

    /* Ops Districts */
    feature.properties.districtName?.let { district ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = district,
                stringId = R.string.district_name
            )
        )
    } // Also, region

    /* Ops Regions */
    feature.properties.regionName?.let { region ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = region,
                stringId = R.string.region_name
            )
        )
        feature.properties.regionCode?.let { code ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = code,
                    stringId = R.string.region_code
                )
            )
        }
    }

    /* Pub Cons Land */
    /* In this context, identical to
       Covenant Areas & Marine Reserves */

    /* Rec Hunt Perm Areas */
    feature.properties.huntBlockName?.let { name ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = name,
                stringId = R.string.prop_hunt_block_name
            )
        )
        feature.properties.permitArea?.let { permitArea ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = permitArea,
                    stringId = R.string.prop_permit_area
                )
            )
        }
    }

    /* DOC Sanctuaries */
    // Name already above
    // (napalisId will apply to others as well)
    feature.properties.napalisId?.let { id ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = id,
                stringId = R.string.prop_napalis_id
            )
        )
    }

    /* DOC Roads | Tracks */
    feature.properties.techObjectName?.let { name ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = name,
                stringId = R.string.prop_tech_object_name
            )
        )
        /* All CharName2 labels contain "USER_STATUS" */
        feature.properties.charValue2?.let { status ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = status,
                    stringId = R.string.prop_status
                )
            )
        }
    }

    /* Walk Tramp Exp Locations */
    // In this context, identical to MTB Locations

    /* Walk Tramp Exp Routes */
    // In this context, identical to MTB Locations

    /* Threatened Invertebrates */
    feature.properties.commonName1?.let { name ->
        Text(
            modifier = modifier,
            text = getPropertyText(
                property = name,
                stringId = R.string.prop_common_name
            )
        )
        feature.properties.umbrellaCat?.let { category ->
            Text(
                modifier = modifier,
                text = getPropertyText(
                    property = category,
                    stringId = R.string.prop_umbrella_category
                )
            )
        }
    }
}