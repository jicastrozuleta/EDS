/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.servicentro.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Document para validar que los textos ingresados a un TextField sean unicamente numericos
 * de tipo entero. 
 * @author JICZ4
 */
public class DocumentTypeDouble extends PlainDocument {

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i)) || (str.charAt(i) == '.')) {
                super.insertString(offs, str, a);//To change body of generated methods, choose Tools | Templates.
            }
            else
                return;
             
        }
    }    
}
