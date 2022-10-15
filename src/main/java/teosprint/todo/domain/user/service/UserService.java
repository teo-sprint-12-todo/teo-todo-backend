package teosprint.todo.domain.user.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import teosprint.todo.domain.user.data.dto.SignInDto;
import teosprint.todo.domain.user.data.dto.SignUpDto;

@Component
@Service
public interface UserService {
    public Integer signUp(SignUpDto signUpDto);
    public String signIn(SignInDto signInDto);
    public boolean checkEmail(String email);
    public String getEmailByToken(String token);

}
