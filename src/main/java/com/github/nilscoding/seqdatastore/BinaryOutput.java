package com.github.nilscoding.seqdatastore;

import java.io.OutputStream;
import java.util.Date;

/**
 * Binary output, write methods also write MagicMarker byte and do not throw exceptions
 * @author NilsCoding
 */
public class BinaryOutput {
    
    protected final OutputStream stream;
    protected final boolean flush;
    
    /**
     * Creates a new binary output for writing to the given output stream
     * @param outStream     output stream to write to
     */
    public BinaryOutput(OutputStream outStream) {
        this.stream = outStream;
        this.flush = true;
    }
    
    /**
     * Creates a new binary output for writing to the given output stream and flushing after each logical write
     * @param outStream output stream
     * @param flush     true to flush after logic write, false otherwise
     */
    public BinaryOutput(OutputStream outStream, boolean flush) {
        this.stream = outStream;
        this.flush = flush;
    }
    
    /**
     * Writes an int value
     * @param i int to write
     * @return  this instance
     */
    public BinaryOutput writeInt(int i) {
        try {
            this.stream.write(MagicMarker.B_INT);
            this.stream.write(ByteConversion.fromInt(i));
            if (this.flush) {
                this.stream.flush();
            }
        } catch (Exception ex) {
        }
        return this;
    }
    
    /**
     * Writes a long value
     * @param l long value to write
     * @return  this instance
     */
    public BinaryOutput writeLong(long l) {
        try {
            this.stream.write(MagicMarker.B_LONG);
            this.stream.write(ByteConversion.fromLong(l));
            if (this.flush) {
                this.stream.flush();
            }
        } catch (Exception ex) {
        }
        return this;
    }
    
    /**
     * Writes a double value
     * @param d double value to write
     * @return  this instance
     */
    public BinaryOutput writeDouble(double d) {
        try {
            this.stream.write(MagicMarker.B_DOUBLE);
            this.stream.write(ByteConversion.fromDouble(d));
            if (this.flush) {
                this.stream.flush();
            }
        } catch (Exception ex) {
        }
        return this;
    }
    
    /**
     * Writes a Date value
     * @param d Date value to write
     * @return  this instance
     */
    public BinaryOutput writeDate(Date d) {
        try {
            if (d == null) {
                this.stream.write(MagicMarker.B_DATE_NULL);
            } else {
                this.stream.write(MagicMarker.B_DATE);
                this.stream.write(ByteConversion.fromLong(d.getTime()));
            }
            if (this.flush) {
                this.stream.flush();
            }
        } catch (Exception ex) {
        }
        return this;
    }
    
    /**
     * Writes byte data
     * @param bytes byte data to write
     * @return  this instance
     */
    public BinaryOutput writeBytes(byte[] bytes) {
        try {
            if (bytes == null) {
                this.stream.write(MagicMarker.B_BYTES_NULL);
            } else {
                this.stream.write(MagicMarker.B_BYTES);
                this.stream.write(ByteConversion.fromInt(bytes.length));
                if (bytes.length > 0) {
                    this.stream.write(bytes);
                }
            }
            if (this.flush) {
                this.stream.flush();
            }
        } catch (Exception ex) {
        }
        return this;
    }
    
    /**
     * Writes character data
     * @param ch    character data to write
     * @return  this instance
     */
    public BinaryOutput writeChars(char[] ch) {
        try {
            if (ch == null) {
                this.stream.write(MagicMarker.B_CHARS_NULL);
            } else {
                this.stream.write(MagicMarker.B_CHARS);
                this.stream.write(ByteConversion.fromInt(ch.length));
                if (ch.length > 0) {
                    for (int i = 0; i < ch.length; i++) {
                        this.stream.write(ByteConversion.fromShort((short)ch[i]));
                    }
                }
            }
            if (this.flush) {
                this.stream.flush();
            }
        } catch (Exception ex) {
        }
        return this;
    }
    
    /**
     * Writes String data
     * @param s String data to write
     * @return  this instance
     */
    public BinaryOutput writeString(String s) {
        try {
            if (s == null) {
                this.stream.write(MagicMarker.B_STRING_NULL);
            } else {
                this.stream.write(MagicMarker.B_STRING);
                char[] ch = s.toCharArray();
                this.stream.write(ByteConversion.fromInt(ch.length));
                if (ch.length > 0) {
                    for (int i = 0; i < ch.length; i++) {
                        this.stream.write(ByteConversion.fromShort((short)ch[i]));
                    }
                }
            }
            if (this.flush) {
                this.stream.flush();
            }
        } catch (Exception ex) {
        }
        return this;
    }
    
    /**
     * Writes custom data with the int length marker
     * @param data  data to write
     * @return  this instance
     */
    public BinaryOutput writeCustomInt(byte[] data) {
        try {
            if ((data == null) || (data.length == 0)) {
                this.stream.write(MagicMarker.B_CUSTOM_INT);
                this.stream.write(ByteConversion.ZERO_INT);
            } else {
                this.stream.write(MagicMarker.B_CUSTOM_INT);
                this.stream.write(ByteConversion.fromInt(data.length));
                this.stream.write(data);
            }
            if (this.flush) {
                this.stream.flush();
            }
        } catch (Exception ex) {
        }
        return this;
    }
    
    /**
     * Writes custom data with the long length marker
     * @param data  data to write
     * @return  this instance
     */
    public BinaryOutput writeCustomLong(byte[] ... data) {
        try {
            if ((data == null) || (data.length == 0)) {
                this.stream.write(MagicMarker.B_CUSTOM_LONG);
                this.stream.write(ByteConversion.ZERO_LONG);
            } else {
                this.stream.write(MagicMarker.B_CUSTOM_LONG);
                long tmpLen = 0;
                for (byte[] d : data) {
                    if (d != null) {
                        tmpLen += (long)d.length;
                    }
                }
                if (tmpLen == 0) {
                    this.stream.write(MagicMarker.B_CUSTOM_LONG);
                    this.stream.write(ByteConversion.ZERO_LONG);
                } else {
                    this.stream.write(ByteConversion.fromLong(tmpLen));
                    for (byte[] d : data) {
                        if (d != null) {
                            this.stream.write(d);
                        }
                    }
                }
            }
            if (this.flush) {
                this.stream.flush();
            }
        } catch (Exception ex) {
        }
        return this;
    }
    
    // Remark: raw writing would be useful if the stream supports a seek method
    //   which would allow some fancy custom data writing implementations
//    /**
//     * Writes raw data to the underlaying stream, without adding any marker byte
//     * @param data  data to write
//     * @return  this instance
//     */
//    public BinaryOutput rawWrite(byte[] data) {
//        if ((data == null) || (data.length == 0)) {
//            return this;
//        }
//        try {
//            this.stream.write(data);
//            if (this.flush) {
//                this.stream.flush();
//            }
//        } catch (Exception ex) {
//        }
//        return this;
//    }
    
}
