//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tech.ynfy.frame.config.feign;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@RefreshScope
public class FeignLogger extends Logger {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(FeignLogger.class);
    @Value("${spring.profiles.include}")
    private String profile;
    @Value("${log.enable:false}")
    private String logEnable;

    public FeignLogger() {
    }

    protected void log(String configKey, String format, Object... args) {
    }

    protected void logRequest(String configKey, Level logLevel, Request request) {
        Request.HttpMethod httpMethod = request.httpMethod();
        String url = request.url();
        String param = " ";
        if (request.body() != null) {
            Collection<String> contentType = (Collection)request.headers().get("Content-Type");
            if (CollectionUtils.isEmpty(contentType) || !contentType.toArray()[0].toString().contains("multipart/form-data")) {
                param = new String(request.body(), Util.UTF_8);
            }
        }

        log.info("{}------> {} url:{}, param:{}, header:{}", configKey, httpMethod, url, param, request.headers());
    }

    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String result = "";
        int status = response.status();

        Response var9;
        try {
            if (response.body() == null) {
                Response var13 = response;
                return var13;
            }

            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            result = Util.decodeOrDefault(bodyData, Util.UTF_8, "Binary data");
            var9 = response.toBuilder().body(bodyData).build();
        } finally {
            if (this.profile.contains("prod") && !Objects.equals(this.logEnable, "true")) {
                log.info("{}<------ status:{}, ElapsedTime:[{}ms]", configKey, status, elapsedTime);
            } else {
                log.info("{}<------ status:{}, result:{}, ElapsedTime:[{}ms]", configKey, status, result, elapsedTime);
            }

        }

        return var9;
    }
}
