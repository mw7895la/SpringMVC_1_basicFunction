package hello.springmvc.basic.response;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@ResponseBody를 계속 붙이기 귀찮으면 ,  클래스 레벨에 붙일 수 있다. 그럼 다 적용이 된다.

@Slf4j
//@Controller
//@ResponseBody         //이 2개를 합친게 @RestController
@RestController
public class ResponseBodyController {
    @GetMapping("/response-body-string-v1")
    public void responseBodyV1(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }

    @GetMapping("/response-body-string-v2")
    public ResponseEntity<String> responseBodyV2() {
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    //@ResponseBody
    @GetMapping("/response-body-string-v3")
    public String responseBodyV3() {
        return "ok";
    }

    @GetMapping("/response-body-json-v1")
    public ResponseEntity<HelloData> responseBodyJsonV1() {
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);

        return new ResponseEntity<>(helloData, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    //@ResponseBody
    @GetMapping("/response-body-json-v2")
    public HelloData responseBodyJsonV2() {     //  바로 위의 메소드는 상태코드를 바꿀 수 있는데 여기는 못 바꿔. 그래서 어노테이션이 제공이 된다
        //@ResponseStatus 를 사용하면 된다. 원하는 메시지로 나갈 수 있다.
        HelloData helloData = new HelloData();
        helloData.setUsername("userA");
        helloData.setAge(20);
        return helloData;
    }
}
