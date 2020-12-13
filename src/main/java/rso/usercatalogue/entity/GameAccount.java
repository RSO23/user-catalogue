package rso.usercatalogue.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GameAccount
{
    @Id
    private String accountId;

    private String username;

    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;
}
