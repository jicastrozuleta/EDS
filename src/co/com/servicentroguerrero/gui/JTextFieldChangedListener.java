/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentroguerrero.gui;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Interface usada para detectar el cambio de texto de un jTextField 
 * @author JICZ4
 */
public interface JTextFieldChangedListener extends DocumentListener {

    /**
     * metodo para capturar los cambios de texto en un JTextFiel
     * @param e 
     */
    void textChange(DocumentEvent e);

    @Override
    default void insertUpdate(DocumentEvent e) {
        textChange(e);
    }
    @Override
    default void removeUpdate(DocumentEvent e) {
        textChange(e);
    }
    @Override
    default void changedUpdate(DocumentEvent e) {
        textChange(e);
    }
}
    
    