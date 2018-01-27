/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.disi.buybuy.utils;

/**
 *
 * @author maxfrax
 */
public class Converter {

    public static Integer parseInt(String parameter) {
        Integer res = null;

        try {
            res = Integer.parseInt(parameter);
        } catch (NumberFormatException ex) {
            res = null;
        }

        return res;
    }

    public static Float parseFloat(String parameter) {
        Float res = null;

        try {
            res = Float.parseFloat(parameter);
        } catch (NumberFormatException ex) {
            res = null;
        }

        return res;
    }

}
