package doctorsOffice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = "/")
public class HomeController {
    @GetMapping()
    public String showHomeScreen(){
        return "home";
    }//end showHomeScreen()

    {
        log.info("LOG: HomeController Objekt/Bean wird erstellt");
    }

}//end class
