package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody ={}", messageBody);
        response.getWriter().write("ok");
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        //ServletInputStream inputStream = request.getInputStream();

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody ={}", messageBody);
        //response.getWriter().write("ok");
        responseWriter.write("ok");
    }

    //  HttpEntity<HelloData> httpEntity도 해볼것
    @PostMapping("/request-body-string-v3")
    //Http Message Converter  스프링이 제공하는 기능 -> HttpEntity가 너 문자구나 그럼 내가 Http 바디에 있는걸 문자로 바꿔서 너한테 넣어줄게
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {
        //ServletInputStream inputStream = request.getInputStream();

        //String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        String messageBody = httpEntity.getBody();     //이미 변환된 Http Message Body에 있는 메시지를 꺼낼 수 있다.
        HttpHeaders headers = httpEntity.getHeaders();

        log.info("messageBody ={}", messageBody);

        log.info("headers ={}", headers);
        //response.getWriter().write("ok");
        //responseWriter.write("ok");
        return new HttpEntity<>("ok");
    }

    @PostMapping("/request-body-string-v3-1")           //RequestEntity<HelloData>도 해볼것
    public HttpEntity<String> requestBodyStringV3_1(RequestEntity<String> httpEntity) throws IOException {

        String messageBody = httpEntity.getBody();     //이미 변환된 Http Message Body에 있는 메시지를 꺼낼 수 있다.
        log.info("messageBody ={}", messageBody);

        return new ResponseEntity<String>("ok", HttpStatus.CREATED);     //상태 코드가 201이 되겠지.
    }
    //v1 - v2 - v3 - v3-1 도 귀찮아


    /**
     * @RequestBody 요청
     * JSON 요청 HTTP 메시지 컨버터 객체
     * @ResponseBody 응답
     * 객체 HTTP 메시지 컨버터 JSON 응답
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody){
        log.info("messageBody={}",messageBody);
        return "ok";
    }//요청 오는건 RequestBody로 응답 나가는건 ResponseBody로
}

/**
 * 요청 파라미터는 뭐다?  get 방식으로 쿼리 파라미터 오는것 또는 Post방식으로 Form에서 HTML으로 전송하는 방식 x-www-form-urlencoded  인 경우에만 @RequestParam과 @ModelAttribute를 사용할 수 있다.
 * application/x-www-form-urlencoded 사용
 * • form의 내용을 메시지 바디를 통해서 전송(key=value, 쿼리 파라미터 형식)
 * • 전송 데이터를 url encoding 처리
 * • 예) abc김 -> abc%EA%B9%80
 */

