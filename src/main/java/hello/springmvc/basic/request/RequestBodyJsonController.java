package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username":"hello", "age":20}
 * content-type: application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {
    private ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();       //HTTP 메시지 바디에 있는것을 가져온다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);//messageBody를 HelloData 클래스 타입으로
        //ObjectMapper란?
        //JSON 컨텐츠를 Java 객체로 deserialization 하거나 Java 객체를 JSON으로 serialization 할 때 사용하는 Jackson 라이브러리의 클래스이다.
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");

    }

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);//messageBody를 HelloData 클래스 타입으로
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";

    }

    //@RequestBody에 직접 만든 객체를 파라미터로 넘길 수 있다.  // HttpEntity나 @RequestBody를 사용하면 HTTP메시지 컨버터가 바디 메시지의 내용을 우리가 원하는 문자나 객체로 변환해준다.
    //HTTP 컨버터가  HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); 과정을 대신 해주는 것.
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData helloData) throws IOException {       //@RequestBody 생략 불가. 생략하면 @ModelAttribute가 적용된다.

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";

    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {
        HelloData helloData = httpEntity.getBody();
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";

    }

    @ResponseBody
    @PostMapping("/request-body-json-v5")
    // v4와 리턴 타입이 다름,  HTTP 메시지 Converter가 들어올때도 적용되지만  @ResponseBody가 있으면 나갈 때 도 적용된다.
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) {

        log.info("username={}, age={}", data.getUsername(), data.getAge());
        return data;        //HelloData라는 타입의 객체가 HTTP 메시지 컨버터로 인해 JSON으로 바뀌고 이 바뀐 문자가 HTTP 응답으로 나가는 것.

    }//1. HTTP 요청 메시지를 JSON 형식으로 보냄
     //2. @RequestBody나 HttpEntity<>를 사용하면  HTTP 컨버터가  HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); 을 진행 해줌.
     //3. 내가 만든 클래스 타입으로 된 객체로, JSON -> 객체로 변환이 됨
     //4. 리턴 타입이 아까와는 다르게 HelloData 클래스 타입임. return data를 하면 다시 객체를 -> JSON 타입으로 바꿔서 HTTP 응답 메시지에 보내서 우리가 보게 됨. (@ResponseBody는 나갈때, 들어올때 다 적용)
}
