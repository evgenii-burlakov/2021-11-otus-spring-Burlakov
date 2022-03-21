package ru.otus.libraryapplication.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.libraryapplication.domain.User;
import ru.otus.libraryapplication.repositories.user.UserRepository;

import static org.springframework.security.core.userdetails.User.UserBuilder;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        UserBuilder builder = null;

        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);

            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }
}
