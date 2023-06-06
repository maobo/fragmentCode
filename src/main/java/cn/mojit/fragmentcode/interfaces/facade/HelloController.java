package cn.mojit.fragmentcode.interfaces.facade;
import cn.mojit.fragmentcode.infrastructure.utils.BarQrCodeService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("tt")
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


    /**
     * http://127.0.0.1:6677/tt/qr?code=hello&size=10
     *
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("/qr")
    public String getqr(String code, Integer size) throws Exception {
        val hello_world = BarQrCodeService.createQRStr(code, size);
        System.out.println(hello_world);
        return hello_world;
    }

    //http://172.16.91.74:8877/test/tt/qr_1?code=http://www.baidu.com&size=80
    @GetMapping("/qr_1")
    public String getqrtest(String code, Integer size) throws Exception {
        val hello_world = BarQrCodeService.createQRStr1(code, size);
        System.out.println(hello_world);
        return hello_world;
    }

    /**
     * http://127.0.0.1:8877/test/tt/qr2?code=http://www.baidu.com&size=80
     *
     * @param response
     * @param code
     * @param size
     * @throws Exception
     */
    @GetMapping("/qr2")
    public void getqr2(HttpServletResponse response, String code, Integer size) throws Exception {
        BarQrCodeService.getqrSVGStrOut(response, code, size);
    }

    @GetMapping("/bar")
    public void getbar(HttpServletResponse response, String code) throws Exception {
        BarQrCodeService.getBarCodeSVGStrOut(response, code);
    }

    @GetMapping("/bar2")
    public String getbar2(HttpServletResponse response, String code) throws Exception {
        return BarQrCodeService.getBarCodeSVGStr(code);
    }
}
