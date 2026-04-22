package com.object.usercenter;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
public class GlobalTest {

    public static void main(String[] args) {
        System.out.println(IdUtil.fastSimpleUUID());
    }

}
