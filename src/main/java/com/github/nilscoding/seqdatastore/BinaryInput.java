package com.github.nilscoding.seqdatastore;

import java.io.InputStream;
import java.util.Date;

/**
 * Binary input, reading data from a stream<br>
 * When using the marker mask, data might be empty and the stream must support skip(n)
 * @author NilsCoding
 */
public class BinaryInput {
    
    protected final InputStream stream;
    protected long relativePosition = 0L;
    protected byte[] markerMask = null;
    
    /**
     * Creates a new BinaryInput using an InputStream
     * @param inStream  input stream
     */
    public BinaryInput(InputStream inStream) {
        this.stream = inStream;
    }

    /**
     * Set a marker mask, see MagicMarker.MARKER_MASK_*
     * @param markerMask    marker mask or null for none
     */
    public void setMarkerMask(byte ... markerMask) {
        this.markerMask = markerMask;
    }

    /**
     * Returns the marker mask
     * @return  marker mask
     */
    public byte[] getMarkerMask() {
        return markerMask;
    }
    
    /**
     * Reads the next input data, consuming the input stream
     * @return  resulting binary input data or null if invalid
     */
    public BinaryInputData read() {
        BinaryInputData result = null;
        try {
            long currentPos = this.relativePosition;
            // read marker
            int marker = this.stream.read();
            this.increasePosition(1);
            if (marker == -1) {
                return null;
            } else {
                byte bMarker = (byte)(marker & 0xFF);
                if (bMarker == MagicMarker.INT) {
                    if (this.isMask(bMarker)) {
                        byte[] data = new byte[4];
                        int dataCount = this.stream.read(data);
                        this.increasePosition(dataCount);
                        if (dataCount == 4) {
                            result = new BinaryInputData(currentPos, bMarker, ByteConversion.toInt(data));
                        } else {
                            result = new BinaryInputData(currentPos, bMarker);
                        }
                    } else {
                        long skipCount = this.stream.skip(4);
                        this.increasePosition(skipCount);
                        result = new BinaryInputData(currentPos, bMarker, null, true);
                    }
                } else if (bMarker == MagicMarker.LONG) {
                    if (this.isMask(bMarker)) {
                        byte[] data = new byte[8];
                        int dataCount = this.stream.read(data);
                        this.increasePosition(dataCount);
                        if (dataCount == 8) {
                            result = new BinaryInputData(currentPos, bMarker, ByteConversion.toLong(data));
                        } else {
                            result = new BinaryInputData(currentPos, bMarker);
                        }
                    } else {
                        long skipCount = this.stream.skip(8);
                        this.increasePosition(skipCount);
                        result = new BinaryInputData(currentPos, bMarker, null, true);
                    }
                } else if (bMarker == MagicMarker.DOUBLE) {
                    if (this.isMask(bMarker)) {
                        byte[] data = new byte[8];
                        int dataCount = this.stream.read(data);
                        this.increasePosition(dataCount);
                        if (dataCount == 8) {
                            result = new BinaryInputData(currentPos, bMarker, ByteConversion.toDouble(data));
                        } else {
                            result = new BinaryInputData(currentPos, bMarker);
                        }
                    } else {
                        long skipCount = this.stream.skip(8);
                        this.increasePosition(skipCount);
                        result = new BinaryInputData(currentPos, bMarker, null, true);
                    }
                } else if (bMarker == MagicMarker.DATE) {
                    if (this.isMask(bMarker)) {
                        byte[] data = new byte[8];
                        int dataCount = this.stream.read(data);
                        this.increasePosition(dataCount);
                        if (dataCount == 8) {
                            long l = ByteConversion.toLong(data);
                            result = new BinaryInputData(currentPos, bMarker, new Date(l));
                        } else {
                            result = new BinaryInputData(currentPos, bMarker);
                        }
                    } else {
                        long skipCount = this.stream.skip(8);
                        this.increasePosition(skipCount);
                        result = new BinaryInputData(currentPos, bMarker, null, true);                        
                    }
                } else if (bMarker == MagicMarker.DATE_NULL) {
                    result = new BinaryInputData(currentPos, bMarker, null, true);
                } else if (bMarker == MagicMarker.BYTES) {
                    byte[] data = new byte[4];
                    int dataCount = this.stream.read(data);
                    this.increasePosition(dataCount);
                    if (dataCount == 4) {
                        int byteLen = ByteConversion.toInt(data);
                        if (this.isMask(bMarker)) {
                            byte[] data2 = new byte[byteLen];
                            int data2Count = this.stream.read(data2);
                            this.increasePosition(data2Count);
                            if (data2Count == byteLen) {
                                result = new BinaryInputData(currentPos, bMarker, data2);
                            } else {
                                result = new BinaryInputData(currentPos, bMarker);
                            }
                        } else {
                            long skipCount = this.stream.skip(byteLen);
                            this.increasePosition(skipCount);
                            result = new BinaryInputData(currentPos, bMarker, null, true);                    
                        }
                    } else {
                        result = new BinaryInputData(currentPos, bMarker);
                    }
                } else if (bMarker == MagicMarker.BYTES_NULL) {
                    result = new BinaryInputData(currentPos, bMarker, null, true);
                } else if (bMarker == MagicMarker.CHARS) {
                    byte[] data = new byte[4];
                    int dataCount = this.stream.read(data);
                    this.increasePosition(dataCount);
                    if (dataCount == 4) {
                        int charLen = ByteConversion.toInt(data);
                        if (this.isMask(bMarker)) {
                            char[] ch = new char[charLen];
                            byte[] buffer = new byte[2];
                            for (int index = 0; index < charLen; index++) {
                                int byteCount = this.stream.read(buffer);
                                this.increasePosition(byteCount);
                                if (byteCount == 2) {
                                    ch[index] = (char)ByteConversion.toShort(data);
                                }
                            }
                            result = new BinaryInputData(currentPos, bMarker, ch);
                        } else {
                            long skipCount = this.stream.skip(charLen * 2);
                            this.increasePosition(skipCount);
                            result = new BinaryInputData(currentPos, bMarker, null, true);
                        }
                    } else {
                        result = new BinaryInputData(currentPos, bMarker);
                    }
                } else if (bMarker == MagicMarker.CHARS_NULL) {
                    result = new BinaryInputData(currentPos, bMarker, null, true);
                } else if (bMarker == MagicMarker.STRING) {
                    byte[] data = new byte[4];
                    int dataCount = this.stream.read(data);
                    this.increasePosition(dataCount);
                    if (dataCount == 4) {
                        int charLen = ByteConversion.toInt(data);
                        if (this.isMask(bMarker)) {
                            char[] ch = new char[charLen];
                            byte[] buffer = new byte[2];
                            for (int index = 0; index < charLen; index++) {
                                int byteCount = this.stream.read(buffer);
                                this.increasePosition(byteCount);
                                if (byteCount == 2) {
                                    ch[index] = (char)ByteConversion.toShort(buffer);
                                }
                            }
                            result = new BinaryInputData(currentPos, bMarker, new String(ch));
                        } else {
                            long skipCount = this.stream.skip(charLen * 2);
                            this.increasePosition(skipCount);
                            result = new BinaryInputData(currentPos, bMarker, null, true);
                        }
                    } else {
                        result = new BinaryInputData(currentPos, bMarker);
                    }
                } else if (bMarker == MagicMarker.STRING_NULL) {
                    result = new BinaryInputData(currentPos, bMarker, null, true);
                } else if (bMarker == MagicMarker.CUSTOM_INT) {
                    byte[] data = new byte[4];
                    int dataCount = this.stream.read(data);
                    this.increasePosition(dataCount);
                    if (dataCount == 4) {
                        int customLen = ByteConversion.toInt(data);
                        if (customLen <= 0) {
                            result = new BinaryInputData(currentPos, bMarker, new byte[0]);
                        } else {
                            if (this.isMask(bMarker)) {
                                byte[] buffer = new byte[customLen];
                                int countBuffer = this.stream.read(buffer);
                                this.increasePosition(countBuffer);
                                if (countBuffer == customLen) {
                                    result = new BinaryInputData(currentPos, bMarker, buffer);
                                } else {
                                    result = new BinaryInputData(currentPos, bMarker, new byte[0]);
                                }
                            } else {
                                long skipCount = this.stream.skip(customLen);
                                this.increasePosition(skipCount);
                                result = new BinaryInputData(currentPos, bMarker, null, true);
                            }
                        }
                    } else {
                        result = new BinaryInputData(currentPos, bMarker, new byte[0]);
                    }
                } else if (bMarker == MagicMarker.CUSTOM_LONG) {
                    byte[] data = new byte[8];
                    int dataCount = this.stream.read(data);
                    this.increasePosition(dataCount);
                    if (dataCount == 8) {
                        long customLen = ByteConversion.toLong(data);
                        if (customLen <= 0) {
                            result = new BinaryInputData(currentPos, bMarker, new byte[0]);
                        } else {
                            // this could be done the same way than reading custom int
                            // but there is probably too many data, so limit the reading here
                            int maxRead = 0;
                            if (customLen < Integer.MAX_VALUE) {
                                maxRead = (int)customLen;
                            } else {
                                maxRead = Integer.MAX_VALUE;
                            }
                            if (this.isMask(bMarker)) {
                                byte[] buffer = new byte[maxRead];
                                int countBuffer = this.stream.read(buffer);
                                this.increasePosition(countBuffer);
                                if (countBuffer == maxRead) {
                                    result = new BinaryInputData(currentPos, bMarker, buffer);
                                } else {
                                    result = new BinaryInputData(currentPos, bMarker, new byte[0]);
                                }
                            } else {
                                long skipCount = this.stream.skip(maxRead);
                                this.increasePosition(skipCount);
                                result = new BinaryInputData(currentPos, bMarker, null, true);   
                            }
                            long skip = customLen - maxRead;
                            if (skip > 0) {
                                long countSkip = this.stream.skip(skip);
                                this.increasePosition(countSkip);
                            }
                        }
                    } else {
                        result = new BinaryInputData(currentPos, bMarker, new byte[0]);
                    }                    
                } else {
                    // unsupported marker
                    result = new BinaryInputData(currentPos, MagicMarker.UNKNOWN);
                }
            }
        } catch (Exception ex) {
        }
        return result;
    }

    /**
     * Returns the relative input stream position
     * @return  relative input stream position
     */
    public long getRelativePosition() {
        return this.relativePosition;
    }
    
    /**
     * Internally increases the input stream position value
     * @param count     number of bytes to increase position
     */
    protected void increasePosition(long count) {
        if (count > 0) {
            this.relativePosition += count;
        }
    }
    
    /**
     * Checks if the given marker matches the marker mask
     * @param marker    marker to check
     * @return  true if marker matches the marker mask
     */
    protected boolean isMask(byte marker) {
        if (this.markerMask == null) {
            return true;
        }
        if (this.markerMask.length == 0) {
            return false;
        }
        for (byte oneMarker : this.markerMask) {
            if (oneMarker == marker) {
                return true;
            }
        }
        return false;
    }
    
}
