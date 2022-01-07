package com.game.filter;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

//Класс фильтов персонажа
public class PlayerFilter {

    //Поиск по name
    public static Specification<Player> findByName(String name) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> playerRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (name == null) {
                    return null;
                }
                return criteriaBuilder.like(playerRoot.get("name"), "%" + name + "%");
            }
        };
    }

    //Поиск по title
    public static Specification<Player> findByTitle(String title) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> playerRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (title == null) {
                    return null;
                }
                return criteriaBuilder.like(playerRoot.get("title"), "%" + title + "%");
            }
        };
    }

    //Поиск по birthday
    public static Specification<Player> findByDate(Long after, Long before) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> playerRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate bigger = null;
                Predicate smaller = null;

                if (after == null && before != null) {
                    smaller = criteriaBuilder.lessThanOrEqualTo(playerRoot.get("birthday"), new Date(before));
                    return smaller;
                }
                if (after != null && before == null) {
                    bigger = criteriaBuilder.greaterThanOrEqualTo(playerRoot.get("birthday"), new Date(after));
                    return bigger;
                }

                if (after != null) {
                    bigger = criteriaBuilder.greaterThanOrEqualTo(playerRoot.get("birthday"), new Date(after));
                    smaller = criteriaBuilder.lessThanOrEqualTo(playerRoot.get("birthday"), new Date(before));
                    return criteriaBuilder.and(bigger, smaller);
                }
                return null;
            }
        };
    }

    //Поиск по experience
    public static Specification<Player> findByExperience(Integer minExperience, Integer maxExperience) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> playerRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate bigger = null;
                Predicate smaller = null;

                if (minExperience == null && maxExperience != null) {
                    smaller = criteriaBuilder.lessThanOrEqualTo(playerRoot.get("experience"), maxExperience);
                    return smaller;
                }
                if (minExperience != null && maxExperience == null) {
                    bigger = criteriaBuilder.greaterThanOrEqualTo(playerRoot.get("experience"), minExperience);
                    return bigger;
                }

                if (minExperience != null) {
                    bigger = criteriaBuilder.greaterThanOrEqualTo(playerRoot.get("experience"), minExperience);
                    smaller = criteriaBuilder.lessThanOrEqualTo(playerRoot.get("experience"), maxExperience);
                    return criteriaBuilder.and(bigger, smaller);
                }
                return null;
            }
        };
    }

    //Поиск по level
    public static Specification<Player> findByLevel(Integer minLevel, Integer maxLevel) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> playerRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate bigger = null;
                Predicate smaller = null;

                if (minLevel == null && maxLevel != null) {
                    smaller = criteriaBuilder.lessThanOrEqualTo(playerRoot.get("level"), maxLevel);
                    return smaller;
                }
                if (minLevel != null && maxLevel == null) {
                    bigger = criteriaBuilder.greaterThanOrEqualTo(playerRoot.get("level"), minLevel);
                    return bigger;
                }

                if (minLevel != null) {
                    bigger = criteriaBuilder.greaterThanOrEqualTo(playerRoot.get("level"), minLevel);
                    smaller = criteriaBuilder.lessThanOrEqualTo(playerRoot.get("level"), maxLevel);
                    return criteriaBuilder.and(bigger, smaller);
                }
                return null;
            }
        };
    }

    //Поиск по race
    public static Specification<Player> findByRace(Race race) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> playerRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (race == null) {
                    return null;
                }
                return criteriaBuilder.equal(playerRoot.get("race"), race);
            }
        };
    }

    //Поиск по profession
    public static Specification<Player> findByProfession(Profession profession) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> playerRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (profession == null) {
                    return null;
                }
                return criteriaBuilder.equal(playerRoot.get("profession"), profession);
            }
        };
    }

    //Поиск по banned
    public static Specification<Player> findByBanned(Boolean banned) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> playerRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (banned == null) {
                    return null;
                }
                return criteriaBuilder.equal(playerRoot.get("banned"), banned);
            }
        };
    }
}
