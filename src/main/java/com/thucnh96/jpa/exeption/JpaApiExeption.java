package com.thucnh96.jpa.exeption;

import com.thucnh96.jpa.modal.JpaApiError;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :09/11/2021 - 10:28 AM
 */
public class JpaApiExeption extends RuntimeException{

    private static final long serialVersionUID = 3788669840036201041L;

    /**
     * Error response object returned by Binance API.
     */
    private JpaApiError error;

    public JpaApiExeption(JpaApiError error) {

        this.error = error;
    }

    public JpaApiExeption(){
        super();
    }
    /**
     * Instantiates a new binance api exception.
     *
     * @param message the message
     */
    public JpaApiExeption(String message) {
        super(message);
    }

    /**
     * Instantiates a new binance api exception.
     *
     * @param cause the cause
     */
    public JpaApiExeption(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new binance api exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public JpaApiExeption(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @return the response error object from Binance API, or null if no response object was returned (e.g. server returned 500).
     */
    public JpaApiError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        if (error != null) {
            return error.getMsg();
        }
        return super.getMessage();
    }

}
