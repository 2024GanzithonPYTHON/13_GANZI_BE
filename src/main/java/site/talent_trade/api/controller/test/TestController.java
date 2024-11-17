package site.talent_trade.api.controller.test;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.talent_trade.api.util.response.ResponseDTO;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("")
  public ResponseDTO<String> test() {
    return new ResponseDTO<>("Hello, World!", HttpStatus.OK);
  }
}
