package com.dummy.myerp.testbusiness.business;



import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;


import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/bootstrapContext.xml"})
public class IntegrationManagerImpl {
    
                       //CLASSE A TESTER
	private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
	
	@Test
	public void insertEcritureTest() throws FunctionalException {
		
		EcritureComptable vEcritureComptable;
        
                                                vEcritureComptable = new EcritureComptable();
        
                                                vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        
                                                Calendar calendar = new GregorianCalendar(2018,1,1);
        
                                                vEcritureComptable.setDate(calendar.getTime());
        
                                                vEcritureComptable.setReference("AC-2019/00001");
        
                                                vEcritureComptable.setLibelle("Libelle");
        
                                                vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal("123.42"), null));
        
                                                vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512), null, null,new BigDecimal("123.42")));
        
        
                                                 //Insertion d'une écriture correcte
                                                manager.insertEcritureComptable(vEcritureComptable);
        
                                                List <EcritureComptable> liste = manager.getListEcritureComptable();
        
                                                assertTrue(liste.toString(), liste.stream().filter(o -> o.getReference().equals("AC-2019/00001")).findFirst().isPresent());
        
        
                                                //Insertion d'une écriture éronnée
        
                                                vEcritureComptable.getListLigneEcriture().clear();
        
                                                vEcritureComptable.setReference("AC-2019/00002");
        
                                                vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal("123.42"), null));

                                                vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512), null, null, new BigDecimal("123.41")));
		
		try {
		manager.insertEcritureComptable(vEcritureComptable);
            
                                                fail("Functional Exception attendu");
        
                                                } catch (FunctionalException e) {
            
                                               assertThat(e.getMessage(), is("L'écriture comptable n'est pas équilibrée."));
        
                                                }

	
                                                }
	
	                        @Test
	                        public void updateEcritureTest() throws FunctionalException {
		
		//Récupération des écritures
		List <EcritureComptable> liste = manager.getListEcritureComptable();
                
		EcritureComptable vEcriture;
                
		//Modification d'une écriture correctement (on change la reference)
		Stream<EcritureComptable> sp=liste.stream();
                
		vEcriture=sp.filter(o -> o.getReference().equals("AC-2016/00001")).findFirst().get();
                
		vEcriture.setReference("AC-2016/00002");
                
		//Update de l'ecriture
		manager.updateEcritureComptable(vEcriture);
		
		//Récupération de la nouvelle liste
		liste.clear();
		liste = manager.getListEcritureComptable();
		assertTrue("check que la nouvelle écriture est bien en db", liste.stream().filter(o -> o.getReference().equals("AC-2016/00002")).findFirst().isPresent());
		assertFalse("check que l'ancienne écriture n'est plus en db", liste.stream().filter(o -> o.getReference().equals("AC-2016/00001")).findFirst().isPresent());
        
		
		
		//Modification d'une écriture en faisant en sorte qu'elle soit éronnée
		sp=liste.stream();
		vEcriture=sp.filter(o -> o.getReference().equals("AC-2016/00002")).findFirst().get();
		vEcriture.getListLigneEcriture().clear();
        
                                                vEcriture.setReference("AC-2019/00002");
        
                                                vEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal("123.42"), null));
        
                                                vEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512), null, null, new BigDecimal("123.41")));
		
		try {
		manager.updateEcritureComptable(vEcriture);
            
                                                fail("Functional Exception attendu");
        
                                                } catch (FunctionalException e) {
            
                                                assertThat(e.getMessage(), is("L'écriture comptable n'est pas équilibrée."));
        }
		            
	}

    
}
