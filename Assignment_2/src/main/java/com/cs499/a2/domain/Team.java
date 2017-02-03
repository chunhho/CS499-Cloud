package com.cs499.a2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.cs499.a2.domain.enumeration.TeamType;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name")
    private String teamName;

    @Enumerated(EnumType.STRING)
    @Column(name = "team_type")
    private TeamType teamType;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private Set<Player> teams = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public Team teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public TeamType getTeamType() {
        return teamType;
    }

    public Team teamType(TeamType teamType) {
        this.teamType = teamType;
        return this;
    }

    public void setTeamType(TeamType teamType) {
        this.teamType = teamType;
    }

    public Set<Player> getTeams() {
        return teams;
    }

    public Team teams(Set<Player> players) {
        this.teams = players;
        return this;
    }

    public Team addTeam(Player player) {
        this.teams.add(player);
        player.setTeam(this);
        return this;
    }

    public Team removeTeam(Player player) {
        this.teams.remove(player);
        player.setTeam(null);
        return this;
    }

    public void setTeams(Set<Player> players) {
        this.teams = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if (team.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + id +
            ", teamName='" + teamName + "'" +
            ", teamType='" + teamType + "'" +
            '}';
    }
}
