package com.bigao.backend.module.zone;

import java.lang.annotation.*;

/**
 * Created by wait on 2015/12/28.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CopyDesc {
    String value();
}
