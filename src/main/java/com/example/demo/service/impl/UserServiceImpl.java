package com.example.demo.service.impl;

import com.example.demo.dto.response.CheckOtpResponse;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.entity.OtpUser;
import com.example.demo.entity.RoleUser;
import com.example.demo.entity.User;
import com.example.demo.exception.IllegalArgumentInMyFunctionException;
import com.example.demo.exception.NotFoundRecordInDataException;
import com.example.demo.repository.OtpUserRepository;
import com.example.demo.repository.RoleUserRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IUserService;
import com.example.demo.util.EmailSenderUtil;
import com.example.demo.util.GenerateCodeUtil;
import com.example.demo.util.GenerateTokenHMAC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private OtpUserRepository otpUserRepository;
    @Autowired
    private EmailSenderUtil emailSenderUtil;
    @Autowired
    private GenerateTokenHMAC generateTokenHMAC;
    private static final Integer ID_ROLE_STAFF = 2;
    @Autowired
    private RoleUserRepository roleUserRepository;

    /**
     * check username and password
     * @param username
     * @param password
     * @return object contain id user
     */
    @Override
    public LoginResponse auth(String username, String password) {
        LoginResponse loginResponse = new LoginResponse();
        if(username.isBlank()||password.isBlank()){
            throw new IllegalArgumentInMyFunctionException("username or password is null or empty");
        }
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            if(passwordEncoder.matches(password,user.getPassword())){
                loginResponse.setIdUser(user.getId());
                CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> reNewOtp(user.getId(),user.getEmail()));
            }else {
                throw new BadCredentialsException("username or password is incorrect");
            }
        }else {
            throw new BadCredentialsException("username or password is incorrect");
        }
        return loginResponse;
    }

    /**
     * check otp
     * @param idUser
     * @param otp
     * @return return token with user's id and role
     */
    @Override
    public CheckOtpResponse checkOtp(String idUser, String otp) {
        if(idUser.isBlank()||otp.isBlank()){
            throw new IllegalArgumentInMyFunctionException("idUser or otp is null or empty");
        }
        CheckOtpResponse checkOtpResponse = new CheckOtpResponse();
        Optional<OtpUser> optionalOtpUser = otpUserRepository.findById(UUID.fromString(idUser));
        Optional<User> optionalUser = userRepository.findById(UUID.fromString(idUser));
        if(optionalOtpUser.isPresent()&&optionalUser.isPresent()){
            OtpUser otpUser = optionalOtpUser.get();
            User user = optionalUser.get();
            if(otpUser.getOtp().equals(otp)){
                String token = generateTokenHMAC.createTokenHMAC(idUser,user.getRole().getRoleName());
                checkOtpResponse.setToken(token);
            }
        }else {
            throw new NotFoundRecordInDataException("User's otp not found");
        }
        return checkOtpResponse;
    }

    /**
     * register user
     * @param username
     * @param password
     */
    @Override
    public void add(String username, String password) {
        if(username.isBlank()||password.isBlank()){
            throw new IllegalArgumentInMyFunctionException("username or password is null or empty");
        }
        User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(new RoleUser(ID_ROLE_STAFF));
        userRepository.save(user);
    }

    /**
     * send otp to user
     * @param userId receiver's id user
     * @param email receiver's email
     * @return
     */
    private String reNewOtp(UUID userId, String email) {
        if(String.valueOf(userId).isBlank()||email.isBlank()){
            throw new IllegalArgumentInMyFunctionException("username or email is null or empty");
        }
        String code = GenerateCodeUtil.generateCode();
        Optional<OtpUser> optionalOtpUser = otpUserRepository.findById(userId);
        OtpUser otpUser;
        if(optionalOtpUser.isPresent()){
            otpUser = optionalOtpUser.get();
            otpUser.setOtp(code);
        }else {
            otpUser = new OtpUser(userId,code);
        }
        otpUserRepository.save(otpUser);
        emailSenderUtil.sendSimpleMail(email,"mã xác nhận từ Red Sea",code);
        return code;
    }
}
