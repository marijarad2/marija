package ch.bbw.pr.sospri;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginLogoutController {

    Logger logger = LoggerFactory.getLogger(LoginLogoutController.class);

    // HTTP Anfrage zeigt view login wenn erfolgreich angemeldet
    @GetMapping("/login")
    public String login() {
        logger.info("Erfolgreich eingeloggt {}");
        logger.debug("Add Debug bei erstellen von usern");
        logger.error("Error beim login");
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

}
