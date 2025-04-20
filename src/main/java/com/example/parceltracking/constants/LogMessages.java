package com.example.parceltracking.constants;

public class LogMessages {

    public static final String CHECK_IN_START = "Start check-in for guest ID: %s";
    public static final String CHECK_IN_FINISH = "Check-in finished for guest ID: %s";
    public static final String GUEST_SAVED = "Guest saved with ID: %s";

    public static final String CHECK_OUT_START = "Start check-out for guest ID: %s";
    public static final String GUEST_NOT_FOUND = "Guest with ID %s not found";
    public static final String UNCLAIMED_PARCELS_WARNING = "Guest [%s] has [%d] unclaimed parcels at check-out!";
    public static final String GUEST_CHECK_OUT_SUCCESS = "Guest with ID: %s successfully checked out";
    public static final String GUEST_UNCLAIMED_PARCELS = "Guest has unclaimed parcels";

    public static final String FORGET_RECEIVE_PARCEL_WHEN_GUEST_CHECKED_OUT = "Cannot receive parcel for guest ID %s who has already checked out";
    public static final String PARCEL_RECEIVED = "Parcel [%s] received for guest [%s]";
    public static final String FETCHING_PARCELS = "Fetching parcels for guest ID: %s";
    public static final String PARCEL_NOT_FOUND = "Parcel with ID %s not found";
    public static final String PARCEL_MISMATCH = "Parcel with ID %s does not belong to guest ID %s";
    public static final String PARCEL_PICKED_UP = "Parcel [%s] marked as picked up for guest [%s]";
}
