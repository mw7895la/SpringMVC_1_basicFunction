package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

//  /mapping/users 이 공통 경로다
@RequestMapping("/mapping/users")
@RestController
public class MappingClassController {
    /**
     * 회원 목록 조회: GET /users
     * 회원 등록: POST /users
     * 회원 조회: GET /users/{userId}
     * 회원 수정: PATCH /users/{userId}
     * 회원 삭제: DELETE /users/{userId}
     *
     * @return
     */
    @GetMapping           //  @GetMapping("/mapping/users")
    public String users() {
        return "get users";
    }

    @PostMapping
    public String addUser() {
        return "post user";
    }

    @GetMapping("/{userId}")
    public String findUser(@PathVariable("userId") String userId) {
        return "get userId= " + userId;
    }

    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable("userId") String userId) {
        return "update userId= " + userId;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        return "delete userId= "+userId;
    }
}
