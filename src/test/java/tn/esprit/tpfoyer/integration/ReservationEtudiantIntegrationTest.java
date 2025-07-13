package tn.esprit.tpfoyer.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import jakarta.transaction.Transactional;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.IEtudiantService;
import tn.esprit.tpfoyer.service.IReservationService;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ReservationEtudiantIntegrationTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    public void cleanDatabase() {
        reservationRepository.deleteAll();
        etudiantRepository.deleteAll();
    }

    @Test
    public void testLinkEtudiantsToReservationAndPersist() {
        // Manually create Reservation entity (not a nested class, just instantiate)
        Reservation reservation = new Reservation();
        reservation.setIdReservation("RES123"); // manually assigned string ID
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);

        // Create Etudiant and set fields
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEtudiant("Aziz");

        Etudiant etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2L);
        etudiant2.setNomEtudiant("Sara");

        // Persist Etudiants first if cascade is not set
        etudiantRepository.saveAll(Arrays.asList(etudiant1, etudiant2));

        // Link etudiants to reservation (assuming many-to-many)
        reservation.getEtudiants().add(etudiant1);
        reservation.getEtudiants().add(etudiant2);

        // Save Reservation
        reservationRepository.save(reservation);

        // Assert
        Optional<Reservation> found = reservationRepository.findById("RES123");
        assertTrue(found.isPresent());
        assertEquals(2, found.get().getEtudiants().size());
    }
}

