package ch.bbw.pr.sospri.member;

import org.springframework.security.core.GrantedAuthority;

//GrantedAuthority representiert Berechtigung oder ROlle 
// Die Klasse implementiert das Spring Security Interface GrantedAuthority schnittstelle, um rollen / berechtigungen eines benutzers zu repräsentieren
public class MemberGrantedAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 8835903531623403993L;
    // speicher Name der ROlle
    private String authority;

    public MemberGrantedAuthority(String authority) {
        super();
        // speichert die Rolle in authority
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return "MemberGrantedAuthority [authority=" + authority + "]";
    }

}
