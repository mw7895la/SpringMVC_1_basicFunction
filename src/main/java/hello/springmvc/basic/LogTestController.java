package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j      //Lombok이 제공하는 애노테이션
@RestController
public class LogTestController {
    //private final Logger log = LoggerFactory.getLogger(LogTestController.class);    //이거 매번 쓰기 귀찮으면 @Slf4j 어노테이션 추가해라

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        //과거엔
        System.out.println("name = " + name);
        //log.trace("trace log={} ,{}",name, name2); {}으로 치환이 된다.
        log.trace("trace log={}",name); //trace와 debug로그는 남지 않는다.
        log.debug("debug log={}",name);// 개발 서버에서 보는 로그
        log.info("info log="+name);
        log.warn("warn log={}",name);
        log.error("error log={}",name);
        return "ok";
    }
}
