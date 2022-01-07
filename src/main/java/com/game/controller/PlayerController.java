package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.validation.PlayerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//getPlayersList --- yes
//getCountPlayers --- yes
//createPlayer --- yes
//getPlayer --- yes
//updatePlayer --- yes
//deletePlayer --- yes

@RestController
@RequestMapping("/rest")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    //Получение списка персонажей
    @GetMapping("/players")
    public ResponseEntity<List<Player>> getPlayersList(@RequestParam(name = "name", defaultValue = "", required = false) String name,
                                                       @RequestParam(name = "title", defaultValue = "", required = false) String title,
                                                       @RequestParam(name = "race", defaultValue = "", required = false) Race race,
                                                       @RequestParam(name = "profession", defaultValue = "", required = false) Profession profession,
                                                       @RequestParam(name = "after", defaultValue = "", required = false) Long after,
                                                       @RequestParam(name = "before", defaultValue = "", required = false) Long before,
                                                       @RequestParam(name = "banned", defaultValue = "", required = false) Boolean banned,
                                                       @RequestParam(name = "minExperience", defaultValue = "", required = false) Integer minExperience,
                                                       @RequestParam(name = "maxExperience", defaultValue = "", required = false) Integer maxExperience,
                                                       @RequestParam(name = "minLevel", defaultValue = "", required = false) Integer minLevel,
                                                       @RequestParam(name = "maxLevel", defaultValue = "", required = false) Integer maxLevel,
                                                       @RequestParam(name = "order", defaultValue = "ID", required = false) PlayerOrder order,
                                                       @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                       @RequestParam(name = "pageSize", defaultValue = "3", required = false) Integer pageSize) {
        List<Player> playerList = playerService.getAllPlayers(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
        return ResponseEntity.ok(playerList);
    }

    //Получить количество персонажей
    @GetMapping("players/count")
    public ResponseEntity<Integer> GetPlayersCount(@RequestParam(name = "name", defaultValue = "", required = false) String name,
                                                   @RequestParam(name = "title", defaultValue = "", required = false) String title,
                                                   @RequestParam(name = "race", defaultValue = "", required = false) Race race,
                                                   @RequestParam(name = "profession", defaultValue = "", required = false) Profession profession,
                                                   @RequestParam(name = "after", defaultValue = "", required = false) Long after,
                                                   @RequestParam(name = "before", defaultValue = "", required = false) Long before,
                                                   @RequestParam(name = "banned", defaultValue = "", required = false) Boolean banned,
                                                   @RequestParam(name = "minExperience", defaultValue = "", required = false) Integer minExperience,
                                                   @RequestParam(name = "maxExperience", defaultValue = "", required = false) Integer maxExperience,
                                                   @RequestParam(name = "minLevel", defaultValue = "", required = false) Integer minLevel,
                                                   @RequestParam(name = "maxLevel", defaultValue = "", required = false) Integer maxLevel,
                                                   @RequestParam(name = "order", defaultValue = "ID", required = false) PlayerOrder order,
                                                   @RequestParam(name = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                   @RequestParam(name = "pageSize", defaultValue = "3", required = false) Integer pageSize) {
        Integer playersCount = playerService.getPlayersCount(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, order, pageNumber, pageSize);
        return ResponseEntity.ok(playersCount);
    }

    // Создание персонажа
    @PostMapping("/players")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (!PlayerValidation.canCreate(player) || !PlayerValidation.isValidData(player)) {
            return ResponseEntity.badRequest().build();
        }
        playerService.changePlayer(player);
        return ResponseEntity.ok(player);
    }

    //Получение персонажа по id
    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (!playerService.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        Player resultPlayer = playerService.getById(id);
        return ResponseEntity.ok(resultPlayer);
    }

    //Обновление персонажа
    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player, @PathVariable("id") Integer id) {
        if (id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        Player resultPlayer = playerService.getById(id);
        if (player.getName() == null && player.getTitle() == null && player.getRace() == null &&
                player.getProfession() == null && player.getBirthday() == null && player.getBanned() == null
                && player.getExperience() == null) {
            return ResponseEntity.ok(resultPlayer);
        }
        if (resultPlayer == null) {
            return ResponseEntity.notFound().build();
        }
        if (!PlayerValidation.checkExperience(player.getExperience())) {
            return ResponseEntity.badRequest().build();
        }
        if (!PlayerValidation.checkBirthday(player.getBirthday())) {
            return ResponseEntity.badRequest().build();
        }

        if (player.getName() != null) {
            resultPlayer.setName(player.getName());
        }
        if (player.getTitle() != null) {
            resultPlayer.setTitle(player.getTitle());
        }
        if (player.getRace() != null) {
            resultPlayer.setRace(player.getRace());
        }
        if (player.getProfession() != null) {
            resultPlayer.setProfession(player.getProfession());
        }
        if (player.getBirthday() != null) {
            resultPlayer.setBirthday(player.getBirthday());
        }
        if (player.getBanned() != null) {
            resultPlayer.setBanned(player.getBanned());
        }
        if (player.getExperience() != null) {
            resultPlayer.setExperience(player.getExperience());
        }

        playerService.changePlayer(resultPlayer);
        return ResponseEntity.ok(resultPlayer);
    }

    //Удаление персонажа
    @DeleteMapping("/players/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        if (!playerService.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        playerService.delete(id);
        return ResponseEntity.ok().build();
    }
}