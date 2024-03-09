package com.van.lily.access.define;

public interface SecurityConstants {

    String API = "/auth";

    // 白名单
    String[] WHITE_LIST = new String[] {
        API + "/local",
        API + "/register",
    };
}
