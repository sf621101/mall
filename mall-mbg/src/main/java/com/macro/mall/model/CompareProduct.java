package com.macro.mall.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class CompareProduct implements Comparator<PmsProduct> {
    public int compare(PmsProduct o1, PmsProduct o2) {
        try {
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date1=dateFormat.parse(o1.getDescription());
            Date date2=dateFormat.parse(o2.getDescription());
            Long de=date1.getTime()-date2.getTime();
            return de.intValue();
        }catch (Exception e){
e.printStackTrace();
        }

        return 0;
    }
}
