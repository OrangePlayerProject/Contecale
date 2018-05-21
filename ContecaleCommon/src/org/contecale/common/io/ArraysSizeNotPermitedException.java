/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.contecale.common.io;

/**
 *
 * @author martin
 */
public class ArraysSizeNotPermitedException extends RuntimeException {

    /**
     * Creates a new instance of <code>ArraysSizeNotPermitedException</code>
     * without detail message.
     */
    public ArraysSizeNotPermitedException() {
    }

    /**
     * Constructs an instance of <code>ArraysSizeNotPermitedException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ArraysSizeNotPermitedException(String msg) {
        super(msg);
    }
}
