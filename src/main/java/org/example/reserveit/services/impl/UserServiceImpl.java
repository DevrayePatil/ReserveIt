package org.example.reserveit.services.impl;

import lombok.AllArgsConstructor;
import org.example.reserveit.exceptions.EmailAlreadyExistException;
import org.example.reserveit.exceptions.UserNotFoundException;
import org.example.reserveit.models.User;
import org.example.reserveit.models.UserType;
import org.example.reserveit.repositories.UserRepository;
import org.example.reserveit.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User createUser(String name, String email, Long phoneNumber, String Password, UserType type) throws EmailAlreadyExistException{

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistException();
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(Password);
        user.setType(type != null ? type : UserType.USER);

        return userRepository.save(user);
    }

    @Override
    public User getUser(Long id) throws UserNotFoundException {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User updateUser(Long id, String name, String email, Long phoneNumber, UserType type) throws UserNotFoundException {

        boolean isUpdated = false;
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (name != null) {
            user.setName(name);
            isUpdated = true;
        }
        if (email != null) {
            user.setEmail(email);
            isUpdated = true;
        }
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
            isUpdated = true;
        }
        if (type != null) {
            user.setType(type);
            isUpdated = true;
        }


        return isUpdated ? userRepository.save(user): user;

    }

    @Override
    public User deleteUser(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
