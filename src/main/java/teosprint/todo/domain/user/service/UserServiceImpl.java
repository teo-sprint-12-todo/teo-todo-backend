package teosprint.todo.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import teosprint.todo.domain.user.data.dto.SignInDto;
import teosprint.todo.domain.user.data.dto.SignUpDto;
import teosprint.todo.domain.user.data.entity.User;
import teosprint.todo.domain.user.filter.JwtTokenProvider;
import teosprint.todo.domain.user.repository.UserRepository;

import java.util.Collections;

@Component
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Integer signUp(SignUpDto signUpDto) {
        Integer id = userRepository.save(
                        User.builder()
                                .email(signUpDto.getEmail())
                                .password(passwordEncoder.encode(signUpDto.getPassword()))
                                .tier(0)
                                .roles(Collections.singletonList("ROLE_USER"))
                                .build())
                .getId();

        return id;
    }

    @Override
    public String signIn(SignInDto signInDto) {
        if (!userRepository.existsByEmail(signInDto.getEmail())) return null;
        User user = userRepository.findByEmail(signInDto.getEmail()).get();

        if (passwordEncoder.matches(signInDto.getPassword(), user.getPassword()));
        return jwtTokenProvider.createToken(user.getEmail(), "USER");
    }

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public String getEmailByToken(String token) {
        return jwtTokenProvider.getUserEmail(token.substring(7));
    }
}
