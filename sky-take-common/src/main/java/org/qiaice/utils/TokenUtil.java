package org.qiaice.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.qiaice.exception.InvalidTimeException;
import org.qiaice.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TokenUtil {

    private TokenUtil() {
    }

    // 加密/解密 密钥
    private static String key;

    @Value(value = "${token.key}")
    private void setKey(String key) {
        TokenUtil.key = key;
    }

    // token 签发者
    private static String iss;

    @Value(value = "${token.iss}")
    private void setIss(String iss) {
        TokenUtil.iss = iss;
    }

    // token 主题
    private static String sub;

    @Value(value = "${token.sub}")
    private void setSub(String sub) {
        TokenUtil.sub = sub;
    }

    // token 过期时间
    private static String exp;

    @Value(value = "${token.exp}")
    private void setExp(String exp) {
        TokenUtil.exp = exp;
    }

    // token 过期时间的正则表达式
    private static final Pattern EXP_PATTERN = Pattern.compile("(\\d+)([mshd]{1,2})?");

    /**
     * 使用自定义负载创建 token
     *
     * @param payload 自定义负载
     * @return token
     */
    public static String createToken(Map<String, Object> payload) {
        return createToken(Map.of("typ", "JWT"), payload);
    }

    /**
     * 使用自定义头部和自定义负载创建 token
     *
     * @param header 自定义头部
     * @param payload 自定义负载
     * @return token
     */
    public static String createToken(Map<String, Object> header, Map<String, Object> payload) {
        return Jwts.builder()
                .header()
                .add(header)
                .and()
                .claims(payload)
                .id(UUID.randomUUID().toString().replace("-", ""))
                .issuer(iss)
                .subject(sub)
                .issuedAt(convert2Date("0"))
                .expiration(convert2Date(exp))
                .signWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * 解析字符串格式的过期时间，并以 Date 对象返回
     *
     * @param exp 字符串格式的过期时间
     * @return Date 对象格式的过期时间
     */
    private static Date convert2Date(String exp) {
        Matcher matcher = EXP_PATTERN.matcher(exp);
        if (!matcher.matches()) throw new InvalidTimeException("无效的 token 过期时间: " + exp);
        Calendar calendar = Calendar.getInstance();
        calendar.add(switch (matcher.group(2)) {
            case "s" -> Calendar.SECOND;
            case "m" -> Calendar.MINUTE;
            case "h" -> Calendar.HOUR;
            case "d" -> Calendar.DAY_OF_YEAR;
            case null, default -> Calendar.MILLISECOND;
        }, Integer.parseInt(matcher.group(1)));
        return calendar.getTime();
    }

    /**
     * 解析 token 从而得知该 token 是否有效
     *
     * @param token token
     */
    public static void parseToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("token 已过期");
        } catch (SignatureException | MalformedJwtException e) {
            throw new InvalidTokenException("token 被篡改");
        }
    }
}
