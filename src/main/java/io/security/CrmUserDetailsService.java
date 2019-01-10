package io.security;

import io.models.Role;
import io.models.User;
import io.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CrmUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public void setPasswordEncoder(PasswordEncoder passwordEncoder){this.passwordEncoder=passwordEncoder;}

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

       User user = this.userRepository.findUserByEmail(userName);

       if(user !=null){
           return new CustomerUserDetails(user);
       }
       else{
           throw new UsernameNotFoundException("couldn't not find "+userName+ " !");
       }

    }
}
