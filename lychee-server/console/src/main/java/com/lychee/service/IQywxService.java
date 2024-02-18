package com.lychee.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IQywxService {

    String verificationUrl(HttpServletRequest request);

    Map<String, String> getRequestParameter(HttpServletRequest request, String body);

}
