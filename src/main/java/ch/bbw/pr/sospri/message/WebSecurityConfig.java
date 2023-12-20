package ch.bbw.pr.sospri.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ch.bbw.pr.sospri.member.MemberService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    // Konfiguration der globalen Sicherheit mit In-Memory-Authentifizierung
    // @Autowired
    // public void globalSecurityConfiguration(AuthenticationManagerBuilder auth)
    // throws Exception {
    // MemoryBasedAuthentication
    // auth.inMemoryAuthentication().withUser("user").password("{noop}1234").roles("user");
    // auth.inMemoryAuthentication().withUser("admin").password("{noop}5678").roles("user",
    // "admin");

    // }

    @Autowired
    private MemberService memberservice;

    // PasswordEncoder verschlüsselt das Passwort vom User
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.memberservice);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    // Konfiguration der HTTP-Sicherheit
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Ausgabe einer Meldung zur Konfiguration der HTTP-Sicherheit
        System.out.println("Using default configure(HttpSecurity)." +
                "If subclassed this will potentially override sublass configure(HttpSecurity).");
        // Konfiguration der Zugriffsberechtigungen auf die Pfade
        http.authorizeRequests()
                // Zugriff auf die Console der DB
                .antMatchers("/h2-console/***").permitAll()
                // .antMatchers("/get-channel").authenticated()
                // antMatchers welch URL/File/Zugriff
                .antMatchers("/get-channel").hasAnyAuthority("admin", "supervisor", "member")
                .antMatchers("/").permitAll()
                // .antMatchers("/get-members").hasRole("admin")
                .antMatchers("/get-members").hasAuthority("admin")
                // Für jeden anderen Request hat jeder Zuggriff
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // Logout-URL hinzufügen
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403.html")
                // erstellung von Session if required: dann mach eine session
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // Zugriff auf die Console der DB jeder hat zugriff ignoringAntMatchers() man
        // ohne das Spring Security den Zugriff blockiert
        // braucht keinen Token für die URL
        http.csrf().ignoringAntMatchers("/h2-console/***")
                .and().headers().frameOptions().sameOrigin();
        // Laden der Seiter nur erlaubt wenn gleicher Domän
    }

}
