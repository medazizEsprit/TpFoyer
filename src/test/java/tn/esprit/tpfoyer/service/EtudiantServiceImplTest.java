package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant1;
    private Etudiant etudiant2;

    @BeforeEach
    void setUp() {
        // Create Etudiant objects with all required parameters
        etudiant1 = new Etudiant();
        etudiant1.setIdEtudiant(1L);
        etudiant1.setNomEtudiant("John");
        etudiant1.setPrenomEtudiant("Doe");
        etudiant1.setCinEtudiant(12345678L);
        etudiant1.setDateNaissance(new Date()); // Add required date
        etudiant1.setReservations(new HashSet<>()); // Add empty set for reservations

        etudiant2 = new Etudiant();
        etudiant2.setIdEtudiant(2L);
        etudiant2.setNomEtudiant("Jane");
        etudiant2.setPrenomEtudiant("Smith");
        etudiant2.setCinEtudiant(87654321L);
        etudiant2.setDateNaissance(new Date());
        etudiant2.setReservations(new HashSet<>());
    }

    @Test
    void retrieveAllEtudiants_ShouldReturnAllStudents() {
        // Arrange
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(etudiant1, etudiant2));
        
        // Act
        List<Etudiant> result = etudiantService.retrieveAllEtudiants();
        
        // Assert
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void retrieveEtudiant_WhenExists_ShouldReturnStudent() {
        // Arrange
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant1));
        
        // Act
        Etudiant result = etudiantService.retrieveEtudiant(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        verify(etudiantRepository, times(1)).findById(1L);
    }

}