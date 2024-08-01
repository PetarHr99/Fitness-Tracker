package bg.softuni.finalproject.service;

import bg.softuni.finalproject.Entity.Role;
import bg.softuni.finalproject.Entity.User;
import bg.softuni.finalproject.Entity.enums.RoleEnum;
import bg.softuni.finalproject.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                .authorities(mapRoles(user.getRoles()))
                .disabled(false)
                .build();
    }

    private static Set<GrantedAuthority> mapRoles(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()))
                .collect(Collectors.toSet());
    }
}
