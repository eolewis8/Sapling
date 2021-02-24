package com.example.sapling;

import android.provider.BaseColumns;

public final class UsersInfoContract {
    private UsersInfoContract() {}
    public static class Users implements BaseColumns {
        public static final String TABLE_NAME = "UsersInfo";
        public static final String USER_FIRST_NAME = "first_name";
        public static final String USER_LAST_NAME = "last_name";
        public static final String USER_EMAIL = "email";
        public static final String IS_INSTRUCTOR = "is_instuctor";
    }
}
