package com.lychee.service;

import javax.servlet.http.HttpServletRequest;

public interface IQywxService {

    String verificationUrl(HttpServletRequest request);

    void getRequestParameter(HttpServletRequest request, String body);

}
