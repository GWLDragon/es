package com.hcf.nszh.common.exception.handler;

import com.alibaba.fastjson.JSON;
import com.hcf.nszh.common.base.ResponseVo;
import com.hcf.nszh.common.enums.ErrorEnum;
import com.hcf.nszh.common.utils.StringUtils;
import com.hcf.nszh.common.exception.BusinessException;
import com.hcf.nszh.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @Author hx
 * @Date 2019/6/13 0013
 **/
@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    private static final String RESPONSE_CONTENT_HEADER = "Content-type";

    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public void handleErrors(HttpServletResponse response, Exception e) {

        ResponseVo<Object> responseVo;
        if (e instanceof MissingServletRequestParameterException
                || e instanceof BindException
                || e instanceof IllegalArgumentException) {
            responseVo = new ResponseVo<>(ErrorEnum.PARAMS_WRONG);
        } else if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException em = (MethodArgumentNotValidException) e;
            List<ObjectError> errors = em.getBindingResult().getAllErrors();
            StringBuilder error = new StringBuilder();
            if (errors.size() > 0) {
                error.append(errors.get(0).getDefaultMessage());
            }
            responseVo = new ResponseVo<>(ErrorEnum.PARAMS_WRONG.getErrorCode(), error.toString());
        } else if (e instanceof UnauthenticatedException) {
            responseVo = new ResponseVo<>(ErrorEnum.TOKEN_INVALID);
        } else if (e instanceof UnauthorizedException) {
            responseVo = new ResponseVo<>(ErrorEnum.USER_NO_PERMISSION);

        } else if (e instanceof BusinessException) {
            responseVo = new ResponseVo<>();
            responseVo.setCode(((BusinessException) e).getErrorCode());
            responseVo.setError(e.getMessage());
        } else if (e instanceof ServiceException) {
            responseVo = new ResponseVo<>();
            if(StringUtils.isBlank(e.getMessage())){
                responseVo = new ResponseVo<>(ErrorEnum.FAILED);
            }else{
                responseVo.setCode(-1);
                responseVo.setError(e.getMessage());
            }

        } else {
            logger.error("server error : {}", e);
            responseVo = new ResponseVo<>(ErrorEnum.FAILED);
        }

        response.reset();
        response.setHeader(RESPONSE_CONTENT_HEADER, "application/json;charset=UTF-8");
        try (OutputStream out = response.getOutputStream()) {
            out.write(JSON.toJSONString(responseVo).getBytes());
        } catch (Exception e1) {
            logger.error("resp error:{}", e1);
        }
    }
}
