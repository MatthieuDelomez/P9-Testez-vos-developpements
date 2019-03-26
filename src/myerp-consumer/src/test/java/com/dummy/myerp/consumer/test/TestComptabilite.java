
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
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/com/dummy/myerp/consumer/applicationContext.xml"})
@ActiveProfiles(profiles="test")

 public class TestComptabilite {
    
    
                                            /*
                                            Implementation de la classe ComptaDao que nous allons tester
                                            */
                                            ComptabiliteDaoImpl dao = ComptabiliteDaoImpl.getInstance();
                                            
    
                                            //************************************************************************************************************/
                                            
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
                              
        
                                            //************************************************************************************************************/
        
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
                                            assertTrue("Vérification de la référence", testEcriture.getReference().equals("AC-2019/00001"));
                                            
                                            //Retourn False si la donnée contient un Libelle
                                            assertFalse(testEcriture.getLibelle() == "");
                                            
                                            assertTrue("Nous allons vérifier que la liste à été chargée", testEcriture.getListLigneEcriture().size() == 3);
                                            
                                           
                                            
                                            /*
                                            Nous allons tester avec une écriture comptable qui n'existe pas
                                            Exception attendue
                                           
                                            Id = 35;
                                            
                                            try {
                                                
                                            testEcriture = dao.getEcritureComptable(Id);
                                            
                                            fail("Exception qui sera attendue");
                                            
                                            } catch (NotFoundException exception) {
                                                
                                            assertThat(exception.getMessage(), is("Ecriture Comptable n'a pas été trouvée. Id = " + Id));
                                            
                                            }*/

                                            }
                                           
                                            
                                            //************************************************************************************************************/
                                            
                                            /*
                                            Création de la méthode Test pour vérifier les valeurs des journaux comptable
                                           */
                                            @Test
                                            public void getListJournalComptaTest() {
                                                
                                            //Variable qui va contenir la liste des journaux
                                            List<JournalComptable> listeJournaux;
                                            
                                            listeJournaux = dao.getListJournalComptable();
                                            
                                            /*
                                            Vérification de la taille de la liste des journaux |  Test si le journal existe [AC]
                                            */
                                            
                                            // La taille de la liste est bien égale à 4
                                            assertTrue("Nous allons tester la liste attendu, si elle correspond bien à la taille donnée", listeJournaux.size() ==  4 );
                                            
                                            // Nom du code du journal = AC | Libelle = Achat
                                            assertTrue("Test si le journal est bien existant", listeJournaux.stream().filter(o -> o.getCode().equals("AC")).findFirst().isPresent());
		
		
	                      }
                                            
                                            
                                            //************************************************************************************************************/
                                            
                                            /*
                                            Création de la methode qui va tester la requête Sql InsertEcriture 
                                           
                                            @Test
                                            public void insertSqlEcritureTest() throws FunctionalException, NotFoundException{
                                                
                                                
                                            EcritureComptable ecritureComptable;
                                            
                                            //Création d'une variable écriture comptable
                                            ecritureComptable = new EcritureComptable();
                                            
                                            //Nous allons Set écriture comptable au nom de code du journal comptable ainsi qu'a son libelle
                                            ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
                                            
                                            //Création d'une variable Calendar qui sera défini via un GregorianCalendar
                                            Calendar calendar = new GregorianCalendar(2019,1, 1);
                                            
                                            //Nous allons Set la date sur l'écriture comptable
                                            ecritureComptable.setDate(calendar.getTime());
                                            
                                            //Nous définissons la référence de l'écriture comptable
                                            ecritureComptable.setReference("AC-2019/00001");
                                            
                                            //On set le libelle
                                            ecritureComptable.setLibelle("Libelle");
                                            
                                            // 401 = Fournisseur | 512 = Banque
                                            ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal("35"), null));
                                            
                                            ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512), null, null, new BigDecimal("35")));

                                            //On initialise la requête dans la variable écriture comptable
                                            dao.insertEcritureComptable(ecritureComptable);
                                            
                                            //Initialisation d'une variable test
                                            EcritureComptable testEcritureComptable;
                                            
                                            //Définition de la variable test en fonction de sa référence
                                            testEcritureComptable = dao.getEcritureComptableByRef("AC-2019/00001");
                                            
                                            //Nous vérifions que la variable TestEcriture n'est pas null
                                                assertNotNull(testEcritureComptable);
                                            
                                            }*/
                                            
                                            
                                            //************************************************************************************************************/
                                            
                                            
                                            /*
                                            Création de la méthode test pour supprimer une écriture comptable en base de donnée
                                            @throws NotFoundException
                                            
                                            @Test
                                            public void deleteSqlEcritureComptableTest() throws NotFoundException {
                                            
                                            // Référence de l'écriture
                                            Integer Id = -1;
                                            
                                            EcritureComptable testEcriture;
                                            
                                            testEcriture = dao.getEcritureComptable(Id);
                                            
                                            //Invocation de la méthode delete
                                            dao.deleteEcritureComptable(Id);
                                            
                                            
                                            
                                            
                                            //Vérification que l'écriture à bien été supprimée [Exception attendu]
                                            
                                            try {
                                                
                                            testEcriture = dao.getEcritureComptable(Id);
                                            
                                            fail("Exception attendue via NotFoundException");
                                            
                                            } catch (NotFoundException exception) {
                                                
                                            assertThat(exception.getMessage(), is("L'écriture comptale n'a pas été trouvée ! [Normal elle vient d'être supp] Id = " + Id));
                                            
                                            }
                                            
                                           // On nettoie la liste des lignes d'écritures
                                           testEcriture.getListLigneEcriture().clear();
                                           
                                           //On charge la liste des lignes d'écritures via la méthode Load du Dao
                                           dao.loadListLigneEcriture(testEcriture);
                                           
                                           
                                           
                                            //Vérification que les lignes ont bien étaient supprimées
                                            assertTrue(testEcriture.getListLigneEcriture().isEmpty());

                                                
                                            }*/
                                            
                                            
                                           //************************************************************************************************************/
                                            
                                            
                                            /*
                                            Test de la méthode update - Mise à jour de l'écriture comptable directement en base
                                            Mise à jour de la référence
                                            @throws NotFoundException
                                           */
                                            @Test
                                            public void updateSqlEcritureComptableTest() throws NotFoundException {
                                                
                                            EcritureComptable testEcritureUpdate;
                                            
                                            //Définition de la ligne que nous voulons mettre à jour
                                            testEcritureUpdate = dao.getEcritureComptable(-5);
                                            
                                            // Nous définissions la nouvelle référence
                                            testEcritureUpdate.setReference("AC-2019/00005");
                                            
                                            dao.updateEcritureComptable(testEcritureUpdate);
                                            
                                            EcritureComptable testEcritureToMAJ;
                                            
                                            testEcritureToMAJ = dao.getEcritureComptableByRef("AC-2019/00005");
                                            
                                            
                                            // On vérifie si l'update à bien été fonctionnel sur la ligne que nous avons défini
                                            assertNotNull("La mise à jour à t'elle bien était effective et bien trouvée avec la nouvelle référence attribuée qui est : ", testEcritureToMAJ);
                                            
                                            
                                            }
                                            
	
                                            
                                            }
                                            
        
        
        

    
    

