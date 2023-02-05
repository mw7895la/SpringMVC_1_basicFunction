package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {

    //@RequestHeader MultiValueMap<String,String> headerMap 헤더를 한번에 다 받을 때.  //MultiValueMap<key,value> 는 스프링에서 지원하는 것.
    //@CookieValue(value="myCookie", required=false) String cookie  // value는 쿠키 이름,  required false면 없어도 된다.뜻.
    @RequestMapping("/headers")
    public String headers(              //@RequestMapping이 붙어있는 메소드의 파라미터는 아래것들 이상으로 지원이 된다.
            HttpServletRequest request,
            HttpServletResponse response,
            HttpMethod httpMethod,
            Locale locale,
            @RequestHeader MultiValueMap<String,String> headerMap,
            @RequestHeader("host") String myHost,
            @CookieValue(value="myCookie", required=false) String cookie
            ) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", myHost);
        log.info("myCookie={}", cookie);
        return "ok";
    }
}
