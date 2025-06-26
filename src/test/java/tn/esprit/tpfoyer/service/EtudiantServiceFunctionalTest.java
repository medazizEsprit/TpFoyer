package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class EtudiantServiceFunctionalTest {

    @Autowired
    private IEtudiantService etudiantService;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    void setUp() {
        // Clear the database before each test
        etudiantRepository.deleteAll();
    }

    @Test
    void testAddAndRetrieveEtudiant() {
        // Create and save a student
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Test");
        etudiant.setPrenomEtudiant("User");
        etudiant.setCinEtudiant(12345678L);
        etudiant.setDateNaissance(new Date());
        
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);
        
        // Retrieve the student
        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(savedEtudiant.getIdEtudiant());
        
        // Assertions
        assertNotNull(retrievedEtudiant);
        assertEquals("Test", retrievedEtudiant.getNomEtudiant());
        assertEquals("User", retrievedEtudiant.getPrenomEtudiant());
    }

    @Test
    void testUpdateEtudiant() {
        // Create and save initial student
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("Original");
        etudiant.setPrenomEtudiant("Name");
        etudiant.setCinEtudiant(11111111L);
        etudiant.setDateNaissance(new Date());
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);
        
        // Update the student
        savedEtudiant.setNomEtudiant("Updated");
        Etudiant updatedEtudiant = etudiantService.modifyEtudiant(savedEtudiant);
        
        // Assertions
        assertEquals("Updated", updatedEtudiant.getNomEtudiant());
        assertEquals(savedEtudiant.getIdEtudiant(), updatedEtudiant.getIdEtudiant());
    }

    @Test
    void testDeleteEtudiant() {
        // Create and save a student
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("ToDelete");
        etudiant.setPrenomEtudiant("Student");
        etudiant.setCinEtudiant(22222222L);
        etudiant.setDateNaissance(new Date());
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);
        
        // Delete the student
        etudiantService.removeEtudiant(savedEtudiant.getIdEtudiant());
        
        // Verify deletion
        assertThrows(Exception.class, () -> {
            etudiantService.retrieveEtudiant(savedEtudiant.getIdEtudiant());
        });
    }

    @Test
    void testRetrieveAllEtudiants() {
        // Create and save multiple students
        Etudiant etudiant1 = new Etudiant();
        etudiant1.setNomEtudiant("Student1");
        etudiant1.setPrenomEtudiant("One");
        etudiant1.setCinEtudiant(33333333L);
        etudiant1.setDateNaissance(new Date());
        etudiantService.addEtudiant(etudiant1);
        
        Etudiant etudiant2 = new Etudiant();
        etudiant2.setNomEtudiant("Student2");
        etudiant2.setPrenomEtudiant("Two");
        etudiant2.setCinEtudiant(44444444L);
        etudiant2.setDateNaissance(new Date());
        etudiantService.addEtudiant(etudiant2);
        
        // Retrieve all students
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();
        
        // Assertions
        assertEquals(2, etudiants.size());
    }

    @Test
    void testFindByCin() {
        // Create and save a student with specific CIN
        long testCin = 55555555L;
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant("CinTest");
        etudiant.setPrenomEtudiant("Student");
        etudiant.setCinEtudiant(testCin);
        etudiant.setDateNaissance(new Date());
        etudiantService.addEtudiant(etudiant);
        
        // Find by CIN
        Etudiant found = etudiantService.recupererEtudiantParCin(testCin);
        
        // Assertions
        assertNotNull(found);
        assertEquals(testCin, found.getCinEtudiant());
    }
}