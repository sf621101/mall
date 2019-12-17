package com.macro.mall.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.*;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 * Created by macro on 2018/4/26.
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{}",token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username =  claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public static void main(String[] args) {
        File files[]=FileUtil.ls("C:\\Users\\sf621\\Desktop\\mall\\images\\");
        String sts=
                "1868030," +
                "1868042," +
                "2172342," +
                "2172463," +
                "2172484," +
                "2172492," +
                "2172507," +
                "2172809," +
                "2173097," +
                "2225840," +
                "2230897," +
                "2231034," +
                "2231062," +
                "2268220," +
                "2268296," +
                "2268341," +
                "2268362," +
                "2268388," +
                "2268405," +
                "2268418," +
                "2268443," +
                "2269416," +
                "2287417," +
                "2287425," +
                "2287436," +
                "2287442," +
                "2287456," +
                "2287463," +
                "2287467," +
                "2287474," +
                "2287479," +
                "2287485," +
                "2287490," +
                "2412803," +
                "2441284," +
                "2441399," +
                "2441404," +
                "2444620," +
                "2444636," +
                "2444696," +
                "2444732," +
                "2444772," +
                "2444777," +
                "2444815," +
                "2444855," +
                "2444946," +
                "2453113," +
                "2453143," +
                "2453168," +
                "2471375," +
                "2471382," +
                "2471384," +
                "2471388," +
                "2500402," +
                "2501470," +
                "2501473," +
                "2501478," +
                "1853181," +
                "1853240," +
                "1855608," +
                "1855628," +
                "1855713," +
                "1855733," +
                "1855821," +
                "1855855," +
                "1855878," +
                "1855897," +
                "1865005," +
                "1865032," +
                "1865043," +
                "1865063," +
                "1865076," +
                "1865084," +
                "1865832," +
                "1865879," +
                "1865968," +
                "1865983," +
                "1867938," +
                "1867970," +
                "1867981," +
                "1867998," +
                "1870150," +
                "1870171," +
                "1870323," +
                "2172385," +
                "2172420," +
                "2172470," +
                "2172478," +
                "2172524," +
                "2172569," +
                "2172582," +
                "2172612," +
                "2172630," +
                "2172637," +
                "2172652," +
                "2172762," +
                "2172778," +
                "2172798," +
                "2172814," +
                "2172819," +
                "2172821," +
                "2172835," +
                "2172844," +
                "2172861," +
                "2172872," +
                "2172877," +
                "2172992," +
                "2172996," +
                "2172999," +
                "2173006," +
                "2173012," +
                "2173016," +
                "2173021," +
                "2173028," +
                "2173034," +
                "2173045," +
                "2173059," +
                "2173142," +
                "2213280," +
                "2213330," +
                "2213398," +
                "2214157," +
                "2214203," +
                "2214270," +
                "2214336," +
                "2214807," +
                "2214866," +
                "2214945," +
                "2214979," +
                "2217427," +
                "2217537," +
                "2217578," +
                "2218288," +
                "2218314," +
                "2218351," +
                "2224549," +
                "2224615," +
                "2224680," +
                "2225835," +
                "2225893," +
                "2225912," +
                "2230856," +
                "2230865," +
                "2230934," +
                "2230969," +
                "2231771," +
                "2231807," +
                "2231945," +
                "2232006," +
                "2232069," +
                "2232165," +
                "2287493," +
                "2377231," +
                "2379177," +
                "2379284," +
                "2379290," +
                "2379295," +
                "2400303," +
                "2401910," +
                "2402905," +
                "2410846," +
                "2410862," +
                "2410867," +
                "2410875," +
                "2410884," +
                "2410894," +
                "2410901," +
                "2410904," +
                "2410913," +
                "2426183," +
                "2426591," +
                "2431264," +
                "2431270," +
                "2431359," +
                "2441058," +
                "2441110," +
                "2441347," +
                "2442882," +
                "2442949," +
                "2453233," +
                "2469400," +
                "2472207," +
                "2473899," +
                "2473912," +
                "2473923," +
                "2477107," +
                "2495434," +
                "2495444," +
                "2497423," +
                "2500382," +
                "2500393," +
                "2500412," +
                "2500418," +
                "2500783," +
                "2500798," +
                "2500818," +
                "2500852," +
                "2501430," +
                "2501443," +
                "2501448," +
                "2501459," +
                "2501484," +
                "2501489," +
                "2501494," +
                "2511940," +
                "2511959," +
                "2512904," +
                "2512920," +
                "2522827," +
                "2522853," +
                "2522870," +
                "2570804," +
                "2570876," +
                "2571866," +
                "2572257," +
                "2572270," +
                "2572291," +
                "2580600," +
                "2580607," +
                "2580622," +
                "2581202," +
                "2594464," +
                "2594501," +
                "2594538," +
                "2594550," +
                "2595337," +
                "2595348," +
                "2595354," +
                "2596651," +
                "2596657," +
                "2596665," +
                "2596682," +
                "2596691," +
                "2596724," +
                "2596737," +
                "2596744," +
                "2596763," +
                "2598116," +
                "2598147," +
                "2598162," +
                "2598188," +
                "2598205," +
                "2598230," +
                "2655637," +
                "2655663," +
                "2218241," +
                "2218441," +
                "2223891," +
                "2224497," +
                "2232738";
        String str[]=sts.split(",");
       List<String > list= CollectionUtils.arrayToList(str);
       List<String > fileNames=new ArrayList<>();
        for(File f: files){
                fileNames.add(f.getName().replace(".jpg",""));
        }


        for(String s:list){
           if(!fileNames.contains(s)){
               System.out.println("'"+s+"',");
           }
        }
    }

}
