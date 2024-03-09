package com.van.lily.access.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.van.lily.access.define.CacheConstants;
import com.van.lily.access.model.SecurityUserDetail;
import com.van.lily.access.model.ViewSecurityUserDetail;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Component
public class TokenJwtService {
    // 令牌秘钥
    private static final String SECRET = "VcrTing";

    final static String KEY_ID = "id";
    final static String KEY_ENTITY_TYPE = "entity_type";

    /**
    * 计算 过期 时间
    */
    public static Date expireTime(int minute) {
        Calendar ex = Calendar.getInstance();
        ex.add(Calendar.MINUTE, minute);
        return ex.getTime();
    }

    /**
    * 解密
    */
    DecodedJWT decodeJWT(String jwt) {
        if (jwt == null) return null;
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        return verifier.verify(jwt);
    }
    public ViewSecurityUserDetail decode(String jwt) {
        DecodedJWT dj = decodeJWT(jwt);
        if (dj == null) return null;
        Claim cID = dj.getClaim(KEY_ID);
        Claim cType = dj.getClaim(KEY_ENTITY_TYPE);
        return (cID == null || cType == null) ? null :
                ViewSecurityUserDetail.init(cID.asLong(), cType.asString());
    }

    /**
    * 加密
    */
    String encode(Long id, String typed) {
        HashMap<String, Object> map = new HashMap<>();
        return JWT.create().withHeader(map)
                .withClaim(KEY_ID, id)
                .withClaim(KEY_ENTITY_TYPE, typed)
                .withExpiresAt(expireTime(CacheConstants.AUTH_EXPIRE_MINUET))
                .sign(Algorithm.HMAC256(SECRET));
    }
    public String encode(ViewSecurityUserDetail view) {
        if (view == null || view.getEntityType() == null) return null;
        return encode(view.getId(), view.getEntityType().getValue());
    }
    public String encode(SecurityUserDetail src) {
        if (src == null || src.getEntityType() == null) return null;
        return encode(src.getId(), src.getEntityType().getValue());
    }
}
