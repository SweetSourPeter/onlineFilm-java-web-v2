package edu.hitech.onlinefilm.utils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import edu.hitech.onlinefilm.domain.Customer;


import java.util.concurrent.TimeUnit;

public class CacheUtils {
    private static final ThreadLocal<String> currentToken = new ThreadLocal<>();

    private static final Cache<String, Customer> USER_CACHE = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .build();

    public static void cacheUser(String token,Customer customer){
            USER_CACHE.put(token,customer);
    }

    public static Customer getUser(String token){
         return USER_CACHE.getIfPresent(token);
    }


    public static Customer getCurrentUser(){
            return USER_CACHE.getIfPresent(currentToken.get());

    }

    public static  void removeUser(){
          USER_CACHE.invalidate(currentToken.get());

    }

    public static void storeCurrentToken(String token){
            currentToken.set(token);
    }

}
