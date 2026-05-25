package com.blogging.Service;

import com.blogging.Exception.ResourceNotFoundException;
import com.blogging.Model.Role;
import com.blogging.Model.User;
import com.blogging.Repository.UserRepository;
import com.blogging.config.AppConstants;
import com.blogging.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDto createUser(UserDto userDto){
        User user = modelMapper.map(userDto,User.class);
        //we are encoding password:
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //user: role as default
        user.setUserRole(Role.valueOf(AppConstants.ROLE_USER));
        User savedUser = userRepository.save(user);
        return  modelMapper.map(savedUser,UserDto.class);
    }

    public UserDto updateUser(UserDto userDto, String id){
        //getting user from repository
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found"));

        //Updating user details
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

       User updatedUser = userRepository.save(user);
       //return UserDto
       return modelMapper.map(updatedUser,UserDto.class);

    }

    public List<UserDto> getAllUsers(){
        List<User> users =  userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
    }

    public UserDto getUserById(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
        return modelMapper.map(user, UserDto.class);
    }

    public void deleteUser(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
        userRepository.delete(user);
    }


}
