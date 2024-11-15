package noverlin.timetracker.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import noverlin.timetracker.DTOs.UserDto;
import noverlin.timetracker.entities.User;
import noverlin.timetracker.mappers.UserMapper;
import noverlin.timetracker.repositories.UserRepository;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Optional<User> findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    //в качестве логина используется email. Метод используется Security
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", email)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public User createNewUser(UserDto userDto) {
        //создает имя по умолчанию - часть эл. почты до символа @    <semenoff1352 | @gmail.com>
        String defaultName = userDto.getEmail().substring(0, userDto.getEmail().indexOf("@")).toLowerCase();
        User user = new User()
                .setName(defaultName)
                .setEmail(userDto.getEmail())
                .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .setRoles(List.of(roleService.getUserRole()));
        System.out.println(user.toString());
        return userRepository.save(user);
    }

    public User getUser(String email)
    {
        return findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", email)
        ));
    }

    //получает из репозитория Iterator с пользователями и через stream и collect собирает в List
    public List<User> getAllUsers() {
        return StreamSupport.stream(
                userRepository
                        .findAll()
                        .spliterator()
                , false)
                .collect(Collectors.toList());
    }
}
