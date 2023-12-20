package ch.bbw.pr.sospri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/example")
    public String example() {
        try {
            // Do something that may trigger an event
            emailService.sendEmail();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Success";
    }
}
