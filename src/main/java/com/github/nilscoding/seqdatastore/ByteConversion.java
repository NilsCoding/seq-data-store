package com.github.nilscoding.seqdatastore;

/**
 * Byte conversion
 * @author NilsCoding
 */
public class ByteConversion {
    
    /**
     * the int bytes for zero
     */
    public static final byte[] ZERO_INT = new byte[]{ (byte)0, (byte)0, (byte)0, (byte)0 };
    /**
     * the long bytes for zero
     */
    public static final byte[] ZERO_LONG = new byte[]{ (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0 };
    
    private ByteConversion() { }
    
    /**
     * Converts a short value to byte[]
     * @param s short value
     * @return  byte[] with length 2
     */
    public static byte[] fromShort(short s) {
        byte[] data = new byte[] {
            (byte) (s >> 8),
            (byte) (s)
        };
        return data;
    }
    
    /**
     * Converts a int value to byte[]
     * @param i int value
     * @return  byte[] with length 4
     */
    public static byte[] fromInt(int i) {
        byte[] data = new byte[] {
            (byte) (i >> 24),
            (byte) (i >> 16),
            (byte) (i >> 8),
            (byte) (i)
        };
        return data;
    }
    
    /**
     * Converts a long value to byte[]
     * @param l long value
     * @return  byte[] of length 8
     */
    public static byte[] fromLong(long l) {
        byte[] data = new byte[] {
            (byte) (l >> 56),
            (byte) (l >> 48),
            (byte) (l >> 40),
            (byte) (l >> 32),
            (byte) (l >> 24),
            (byte) (l >> 16),
            (byte) (l >> 8),
            (byte) (l)
        };
        return data;
    }
    
    /**
     * Converts a double value to byte[], using Double.doubleToLongBits(double)
     * @param d double value
     * @return  byte[] of length 8
     */
    public static byte[] fromDouble(double d) {
        long l = Double.doubleToLongBits(d);
        return fromLong(l);
    }
    
    /**
     * Converts a byte[] to short, using 2 bytes
     * @param b byte[]
     * @return  short value, Short.MIN_VALUE if byte[] is invalid
     */
    public static short toShort(byte[] b) {
        if ((b == null) || (b.length < 2)) {
            return Short.MIN_VALUE;
        }
        short s = (short)(b[0] << 8 | b[1]);
        return s;
    }
    
    /**
     * Converts a byte[] to int, using 4 bytes
     * @param b byte[]
     * @return  int value, Integer.MIN_VALUE if byte[] is invalid
     */
    public static int toInt(byte[] b) {
        if ((b == null) || (b.length < 4)) {
            return Integer.MIN_VALUE;
        }
        int i =   (int)(0xff & b[0]) << 24 
                | (int)(0xff & b[1]) << 16 
                | (int)(0xff & b[2]) << 8 
                | (int)(0xff & b[3]);
        return i;
    }
    
    /**
     * Converts a byte[] to long, using 8 bytes
     * @param b byte[]
     * @return  long value, Long.MIN_VALUE if byte[] is invalid
     */
    public static long toLong(byte[] b) {
        if ((b == null) || (b.length < 8)) {
            return Long.MIN_VALUE;
        }
        long l =  (long)(0xff & b[0]) << 56 
                | (long)(0xff & b[1]) << 48 
                | (long)(0xff & b[2]) << 40 
                | (long)(0xff & b[3]) << 32 
                | (long)(0xff & b[4]) << 24 
                | (long)(0xff & b[5]) << 16 
                | (long)(0xff & b[6]) << 8 
                | (long)(0xff & b[7]);
        return l;
    }
    
    /**
     * Converts a byte[] to double, using 8 bytes and Double.longBitsToDouble(long)
     * @param b byte[]
     * @return  double value, Double.NaN if byte[] is invalid
     */
    public static double toDouble(byte[] b) {
        if ((b == null) || (b.length < 8)) {
            return Double.NaN;
        }
        long l = toLong(b);
        return Double.longBitsToDouble(l);
    }
    
}
