package com.macro.mall;


import cn.hutool.json.JSONUtil;
import com.macro.mall.dao.PmsMemberPriceDao;
import com.macro.mall.dao.PmsProductDao;
import com.macro.mall.dto.PmsProductResult;
import com.macro.mall.model.PmsMemberPrice;
import com.macro.mall.model.PmsProduct;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        int amount=2000000;
        Random random = new Random();
        List<PmsProduct> newList=new ArrayList<>();
        int result=0;
        String dates="2019/01/";
        while (amount>0){
            int i=random.nextInt(proList.size()-1);
            PmsProduct p=proList.get(i);
            int size=random.nextInt(9)+1;
            amount-=p.getPromotionPrice().intValue()*size;
            result+=p.getPromotionPrice().intValue()*size;
            int intDate=random.nextInt(30)+1;
            String startDate=dates+intDate;
            String payTime=dates+intDate;
            int deliverInt=intDate+random.nextInt(2)+1;
            String deliveryTime=dates+deliverInt;
            String receiveTime=dates+(deliverInt+random.nextInt(2)+3);
            p.setDescription(changeDate(startDate));
            p.setDetailDesc(changeDate(payTime));
            p.setDetailHtml(changeDate(deliveryTime));
            p.setDetailMobileHtml(changeDate(receiveTime));
            p.setSale(size);
            newList.add(p);
        }


        newList.forEach(pmsProduct -> System.out.println(pmsProduct.toString()));
        System.out.println("size: "+newList.size());
        System.out.println("result: "+result);
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
