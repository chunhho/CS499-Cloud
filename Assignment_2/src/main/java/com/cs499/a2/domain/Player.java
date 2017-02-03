package com.cs499.a2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "character_name")
    private String characterName;

    @Column(name = "energy")
    private Integer energy;

    @Column(name = "cp")
    private Integer cp;

    @ManyToOne
    private Team team;

    @OneToMany(mappedBy = "player")
    @JsonIgnore
    private Set<Role> players = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public Player familyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public Player characterName(String characterName) {
        this.characterName = characterName;
        return this;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public Integer getEnergy() {
        return energy;
    }

    public Player energy(Integer energy) {
        this.energy = energy;
        return this;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Integer getCp() {
        return cp;
    }

    public Player cp(Integer cp) {
        this.cp = cp;
        return this;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public Team getTeam() {
        return team;
    }

    public Player team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Role> getPlayers() {
        return players;
    }

    public Player players(Set<Role> roles) {
        this.players = roles;
        return this;
    }

    public Player addPlayer(Role role) {
        this.players.add(role);
        role.setPlayer(this);
        return this;
    }

    public Player removePlayer(Role role) {
        this.players.remove(role);
        role.setPlayer(null);
        return this;
    }

    public void setPlayers(Set<Role> roles) {
        this.players = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        if (player.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Player{" +
            "id=" + id +
            ", familyName='" + familyName + "'" +
            ", characterName='" + characterName + "'" +
            ", energy='" + energy + "'" +
            ", cp='" + cp + "'" +
            '}';
    }
}
