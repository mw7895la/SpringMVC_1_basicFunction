package hello.springmvc.basic;

import lombok.Data;

//@Data 어노테이션을 활용하면 @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor를 자동완성 시켜준다. 실무에서는 너무 무겁고 객체의 안정성을 지키기 때문에 @Data의 활용을 지양한다.
//@RequiredArgsConstructor는 특정 변수만을 활용하는 생성자를 자동완성 시켜주는 어노테이션이다.

@Data
public class HelloData {
    private String username;
    private int age;

}
