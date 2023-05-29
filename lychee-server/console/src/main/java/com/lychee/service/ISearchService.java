package com.lychee.service;

import com.lychee.model.Result;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/5/29 13:01
 */
public interface ISearchService {

    Result<?> ipData(String ip);

    Result<?> rubbishClassify(String name);

}
