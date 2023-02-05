package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 편리한 축약 어노테이션
 * * @GetMapping
 * * @PostMapping
 * * @PutMapping
 * * @DeleteMapping
 * * @PatchMapping
 */

@RestController
public class MappingController {
    private Logger log = LoggerFactory.getLogger(MappingController.class);

    @RequestMapping(value={"/hello-basic","/hello-gogo"}, method= RequestMethod.GET)
    public String helloBasic(){
        log.info("hellobasic");
        return "ok";
    }

    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable userId
     *
     * /mapping/userA   url자체에 뭔가 값이 들어가 있는 것.
     * @return
     */
    //요즘 스타일
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId")String data) {     //@PathVariable String userId 변수명이 동일하면 앞에 생략 가능.
        //url 에 값을 써서 그 값을 PathVariable로 userId 값을 꺼내서 사용할 수 있다.
        log.info("mappingPath userId ={}",data);
        return "ok";

    }

    //다중 매핑             특정 유저에 대해 그 유저가 주문한 주문번호를 달라.
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long orderId){
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**              아래는 사용할 일이 거의 없다.
     *
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")        //요청이 왔을 때 특정 파라미터 정보가 있어야 출력된다./mapping-param?mode=debug
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }


    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")      //post man의 헤더에 key value를 넣어서 사용
    public String mappingHeader() {
        log.info("mappingHeader");
        return "ok";
    }


    /**
     * Content-Type 헤더 기반 추가 매핑  *** Media Type ***
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    //컨텐트 타입에 따라 분리할 수 있다.      //text/html 등등..
    @PostMapping(value="/mapping-consume",consumes= MediaType.APPLICATION_JSON_VALUE) //헤더에 컨텐트 타입이 JSON인 경우에만 호출된다.
    //consumes 은 서버입장을 기준으로 클라이언트가 보낸것이 요청헤더의 컨텐트 타입이 위의 consumes로 들어온 경우만 처리해준다.   
    public String mappingConsumes(){
        log.info("mappingConsumes");
        return "ok";
    }
    //consumes="application/json" 으로 직접 적거나 위에 처럼 하거나.

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    //Accept: 클라이언트가 선호하는 미디어 타입 전달         // produces는 컨트롤러 입장 기준
    @PostMapping(value = "/mapping-produce", produces = "text/html")        //얘는 text/html을 생산하는 애다. PostMan으로 http 요청시 Accept가 text/html로 되어있어야 한다.
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }//Post Man으로 테스트시 잘 실행이 된다  왜냐면 Accept가 */* 로 되어 있었기 때문에..  PostMan의 Accept를 수정하면서 테스트 해라.
    //Accept application/json 로 수정하면 클라이언트는 자기가 요청 후 서버가 보낸것이 json이면 받아 들일 수 있다 라는 말. 다른것으로 하면 not Allowed

    //PostMan
}
