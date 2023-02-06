package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {               // viewName에 대해서는 6장 스프링MVC -기본기능 의 39~40 p
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello").addObject("data", "hello");

        return mav;

    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello!");
        return "response/hello";
        //@Controller면서 String으로 반환하면  뷰의 논리적 이름이다.     메소드에 @ResponseBody하거나 클래스에 @RestController하면 그냥 메시지응답으로 나간다.
    }

    @RequestMapping("/response/hello")
    public void responseViewV3(Model model){
        model.addAttribute("data","hello!");
    }
    //리턴 타입이 void 면  조건이 있다.     @Controller를 사용하고 요청경로가 뷰 템플릿 경로와 같으면 스프링이 알아서 뷰 리졸버 실행 및 렌더링 해줌.
    // V3 방식은 사용하지 않는 것을 권장.
}
