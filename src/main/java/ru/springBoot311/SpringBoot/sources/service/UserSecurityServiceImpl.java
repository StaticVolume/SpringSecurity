package ru.springBoot311.SpringBoot.sources.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.springBoot311.SpringBoot.sources.dao.UserDao;
import ru.springBoot311.SpringBoot.sources.model.User;
import ru.springBoot311.SpringBoot.sources.security.MyUserDetails;

import java.util.Optional;

@Service
public class UserSecurityServiceImpl implements UserDetailsService {
    UserDao userDao;

    @Autowired
    public UserSecurityServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User>  user = userDao.loadUserByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with this name not found");
        }
        return new MyUserDetails(user.get());
    }
}
