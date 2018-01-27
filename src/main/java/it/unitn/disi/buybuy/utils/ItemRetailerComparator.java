/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.disi.buybuy.utils;

import it.unitn.disi.buybuy.dao.entities.Item;
import it.unitn.disi.buybuy.dao.entities.Retailer;
import java.util.Comparator;
import javafx.util.Pair;

/**
 *
 * @author maxfrax
 */
public class ItemRetailerComparator implements Comparator<Pair<Item, Retailer>> {

    private Float latitude, longitude;

    public ItemRetailerComparator(float lat, float lng) {
        latitude = lat;
        longitude = lng;
    }

    private ItemRetailerComparator() {
    }

    @Override
    public int compare(Pair<Item, Retailer> o1, Pair<Item, Retailer> o2) {
        // se distanza di o1 rispetto a me è maggiore di distanza da me di o2
        Retailer r1 = o1.getValue();
        Retailer r2 = o2.getValue();
        Float distance1 = distance(latitude, longitude, r1.getLatitude(), r1.getLongitude());
        Float distance2 = distance(latitude, longitude, r2.getLatitude(), r2.getLongitude());
        return (int) Math.signum(distance1 - distance2);
    }

    private Float distance(Float lat1, Float lon1, Float lat2, Float lon2) {
        final int R = 6371; // Radious of the earth
        Float latDistance = toRad(lat2 - lat1);
        Float lonDistance = toRad(lon2 - lon1);
        Float a = (float) (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(toRad(lat1)) * Math.cos(toRad(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2));
        Float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));

        return R * c;
    }

    private static Float toRad(Float value) {
        return (float) (value * Math.PI / 180);
    }

}
