package com.example.maoyan.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *  * 流水号生成器，，
 *  * @生成规则 1位类型+17位时间戳+14位(用户id加密&随机数)
 * <p>
 *  
 */
public class OrderCodeFactory {

    private static final String USER_CODE = "U";

    private static final String ARTICLE_ORDER = "A";

    private static final String BOOK_ORDER = "B";

    private static final String COLLECTBOOK_ORDER = "CB";
    /**
     * 随即编码
     */
    private static final int[] r = new int[]{7, 9, 6, 2, 8, 1, 3, 0, 5, 4};
    /**
     * 用户id和随机数总长度
     */
    private static final int maxLength = 5;

    /**
     *      * 更具id进行加密+加随机数组成固定长度编码
     *      
     */
    private static String toCode(Long id) {
        String idStr = id.toString();
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1; i >= 0; i--) {
            idsbs.append(r[idStr.charAt(i) - '0']);
        }
        return idsbs.append(getRandom(maxLength - idStr.length())).toString();
    }

    /**
     *      * 生成时间戳
     *      
     */
    private static String getDateTime() {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     *      * 生成固定长度随机码
     *      * @param n    长度
     *      
     */
    private static long getRandom(long n) {
        long min = 1, max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min;
        return rangeLong;
    }

    /**
     *      * 生成不带类别标头的编码
     *      * @param userId
     *      
     */
    private static synchronized String getCode() {
//        userId = userId == null ? 10000 : userId;
        Long randomNumber = getRandom(maxLength);
        return getDateTime() + toCode(randomNumber);
    }

    /**
     *      * 生成用户流水号编码
     *      * @param userId
     *      
     */
    public static String generateUserID() {
        return USER_CODE + getCode();
    }

    /**
     *      * 生成文章流水号编码
     *      * @param userId
     *      
     */
    public static String generateArticleID() {
        return ARTICLE_ORDER + getCode();
    }

    /**
     *      * 生成图书流水号编码
     *      * @param userId
     *      
     */
    public static String generateBookID() {
        return BOOK_ORDER + getCode();
    }

    /**
     *      * 生成收藏图书流水号编码
     *      * @param userId
     *      
     */
    public static String generateCollectBookID() {
        return COLLECTBOOK_ORDER + getCode();
    }


}
