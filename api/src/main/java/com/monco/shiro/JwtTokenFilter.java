package com.monco.shiro;

import com.alibaba.fastjson.JSONObject;
import com.monco.common.bean.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.ConcurrentAccessException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtTokenFilter
 *
 * @author Lijin
 * @version 1.0.0
 */
@Slf4j
public class JwtTokenFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("token");
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        req.setAttribute("token", token);
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(token)) {
            JwtAuthToken jwtAuthToken = new JwtAuthToken(token);
            getSubject(request, response).login(jwtAuthToken);
            return true;

        }
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws IOException {
        try {
            return super.preHandle(request, response);
        } catch (Exception e) {
            log.error("JwtTokenFilter=>preHandle=>" + e.getMessage());
            if (e instanceof ConcurrentAccessException) {
                writeResponse(response, 999, e.getMessage());
            } else {
                writeResponse(response, 401, e.getMessage());
            }
        }
        return false;
    }

    private void writeResponse(ServletResponse response, Integer status, String msg) throws IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(status);
        response.setContentType("application/json ;charset=utf-8");
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(status);
        apiResult.setMsg(msg);
        httpResponse.getWriter().write(JSONObject.toJSONString(apiResult));
    }

}