package com.bigao.backend.common.config;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by wait on 2015/12/15.
 */
@Service
public class GameConfigService {

    public List<?> read(InputStream inputStream, Class<? extends BaseBean> clazz) throws IOException, IllegalAccessException, InstantiationException {
        InputStreamReader r = new InputStreamReader(inputStream, "utf-8");
        BufferedReader reader = new BufferedReader(r);

        reader.readLine(); // 第一行字段名称
        reader.readLine(); // 第二行字段说明

        String line;
        List<BaseBean> beans = Lists.newArrayList();
        while ((line = reader.readLine()) != null) {
            BaseBean bean = clazz.newInstance();
            bean.read(line.split("\t"));
            beans.add(bean);
        }

        reader.close();
        r.close();

        return beans;
    }
}
