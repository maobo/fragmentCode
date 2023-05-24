package cn.mojit.fragmentcode.web;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
@Slf4j
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    /**
     * 测试方法
     *
     * @param content
     * @param openid
     * @return
     */
    @RequestMapping(value = {"hi"}, method = RequestMethod.GET)
    public String custom(String content, String openid) {
        String json = "{\n" +
                "    \"touser\":\"" + openid + "\",\n" +
                "    \"msgtype\":\"text\",\n" +
                "    \"text\":\n" +
                "    {\n" +
                "         \"content\":\"" + content + "\"\n" +
                "    }\n" +
                "}";
        return json;
    }

    @GetMapping("/test/{key1}/{key2}")
    public String test(@PathVariable("key1") String key1, @PathVariable("key2") String key2) {
        return key1 + key2;
    }


}
