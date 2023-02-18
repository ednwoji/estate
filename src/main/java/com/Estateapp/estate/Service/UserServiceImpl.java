package com.Estateapp.estate.Service;

import com.Estateapp.estate.Entity.Users;
import com.Estateapp.estate.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public void addNewUser(Users users) {

        usersRepository.save(users);

    }
}
