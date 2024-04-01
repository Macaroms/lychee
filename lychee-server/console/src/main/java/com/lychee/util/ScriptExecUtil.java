package com.lychee.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * @author : jiangwei97@aliyun.com
 * @since : 2024/3/29 15:49
 **/

@Component
@Slf4j
public class ScriptExecUtil {

    public String scriptEngineJsByPath(String functionName, String scriptPath, Object... args) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        try {
            engine.eval(new FileReader(scriptPath));
            Object result = ((Invocable) engine).invokeFunction(functionName, args);
            return result.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public String scriptEngineJs(String functionName, String jsCode, Object... args) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        try {
            engine.eval(jsCode);
            Object result = ((Invocable) engine).invokeFunction(functionName, args);
            return result.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

    public String scriptEngine(String language, String functionName, String jsCode, Object... args) {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName(language);
        try {
            engine.eval(jsCode);
            Object result = ((Invocable) engine).invokeFunction(functionName, args);
            return result.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            return e.getMessage();
        }
    }

}
