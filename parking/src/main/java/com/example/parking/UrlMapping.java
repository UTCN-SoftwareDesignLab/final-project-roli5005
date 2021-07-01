package com.example.parking;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ENTITY = "/{id}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String PARKING = API_PATH + "/parking";
    public static final String RESERVATIONS = API_PATH + "/reservations";
    public static final String CLIENT_RESERVATIONS = "/clients";
    public static final String USERS = API_PATH + "/users";

    public static final String NOTIFICATION = USERS + "/notification";
    public static final String REPORT = "/report";
    public static final String PAYMENT = RESERVATIONS + "/payment";
}
