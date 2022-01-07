package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import com.game.filter.PlayerFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    //Получение всех персонажей по фильтрам
    public List<Player> getAllPlayers(String name,
                                      String title,
                                      Race race,
                                      Profession profession,
                                      Long after,
                                      Long before, Boolean banned,
                                      Integer minExperience,
                                      Integer maxExperience,
                                      Integer minLevel,
                                      Integer maxLevel,
                                      PlayerOrder order,
                                      Integer pageNumber,
                                      Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        Page<Player> page = getPages(name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel, pageable);
        return page.getContent();
    }

    //Подсчитывает количество персонажей, соответствующих фильтрам
    public Integer getPlayersCount(String name,
                                   String title,
                                   Race race,
                                   Profession profession,
                                   Long after,
                                   Long before,
                                   Boolean banned,
                                   Integer minExperience,
                                   Integer maxExperience,
                                   Integer minLevel,
                                   Integer maxLevel,
                                   PlayerOrder order,
                                   Integer pageNumber,
                                   Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName()));
        Page<Player> page = getPages(name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel, pageable);
        return (int) page.getTotalElements();
    }

    //Фильтрация персонажей
    private Page<Player> getPages(String name,
                                  String title,
                                  Race race,
                                  Profession profession,
                                  Long after,
                                  Long before,
                                  Boolean banned,
                                  Integer minExperience,
                                  Integer maxExperience,
                                  Integer minLevel,
                                  Integer maxLevel,
                                  Pageable pageable) {
        return playerRepository.findAll(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(
                                                                PlayerFilter.findByName(name)
                                                                        .and(PlayerFilter.findByRace(race)))
                                                        .and(PlayerFilter.findByProfession(profession)))
                                                .and(PlayerFilter.findByBanned(banned)))
                                        .and(PlayerFilter.findByTitle(title)))
                                .and(PlayerFilter.findByDate(after, before)))
                        .and(PlayerFilter.findByExperience(minExperience, maxExperience)))
                .and(PlayerFilter.findByLevel(minLevel, maxLevel)), pageable);
    }

    //Создание и обноваление полей для персонажа
    public void changePlayer(Player player) {
        player.setLevel(player.getCurrentLevel());
        player.setUntilNextLevel(player.getNextLevel());
        playerRepository.save(player);
    }

    //Получение персонажа по id
    public Player getById(long id) {
        Optional<Player> oPlayer = playerRepository.findById(id);
        Player player = null;
        try {
            player = oPlayer.get();
        } catch (Exception e) {
        }
        return player;
    }

    //Удаление персонажа
    public void delete(long id) {
        playerRepository.deleteById(id);
    }

    //Есть ли персонаж с таким id
    public boolean exists(long id) {
        return playerRepository.existsById(id);
    }
}