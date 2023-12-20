package ch.bbw.pr.sospri.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * A chat-member
 *
 * @author Peter Rutschmann
 * @version 15.03.2023
 */
@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
   @Id
   @GeneratedValue(generator = "generatorMember", strategy = GenerationType.SEQUENCE)
   @SequenceGenerator(name = "generatorMember", initialValue = 20)
   private Long id;

   @NotEmpty(message = "prename may not be empty")
   @Size(min = 2, max = 512, message = "Die Länge des Vornamens muss 2 bis 25 Zeichen sein.")
   private String prename;

   @NotEmpty(message = "lastname may not be empty")
   @Size(min = 2, max = 20, message = "Die Länge des Nachnamens 2 bis 25 Zeichen sein.")
   private String lastname;
   @NotEmpty
   private String password;
   @NotEmpty
   private String username;

   private String authority = "member";
}
