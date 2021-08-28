package com.palmatoro.cmmimplant.service;

import com.palmatoro.cmmimplant.domain.User;
import com.palmatoro.cmmimplant.domain.User.UserRole;
import com.palmatoro.cmmimplant.exception.ResourceNotFoundException;
import com.palmatoro.cmmimplant.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    private ProjectService projectService;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found on ID: " + id));
        return user;
    }

    public User getUserByUsername(String username) {

        User user;
        try {
            user = userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new ResourceNotFoundException("No se ha encontrado el usuario " + username);
        }

        return user;
    }

    public User getUserByEmail(String email) {

        User user;
        try {
            user = userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new ResourceNotFoundException("No se ha encontrado el usuario con email " + email);
        }

        return user;
    }

    @Transactional
    public User addNewUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User addNewUser(User user, Integer projectId) {
        user.setProject(projectService.getProjectById(projectId));
        return userRepository.save(user);
    }

    @Transactional
    public User editUser(Integer id, String name, String surname, String acronym, UserRole userRole, String email, String password) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found on ID: " + id));

        user.setName(name);
        user.setSurname(surname);
        user.setAcronym(acronym);
        user.setUserRole(userRole);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found on ID: " + id));
        userRepository.delete(user);
    }

}
