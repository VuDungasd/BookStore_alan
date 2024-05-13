package com.example.ecomerce_bookstore;

import com.example.ecomerce_bookstore.config.AppConstants;
import com.example.ecomerce_bookstore.entities.Role;
import com.example.ecomerce_bookstore.entities.User;
import com.example.ecomerce_bookstore.exceptions.ResourceNotFoundException;
import com.example.ecomerce_bookstore.repositories.RoleRepo;
import com.example.ecomerce_bookstore.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class EcomerceBookstoreApplication implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(EcomerceBookstoreApplication.class, args);
    }

    @Autowired
    UserRepo userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        try {
//            Role adminRole = new Role();
//            adminRole.setRoleId(AppConstants.ADMIN_ID);
//            adminRole.setRoleName("ADMIN");
//
//            Role userRole = new Role();
//            userRole.setRoleId(AppConstants.USER_ID);
//            userRole.setRoleName("USER");
//
//            List<Role> roles = List.of(adminRole, userRole);
//
//            List<Role> savedRoles = roleRepo.saveAll(roles);
//
//            savedRoles.forEach(System.out::println);


//             add cứng ngừoi dùng
//
//             Tìm kiếm vai trò trong cơ sở dữ liệu
//            Role role = roleRepo.findById(AppConstants.USER_ID)
//                    .orElseThrow(() -> new ResourceNotFoundException("Role", "roleId", AppConstants.USER_ID));
//
//            User user = new User();
//            user.setEmail("loda@gmail.com");
//            user.setPassword(passwordEncoder.encode("1234"));
//
//            System.out.println("chay duoc role");
//            userRepository.save(user);
//            System.out.println(user);


//                User user = new User();
//                user.setEmail("loda@gmail.com");
//                user.setPassword(passwordEncoder.encode("1234"));

                // Lưu lại thông tin của người dùng để cập nhật vai trò
//                userRepository.save(user);
//                System.out.println(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}


}
