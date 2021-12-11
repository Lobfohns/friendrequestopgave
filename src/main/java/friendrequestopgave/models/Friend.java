package friendrequestopgave.models;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "friends")
public class Friend {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String request;

    @Column
    private String status;

}
