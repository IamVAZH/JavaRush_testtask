package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    //Все персонажи в страницу
    Page<Player> findAll(Specification<Player> and, Pageable pageable);
    //Проверка на нахождение персонажа в базе данных
    boolean existsById(Long id);
}
