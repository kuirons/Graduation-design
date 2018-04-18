package hbase;

import com.bigao.backend.util.Time;
import hbase.util.HBaseUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class test {
    private static HBaseServiceImpl hBaseService = new HBaseServiceImpl();
    private final static Logger logger = LoggerFactory.getLogger(test.class);

    public static void main(String[] args) {
        String TABLENAME = "backtable";
        List<Filter> list = new ArrayList<>();
        Filter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL,new SubstringComparator("张言"));
        Filter rowFilter1 = new RowFilter(CompareFilter.CompareOp.EQUAL,new SubstringComparator("&03&"));
        SingleColumnValueExcludeFilter single = new SingleColumnValueExcludeFilter("backinfo".getBytes(),"server".getBytes(), CompareFilter.CompareOp.EQUAL,new BinaryComparator("2,2".getBytes()));
        single.setFilterIfMissing(true);
//        Filter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator("2,2".getBytes()));
//        Filter rowFilter2 = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL,new SubstringComparator("1498839015"));
        list.add(single);
        list.add(rowFilter);
        list.add(rowFilter1);
//        list.add(rowFilter2);
        Filter filter = new FilterList(FilterList.Operator.MUST_PASS_ALL,list);
        List<String> param = new ArrayList<>();
        param.add("1502331080000-1512871880000");
        HashMap<String,List<String>> p = new HashMap<>();
        p.put("timeRange",param);
        ResultScanner results = HBaseUtil.getScan(TABLENAME,p,filter);
//        Filter rowFilterO = new RowFilter(CompareFilter.CompareOp.EQUAL,new SubstringComparator("&01&"));
//        Filter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("用"));
//        Filter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator("2,2".getBytes()));
//        Filter keyOnlyFilter = new KeyOnlyFilter();
//        List<Filter> list = new ArrayList<Filter>();
//        list.add(rowFilterO);
//        list.add(rowFilter);
//        list.add(valueFilter);
//        list.add(keyOnlyFilter);
//        Filter filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL,list);
//        ResultScanner results= HBaseUtil.getScan(TABLENAME,null,filterList);
        Result r = null;
        try {
            while ((r=results.next()) != null){
                String levelInfo = Bytes.toString(r.getValue("backinfo".getBytes(),"logio".getBytes()));
                System.out.println(levelInfo);
                System.out.println(Bytes.toString(r.getRow()));
            }
        }catch (IOException e){
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("异常");
            System.out.println();
            System.out.println();
            System.out.println();
        }
//        for (Result r :
//                results) {
//            System.out.println(Bytes.toString(r.getRow()));
//        }
//        results.close();
//        System.out.println(Bytes.toString(HBaseUtil.getRow(TABLENAME,"角色名#&用户名#&1&1512117576".getBytes()).getValue("backloginfo".getBytes(),"PS".getBytes())));
//        System.out.println();
//        System.out.println(System.currentTimeMillis());
//        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("用"));
//        Filter qualifierFilter = new QualifierFilter(CompareFilter.CompareOp.EQUAL,new BinaryComparator("2,2".getBytes()));
//        ResultScanner results=HBaseUtil.getScan(TABLENAME,null);
//        for (Result r :
//                results) {
//            System.out.println(r.getValue("backloginfo".getBytes(),"PS".getBytes()));
//        }
//        String tableName = "backtable";
//
//        String rowKey="张用#&张三#&1&"+System.currentTimeMillis();
//        Put put = new Put(rowKey.getBytes());
//        //PS:platform and server
//        put.addColumn("backloginfo".getBytes(), "PS".getBytes(), "2,2".getBytes());
//        HBaseUtil.sycPut(TABLENAME,put);
//        try {
//            HBaseUtil.asynPut(tableName,puts);
//        } catch (Exception e) {
//            logger.error("异步添加失败");
//        }
        HBaseUtil.closeConnection();
    }
}