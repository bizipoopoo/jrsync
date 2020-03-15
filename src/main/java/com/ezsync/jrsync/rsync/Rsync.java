package com.ezsync.jrsync.rsync;

/**
 * @ClassName Rsync
 * @Description TODO
 * @Author chenwj
 * @Date 2020/3/2 14:15
 * @Version 1.0
 **/

public class Rsync {

    public static final int MOD_ADLER = 65521;

    public static final int TRUNCK_SIZE_1024 = 1024;

    public static final int TRUNCK_SIZE_512 = 512;

    public static final int TRUNCK_SIZE_256 = 256;

    public static final int TRUNCK_SIZE_125 = 125;

    public static final int TRUNCK_SIZE_8 = 8;

    public static int adler32(byte[] data, int offset, int length) {
        int a = 0;
        int b = 0;

        for (int i = offset, limit = i + length; i < limit; i++) {
            a += data[i] & 0xff;
            a %= MOD_ADLER;
            b += a;
            b %= MOD_ADLER;
        }

        return b << 16 | a;
    }

    public static int adler32(byte[] data, int length) {
        return adler32(data, 0, length);
    }

    /**
     * @param data
     * @return
     */
    public static int adler32(byte[] data) {
        return adler32(data, 0, data.length);
    }

    /**
     * @param oldAdler32
     * @param preByte
     * @param nextByte
     * @return
     */
    public static int nextAdler32(int oldAdler32, byte preByte, byte nextByte, int size) {
        int a = oldAdler32 & 0xffff;
        int b = (oldAdler32 >>> 16) & 0xffff;

        int an = a - preByte + nextByte;

        int bn = b - (preByte) * size+ an;

        return (bn << 16) + (an & 0xffff);
    }

}
