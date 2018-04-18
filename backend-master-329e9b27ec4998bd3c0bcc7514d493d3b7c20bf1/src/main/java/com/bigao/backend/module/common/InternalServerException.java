package com.bigao.backend.module.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author wait
 * @date 2015年5月6日 下午1:06:45
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1741809228235473345L;

}
