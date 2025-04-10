package com.cyku.Treasurehunt.repo;

import com.cyku.Treasurehunt.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, String> {
    boolean existsByTeam(String team);
    Optional<Team> findByTeam(String team);
}
