package rso.usercatalogue.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "_user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Id", notes = "User's id.", position = 1, required = true)
    private Long id;

    @ApiModelProperty(value = "Name", notes = "User's full name.", position = 2, required = true)
    @NotEmpty(message = "Name can not be empty.")
    private String name;

    @ApiModelProperty(value = "Username", notes = "User's username.", position = 3, required = true)
    @NotEmpty(message = "Username can not be empty.")
    @Column(unique = true)
    private String username;

    @ApiModelProperty(value = "Password", notes = "User's password.", position = 4, required = true)
    @NotEmpty(message = "Password can not be empty")
    private String password;

    @ApiModelProperty(value = "Email", notes = "User's email.", position = 5, required = true)
    @Email(message = "Email must be valid.")
    @Column(unique = true)
    private String email;

    @OneToMany(
            targetEntity = GameAccount.class,
            cascade = CascadeType.ALL,
            mappedBy = "user",
            fetch = FetchType.LAZY
    )
    private Set<GameAccount> gameAccounts;
}
