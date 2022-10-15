package teosprint.todo.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import teosprint.todo.domain.user.data.dto.SignInDto;
import teosprint.todo.domain.user.data.dto.SignUpDto;
import teosprint.todo.domain.user.filter.JwtTokenProvider;
import teosprint.todo.domain.user.repository.UserRepository;
import teosprint.todo.domain.user.data.entity.User;
import java.util.Collections;

@Component
@Service
public interface UserService {
    public Integer signUp(SignUpDto signUpDto);
    public String signIn(SignInDto signInDto);
    public boolean checkEmail(String email);
    public String getEmailByToken(String token);

}
