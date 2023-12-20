package ch.bbw.pr.sospri.member;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class MemberToUserDetailsMapper {
    static public UserDetails toUserDetails(Member member) {
        User user = null;
        // Wenn nicht null erstellt eine Methode Arraylist namens authorities und fügt
        // eine SimpleGrantedAuthority Instanz hinzu, die von getAuthority methode des
        // objekts member zurückgegeben wird
        // Die authorities Liste wird später dem User Obj als Teil seiner Konstruktion
        // hinzugefügt
        if (member != null) {
            System.out.println("MemberRoUserDetailsMapper.toUserDetails(): member: " + member);

            java.util.Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(member.getAuthority()));
            System.out.println("MemberToUserDetailsMapper.toUserDetails(): authorities");
            System.out.println("MemberToUserDetailsMapper.toUserDetails(): authorities"
                    + Arrays.toString(authorities.toArray()));
            // User Objekt erstellt führt Prename und Lastname zusammen wird als
            // Benutzername verwendet
            user = new User(member.getPrename() + " " + member.getLastname() // getjava.lang.String username
            // gibt das Passwort des Mitglieds zurück
                    , member.getPassword() // java.lang.String password
                    , true // boolean enable acc aktiviert?
                    , true // boolean accountNonExpired Konto noch gültig
                    , true // boolean credentialsNonExpired ist konto abgelaufen ? Wäre es false musste man
                           // sich noch einmal anmelden
                    , true // boolean accountNonLocked acc nicht gesperrt
                    , authorities // java.util.Collection<? extends GrantedAuthority> authorities) fügt die
                                  // Benutzer berechtigten Rollen hinzu

            );
        }
        return user;

    }

}
