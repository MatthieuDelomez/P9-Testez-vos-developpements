
package com.dummy.myerp.consumer.test;

import static org.junit.Assert.*;
import org.junit.Test;


import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import java.util.List;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


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
        
        
        

    
    
}
