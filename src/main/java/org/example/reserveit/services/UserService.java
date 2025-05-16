package org.example.reserveit.services;

import org.example.reserveit.exceptions.EmailAlreadyExistException;
import org.example.reserveit.exceptions.UserNotFoundException;
import org.example.reserveit.models.User;
import org.example.reserveit.models.UserType;

import java.util.List;

public interface UserService {

    User createUser(String name, String email, Long phoneNumber, String password, UserType type) throws EmailAlreadyExistException;
    User getUser(Long id) throws UserNotFoundException;
    User updateUser(Long id, String name, String email, Long phoneNumber, UserType type) throws UserNotFoundException;
    User deleteUser(Long id) throws UserNotFoundException;
    List<User> getUsers();

}
