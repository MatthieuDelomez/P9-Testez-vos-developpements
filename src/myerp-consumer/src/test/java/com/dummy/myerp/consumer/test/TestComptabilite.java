
package com.dummy.myerp.consumer.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;



                                            @RunWith(SpringJUnit4ClassRunner.class)
                                            @ContextConfiguration(locations= {"/com/dummy/myerp/consumer/applicationContext.xml"})
                                            @ActiveProfiles(profiles="test")

                                            public class TestComptabilite {
    
    
                                            /*
                                            Implementation de la classe ComptaDao que nous allons tester
                                            */
                                            ComptabiliteDaoImpl dao = ComptabiliteDaoImpl.getInstance();
    
        
                                            /*
	                      * Vérification de l'obtention de la liste des écritures comptables
                                            */
	                      @Test
	                      public void getListEcritureComptableTest() {
            
		List<EcritureComptable>liste;
		
		liste=dao.getListEcritureComptable();

		assertTrue("Test taille de la liste attendu Ecritures Comptables", liste.size() == 5);
		assertTrue("Test si une ecriture test est bien présente",liste.stream().filter(o -> o.getReference().equals("BQ-2016/00005")).findFirst().isPresent());
		
		
	                      }
        
        
        
                                            /*
                                            Création de la méthode pour récupérer une écriture comptable par ID
                                            @throws NotFoundException
                                           */
                                            @Test
                                            public void getEcritureComptableWithIdTest() throws NotFoundException {
                         
                         
                                            EcritureComptable testEcriture;
                                            
                                            // Id correspondant à la première donnée de la base [Libelle = cartouche d'imprimante]
                                            Integer Id = -1; 
                                            
                                            //Nous allons récupérer l'écriture comptable
                                            testEcriture = dao.getEcritureComptable(Id);
                                            
                                            assertNotNull("Verification que l'ecriture n'est pas nulle", testEcriture);
                                            assertTrue("Vérification de la référence", testEcriture.getReference().equals("AC-2016/00001"));
                                            
                                            //Retourn False si la donnée contient un Libelle
                                            assertFalse(testEcriture.getLibelle() == "");
                                            
                                            assertTrue("Nous allons vérifier que la liste à été chargée", testEcriture.getListLigneEcriture().size() == 3);
                                            
                                            //************************************************************************************************************//
                                            
                                            /*
                                            Nous allons tester avec une écriture comptable qui n'existe pas
                                            Exception attendue
                                           */
                                            Id = 35;
                                            
                                            try {
                                                
                                            testEcriture = dao.getEcritureComptable(Id);
                                            
                                            fail("Exception qui sera attendue");
                                            
                                            } catch (NotFoundException exception) {
                                                
                                            assertThat(exception.getMessage(), is("Ecriture Comptable n'a pas été trouvée. Id = " + Id));
                                            
                                            }

                                            }
        
        
        

    
    
}
