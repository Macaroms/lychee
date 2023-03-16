package com.lychee.service;

import com.lychee.model.param.TranslateParam;
import com.lychee.model.result.PoetryResult;
import com.lychee.model.result.TranslateResult;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/3/14 00:15
 */
public interface IRemoteService {

    PoetryResult poetry();

    TranslateResult translate(TranslateParam param);

}
