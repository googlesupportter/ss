package com.vm.shadowsocks.tunnel.shadowsocks;

import android.net.Uri;
import android.util.Base64;

import com.vm.shadowsocks.tunnel.Config;
import com.vm.shadowsocks.utils.L;

import java.net.InetSocketAddress;

/**
 * @Copyright © 2018 sanbo Inc. All rights reserved.
 * @Description: ss地址解析类
 * @Version: 1.0
 * @Create: 2018/08/03 16:52:11
 * @Author: sanbo
 */
public class ShadowsocksConfig extends Config {
    //密码方式
    public String EncryptMethod;
    //密码
    public String Password;

    //解析ss地址
    //default ss addr:   ss://String(Base64(byte(ASCII)))
    public static ShadowsocksConfig parse(String proxyInfo) throws Exception {
        ShadowsocksConfig config = new ShadowsocksConfig();
        Uri uri = Uri.parse(proxyInfo);
        if (uri.getPort() == -1) {
            String host = uri.getHost();
            proxyInfo = "ss://" + new String(Base64.decode(host.getBytes("ASCII"), Base64.DEFAULT));
            uri = Uri.parse(proxyInfo);
        }
        //ss://加密方式:密码@域名:端口
        L.i("ShadowsocksConfig uri:" + uri.toString());

        String userInfoString = uri.getUserInfo();
        //加密方式:密码
        L.i("ShadowsocksConfig userInfoString:" + userInfoString);
        if (userInfoString != null) {
            String[] userStrings = userInfoString.split(":");
            config.EncryptMethod = userStrings[0];
            if (userStrings.length >= 2) {
                config.Password = userStrings[1];
            }
        }
        if (!CryptFactory.isCipherExisted(config.EncryptMethod)) {
            throw new Exception(String.format("Method: %s does not support", config.EncryptMethod));
        }
        //ss://加密方式:密码@域名:端口
        L.i("ShadowsocksConfig InetSocketAddress uri:" + uri.toString());
        config.ServerAddress = new InetSocketAddress(uri.getHost(), uri.getPort());// 域名,端口
        return config;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return this.toString().equals(o.toString());
    }

    @Override
    public String toString() {
        return String.format("ss://%s:%s@%s", EncryptMethod, Password, ServerAddress);
    }
}
