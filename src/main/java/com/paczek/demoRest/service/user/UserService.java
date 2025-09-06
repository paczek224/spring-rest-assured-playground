package com.paczek.demoRest.service.user;

import com.paczek.demoRest.dto.user.UserDto;
import com.paczek.demoRest.entity.user.UserEntity;
import com.paczek.demoRest.repository.user.UserRepository;
import com.paczek.demoRest.util.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<UserEntity> getAllUsers() {
        return repo.findAll();
    }

    public List<UserEntity> findByEmailLikeAndGender(String email, String gender) {
        return repo.findByEmailLikeAndGender(email, gender);
    }

    public UserDto save(UserEntity userDto) {
        return Mappers.map(repo.save(userDto));
    }

    public UserDto save(UserDto userDto) {
        return save(Mappers.map(userDto));
    }

    public UserEntity getUser(Long id) {
        return repo.getReferenceById(id);
    }
}
