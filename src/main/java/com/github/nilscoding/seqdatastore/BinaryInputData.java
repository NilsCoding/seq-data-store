package com.github.nilscoding.seqdatastore;

/**
 * Binary input data
 * @author NilsCoding
 */
public class BinaryInputData {
    
    protected byte type;
    protected Object value;
    protected boolean valid;
    protected long position = -1L;

//    public BinaryInputData(byte type) {
//        this.type = type;
//        this.value = null;
//        this.valid = false;
//    }
    
    /**
     * Creates a new empty, invalid binary input data object of given type with given position
     * @param position  position
     * @param type  type
     */
    public BinaryInputData(long position, byte type) {
        this.position = position;
        this.type = type;
        this.value = null;
        this.valid = false;
    }
    
    /**
     * Creates a new valid binary input data object of given type with given position and given value
     * @param position  position
     * @param type  type
     * @param value     value (can be null)
     */
    public BinaryInputData(long position, byte type, Object value) {
        this.position = position;
        this.type = type;
        this.value = value;
        this.valid = true;
    }
    
//    public BinaryInputData(byte type, Object value) {
//        this.type = type;
//        this.value = value;
//        this.valid = true;
//    }
    
    /**
     * Creates a new binary input data object of given type with given position and given value
     * @param position  position
     * @param type  type
     * @param value     value (can be null)
     * @param valid     true for valid data, false for invalid data
     */
    public BinaryInputData(long position, byte type, Object value, boolean valid) {
        this.position = position;
        this.type = type;
        this.value = value;
        this.valid = valid;
    }

//    public BinaryInputData(byte type, Object value, boolean valid) {
//        this.type = type;
//        this.value = value;
//        this.valid = valid;
//    }

    /**
     * Returns the position value
     * @return  position value
     */
    public long getPosition() {
        return position;
    }

    /**
     * Sets the position value
     * @param position  position value
     */
    public void setPosition(long position) {
        this.position = position;
    }

    /**
     * Returns the type
     * @return  type
     */
    public byte getType() {
        return type;
    }

    /**
     * Sets the type
     * @param type  type
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * Returns the value, can be null
     * @return  value, can be null
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value, null is supported
     * @param value     value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Returns whether or not the data is valid
     * @return  true if valid, false if invalid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Sets whether or not the data is valid
     * @param valid     true if data is valid, false if invalid
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
}
