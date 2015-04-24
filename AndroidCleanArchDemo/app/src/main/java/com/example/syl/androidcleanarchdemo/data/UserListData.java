package com.example.syl.androidcleanarchdemo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyunlong on 4/20/15.
 */
//public class UserListData {
//
//    // Test Data
//    // Singleton
//    private UserListData() {}
//
//    private static List<User> mUserList = new ArrayList<User>();
//
//    static {
//        mUserList.add(new User("1001", "Larry Page 1", "page_1@google.com", "1024", "I am Larry Page 1!"));
//        mUserList.add(new User("1002", "Larry Page 2", "page_2@google.com", "1024", "I am Larry Page 2!"));
//        mUserList.add(new User("1003", "Larry Page 3", "page_3@google.com", "1024", "I am Larry Page 3!"));
//        mUserList.add(new User("1004", "Larry Page 4", "page_4@google.com", "1024", "I am Larry Page 4!"));
//        mUserList.add(new User("1005", "Larry Page 5", "page_5@google.com", "1024", "I am Larry Page 5!"));
//        mUserList.add(new User("1006", "Larry Page 6", "page_6@google.com", "1024", "I am Larry Page 6!"));
//        mUserList.add(new User("1007", "Larry Page 7", "page_7@google.com", "1024", "I am Larry Page 7!"));
//        mUserList.add(new User("1008", "Larry Page 8", "page_8@google.com", "1024", "I am Larry Page 8!"));
//        mUserList.add(new User("1009", "Larry Page 9", "page_9@google.com", "1024", "I am Larry Page 9!"));
//        mUserList.add(new User("1010", "Larry Page 10", "page_10@google.com", "1024", "I am Larry Page 10!"));
//        mUserList.add(new User("1011", "Larry Page 11", "page_11@google.com", "1024", "I am Larry Page 11!"));
//        mUserList.add(new User("1012", "Larry Page 12", "page_12@google.com", "1024", "I am Larry Page 12!"));
//    }
//
//    public static List<User> getUserList() {
//        return mUserList;
//    }
//
//    public static User getUserById(String id) {
//        User user = null;
//
//        for(int i=0; i<mUserList.size(); i++) {
//            if(mUserList.get(i).getId().equals(id)) {
//                user = mUserList.get(i);
//                break;
//            }
//        }
//
//        return user;
//    }
//
////    public static void setmUserList(List<User> mUserList) {
////        UserListData.mUserList = mUserList;
////    }
//}
