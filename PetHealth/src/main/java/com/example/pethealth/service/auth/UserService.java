package com.example.pethealth.service.auth;


import com.example.pethealth.components.ImageAvatarUserUtils;
import com.example.pethealth.components.JwtTokenUtil;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.UserResponse;
import com.example.pethealth.dto.authDTO.JWT.JwtLoginDTO;
import com.example.pethealth.dto.authDTO.LoginDTO;
import com.example.pethealth.dto.authDTO.RegisterDTO;
import com.example.pethealth.dto.authDTO.UserDTO;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Image;
import com.example.pethealth.model.Role;
import com.example.pethealth.model.User;
import com.example.pethealth.repositories.auth.UserRepository;
import com.example.pethealth.service.ImageService;
import com.example.pethealth.service.parent.IUserService;

import lombok.AllArgsConstructor;

import org.modelmapper.ModelMapper;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final ImageAvatarUserUtils imageAvatarUserUtils;
    private final  UserRepository userRepository ;
    private final ModelMapper modelMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final RoleService roleService;
    private final ImageService imageService;
    @Override
    public BaseDTO createUser(RegisterDTO request) {
       try{
           boolean user = userRepository.findByUsernameBoolean(request.getUserName());
           if(user != true){
               User newUser = modelMapper.map(request, User.class);
               PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
               Optional<User> test = userRepository.findByEmail(request.getEmail());
               Image image = imageService.findByUrl("b76b5735-800e-40c3-a3ef-f1250366ff06_136-1363971_author-image-logo-user-png.png");
               if(test.isPresent()){
                   return BaseDTO.builder()
                           .message("Email exist!").result(false)
                           .build();
               }
               Role role = roleService.findByCode("ROLE_ADMIN");
               if(role == null){
                   return BaseDTO.builder()
                           .message("dont find by role").result(false)
                           .build();
               }
               newUser.setRole(role);
               newUser.setPassword(passwordEncoder.encode(request.getPassword()));
               newUser.setImage(image);
               userRepository.save(newUser);
               return BaseDTO.builder()
                       .message("success").result(true)
                       .build();
           }
           return BaseDTO.builder()
                   .message("false").result(false)
                   .build();
       }catch (BadRequestException e){
           return BaseDTO.builder()
                   .message(e.getMessage()).result(false)
                   .build();
       }
    }
    @Override
    public User findByIdUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("dont find by id" + id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()-> new BadRequestException("dont find by username: " + username));
    }

    @Override
    public JwtLoginDTO loginUser(LoginDTO request) {
        try{
            User auth = userRepository.findByUsername(request.getUserName()).orElseThrow(()
            -> new BadRequestException("dont find user with username: "+ request.getUserName()));
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

            if(passwordEncoder.matches(request.getPassword(),auth.getPassword())){
                String token = jwtTokenUtil.generateToken(auth);
                return JwtLoginDTO.builder()
                        .baseDTO(BaseDTO.builder().result(true).message("success").build())
                        .token(token)
                        .url(auth.getImage().getUrl())
                        .role(auth.getRole().getCode())
                        .user(auth)
                        .build();
            }else {
                return JwtLoginDTO.builder()
                        .baseDTO(BaseDTO.builder().result(false).message("mat khau sai").build())
                        .build();
            }
        }catch (BadRequestException e){
            return JwtLoginDTO.builder()
                    .baseDTO(BaseDTO.builder().message(e.getMessage()).result(false).build())
                    .build();
        }
    }
    @Override
    public UserResponse findByUserRole(String role) {
        List<UserDTO> userDTOS = new ArrayList<>();
        Role role1 = roleService.findByCode(role);
        List<User> userList = userRepository.findByRole(role1);
        if(userList != null && !userList.isEmpty()){
            for(User items: userList){
                userDTOS.add(new UserDTO().builder()
                        .id(items.getId()).username(items.getUsername()).fullName(items.getFullName())
                        .build());
            }
            return UserResponse.builder()
                    .baseDTO(BaseDTO.builder().message("true").result(true).build())
                    .userList(userDTOS)
                    .build();
        }
        return UserResponse.builder()
                .baseDTO(BaseDTO.builder().message("false").result(false).build())
                .build();
    }

    @Override
    public BaseDTO getAllUser() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> results = converUser(userList);
        return BaseDTO.builder()
                .result(true).results(results)
                .build();
    }

    @Override
    public BaseDTO findUserName(String fullName) {
        List<User> userList = userRepository.findByFullName(fullName);
        List<UserDTO> results = converUser(userList);
        return BaseDTO.builder()
                .result(true).results(results)
                .build();
    }

    @Override
    public BaseDTO findUserWithCode(String code) {
        Role role = roleService.findByCode(code);
        List<User> userList = userRepository.findByRole(role);
        List<UserDTO> results = converUser(userList);
        return BaseDTO.builder()
                .result(true).results(results)
                .build();
    }

    @Override
    public BaseDTO registerUser(RegisterDTO request) {
        try{
            boolean user = userRepository.findByUsernameBoolean(request.getUserName());
            if(user != true){
                User newUser = modelMapper.map(request, User.class);
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
                Optional<User> test = userRepository.findByEmail(request.getEmail());
                Image image = imageService.findByUrl("b76b5735-800e-40c3-a3ef-f1250366ff06_136-1363971_author-image-logo-user-png.png");
                if(test.isPresent()){
                    return BaseDTO.builder()
                            .message("Email exist!").result(false)
                            .build();
                }
                Role role = roleService.findByCode(request.getRole());
                if(role == null){
                    return BaseDTO.builder()
                            .message("dont find by role").result(false)
                            .build();
                }
                newUser.setRole(role);
                newUser.setFullName(request.getFullName());
                newUser.setPassword(passwordEncoder.encode(request.getPassword()));
                newUser.setImage(image);
                userRepository.save(newUser);
                return BaseDTO.builder()
                        .message("success").result(true)
                        .build();
            }
            return BaseDTO.builder()
                    .message("false").result(false)
                    .build();
        }catch (BadRequestException e){
            return BaseDTO.builder()
                    .message(e.getMessage()).result(false)
                    .build();
        }
    }

    @Override
    public BaseDTO updateUser(RegisterDTO registerDTO, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new BadRequestException("dont find by userId")
        );
        if(!registerDTO.getRole().isEmpty()){
            Role role = roleService.findByCode(registerDTO.getRole());
            user.setRole(role);
        }
        if(!registerDTO.getFullName().isEmpty()){
            user.setFullName(registerDTO.getFullName());
        }
        if(!registerDTO.getAddress().isEmpty()){
            user.setAddress(registerDTO.getAddress());
        }
        if(!registerDTO.getPhoneNumber().isEmpty()){
            user.setAddress(registerDTO.getPhoneNumber());
        }
        if(!registerDTO.getEmail().isEmpty()){
            user.setAddress(registerDTO.getEmail());
        }
        userRepository.save(user);
        return BaseDTO.builder()
                .message("thay đổi thông tin thành công!!")
                .result(true)
                .build();
    }

    @Override
    public BaseDTO findByUserWithId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new BadRequestException("dont find by user")
        );
        RegisterDTO userOutPut = RegisterDTO.builder()
                .userName(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole().getName())
                .address(user.getAddress())
                .build();

        return BaseDTO.builder()
                .result(true)
                .object(userOutPut)
                .build();
    }

    public List<UserDTO> converUser(List<User> userList){
        List<UserDTO> results = new ArrayList<>();

        for(User user : userList){
            String username = "";
            if(user.getFullName() != null){
                username = user.getFullName();
            }else{
                username = user.getUsername();
            }
            String role = "";
            if(user.getRole() != null){
                role = user.getRole().getName();
            }
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .fullName(username)
                    .gmail(user.getEmail())
                    .address(user.getAddress())
                    .role(role)
                    .build();
            results.add(userDTO);
        }
        return results;
    }
}
