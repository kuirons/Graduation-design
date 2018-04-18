package com.bigao.backend;

import com.bigao.backend.common.CommonDto;
import com.bigao.backend.util.CommonUtils;
import com.google.common.collect.Lists;
import org.apache.ibatis.io.ResolverUtil;

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 打包, 主要是因为CommonDto的子类需要有一个手动的无参构造方法
 * Created by wait on 2016/4/15.
 */
public class PackMain {

    static class PackStreamGobbler extends Thread {
        InputStream is;
        String type;

        public PackStreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is, "gbk");
                BufferedReader br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(type + ">" + line);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // 检测CommonDto的子类是否都有默认构造方法
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
        resolverUtil.find(new ResolverUtil.IsA(CommonDto.class), "com.bigao.backend.module");
        Set<Class<? extends Class<?>>> dtoClasses = resolverUtil.getClasses();
        List<Class<? extends Class<?>>> errClasses = Lists.newArrayList();
        errClasses.addAll(dtoClasses.stream().filter(dtoClass -> CommonUtils.alterMessage(dtoClass, "test") == null).collect(Collectors.toList()));
        if (!errClasses.isEmpty()) {
            System.out.println("以下这些CommonDto的子类需要有无参构造方法, 要不然CommonUtils.alterMessage(TaskDto.class, CommonErrorKey.UNKNOWN)这种用法会报错");
            errClasses.stream().forEach(c -> System.out.println(c.getCanonicalName()));
            return;
        }

        String curPath = System.getProperty("user.dir");
        File pomFile = new File(curPath + "/pom.xml");
        String diskName = pomFile.getCanonicalPath().substring(0, 1);
        StringBuilder builder = new StringBuilder();
        builder.append(diskName).append(":");
        builder.append("&&");
        builder.append("cd ");
        builder.append(curPath);
        builder.append("&&");
        builder.append("mvn -f ");
        builder.append(curPath).append(File.separator).append("pom.xml");
        builder.append(" clean install");
        String[] commands = new String[]{"cmd.exe", "/C", builder.toString()};

        Runtime rt = Runtime.getRuntime();
        System.err.println("开始打包:" + builder.toString());
        Process process = rt.exec(commands);

        new PackStreamGobbler(process.getErrorStream(), "ERROR").start();
        new PackStreamGobbler(process.getInputStream(), "").start();

        try {
            int exitVal = process.waitFor();
            if (exitVal != 0) {
                throw new RuntimeException("打包maven出错");
            }
        } finally {
            process.destroy();
        }
        System.err.println("done!!!!!!!!!!!!");
    }
}
