import com.github.crab2died.ExcelUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wesharn
 * @version V1.0
 * @Title:
 * @Package ${package_name}
 * @Description: TODO
 * @date ${date}
 */
public class HandlerExel {
     static final String bpath = "c:\\Users\\lenovo\\Desktop\\task\\";

    @Test
    public void excel2Object2() {

        String basefp = bpath+"truemoney.xls";
        Lock lock = new ReentrantLock();
        ArrayList<String> data = new ArrayList<String>();
        try {

            // 1)
            // 不基于注解,将Excel内容读至List<List<String>>对象内
            List<List<String>> listBase = ExcelUtils.getInstance().readExcel2List(basefp, 1, 450, 0);
            System.out.println("读取Excel至String数组：");

            for (List<String> list : listBase) {
                System.out.println(list.get(12));
                data.add(list.get(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("you me : " + data.contains("b4cbf5af-5a11-41f5-b5c9-f4d631aabcc8329"));
        System.out.println("data = " + data.size());
    }
    @Test
    public void readcrv(){

        String basefp = bpath+"truemoney.xls";
        ArrayList<String> data = new ArrayList<String>();
        try {

            // 1)
            // 不基于注解,将Excel内容读至List<List<String>>对象内
            List<List<String>> listBase = ExcelUtils.getInstance().readExcel2List(basefp, 1, 450, 0);
            System.out.println("读取Excel至String数组：");

            for (List<String> list : listBase) {
                System.out.println(list.get(12));
                data.add(list.get(12));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(data.contains("fc606f1c-a9af-4ad6-b0db-013a44e80718556"));
       String filee="20180126095_214";
        String path = bpath+"xls\\"+filee+".xlsx";
        try {

            // 1)
            // 不基于注解,将20180126072_214Excel内容读至List<List<String>>对象内
            List<List<String>> lists = ExcelUtils.getInstance().readExcel2List(path, 0, Integer.MAX_VALUE, 0);
            System.out.println("读取Excel至String数组：");
            List<List<String>> fileList =  new ArrayList<List<String>>();
            for (List<String> list : lists) {
                String cdr = list.get(0);
                List<String> listCdr = Splitter.on('|') .trimResults().omitEmptyStrings().splitToList(cdr);
                List<String> tmp = new ArrayList(listCdr);
                if(data.contains(listCdr.get(3).toString())){
                     Integer price = new Integer(listCdr.get(5))/100;
                    System.out.println("price= " + price+";"+"ID = " + listCdr.get(3)+";"+"beforprice = " + listCdr.get(5));
                    tmp.set(5,price.toString());
                }
                String str = Joiner.on("|").join(tmp.iterator()).toString();
                ArrayList al = new ArrayList();
                al.add(str);
                fileList.add(al);
            }
            //写文件
            ExcelUtils.getInstance().exportObjects2Excel(fileList, new ArrayList<String>(), bpath+"newcsv\\"+filee+".xlsx");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
