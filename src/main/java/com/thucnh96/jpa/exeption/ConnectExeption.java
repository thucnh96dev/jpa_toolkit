package com.thucnh96.jpa.exeption;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :09/11/2021 - 10:28 AM
 */
public class ConnectExeption extends RuntimeException{

    private static final long serialVersionUID = 3788669840036201041L;
    public ConnectExeption(){
        super();
    }
    /**
     * Instantiates a new binance api exception.
     *
     * @param message the message
     */
    public ConnectExeption(String message) {
        super(message);
    }

    /**
     * Instantiates a new binance api exception.
     *
     * @param cause the cause
     */
    public ConnectExeption(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new binance api exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public ConnectExeption(String message, Throwable cause) {
        super(message, cause);
    }


}
