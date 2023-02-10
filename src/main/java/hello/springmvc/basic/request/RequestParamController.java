package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    //Get 방식의 쿼리 파라미터든 Post 요청방식이든 우린 다 꺼낼 수 있던거 기억.
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    // 결국 @RequestParam("username") String memberName 이거나  request.getParameter("username") 이거나 똑같은 것.
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";        //조심 !! @Controller면서 메소드 타입이 String이면??  뷰 리졸버에게 논리 뷰 이름을 전달한다. 우리 이전에 "new-form"
        //그래서 클래스에 @RestController 주석을 달아 주던가  아니면 메소드 위에 @ResponseBody 를 명시해주면 Http 응답 메시지에 "ok" 문자를 넣어서 반환.
    }


    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(           // 요청으로 인해 넘어온 파라미터 이름이 내가 지정한 변수명과 같다면 생략해도 된다.
                                            @RequestParam String username,
                                            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) { // 파라미터 이름이 요청 파라미터 이름과 일치해야 됨.
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(         //(required = true)가 default  // false로 되어 있으면 요청 파라미터에서 꼭 넘어올 필요 없음. //true로 되어 있으면 요청파라미터에서 꼭 넘어와야함.
                                                @RequestParam(required = true) String username,       /* null이랑 ""은 다르다 */
                                                @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }
    //age는 원시타입이라  null이 들어갈 수 없음.그래서 오류가 남.  int -> Integer 래퍼 클래스로 바꿔주면 null 넘어와서 정상 동작.

    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {       //값이 안들어오면 defaultValue로 값이 정해짐. 그래서Integer를 안써도 됨. //defaultValue="" 빈 문자도 가능함.
        log.info("username={},  age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {       //사용자가 파라미터로 전달할 값의 타입이 문자열, 숫자, 날짜 등으로 다양할 수 있습니다. 그런 의미에서 Object를 사용하여 다양한 값이 올 수 있음을 명시할 수 있습니다.
        //Object 로 문자열, 숫자, 날짜를 모두 받을 순 있으나 결국 사용하려면 다운캐스트 해야 하는 점.
        log.info("username = {}, age={}", paramMap.get("username"), paramMap.get("age"));

        //String str = paramMap.get("username").toString();
        int age = Integer.parseInt(paramMap.get("age").toString());
        log.info("down-casting age = {}", age);
        return "ok";
    }
    //log.info의 치환문자 형식에 대해서...
    //형식 지정자를 따로 사용하지 않아도 로거 문구가 알아서 잘 생성되는 이유는 toString()메소드를 이용해 모든 파라미터를 String 형으로 바꿔주기 때문입니다. (MessageFormatter.safeObjectAppend 참고!)
    //파라미터에 String 타입이 아닌 Map이나 다른 객체가 들어와도 에러가 나지 않는 이유는 모든 Object는 toString() 메서드를 가지고 있기 때문.


    @ResponseBody
    @RequestMapping("/model-attribute-v1")                  //@ModelAttribute 특정 네임을 정하지 않으면  Item 클래스의 앞부분 소문자로 한 -> item을 이름으로 요청된 파라미터 값들 바인딩 후 model.addAttribute("item",item)으로 담긴다
    public String modelAttributeV1(/*@RequestParam String username, @RequestParam int age*/ @ModelAttribute HelloData helloData){
        /*HelloData helloData =new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);
*/
        log.info("username = {}, age={}",helloData.getUsername(),helloData.getAge());
        log.info("helloData={}",helloData);     //log format에 toString()이 있기 때문에 객체만 넣어줘도 찍힘.
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(/* 1. @RequestParam String username, @RequestParam int age*/ /* 2. @ModelAttribute HelloData helloData*/ HelloData helloData){
        /*HelloData helloData =new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);
*/      //이번엔 @ModelAttribute 를 생략 했다.
        log.info("username = {}, age={}",helloData.getUsername(),helloData.getAge());
        log.info("helloData={}",helloData);     //log format에 toString()이 있기 때문에 객체만 넣어줘도 찍힘.
        return "ok";
    }

    //그럼 @RequestParam도 생략하고 @ModelAttribute도 생략했는데 뭐지? -> 스프링은 해당 파라미터들 생략시 규칙이 있다.
    //1. String, int, Integer 같은 단순 타입을 @RequestParam으로 처리
    //2. 그 외 내가만들 클래스들 같은 것은 @ModelAttribute로 처리 (argument resolver로 지정해둔 타입은 제외 ex, HttpServletRequest 같은 것들..)
}

//스프링MVC는 앞서 설명한 것 보다 조금 더 복잡하게 동작합니다. 메서드에 있는 @RequestMapping에 있는 URL 정보와 메서드 이름을 모두 알고 있습니다. 그래서 어떤 메서드를 호출해야 할지 이해하고 있습니다.
//스프링MVC는 수 많은 파라미터를 처리할 수 있게 이미 다 설계가 되어있습니다.