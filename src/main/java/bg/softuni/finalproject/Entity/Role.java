package bg.softuni.finalproject.Entity;

import bg.softuni.finalproject.Entity.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User rolesAddedBy;

    public Role() {}

    public User getRolesAddedBy() {
        return rolesAddedBy;
    }

    public void setRolesAddedBy(User rolesAddedBy) {
        this.rolesAddedBy = rolesAddedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }
}
