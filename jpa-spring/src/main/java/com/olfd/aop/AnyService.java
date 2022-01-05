package com.olfd.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnyService {

    public String doMagic(String arg) {
        log.info("doMagic() method call");
        if (StringUtils.equals(arg, "ex")) {
            throw new RuntimeException("exception!!");
        }
        return "MAGIC for " + arg;
    }
}
