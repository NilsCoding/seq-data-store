package com.github.nilscoding.seqdatastore;

/**
 * The magic markers
 * @author NilsCoding
 */
public final class MagicMarker {
    
    //
    // Some remarks:
    //   0b..1nnnnn     marks a null value, nnnnn is the same as non-null version, does not apply to custom types
    //   0b01......     next data is a 4-byte int to indicate the length of the following data
    //   0b10......     next data is a 8-byte long to indicate the length of the following data
    //   0b00......     this is a "direct" data type which does not require length-based processing
    //   TYPE and TYPE_NULL differ in both the null marker and the length flag 
    //     (and the _NULL flag doesn't contain the length indicator because it has no data length)
    //
    
    /**
     * type: int
     */
    public static final byte INT            = (byte)0b00000001;
    /**
     * type: long
     */
    public static final byte LONG           = (byte)0b00000010;
    /**
     * type: double
     */
    public static final byte DOUBLE         = (byte)0b00000011;
    /**
     * type: java.util.Date
     */
    public static final byte DATE           = (byte)0b00000100;
    /**
     * type: java.util.Date, value is null
     */
    public static final byte DATE_NULL      = (byte)0b00100100;
    /**
     * type: byte[]
     */
    public static final byte BYTES          = (byte)0b01000101;
    /**
     * type: byte[], value is null
     */
    public static final byte BYTES_NULL     = (byte)0b00100101;
    /**
     * type: char[]
     */
    public static final byte CHARS          = (byte)0b01000110;
    /**
     * type: char[], value is null
     */
    public static final byte CHARS_NULL     = (byte)0b00100110;
    /**
     * type: java.lang.String
     */
    public static final byte STRING         = (byte)0b01000111;
    /**
     * type: java.lang.String, value is null
     */
    public static final byte STRING_NULL    = (byte)0b00100111;
    /**
     * custom type, length marker of type int
     */
    public static final byte CUSTOM_INT     = (byte)0b01000001;
    /**
     * custom type, length marker of type long
     */
    public static final byte CUSTOM_LONG    = (byte)0b10000010;
    /**
     * unknown type
     */
    public static final byte UNKNOWN        = (byte)0b00000000;
    
    /**
     * Marker mask for all data
     */
    public static final byte MARKER_MASK_ALL = (byte)0b11111111;
    /**
     * Marker mask for meta data
     */
    public static final byte MARKER_MASK_META = (byte)0b00000000;
    /**
     * Marker mask for numeric data
     */
    public static final byte[] MARKER_MASK_NUMBERS = new byte[]{ INT, LONG, DOUBLE };
    
    /**
     * writable marker for type int
     */
    public static final byte[] B_INT        = new byte[] { INT };
    /**
     * writable marker for type long
     */
    public static final byte[] B_LONG       = new byte[] { LONG };
    /**
     * writable marker for type double
     */
    public static final byte[] B_DOUBLE     = new byte[] { DOUBLE };
    /**
     * writable marker for type java.util.Date
     */
    public static final byte[] B_DATE       = new byte[] { DATE };
    /**
     * writable marker for type java.util.Date with value null
     */
    public static final byte[] B_DATE_NULL  = new byte[] { DATE_NULL };
    /**
     * writable marker for type byte[]
     */
    public static final byte[] B_BYTES      = new byte[] { BYTES };
    /**
     * writable marker for type byte[] with value null
     */
    public static final byte[] B_BYTES_NULL = new byte[] { BYTES_NULL };
    /**
     * writable marker for type char[]
     */
    public static final byte[] B_CHARS      = new byte[] { CHARS };
    /**
     * writable marker for type char[] with value null
     */
    public static final byte[] B_CHARS_NULL = new byte[] { CHARS_NULL };
    /**
     * writable marker for type java.lang.String
     */
    public static final byte[] B_STRING     = new byte[] { STRING };
    /**
     * writable marker for type java.lang.String with value null
     */
    public static final byte[] B_STRING_NULL= new byte[] { STRING_NULL };
    /**
     * writable marker for custom type with length marker of int
     */
    public static final byte[] B_CUSTOM_INT = new byte[] { CUSTOM_INT };
    /**
     * writable marker for custom type with length marker of long
     */
    public static final byte[] B_CUSTOM_LONG= new byte[] { CUSTOM_LONG };
    
}
