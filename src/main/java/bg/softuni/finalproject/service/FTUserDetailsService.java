package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FTUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public FTUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        UserDetails mappedUser = map(user);

        if (mappedUser == null || mappedUser.toString().isEmpty()){
            throw  new UsernameNotFoundException("User with " + username + " not found");
        } else {
            return mappedUser;
        }
    }

    private static UserDetails map(User user){
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(List.of()) //TODO
                .disabled(false)
                .build();
    }
}
