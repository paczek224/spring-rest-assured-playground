package com.paczek.demoRest.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("""
                SELECT u FROM UserEntity u
                WHERE (:email IS NULL OR u.email LIKE %:email)
                  AND (:gender IS NULL OR LOWER(u.gender) = LOWER(:gender))
            """)
    List<UserEntity> findByEmailLikeAndGender(@Param("email") String email,
                                              @Param("gender") String gender);
}
