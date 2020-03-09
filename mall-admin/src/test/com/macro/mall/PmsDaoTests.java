package com.macro.mall;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.macro.mall.dao.PmsMemberPriceDao;
import com.macro.mall.dao.PmsProductDao;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.model.CompareProduct;
import com.macro.mall.model.PmsMemberPrice;
import com.macro.mall.model.PmsProduct;
import com.macro.mall.util.IdWorker;
import org.apache.commons.collections.ListUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PmsDaoTests {
    @Autowired
    private PmsMemberPriceDao memberPriceDao;
    @Autowired
    private PmsProductDao productDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(PmsDaoTests.class);
    @Test
    @Transactional
    @Rollback
    public void testInsertBatch(){
        List<PmsMemberPrice> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            PmsMemberPrice memberPrice = new PmsMemberPrice();
            memberPrice.setProductId(1L);
            memberPrice.setMemberLevelId((long) (i+1));
            memberPrice.setMemberPrice(new BigDecimal("22"));
            list.add(memberPrice);
        }
        int count = memberPriceDao.insertList(list);
        Assert.assertEquals(5,count);
    }

    @Test
    public void  testGetProductUpdateInfo(){
        PmsProductResult productResult = productDao.getUpdateInfo(7L);
        String json = JSONUtil.parse(productResult).toString();
        LOGGER.info(json);
    }

    @Test
    public void testKKK(){
        List<PmsProduct>  proList=productDao.selectAll();

        List<String> memberList=getMemberInfo();
        action(2280000,"2019/01/","D://1月.csv",proList,memberList,30);
        action(1010000,"2019/02/","D://2月.csv",proList,memberList,27);
        action(1819000,"2019/03/","D://3月.csv",proList,memberList,30);
        action(1678000,"2019/04/","D://4月.csv",proList,memberList,29);
        action(1301000,"2019/05/","D://5月.csv",proList,memberList,30);
        action(1610000,"2019/06/","D://6月.csv",proList,memberList,29);
        action(1782000,"2019/07/","D://7月.csv",proList,memberList,30);
        action(1170000,"2019/08/","D://8月.csv",proList,memberList,30);
        action(1568000,"2019/09/","D://9月.csv",proList,memberList,29);
        action(1834000,"2019/10/","D://10月.csv",proList,memberList,30);
        action(2810000,"2019/11/","D://11月.csv",proList,memberList,29);
        action(2310000,"2019/12/","D://12月.csv",proList,memberList,30);

    }


    public void  action(int amount,String month,String filepath, List<PmsProduct>  proList,List<String> memberList,int kks){
        Random random = new Random();
        List<PmsProduct> newList=new ArrayList<>();
        int result=0;
        String dates=month;
        List<String>  totleList=new ArrayList<>();
        while (amount>0){
            int i=random.nextInt(proList.size()-1);
            PmsProduct p=proList.get(i);
            int size=random.nextInt(9)+1;
            amount-=p.getPromotionPrice().intValue()*size;
            result+=p.getPromotionPrice().intValue()*size;
            int intDate=random.nextInt(kks)+1;
            String startDate=dates+intDate;
            String payTime=dates+intDate;
            int deliverInt=intDate+random.nextInt(2)+1;
            String deliveryTime=dates+deliverInt;
            String receiveTime=dates+(deliverInt+random.nextInt(2)+3);
            String startdateStr=changeDate(startDate);
            p.setDescription(startdateStr);
            p.setDetailDesc(changeHours(startdateStr));
            p.setDetailHtml(changeDate(deliveryTime));
            p.setDetailMobileHtml(changeDate(receiveTime));
            p.setSale(size);
            newList.add(p);
        }
//        CompareProduct compareProduct=new CompareProduct();
//        CollectionUtil.sort(newList,compareProduct);
        IdWorker ik = new IdWorker();
        newList.forEach(pmsProduct -> {
            int intDate=random.nextInt(memberList.size())-1;
            String info=ik.nextId()+","+memberList.get(intDate>0?intDate:0)+pmsProduct.toString();
            totleList.add(info);
        });
        totleList.add("size: "+newList.size());
        totleList.add("result: "+result);
        System.out.println(filepath+": "+result);

        FileUtil.appendLines(totleList,new File(filepath),"UTF-8");
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

    public static String changeHours(String str){
        Random random = new Random();
        String[] cstr=str.split(":");
        String min=random.nextInt(59)+0+"";
        String secons= random.nextInt(59)+0+"";
        return cstr[0]+":"+addZore(min)+":"+addZore(secons);
    }
    public static String addZore(String bb){
        return bb.length()>1?bb:"0"+bb;
    }

    public static List<String> getMemberInfo(){
        String path="D:\\1.csv";

        List<String> list=FileUtil.readLines(new File(path),"GBK");
        list.remove(0);
//        System.out.println(list);
//        System.out.println(list.size());
        return  list;

    }

    public static void main(String[] args) {
       List<String> list= FileUtil.readLines("D://total.csv","GBK");
       List<String> newList=new ArrayList<>();
        IdWorker ik = new IdWorker();
       for(String str:list){
          String[] newStr= str.split(",");
           BigDecimal nums=new BigDecimal(newStr[13]);
           BigDecimal prices=new BigDecimal(newStr[14]);
           BigDecimal pro=new BigDecimal(newStr[15]);
           String kk=ik.nextId()+","+str+","+nums.multiply(prices)+","+nums.multiply(pro)+","+prices.subtract(pro)+","+nums.multiply(prices.subtract(pro));

            newList.add(kk);
       }
       FileUtil.writeLines(newList,new File("D://newFiles.csv"),"UTF-8" );
//        getMemberInfo();
    }
//    D://1月.csv: 2282694
//    D://2月.csv: 1011393
//    D://3月.csv: 1820720
//    D://4月.csv: 1679959
//    D://5月.csv: 1301493
//    D://6月.csv: 1614558
//    D://7月.csv: 1783232
//    D://8月.csv: 1170396
//    D://9月.csv: 1570849
//    D://10月.csv: 1836163
//    D://11月.csv: 2813668
//    D://12月.csv: 2310665


}
