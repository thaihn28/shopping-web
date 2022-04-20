package com.example.shoppingweb.config;

//see explain detail at 36:00
import com.example.shoppingweb.config.jwtconfig.JwtAuthenticationEntryPoint;
import com.example.shoppingweb.config.jwtconfig.JwtRequestFilter;
import com.example.shoppingweb.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
public class SecurityConfig {

    @Lazy
    @Autowired
    private AdminService adminService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //có jwt như có chìa khóa, cầm chìa khóa mở uri trong controller, để có chìa khóa cần phải đc authenticate bởi bảo vệ
    //flow: client login -> server create jwt -> send jwt to client -> client use jwt to authenticate itself -> server validates jwt -> respond to client
    @Configuration
    @Order(1)
    public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**") //mọi request có api sẽ vào đây trước
                    .cors().and()
                    .csrf().disable()
                    .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                    //.antMatchers("**/test/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //bỏ session

            // Add a filter to validate the tokens with every request
            //sử dụng doFilter (like addFilterBefore): mọi request ngoài login phải đc authenticated trước kji thực hiện lệnh từ phía controller
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }


        @Autowired
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

    @Configuration
    public class FormSecurityConfig extends WebSecurityConfigurerAdapter {
        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
            auth.setUserDetailsService(adminService);
            auth.setPasswordEncoder(passwordEncoder());
            return auth;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http//add this to enable mail sent, have not known yet
                    .authorizeRequests()
                    .antMatchers(
                            "/js/**",
                            "/css/**",
                            "/img/**").permitAll()
                    .antMatchers("/registration").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/admin", true)
                    .permitAll()
                    .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                    .and().exceptionHandling().accessDeniedPage("/access-denied");;
        }

    }
}
