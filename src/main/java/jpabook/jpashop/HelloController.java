package jpabook.jpashop;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){ //앞의 모델이 뒤의 변수에게 데이터를 실어서 뷰에 넘길 수 있음
        model.addAttribute("data", "hello!!"); //데이터 키에 헬로우 값을 넘길것임
        return "hello";
    }
}
