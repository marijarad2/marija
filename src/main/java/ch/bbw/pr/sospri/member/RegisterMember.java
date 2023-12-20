package ch.bbw.pr.sospri.member;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// ermöglich dass ich getter und setter nicht mehr immer schreibe sondern machts automatisch
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * To regist a new Member
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Getter
@Setter
@ToString
public class RegisterMember {

   @Size(min = 2, max = 30)
   private String prename;

   @NotEmpty
   private String lastname;

   // die Regex Validierung frontend
   @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")
   private String password;

   @NotEmpty
   private String confirmation;

   private String message;
}
