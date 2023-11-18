package com.project.utils;

public class Messages {

    private Messages(){}

    public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation";
    public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with id %s";
    public static final String NOT_FOUND_USER_MESSAGE_PARAM = "Error: User not found with %s %s";

    public static final String ALREADY_REGISTER_USER_EMAIL ="Error: User with email %s is already registered";
    public static final String ALREADY_REGISTER_USER_PLATE ="Error: User with plate %s is already registered";
    public static final String ALREADY_REGISTER_USER_PHONE_NUMBER ="Error: User with phone number %s is already registered";

    public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
    public static final String ROLE_ALREADY_EXIST = "Role already exist in DB";
    public static final String USER_DELETE = "%s is deleted successfully";
}
