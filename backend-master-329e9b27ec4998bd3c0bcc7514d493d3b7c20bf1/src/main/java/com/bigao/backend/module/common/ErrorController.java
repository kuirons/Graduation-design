package com.bigao.backend.module.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 拦截错误
 * Created by wait on 2016/4/10.
 */
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({ResourceNotFoundException.class, NoHandlerFoundException.class})
    public ModelAndView handleResourceNotFoundException() {
        ModelAndView view = new ModelAndView();
        view.setViewName("404");
        return view;
    }

    @ExceptionHandler(InternalServerException.class)
    public ModelAndView errCode() {
        ModelAndView view = new ModelAndView();
        view.setViewName("500");
        return view;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView common() {
        ModelAndView view = new ModelAndView();
        view.setViewName("404");
        return view;
    }


    @RequestMapping(value = {"/error/500", "/xiyou/500"})
    public ModelAndView interServerError() {
        ModelAndView view = new ModelAndView();
        view.setViewName("500");
        return view;
    }

    @RequestMapping(value = {"/error/404", "/xiyou/404"})
    public ModelAndView pageNotFound() {
        ModelAndView view = new ModelAndView();
        view.setViewName("404");
        return view;
    }
}
