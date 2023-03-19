package com.lychee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lychee.model.param.ParseTextParam;
import com.lychee.model.result.HistoryResult;
import com.lychee.model.result.IpDataResult;
import com.lychee.model.result.PickTextResult;
import com.lychee.model.result.WeatherResult;
import com.lychee.pojo.TestEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author jiangwei97@aliyun.com
 * @since 2022/4/10 13:01
 */
public interface ITextService extends IService<TestEntity> {

    /**
     *
     * @param cronExpression cron表达式
     * @param numTimes 几次
     * @return 下一(几)次运行的时间
     */
    List<String> getNextExecTime(String cronExpression,Integer numTimes);

    String base64Encode(String src);

    String base64Decode(String src);

    String md5Encode(String src);

    PickTextResult pickTextByPath(ParseTextParam param);

    IpDataResult ipData(String ip);

    WeatherResult weather(HttpServletRequest request);

    List<HistoryResult> history();

}
