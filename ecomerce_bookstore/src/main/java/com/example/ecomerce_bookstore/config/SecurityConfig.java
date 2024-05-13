package com.example.ecomerce_bookstore.config;

import com.example.ecomerce_bookstore.config.AppConstants;
import com.example.ecomerce_bookstore.security.JWTFilter;
import com.example.ecomerce_bookstore.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
//                .requestMatchers(AppConstants.PUBLIC_URLS).permitAll()
                .requestMatchers(AppConstants.USER_URLS).permitAll()
                .requestMatchers(AppConstants.ADMIN_URLS).permitAll()
                .anyRequest().permitAll()
//                .authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();

//        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http.authenticationProvider(daoAuthenticationProvider());
//
//        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

//        return defaultSecurityFilterChain;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}




// ////////////////////start

//package com.example.ecomerce_bookstore.config;
//
////import com.example.ecomerce_bookstore.security.JWTFilter;
////import com.example.ecomerce_bookstore.services.UserDetailsServiceImpl;
//import com.example.ecomerce_bookstore.security.JWTFilter;
//import com.example.ecomerce_bookstore.services.UserDetailsServiceImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import jakarta.servlet.http.HttpServletResponse;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private JWTFilter jwtFilter;
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsServiceImpl;
//
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf()
////                .disable()
////                .authorizeHttpRequests()
//////                .permitall()
//////                .requestMatchers(AppConstants.USER_URLS).hasAnyAuthority("USER", "ADMIN")
//////                .requestMatchers(AppConstants.ADMIN_URLS).hasAuthority("ADMIN")
////                .requestMatchers(AppConstants.USER_URLS).hasAnyRole("USER", "ADMIN")
////                .requestMatchers(AppConstants.ADMIN_URLS).hasRole("ADMIN")
////                .anyRequest()
////                .authenticated()
////                .and()
////                .exceptionHandling().authenticationEntryPoint(
////                        (request, response, authException) ->
////                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
////                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////
//////        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
////
//////        http.authenticationProvider(daoAuthenticationProvider());
////
//////        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();
////
//////        return defaultSecurityFilterChain;
////        return http.build();
////
////    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//
//        provider.setUserDetailsService(userDetailsServiceImpl);
//        provider.setPasswordEncoder(passwordEncoder());
//
//        return provider;
//    }

/////////////////////////////   end

//    // đã chạy được
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable()
////                    .authorizeHttpRequests()
////                    .anyRequest()
////                    .permitAll()
////                    .and()
////                .sessionManagement()
////                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                    .and()
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Thêm JWTFilter trước UsernamePasswordAuthenticationFilter
////
////        return http.build();
////    }
//    // tới đây end
//
//            //http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
//
//            //http.authenticationProvider(daoAuthenticationProvider());
////    }
//
//
//    //@Bean
//    //publicDaoAuthenticationProviderdaoAuthenticationProvider(){
//    //DaoAuthenticationProviderprovider=newDaoAuthenticationProvider();
//    //
//    //provider.setUserDetailsService(userDetailsServiceImpl);
//    //provider.setPasswordEncoder(passwordEncoder());
//    //
//    //returnprovider;
//    //}
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
//        return configuration.getAuthenticationManager();
//    }
//
////    protected void configure(HttpSecurity http) throws Exception {
////        http.csrf().disable()
////                .authorizeRequests()
////                .requestMatchers("/api/login").permitAll()
////                .requestMatchers("/api/register").permitAll()
//////                .antMatchers("/api/login").permitAll()
//////                .antMatchers("/api/register").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .exceptionHandling().authenticationEntryPoint((request, response, authException) ->
////                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
////                .and()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                .and()
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
////    }
//
//
//
//}





//package com.example.ecomerce_bookstore.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.example.ecomerce_bookstore.security.JWTFilter;
//import com.example.ecomerce_bookstore.services.UserDetailsServiceImpl;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private JWTFilter jwtFilter;
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsServiceImpl;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/login").permitAll() // Cho phép truy cập vào đường dẫn /api/login mà không cần xác thực
//                .antMatchers("/api/register").permitAll() // Cho phép truy cập vào đường dẫn /api/register mà không cần xác thực
//                .anyRequest().authenticated() // Các yêu cầu khác cần được xác thực
//                .and()
//                .exceptionHandling().authenticationEntryPoint(
//                        (request, response, authException) ->
//                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}
