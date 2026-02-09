package com.michaelrmossman.docdatasets.model

import com.michaelrmossman.docdatasets.R
import com.michaelrmossman.docdatasets.enum.PropertyType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* Non-null values were found to have actual values in
   Json & are not accessed by the iterator() at EOF */
@Serializable
data class Properties( // @OptIn(ExperimentalUuidApi::class) constructor

    /* Common */
    @SerialName("OBJECTID")
    val objectId: Int = 0,
    @SerialName("Shape__Area")
    val shapeArea: Double? = null,
    @SerialName("Shape__Length")
    val shapeLength: Double? = null,

    /* AATH Concessions */
    // objectId
    @SerialName("BlockType")
    val blockType: String? = null,
    @SerialName("Status")
    val status: String? = null,
    @SerialName("BlockName")
    val blockName: String? = null,
    @SerialName("BlockID")
    val blockId: String? = null,
    @SerialName("LocationID")
    val locationId: Int? = null,
    @SerialName("Restriction_1")
    val restriction1: String? = null,
    @SerialName("Restriction_2")
    val restriction2: String? = null,
    @SerialName("Restriction_3")
    val restriction3: String? = null,
    /* These two verified date strings */
    @SerialName("Created_Date_Editor_Tracking")
    val createdDateEditorTracking: Long? = null,
    @SerialName("Last_Updated_Date_Editor_Track")
    val lastUpdatedDateEditorTrack: Long? = null,
    @SerialName("GlobalID")
    /* (verified string only, in this case,
       but DoC Tracks uses an actual UUID)
       i.e. surrounded in curly braces */
    val globalId: String? = null,

    /* CMS Vis Management */
    // objectId
    @SerialName("PCL_Date") /* verified date string */
    val pclDate: String? = null,
    @SerialName("CMS_AREA")
    val cmsArea: String? = null,
    @SerialName("CMS_PublishDate")
    val cmsPublishDate: String? = null,
    @SerialName("ROS_CLASS")
    val rosClass: Int? = null,
    @SerialName("VMZ_NAME")
    val vmzName: String? = null,
    // globalId (verified string only)
    // shapeArea
    // shapeLength

    /* CMS Air Zones */
    // objectId
    @SerialName("CMS_AIRZONE")
    val cmsAirZone: String? = null,
    // pclDate
    // cmsArea
    // cmsPublishDate
    // globalId (verified string only)

    /* Covenant Areas */
    // objectId
    @SerialName("NaPALIS_ID")
    val napalisId: Int? = null,
    @SerialName("Start_Date")
    val startDate: Long? = null,
    @SerialName("End_Date")
    val endDate: Long? = null,
    @SerialName("Type")
    val type: String? = null,
    @SerialName("Name")
    val name0: String? = null,
    @SerialName("Local_Purpose")
    val localPurpose: String? = null,
    @SerialName("Government_Purpose")
    val governmentPurpose: String? = null,
    @SerialName("Conservation_Unit_Number")
    val conservationUnitNumber: String? = null,
    @SerialName("Vested")
    val vested: String? = null,
    @SerialName("Control_Managed")
    val controlManaged: String? = null,
    @SerialName("Recorded_Area")
    val recordedArea: Double? = null,
    // globalId (verified string only)
    @SerialName("GIS_AREA")
    val gisArea: String? = null,
    @SerialName("GIS_AREA_HA")
    val gisAreaHa: String? = null,
    @SerialName("DOC_FIRE_RESPONSIBILITY")
    val docFireResponsibility: String? = null,
    // shapeArea
    // shapeLength

    /* Distr Of (Species) */
    // objectId
    @SerialName("RecID")
    val recID: Int? = null,
    @SerialName("Office")
    val office: String? = null,
    @SerialName("ShapeFileN")
    val shapeFileN: Int? = null,
    @SerialName("RefElectSh")
    val refElectSh: String? = null,
    @SerialName("Scientific")
    val scientificName2: String? = null,
    @SerialName("CommonName")
    val commonName2: String? = null,
    @SerialName("Observatio")
    val observation: Int? = null,
    @SerialName("PA")
    val pa: String? = null,
    @SerialName("Abundance")
    val abundance: String? = null,
    @SerialName("AbundRelia")
    val abundReliability: String? = null,
    @SerialName("AbundIndex")
    val abundIndex: String? = null,
    @SerialName("AbundMetho")
    val abundMethod: String? = null,
    @SerialName("AbundDataS")
    val abundDataS: String? = null,
    @SerialName("AbundNotes")
    val abundNotes: String? = null,
    @SerialName("Organisati")
    val organisation: String? = null,
    @SerialName("PA_2014")
    val pa2014: String? = null,
    // shapeArea
    // shapeLength

    /* DoC Campsites */
    // objectId
    /* Note the distinction between name and name0 ...
     *  name0 has an uppercase N for its SerialName */
    val name: String? = null,
    val place: String? = null,
    val region: String? = null,
    val introduction: String? = null,
    val campsiteCategory: String? = null,
    val numberOfPoweredSites: Int? = null,
    val numberOfUnpoweredSites: Int? = null,
    val bookable: String? = null,
    val free: String? = null,
    val facilities: String? = null,
    val activities: String? = null,
    val dogsAllowed: String? = null,
    val landscape: String? = null,
    val access: String? = null,
    val hasAlerts: String? = null,
    /* Ignored by iterator */
    val introductionThumbnail: String? = null,
    val staticLink: String? = null,
    val locationString: String? = null,
    val x: Int? = null,
    val y: Int? = null,
    val assetId: Int? = null,
    @SerialName("dateLoadedToGIS")
    val dateLoadedToGis: Long? = null,
    // globalId (verified string only)

    /* Ecological Districts */
    // objectId
    @SerialName("ECOLOGICAL_CODE")
    val ecologicalCode: String? = null,
    @SerialName("ECOLOGICAL_DISTRICT")
    val ecologicalDist: String? = null,
    @SerialName("ECOLOGICAL_REGION")
    val ecologicalReg: String? = null,

    /* Ecosystem Management */
    // objectId
//    @SerialName("PrescriptionID")
//    val prescriptionId: Int? = null,
//    @SerialName("PrescriptionName")
//    val prescriptionName: String? = null,
//    @SerialName("Description")
//    val description: String? = null,
//    @SerialName("Rank")
//    val rank: Double? = null,
    // shapeArea
    // shapeLength

    /* Freedom Camping */
    // objectId
    // name0
    @SerialName("Site_Category_Type")
    val siteCategoryType: String? = null,
    @SerialName("Site_Category_Code")
    val siteCategoryCode: String? = null,
    @SerialName("Condition_1")
    val condition1: String? = null,
    @SerialName("Condition_2")
    val condition2: String? = null,
    @SerialName("Condition_3")
    val condition3: String? = null,
    @SerialName("Condition_4")
    val condition4: String? = null,
    @SerialName("Action_Date")
    val actionDate: Long? = null,
    @SerialName("Functional_Location")
    val functionalLocation: String? = null,
    @SerialName("Address")
    val address: String? = null,
    @SerialName("Location")
    val location: String? = null,
    @SerialName("Area_Phone")
    val areaPhone: String? = null,
    @SerialName("Notice_Number")
    val noticeNumber: Int? = null,
    // status
    @SerialName("Operations_Region")
    val operationsRegion: String? = null,
    @SerialName("Operations_District")
    val operationsDistrict: String? = null,
    // napalisId
    // shapeArea
    // shapeLength

    /* Kea Habitat */
    // objectId
    @SerialName("Kea_Presen")
    val keaPresence: String? = null,
    @SerialName("Source_dat")
    val sourceData: String? = null,
    @SerialName("Edit")
    val edit: String? = null,
    @SerialName("EditNotes")
    val editNotes: String? = null,
    @SerialName("Scrounge_Influenced")
    val scroungeInfluenced: String? = null,
    // shapeArea
    // shapeLength

    /* DoC Huts */
    // objectId
    // name
    // place
    // region
    // bookable
    // facilities
    // hasAlerts
    // introductionThumbnail
    // staticLink
    // locationString
    // x
    // y
    // assetId
    // dateLoadedToGIS
    // globalId (verified string only)

    /* Marine Reserves */
    // objectId
    // napalisId
    // type
    // name0
    @SerialName("Legislation")
    val legislation: String? = null,
    @SerialName("Section")
    val section: String? = null,
    // recordedArea
    // globalId (verified string only)
    // shapeArea
    // shapeLength

    /* DoC MTB Locations */
    // objectId
    // name
    // introduction
    val difficulty: String? = null,
    val bikingTime: String? = null,
    // hasAlerts
    // introductionThumbnail
    val mountainBikingTrackWebPage: String? = null,
    // dateLoadedToGis

    /* DoC MTB Routes: identical to MTB
       Locations, plus [shapeLength] */

    /* NonMigratory Freshwater Fish Dist */
    // objectId
    @SerialName("Location_number")
    val locationNumber: Int? = null,
    @SerialName("Species")
    val species: String? = null,
    @SerialName("Vernacular")
    val vernacular: String? = null,
    @SerialName("Catchment")
    val catchment: String? = null,
    @SerialName("Location_Gazetted")
    val locationGazetted: String? = null,
    @SerialName("Location_DOC_name")
    val locationDocName: String? = null,
    @SerialName("DOC_Region")
    val docRegion: String? = null,
    @SerialName("DOC_District")
    val docDistrict: String? = null,
    @SerialName("Regional_Council")
    val regionalCouncil: String? = null,
    @SerialName("District_Council")
    val districtCouncil: String? = null,
    // status
    @SerialName("Spatial_order")
    val spatialOrder: Int? = null,
    @SerialName("Updated")
    val updated: String? = null, /* verified date string */
    @SerialName("Perimeter")
    val perimeter: Double? = null,

    /* DOC Offices */
    // objectId
    // name0
    @SerialName("Acronym")
    val acronym: String? = null,
    @SerialName("Parent")
    val parent: Int? = null,
    // type
    @SerialName("PhysicalAddressLine1")
    val physicalAddressLine1: String? = null,
    @SerialName("PhysicalAddressLine2")
    val physicalAddressLine2: String? = null,
    @SerialName("PhysicalAddressLine3")
    val physicalAddressLine3: String? = null,
    @SerialName("PhysicalAddressLine4")
    val physicalAddressLine4: String? = null,
    @SerialName("PostalAddressLine1")
    val postalAddressLine1: String? = null,
    @SerialName("PostalAddressLine2")
    val postalAddressLine2: String? = null,
    @SerialName("PostalAddressLine3")
    val postalAddressLine3: String? = null,
    @SerialName("PostalAddressLine4")
    val postalAddressLine4: String? = null,
    @SerialName("PhoneNumber")
    val phoneNumber: String? = null,
    @SerialName("VPN")
    val vpn: String? = null,
    @SerialName("FaxNumber")
    val faxNumber: String? = null,
    @SerialName("FaxVPN")
    val faxVpn:String? = null,
    @SerialName("ManagerID")
    val managerId: String? = null,
    @SerialName("Email")
    val email: String? = null,
    @SerialName("OpeningHour")
    val openingHour: Int? = null,
    @SerialName("OpeningMinutes")
    val openingMinute: Int? = null,
    @SerialName("ClosingHour")
    val closingHour: Int? = null,
    @SerialName("ClosingMinutes")
    val closingMinute: Int? = null,
    @SerialName("ModifiedBy")
    val modifiedBy: String? = null,
    @SerialName("DateModified")
    val dateModified: Long? = null,
    @SerialName("IsActive")
    val isActive: Int? = null,
    @SerialName("CODE")
    val code: String? = null,
    @SerialName("MaoriName")
    val maoriName: String? = null,
    @SerialName("Northing")
    val northing: Double? = null,
    @SerialName("Easting")
    val easting: Double? = null,
    @SerialName("Town")
    val town: String? = null,
    @SerialName("FullNameLabel")
    val fullNameLabel: String? = null,
    // globalId (verified string only)

    /* Ops Districts */
    @SerialName("DistrictName")
    val districtName: String? = null,
    @SerialName("DistrictCode")
    val districtCode: String? = null,
    @SerialName("RegionCode")
    val regionCode: String? = null,
    @SerialName("RegionName")
    val regionName: String? = null,
    // shapeArea
    // shapeLength

    /* Ops Regions */
    // objectId
    // regionName
    // regionCode
    // globalId

    /* Pub Cons Land */
    // objectId
    // napalisId
    /* startDate by MM : verified as string */
    @SerialName("Start_Date_Str")
    val startDateStr: String? = null,
    // endDate /* string, although all null */
    // type
    // name0
    // legislation
    // section
    // localPurpose
    // governmentPurpose
    // conservationUnitNumber
    // vested
    // controlManaged
    // recordedArea
    @SerialName("Overlays")
    val overlays: String? = null,
    @SerialName("Private_Ownership")
    val privateOwnership: String? = null,
    @SerialName("Classified")
    val classified: String? = null,
    // shapeArea
    // shapeLength

    /* Rec Hunt Perm Areas */
    // objectId
    @SerialName("CreatedDate")
    val createdDate: Long? = null,
    @SerialName("ModifiedDate")
    val modifiedDate: Long? = null,
    @SerialName("Conservancy")
    val conservancy: String? = null,
    @SerialName("PermitArea")
    val permitArea: String? = null,
    // blockId
    @SerialName("HuntBlockName")
    val huntBlockName: String? = null,
    // blockType
    @SerialName("HuntStatus")
    val huntStatus: String? = null,
    @SerialName("DateConditions")
    val dateConditions: String? = null,
    @SerialName("AccessConditions")
    val accessConditions: String? = null,
    @SerialName("DogConditions")
    val dogConditions: String? = null,
    @SerialName("SpecialConditions")
    val specialConditions: String? = null,
    @SerialName("Ha")
    val hectares: Double? = null,
    @SerialName("Scale")
    val scale: Int? = null,
    // shapeArea
    // shapeLength

    /* DOC Sanctuaries */
    // objectId
    // napalisId
    // type
    // name0
    // legislation
    // section
    // recordedArea
    // shapeArea
    // shapeLength

    /* DOC Roads | Tracks */
    // objectId
    @SerialName("TechObjectName")
    val techObjectName: String? = null,
    @SerialName("FlocID")
    val flocId: String? = null,
    @SerialName("EquipmentID")
    val equipmentId:Int? = null,
    @SerialName("ObjectType")
    val objectType: String? = null,
    @SerialName("ObjectTypeKey")
    val objectTypeKey: String? = null,
    @SerialName("SubObjectType")
    val subObjectType: String? = null,
    @SerialName("SubObjectTypeKey")
    val subObjectTypeKey: String? = null,
    @SerialName("FlocCat")
    val flocCat: String? = null,
    @SerialName("FlocCatStr")
    val flocCatString: String? = null,
    @SerialName("EquipCat")
    val equipCat: String? = null,
    @SerialName("EquipCatStr")
    val equipCatString: String? = null,
    @SerialName("DataCaptureAccuracy")
    val dataCaptureAccuracy: Int? = null,
    @SerialName("DataCaptureAccuracyStr")
    val dataCaptureAccuracyStr: String? = null,
    @SerialName("GISStatus")
    val gisStatus: String? = null,
    @SerialName("GISStatusStr")
    val gisStatusString: String? = null,
    @SerialName("CharGlobalID")
    val charGlobalId: String = String(), // actual UUID
    @SerialName("CharName1")
    val charName1: String? = null,
    @SerialName("CharValue1")
    val charValue1: String? = null,
    @SerialName("CharName2")
    val charName2: String? = null,
    @SerialName("CharValue2")
    val charValue2: String? = null,
    @SerialName("CharName3")
    val charName3: String? = null,
    @SerialName("CharValue3")
    val charValue3: String? = null,
    @SerialName("CharName4")
    val charName4: String? = null,
    @SerialName("CharValue4")
    val charValue4: String? = null,
    @SerialName("CharName5")
    val charName5: String? = null,
    @SerialName("CharValue5")
    val charValue5: String? = null,
    @SerialName("CharName6")
    val charName6: String? = null,
    @SerialName("CharValue6")
    val charValue6: String? = null,
    @SerialName("CharName7")
    val charName7: String? = null,
    @SerialName("CharValue7")
    val charValue7: String? = null,
    @SerialName("CharName8")
    val charName8: String? = null,
    @SerialName("CharValue8")
    val charValue8: String? = null,
    @SerialName("CharName9")
    val charName9: String? = null,
    @SerialName("CharValue9")
    val charValue9: String? = null,
    @SerialName("CharName10")
    val charName10: String? = null,
    @SerialName("CharValue10")
    val charValue10: String? = null,
    @SerialName("CharName11")
    val charName11: String? = null,
    @SerialName("CharValue11")
    val charValue11: String? = null,
    /* Ignored by iterator */
    @SerialName("SortField")
    val sortField: Int? = null,
    // createdDateEditorTracking
    // lastUpdatedDateEditorTrack
    // globalId (actual UUID, in this case)

    /* Walk Tramp Exp Locations */
    // objectId
    // name
    // introduction
    // difficulty
    val completionTime: String? = null,
    // hasAlerts
    // introductionThumbnail
    val walkingAndTrampingWebPage: String? = null,
    // dateLoadedToGis

    /* Walk Tramp Exp Routes : identical to Walk
       Tramp Exp Locations, but adds shapeLength */
    // shapeLength

    /* Threatened Invertebrates */
    // objectId
    @SerialName("Common_name")
    val commonName1: String? = null,
    @SerialName("Scientific_Name")
    val scientificName1: String? = null,
    @SerialName("Umbrella_Category")
    val umbrellaCat: String? = null,
    @SerialName("Threat_Class")
    val threatClass: String? = null,
    /* Note distinction between Northing and Northings */
    @SerialName("Northings")
    val northings: Double? = null,
    /* Note distinction between Easting and Eastings */
    @SerialName("Eastings")
    val eastings: Double? = null,
    @SerialName("Locality")
    val locality: String? = null,
    @SerialName("Number_")
    val number: Int? = null,
    @SerialName("Habitat")
    val habitat: String? = null,
    @SerialName("Original_Reference")
    val originalRef: String? = null,
    @SerialName("Data_Source")
    val dataSource: String? = null,
    @SerialName("Number_of_individuals")
    val numOfIndiv: Int? = null,
    @SerialName("Certainty_of_location")
    val certaintyOfLocation: String? = null,
    @SerialName("Date_")
    val date: String? = null,
    @SerialName("Threat_Assessment_Notes")
    val threatAssessmentNotes: String? = null,
    @SerialName("Former_Name")
    val formerName: String? = null,
    @SerialName("Family")
    val family: String? = null,
    @SerialName("GroupA")
    val groupA: String? = null,
    @SerialName("Comments")
    val comments: String? = null,
    @SerialName("BMI_ID")
    val bmiId: String? = null,

    /* WARO Concessions: identical to AATH, but without BlockID */
) {
    // @OptIn(ExperimentalUuidApi::class)
    fun iterator(): List<Pair<Int, Pair<Any?, PropertyType>>> {
        return listOf(
            R.string.image_load_error to Pair(
                introductionThumbnail,PropertyType.Image
            ),
            /* Properties Common */
            R.string.prop_object_id to Pair(objectId,PropertyType.Integer),
            R.string.prop_shape_area to Pair(shapeArea,PropertyType.Double),
            R.string.prop_shape_length to Pair(shapeLength,PropertyType.Double),
            /* AATH Concessions */
            R.string.prop_block_type to Pair(blockType,PropertyType.String),
            R.string.prop_status to Pair(status,PropertyType.String),
//            R.string.prop_block_name to Pair(blockName,PropertyType.String),
            R.string.prop_block_id to Pair(blockId,PropertyType.String),
            R.string.prop_location_id to Pair(locationId,PropertyType.Integer),
//            R.string.prop_restriction_1 to Pair(
//                restriction1,PropertyType.String
//            ),
//            R.string.prop_restriction_2 to Pair(
//                restriction2,PropertyType.String
//            ),
            R.string.prop_restriction_3 to Pair(
                restriction3,PropertyType.String
            ),
            R.string.prop_created_date_editor_tracking to Pair(
                createdDateEditorTracking,PropertyType.Long
            ),
            R.string.prop_last_updated_date_editor_track to Pair(
                lastUpdatedDateEditorTrack,PropertyType.Long
            ),
//            R.string.prop_global_id to Pair(
//                _globalId.toString(),PropertyType.String
//            ),
            /* CMS Vis Management */
            R.string.prop_pcl_date to Pair(pclDate,PropertyType.String),
//            R.string.prop_cms_area to Pair(cmsArea,PropertyType.String),
            R.string.prop_cms_publish_date to Pair(
                cmsPublishDate,PropertyType.String
            ),
            R.string.prop_ros_class to Pair(rosClass,PropertyType.Integer),
//            R.string.prop_vmz_name to Pair(vmzName,PropertyType.String),
            /* CMS Air Zones */
//            R.string.prop_cms_air_zone to Pair(cmsAirZone,PropertyType.String),
            /* Covenant Areas */
//            R.string.prop_napalis_id to Pair(napalisId,PropertyType.Integer),
            R.string.prop_start_date to Pair(startDate,PropertyType.Long),
            R.string.prop_end_date to Pair(endDate,PropertyType.Long),
            R.string.prop_type to Pair(type,PropertyType.String),
//            R.string.prop_name to Pair(name0,PropertyType.String),
            R.string.prop_local_purpose to Pair(
                localPurpose,PropertyType.String
            ),
            R.string.prop_government_purpose to Pair(
                governmentPurpose,PropertyType.String
            ),
            R.string.prop_conservation_unit_num to Pair(
                conservationUnitNumber,PropertyType.String
            ),
            R.string.prop_vested to Pair(vested,PropertyType.String),
            R.string.prop_control_managed to Pair(
                controlManaged,PropertyType.String
            ),
//            R.string.prop_recorded_area to Pair(
//                recordedArea,PropertyType.Double
//            ),
            R.string.prop_gis_area to Pair(gisArea,PropertyType.String),
            R.string.prop_gis_area_ha to Pair(gisAreaHa,PropertyType.String),
            R.string.prop_doc_fire_responsibility to Pair(
                docFireResponsibility,PropertyType.String
            ),
            /* Distr Of (Species) */
            R.string.prop_rec_id to Pair(recID,PropertyType.Integer),
//            R.string.prop_office to Pair(office,PropertyType.String),
            R.string.prop_shape_file_n to Pair(shapeFileN,PropertyType.Integer),
            R.string.prop_ref_elect_sh to Pair(refElectSh,PropertyType.String),
            R.string.prop_scientific_name to Pair(
                scientificName2,PropertyType.String
            ),
            R.string.prop_common_name to Pair(commonName2,PropertyType.String),
            R.string.prop_observation to Pair(observation,PropertyType.Integer),
            R.string.prop_pa to Pair(pa,PropertyType.String),
//            R.string.prop_abundance to Pair(abundance,PropertyType.String),
            R.string.prop_abund_reliability to Pair(
                abundReliability,PropertyType.String
            ),
            R.string.prop_abund_index to Pair(abundIndex,PropertyType.String),
            R.string.prop_abund_method to Pair(abundMethod,PropertyType.String),
            R.string.prop_abund_data_s to Pair(abundDataS,PropertyType.String),
            R.string.prop_abund_notes to Pair(abundNotes,PropertyType.String),
            R.string.prop_organisation to Pair(
                organisation,PropertyType.String
            ),
            R.string.prop_pa_2014 to Pair(pa2014,PropertyType.String),
            /* DoC Campsites */
//            R.string.prop_name to Pair(name,PropertyType.String),
//            R.string.prop_place to Pair(place,PropertyType.String),
            R.string.prop_region to Pair(region,PropertyType.String),
//            R.string.prop_introduction to Pair(
//                introduction,PropertyType.String
//            ),
            R.string.prop_campsite_category to Pair(
                campsiteCategory,PropertyType.String
            ),
            R.string.prop_number_of_powered_sites to Pair(
                numberOfPoweredSites,PropertyType.Integer
            ),
            R.string.prop_number_of_unpowered_sites to Pair(
                numberOfUnpoweredSites,PropertyType.Integer
            ),
            R.string.prop_bookable to Pair(bookable,PropertyType.String),
            R.string.prop_free to Pair(free,PropertyType.String),
            R.string.prop_facilities to Pair(facilities,PropertyType.String),
            R.string.prop_activities to Pair(activities,PropertyType.String),
            R.string.prop_dogs_allowed to Pair(dogsAllowed,PropertyType.String),
            R.string.prop_landscape to Pair(landscape,PropertyType.String),
            R.string.prop_access to Pair(access,PropertyType.String),
            R.string.prop_has_alerts to Pair(hasAlerts,PropertyType.String),
            // (introductionThumbnail)
            R.string.prop_static_link to Pair(staticLink,PropertyType.WebUrl),
            R.string.prop_location to Pair(
                locationString,PropertyType.String
            ),
            R.string.prop_x to Pair(x,PropertyType.Integer),
            R.string.prop_y to Pair(y,PropertyType.Integer),
            R.string.prop_asset_id to Pair(assetId,PropertyType.Integer),
            R.string.prop_date_loaded_to_gis to Pair(
                dateLoadedToGis,PropertyType.Long
            ),
            /* Ecological Districts */
            R.string.prop_ecological_code to Pair(
                ecologicalCode,PropertyType.String
            ),
//            R.string.prop_ecological_dist to Pair(
//                ecologicalDist,PropertyType.String
//            ),
//            R.string.prop_ecological_reg to Pair(
//                ecologicalReg,PropertyType.String
//            ),
            /* Ecosystem Management */
//            R.string.prop_prescription_id to Pair(
//                prescriptionId,PropertyType.Integer
//            ),
//            R.string.prop_prescription_name to Pair(
//                prescriptionName,PropertyType.String
//            ),
//            R.string.prop_description to Pair(description,PropertyType.String),
//            R.string.prop_rank to Pair(rank,PropertyType.Double),
            /* Kea Habitat */
//            R.string.prop_kea_presence to Pair(keaPresence,PropertyType.String),
            R.string.prop_source_data to Pair(sourceData,PropertyType.String),
            R.string.prop_edit to Pair(edit,PropertyType.String),
            R.string.prop_edit_notes to Pair(editNotes,PropertyType.String),
//            R.string.prop_scrounge_influenced to Pair(
//                scroungeInfluenced,PropertyType.String
//            ),
            /* NonMigratory Freshwater Fish Dist */
            R.string.prop_location_number to Pair(
                locationNumber,PropertyType.Integer
            ),
            R.string.prop_species to Pair(species,PropertyType.String),
            R.string.prop_vernacular to Pair(vernacular,PropertyType.String),
//            R.string.prop_catchment to Pair(catchment,PropertyType.String),
            R.string.prop_location_doc_gazetted to Pair(
                locationGazetted,PropertyType.String
            ),
            R.string.prop_location_doc_name to Pair(
                locationDocName,PropertyType.String
            ),
            R.string.prop_doc_region to Pair(docRegion,PropertyType.String),
//            R.string.prop_doc_district to Pair(docDistrict,PropertyType.String),
            R.string.prop_regional_Council to Pair(
                regionalCouncil,PropertyType.String
            ),
            R.string.prop_district_Council to Pair(
                districtCouncil,PropertyType.String
            ),
            R.string.prop_spatial_order to Pair(
                spatialOrder,PropertyType.Integer
            ),
            R.string.prop_updated to Pair(updated,PropertyType.String),
            R.string.prop_perimeter to Pair(perimeter,PropertyType.Double),
            /* Freedom Camping */
//            R.string.prop_site_category_type to Pair(
//                siteCategoryType,PropertyType.String
//            ),
            R.string.prop_site_category_code to Pair(
                siteCategoryCode,PropertyType.String
            ),
            R.string.prop_condition_1 to Pair(condition1,PropertyType.String),
            R.string.prop_condition_2 to Pair(condition2,PropertyType.String),
            R.string.prop_condition_3 to Pair(condition3,PropertyType.String),
            R.string.prop_condition_4 to Pair(condition4,PropertyType.String),
            R.string.prop_action_date to Pair(actionDate,PropertyType.Long),
            R.string.prop_functional_location to Pair(
                functionalLocation,PropertyType.String
            ),
            R.string.prop_address to Pair(address,PropertyType.String),
            R.string.prop_location to Pair(location,PropertyType.String),
            R.string.prop_area_phone to Pair(areaPhone,PropertyType.String),
            R.string.prop_notice_number to Pair(
                noticeNumber,PropertyType.Integer
            ),
            R.string.prop_status to Pair(status,PropertyType.String),
            R.string.prop_operations_region to Pair(
                operationsRegion,PropertyType.String
            ),
            R.string.prop_operations_district to Pair(
                operationsDistrict,PropertyType.String
            ),
            /* DoC Huts: nothing new */
            /* Marine Reserves */
            R.string.prop_legislation to Pair(
                legislation,PropertyType.String
            ),
            R.string.prop_section to Pair(section,PropertyType.String),
            /* DoC MTB Locations */
            R.string.prop_difficulty to Pair(difficulty,PropertyType.String),
            R.string.prop_biking_time to Pair(bikingTime,PropertyType.String),
            R.string.prop_mountain_biking_track_web_page to Pair(
                mountainBikingTrackWebPage,PropertyType.WebUrl
            ),
            /* DoC MTB Routes: nothing new */
            /* DOC Offices */
            R.string.prop_full_name_label to Pair(
                fullNameLabel,PropertyType.String /* Actually at end of Json */
            ),
            R.string.prop_acronym to Pair(acronym,PropertyType.String),
            R.string.prop_parent to Pair(parent,PropertyType.Integer),
            R.string.prop_physical_address_line_1 to Pair(
                physicalAddressLine1,PropertyType.String
            ),
            R.string.prop_physical_address_line_2 to Pair(
                physicalAddressLine2,PropertyType.String
            ),
            R.string.prop_physical_address_line_3 to Pair(
                physicalAddressLine3,PropertyType.String
            ),
            R.string.prop_physical_address_line_4 to Pair(
                physicalAddressLine4,PropertyType.String
            ),
            R.string.prop_postal_address_line_1 to Pair(
                postalAddressLine1,PropertyType.String
            ),
            R.string.prop_postal_address_line_2 to Pair(
                postalAddressLine2,PropertyType.String
            ),
            R.string.prop_postal_address_line_3 to Pair(
                postalAddressLine3,PropertyType.String
            ),
            R.string.prop_postal_address_line_4 to Pair(
                postalAddressLine4,PropertyType.String
            ),
//            R.string.prop_phone_number to Pair(phoneNumber,PropertyType.String),
            R.string.prop_vpn to Pair(vpn,PropertyType.String),
            R.string.prop_fax_number to Pair(faxNumber,PropertyType.String),
            R.string.prop_fax_vpn to Pair(faxVpn,PropertyType.String),
            R.string.prop_manager_id to Pair(managerId,PropertyType.String),
//            R.string.prop_email to Pair(email,PropertyType.String),
            R.string.prop_opening_hour to Pair(openingHour,PropertyType.String),
            R.string.prop_opening_minute to Pair(
                openingMinute,PropertyType.String
            ),
            R.string.prop_closing_hour to Pair(closingHour,PropertyType.String),
            R.string.prop_closing_minute to Pair(
                closingMinute,PropertyType.String
            ),
            R.string.prop_modified_by to Pair(modifiedBy,PropertyType.String),
            R.string.prop_date_modified to Pair(
                dateModified,PropertyType.Long
            ),
            R.string.prop_is_active to Pair(isActive,PropertyType.Integer),
            R.string.prop_code to Pair(code,PropertyType.String),
            R.string.prop_maori_name to Pair(maoriName,PropertyType.String),
            R.string.prop_northing to Pair(northing,PropertyType.Double),
            R.string.prop_easting to Pair(easting,PropertyType.Double),
            R.string.prop_town to Pair(town,PropertyType.String),
            /* Pub Cons Land */
            R.string.prop_start_date to Pair(startDateStr,PropertyType.String),
            R.string.prop_overlays to Pair(overlays,PropertyType.String),
            R.string.prop_private_ownership to Pair(
                privateOwnership,PropertyType.String
            ),
            R.string.prop_classified to Pair(classified,PropertyType.String),
            /* Ops Districts */
//            R.string.district_name to Pair(districtName,PropertyType.String),
            R.string.district_code to Pair(districtCode,PropertyType.String),
//            R.string.region_name to Pair(regionName,PropertyType.String),
//            R.string.region_code to Pair(regionCode,PropertyType.String),
            /* Ops Regions: nothing new */
            /* Rec Hunt Perm Areas */
            R.string.prop_created_date to Pair(createdDate,PropertyType.Long),
            R.string.prop_modified_date to Pair(
                modifiedDate,PropertyType.Long
            ),
            R.string.prop_conservancy to Pair(conservancy,PropertyType.String),
//            R.string.prop_permit_area to Pair(permitArea,PropertyType.String),
            R.string.prop_block_id to Pair(blockId,PropertyType.Integer),
//            R.string.prop_hunt_block_name to Pair(
//                huntBlockName,PropertyType.String
//            ),
            R.string.prop_block_type to Pair(blockType,PropertyType.String),
            R.string.prop_hunt_status to Pair(huntStatus,PropertyType.String),
            R.string.prop_date_conditions to Pair(
                dateConditions,PropertyType.String
            ),
            R.string.prop_access_conditions to Pair(
                accessConditions,PropertyType.String
            ),
            R.string.prop_dog_conditions to Pair(
                dogConditions,PropertyType.String
            ),
            R.string.prop_special_conditions to Pair(
                specialConditions,PropertyType.String
            ),
            R.string.prop_hectares to Pair(hectares,PropertyType.Double),
            R.string.prop_scale to Pair(scale,PropertyType.Integer),
            /* DOC Sanctuaries: nothing new */
            /* DOC Roads | Tracks */
//            R.string.prop_tech_object_name to Pair(
//                techObjectName,PropertyType.String
//            ),
            R.string.prop_floc_id to Pair(flocId,PropertyType.String),
            R.string.prop_equipment_id to Pair(
                equipmentId,PropertyType.Integer
            ),
            R.string.prop_object_type to Pair(objectType,PropertyType.String),
            R.string.prop_object_type_key to Pair(
                objectTypeKey,PropertyType.String
            ),
            R.string.prop_sub_object_type to Pair(
                subObjectType,PropertyType.String
            ),
            R.string.prop_sub_object_type_key to Pair(
                subObjectTypeKey,PropertyType.String
            ),
            R.string.prop_floc_cat to Pair(flocCat,PropertyType.String),
            R.string.prop_floc_cat_string to Pair(
                flocCatString,PropertyType.String
            ),
            R.string.prop_equip_cat to Pair(equipCat,PropertyType.String),
            R.string.prop_equip_cat_string to Pair(
                equipCatString,PropertyType.String
            ),
            R.string.prop_data_capture_accuracy to Pair(
                dataCaptureAccuracy,PropertyType.Integer
            ),
            R.string.prop_data_capture_accuracy_string to Pair(
                dataCaptureAccuracyStr,PropertyType.String
            ),
            R.string.prop_gis_status to Pair(gisStatus,PropertyType.String),
            R.string.prop_gis_status_string to Pair(
                gisStatusString,PropertyType.String
            ),
//            R.string.prop_global_id to Pair(
//                charGlobalId.toString(),PropertyType.String
//            ),
            R.string.prop_char_name_1 to Pair(charName1,PropertyType.String),
            R.string.prop_char_value_1 to Pair(charValue1,PropertyType.String),
//            R.string.prop_char_name_2 to Pair(charName2,PropertyType.String),
//            R.string.prop_char_value_2 to Pair(charValue2,PropertyType.String),
            R.string.prop_char_name_3 to Pair(charName3,PropertyType.String),
            R.string.prop_char_value_3 to Pair(charValue3,PropertyType.String),
            R.string.prop_char_name_4 to Pair(charName4,PropertyType.String),
            R.string.prop_char_value_4 to Pair(charValue4,PropertyType.String),
            R.string.prop_char_name_5 to Pair(charName5,PropertyType.String),
            R.string.prop_char_value_5 to Pair(charValue5,PropertyType.String),
            R.string.prop_char_name_6 to Pair(charName6,PropertyType.String),
            R.string.prop_char_value_6 to Pair(charValue6,PropertyType.String),
            R.string.prop_char_name_7 to Pair(charName7,PropertyType.String),
            R.string.prop_char_value_7 to Pair(charValue7,PropertyType.String),
            R.string.prop_char_name_8 to Pair(charName8,PropertyType.String),
            R.string.prop_char_value_8 to Pair(charValue8,PropertyType.String),
            R.string.prop_char_name_9 to Pair(charName9,PropertyType.String),
            R.string.prop_char_value_9 to Pair(charValue9,PropertyType.String),
            R.string.prop_char_name_10 to Pair(charName10,PropertyType.String),
            R.string.prop_char_value_10 to Pair(
                charValue10,PropertyType.String
            ),
            R.string.prop_char_name_11 to Pair(charName11,PropertyType.String),
            R.string.prop_char_value_11 to Pair(
                charValue11,PropertyType.String
            ),
            // sortField
            R.string.prop_created_date_editor_tracking to Pair(
                createdDateEditorTracking, PropertyType.Long
            ),
            R.string.prop_last_updated_date_editor_track to Pair(
                lastUpdatedDateEditorTrack,PropertyType.Long
            ),

            /* Walk Tramp Exp Locations */
            R.string.prop_completion_time to Pair(
                completionTime,PropertyType.String
            ),
            R.string.prop_walking_and_tramping_web_page to Pair(
                walkingAndTrampingWebPage,PropertyType.WebUrl
            ),
            /* Walk Tramp Exp Routes: nothing new */
            /* Threatened Invertebrates */
//            R.string.prop_common_name to Pair(commonName1,PropertyType.String),
            R.string.prop_scientific_name to Pair(
                scientificName1,PropertyType.String
            ),
//            R.string.prop_umbrella_category to Pair(
//                umbrellaCat,PropertyType.String
//            ),
            R.string.prop_threat_class to Pair(threatClass,PropertyType.String),
            R.string.prop_northing to Pair(northings,PropertyType.Double),
            R.string.prop_easting to Pair(eastings,PropertyType.Double),
            R.string.prop_locality to Pair(locality,PropertyType.String),
            R.string.prop_number to Pair(number,PropertyType.Integer),
            R.string.prop_habitat to Pair(habitat,PropertyType.String),
            R.string.prop_original_ref to Pair(originalRef,PropertyType.String),
            R.string.prop_data_source to Pair(dataSource,PropertyType.String),
            R.string.prop_num_of_indiv to Pair(numOfIndiv,PropertyType.Integer),
            R.string.prop_certainty_of_loc to Pair(
                certaintyOfLocation,PropertyType.String
            ),
            R.string.prop_date to Pair(date,PropertyType.String),
            R.string.prop_threat_assess_notes to Pair(
                threatAssessmentNotes,PropertyType.String
            ),
            R.string.prop_former_name to Pair(formerName,PropertyType.String),
            R.string.prop_family to Pair(family,PropertyType.String),
            R.string.prop_group_a to Pair(groupA,PropertyType.String),
            R.string.prop_comments to Pair(comments,PropertyType.String),
            R.string.prop_bmi_id to Pair(bmiId,PropertyType.String)
        )
    }
}