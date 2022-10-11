package ru.springBoot311.SpringBoot.sources.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.springBoot311.SpringBoot.sources.service.UserSecurityServiceImpl;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    BcryptBean bcryptBean;
    private final UserSecurityServiceImpl userSecurityService;

    @Autowired
    public SecurityConfig(UserSecurityServiceImpl userSecurityService, BcryptBean bcryptBean) {
        this.userSecurityService = userSecurityService;
        this.bcryptBean = bcryptBean;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(bcryptBean.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin", "/admin/allUsers").hasRole("ADMIN")
                .antMatchers("/", "/userInformation").hasAnyRole("ADMIN","USER")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }
}
