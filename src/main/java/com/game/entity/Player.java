package com.game.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 12)
    private String name;
    @Column(name = "title", length = 30)
    private String title;
    @Column(name = "race")
    @Enumerated(EnumType.STRING)
    private Race race;
    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;
    @Column(name = "birthday")
    @Temporal(value = TemporalType.DATE)
    private Date birthday;
    @Column(name = "banned")
    private Boolean banned;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "level")
    private Integer level;
    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }


    public int getCurrentLevel() {
        return (int) (Math.sqrt(2500 + (200 * this.experience)) - 50) / 100;
    }

    public int getNextLevel() {
        return 50 * (this.level + 1) * (this.level + 2) - this.experience;
    }
}

//expected:<[
// PlayerInfoTest{id=28, name='Камираж', birthday=2005-08-05},
// PlayerInfoTest{id=37, name='Яра', birthday=2004-06-12}
// ]>
// but was:<[]>