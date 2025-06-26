package tn.esprit.tpfoyer.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor


@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Etudiant {

    public Etudiant(long l, String string, String string2, long m) {
        //TODO Auto-generated constructor stub
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idEtudiant;

    String nomEtudiant;
    String prenomEtudiant;
    long cinEtudiant;
    Date dateNaissance;

    @ManyToMany(mappedBy = "etudiants")
    Set<Reservation> reservations;

}



