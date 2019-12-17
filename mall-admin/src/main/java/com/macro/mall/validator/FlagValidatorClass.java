package com.macro.mall.validator;

import cn.hutool.core.io.FileUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 状态标记校验器
 * Created by macro on 2018/4/26.
 */
public class FlagValidatorClass implements ConstraintValidator<FlagValidator,Integer> {
    private String[] values;
    @Override
    public void initialize(FlagValidator flagValidator) {
        this.values = flagValidator.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = false;
        if(value==null){
            //当状态为空时使用默认值
            return true;
        }
        for(int i=0;i<values.length;i++){
            if(values[i].equals(String.valueOf(value))){
                isValid = true;
                break;
            }
        }
        return isValid;
    }
    public static void main(String[] args) {
        List<String> lists= FileUtil.readLines("D://bbbb.csv","GB2312");
        List<String> newLists=new ArrayList<>();
        newLists.add(lists.get(0));
        for(String str:lists){
            String[] arrays=str.split(",");
            try{
                arrays[0]=arrays[0]+"";
                arrays[3]=arrays[3]+"";
                arrays[11]=changeDate(arrays[11]);
                arrays[12]=changeDate(arrays[12]);
                arrays[13]=changeDate(arrays[13]);
                arrays[14]=changeDate(arrays[14]);
                arrays[20]= "http://39.108.109.128/api/images/"+arrays[20].trim().replace("\"","").replace("\t","")+".jpg";
                StringBuffer zz=new StringBuffer();
                for(int i=0;i<arrays.length;i++){

                    zz.append(arrays[i]+",");
                }
                String zzzz=zz.toString();
                newLists.add(zzzz);
            }catch (Exception E){
                System.out.println(str);
            }

        }
//
        FileUtil.appendLines(newLists,new File("D://999889.csv"),"UTF-8");



//        //=========================
//        List<String> lists= FileUtil.readLines("D://ssss.csv","GB2312");
//                List<String> newLists=new ArrayList<>();
//        newLists.add(lists.get(0));
//        for(String str:lists) {
//            String[] arrays = str.split(",");
//            try {
//                arrays[0] = changeDate(arrays[0]);
//                System.out.println(arrays[0]);
//                StringBuffer zz = new StringBuffer();
//                for (int i = 0; i < arrays.length; i++) {
//
//                    zz.append(arrays[i] + ",");
//                }
//                String zzzz = zz.toString();
//                newLists.add(zzzz);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        FileUtil.appendLines(newLists,new File("D://88888.csv"),"UTF-8");
    }

    public static String changeDate(String str){
//        str.replace("//","-");
        Random random = new Random();
        String[] cstr=str.split("/");
        if(cstr[1].length()==1){
            cstr[1]="0"+cstr[1];
        }
        if(cstr[2].length()==1){
            cstr[2]="0"+cstr[2];
        }
        String hours=random.nextInt(14)+8+"";
        String min=random.nextInt(59)+0+"";
        String secons= random.nextInt(59)+0+"";
       return cstr[0]+"-"+cstr[1]+"-"+ cstr[2] +" "+addZore(hours)+":"+addZore(min)+":"+addZore(secons);
    }
    public static String addZore(String bb){
        return bb.length()>1?bb:"0"+bb;
    }

}
