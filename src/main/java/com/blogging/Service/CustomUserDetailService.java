package com.blogging.Service;

import com.blogging.Exception.ResourceNotFoundException;

import com.blogging.Model.User;
import com.blogging.Repository.UserRepository;
import com.blogging.Security.CustomUserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        CustomUserDetail customUserDetail =  new CustomUserDetail(user);
        return customUserDetail;
    }
}
