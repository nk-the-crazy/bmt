package io.gnt.bmt.identity.service.impl;

import io.gnt.bmt.commons.exceptions.InvalidLoginException;
import io.gnt.bmt.commons.exceptions.SystemSecurityException;
import io.gnt.bmt.commons.utils.JwtUtil;
import io.gnt.bmt.commons.utils.SecurityUtils;
import io.gnt.bmt.identity.model.Role;
import io.gnt.bmt.identity.model.User;
import io.gnt.bmt.identity.repository.RoleRepository;
import io.gnt.bmt.identity.service.IdentityService;
import io.gnt.bmt.identity.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.*;


@Slf4j
@Service
public class IdentityServiceImpl implements IdentityService {

    @Value("${security.jwt.secret}")
    private String secret;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    private JwtUtil jwtUtil;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostConstruct
    void init(){
        if(userRepository.count() < 1 ) {


            Role adminRole = new Role("Administrator");
            Role operatorRole = new Role("Operator");
            roleRepository.saveAll(List.of(adminRole,operatorRole));

            User user = new User();
            user.setUsername("admin");
            user.setPassword("c657540d5b315892f950ff30e1394480");
            user.setSalt("salt");

            user.setRoles(List.of(adminRole, operatorRole));

            userRepository.save(user);
        }
    }

    @Override
    public String generateToken(User user) {

        if(jwtUtil == null)
            jwtUtil = new JwtUtil(secret);

        return jwtUtil.generateToken(user.getUsername());
    }


    @Override
    public User auhtenticateUser( String username, String password ) throws SystemSecurityException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            String pswHash = "";
            try {
                pswHash = SecurityUtils.generateSecurePassword(password, user.get().getSalt());
            } catch (NoSuchAlgorithmException e) {
                /* ignore */
            }

            if (!pswHash.equals(user.get().getPassword())) {
                throw new InvalidLoginException("***** Invalid Login for user:" + username);
            }
        } else {
            throw new InvalidLoginException();
        }

        return user.get();
    }


    public User getUser(long id){

        Optional<User> user = userRepository.findById(id);

        return user.orElse(null);
    }

}



