package io.pivotal.fortune;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Fortune {

    @Id
    @GeneratedValue
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String description;

}
