package ua.pt.repository;

import org.springframework.stereotype.Repository;

import ua.pt.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    public User findByName(String name);
}
