package com.bigao.backend.log;

import com.bigao.backend.common.anno.TableDesc;

import java.sql.Timestamp;

/**
 * Created by wait on 2015/12/29.
 */
@TableDesc(value = "roleinmapcountlog")
public class RoleInMapCountLog {
    private int count;
    private Timestamp time;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
