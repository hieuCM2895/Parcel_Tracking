package com.example.parceltracking.constants;

public class ErrorMessages {
    public static final String GUEST_NOT_FOUND_CODE = "guest-404";
    public static final String GUEST_NOT_FOUND_MSG = "Guest not found";

    public static final String PARCEL_NOT_FOUND_CODE = "parcel-404";
    public static final String PARCEL_NOT_FOUND_MSG = "Parcel not found";

    public static final String PARCEL_FOR_CHECKED_OUT_GUEST_CODE = "parcel-4001";
    public static final String PARCEL_FOR_CHECKED_OUT_GUEST_MSG = "Cannot receive parcel for checked-out guest";

    public static final String UNCLAIMED_PARCEL_CODE = "parcel-4002";
    public static final String UNCLAIMED_PARCEL_MSG = "Guest has unclaimed parcels";

    public static final String PARCEL_OWNERSHIP_CODE = "parcel-4003";
    public static final String PARCEL_OWNERSHIP_MSG = "Parcel does not belong to the specified guest";

    public static final String SYSTEM_ERROR_CODE = "system-500";
    public static final String SYSTEM_ERROR_MSG = "Internal server error";
}
