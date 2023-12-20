package ch.bbw.pr.sospri;

import java.security.SecureRandom;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import ch.bbw.pr.sospri.member.RegisterMember;

import org.slf4j.Logger;

/**
 * RegisterController
 *
 * @author Marija Radovanovic
 * @version 15.03.2023
 */
@Validated
@Controller
public class RegisterController {

   Logger logger = LoggerFactory.getLogger(RegisterController.class);

   @Autowired
   MemberService memberservice;

   @GetMapping("/get-register")
   public String getRequestRegistMembers(Model model) {
      model.addAttribute("registerMember", new RegisterMember());
      return "register";
   }

   // Validierung mit @Valid
   @PostMapping("/get-register")
   public String postRequestRegistMembers(@Valid @ModelAttribute("registerMember") RegisterMember registerMember,
         BindingResult bindingResult, Model model) {
      String username = registerMember.getPrename().toLowerCase() + "." + registerMember.getLastname().toLowerCase();
      Member existingMember = memberservice.getByUserName(username);

      // if (!reCaptchaValidationService.validateCaptcha(captcha)) {
      // logger.debug("Captcha fails");
      // registerMember.setMessage("Captcha was not correct");
      // logger.error("Captcha failure: Captcha was not correct");
      // return "register";

      // }
      // logger.debug("Captcha is correct");

      // Überprüfung auf vorhandene Benutzernamen
      // Überprüft ob der Benutzer bereits in der Datenbank ist
      // Wenn es = null ist heisst es dass heisst es gibt keinen Benuter mit demselben
      // Wert und es kann fortgesetzt werden wenn aber !null hat es bereits einen wert
      if (existingMember != null) {
         System.out.println("User already exists, choose other first- or lastname.");
         registerMember.setMessage("Username " + username + " already exists");
         return "register"; // Formular erneut anzeigen
      }
      // Überprüfung, ob Passwörter übereinstimmen
      // !registerMember.getPassword() passwort wo jetzt eingegeben wurde
      // registerMember.getConfirmation() Passwort Bestätigung
      // equals vergleicht diese
      // Wenn das so ist wird die Methode rejectValue aufgerufen
      if (!registerMember.getPassword().equals(registerMember.getConfirmation())) {
         bindingResult.rejectValue("confirmation", "error.member", "Passwords do not match");
         System.out.println("passowrd do not match");
         // registerMember.setMessage("Passwort ist nicht identisch");

         return "register"; // Formular erneut anzeigen
      }
      // Erzeugt Member und speichert in Datenbank
      ch.bbw.pr.sospri.member.Member member = new ch.bbw.pr.sospri.member.Member();
      member.setPrename(registerMember.getPrename());
      member.setLastname(registerMember.getLastname());
      member.setUsername(username);
      member.setPassword(registerMember.getPassword());

      // Passwort Hash Salt ist in BCrypt
      // wert 10 intialisiert variable für später benutzt anzahl Durchläufe des
      // PasswordHashing definieren
      int strength = 10;
      // strength wird an konstruktor übergeben und SecureRandom als Zufallsgenerator
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(strength, new SecureRandom());
      // Passwort von Member wird gehasht und der Variable encodedPassword zugewiesen
      String encodedPassword = bCryptPasswordEncoder.encode(member.getPassword());
      // Das Passwort des member wird durch das gehashte Passwort ersetzt
      member.setPassword(encodedPassword);
      // member obj in memberservice hinzugefügt
      memberservice.add(member);
      // Show a confirmation page
      // fügt atributt username in obj model
      model.addAttribute("username", username);
      return "registerconfirmed";
   }
}
