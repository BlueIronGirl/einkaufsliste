package de.shoppinglist.entity;

import de.shoppinglist.entity.base.EntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Role extends EntityBase {
    @Enumerated(EnumType.STRING)
    private RoleName name;
}
