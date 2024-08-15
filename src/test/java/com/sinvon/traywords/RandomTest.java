package com.sinvon.traywords;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

public class RandomTest {

    @Test
    public void test() {
        String word = RandomUtil.randomString(RandomUtil.BASE_CHAR.toUpperCase(), 1);
        System.out.println(word);
        System.out.println(word);
        System.out.println(word);
        System.out.println(word);
        System.out.println(word);
    }

    @Test
    public void test2() {
    }
}
