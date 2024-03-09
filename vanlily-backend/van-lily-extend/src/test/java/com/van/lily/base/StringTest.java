package com.van.lily.base;


import org.junit.Test;

public class StringTest {

    @Test
    public void stringOne() {
        int num = 33;
        String SFX = "0000";
        String sfx = SFX + num;
        int L = sfx.length();

        System.out.println(sfx);
        String res = sfx.substring(L - SFX.length(), L);
        System.out.println("res = " + res);
    }
}
