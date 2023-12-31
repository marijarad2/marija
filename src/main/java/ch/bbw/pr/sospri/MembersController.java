package ch.bbw.pr.sospri;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UsersController
 *
 * @author Marija Radovanovic
 * @version 15.03.2023
 */
@Controller
public class MembersController {
   @Autowired
   MemberService memberservice;
   Logger logger = LoggerFactory.getLogger(MembersController.class);

   @GetMapping("/get-members")
   public String getRequestMembers(Model model) {
      System.out.println("getRequestMembers");
      model.addAttribute("members", memberservice.getAll());
      return "members";
   }

   @GetMapping("/edit-member")
   public String editMember(@RequestParam(name = "id", required = true) long id, Model model) {
      Member member = memberservice.getById(id);
      System.out.println("editMember get: " + member);
      model.addAttribute("member", member);
      return "editmember";
   }

   @PostMapping("/edit-member")
   public String editMember(Member member, Model model) {
      System.out.println("editMember post: edit member" + member);
      Member value = memberservice.getById(member.getId());
      value.setAuthority(member.getAuthority());
      System.out.println("editMember post: update member" + value);
      memberservice.update(member.getId(), value);
      logger.warn("User bearbeitet");
      return "redirect:/get-members";
   }

   @GetMapping("/delete-member")
   public String deleteMember(@RequestParam(name = "id", required = true) long id, Model model) {
      System.out.println("deleteMember: " + id);
      memberservice.deleteById(id);
      logger.warn("Warnung User ist gelöscht");
      return "redirect:/get-members";
   }
}
