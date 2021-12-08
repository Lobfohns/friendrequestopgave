package friendrequestopgave.models;

import javax.management.relation.Role;
import javax.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "size")
    private Role role;

    private String picture;
    //Getter and setter are removed for the sake of brevity
}
