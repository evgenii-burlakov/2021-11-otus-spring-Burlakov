package ru.otus.libraryapplication.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.libraryapplication.domain.User;
import ru.otus.libraryapplication.repositories.user.UserRepository;

import static org.springframework.security.core.userdetails.User.UserBuilder;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        UserBuilder builder;

        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            builder.roles("USER");
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }
}
