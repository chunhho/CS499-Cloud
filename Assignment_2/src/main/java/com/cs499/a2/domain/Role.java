package com.cs499.a2.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.cs499.a2.domain.enumeration.ClassType;

/**
 * A Role.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_type")
    private ClassType classType;

    @Column(name = "level")
    private Integer level;

    @Column(name = "sp")
    private Integer sp;

    @ManyToOne
    private Player player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassType getClassType() {
        return classType;
    }

    public Role classType(ClassType classType) {
        this.classType = classType;
        return this;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public Integer getLevel() {
        return level;
    }

    public Role level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSp() {
        return sp;
    }

    public Role sp(Integer sp) {
        this.sp = sp;
        return this;
    }

    public void setSp(Integer sp) {
        this.sp = sp;
    }

    public Player getPlayer() {
        return player;
    }

    public Role player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        if (role.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Role{" +
            "id=" + id +
            ", classType='" + classType + "'" +
            ", level='" + level + "'" +
            ", sp='" + sp + "'" +
            '}';
    }
}
