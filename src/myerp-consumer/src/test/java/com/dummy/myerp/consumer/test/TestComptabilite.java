
package com.dummy.myerp.consumer.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Test;


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



public class TestComptabilite {
    
    
    /*
    Implementation de la classe ComptaDao que nous allons tester
    */
    ComptabiliteDaoImpl dao = ComptabiliteDaoImpl.getInstance();
    
    
    
    //************************* TEST DE LA METHODE INSERT ECRITURE******************************
    
    @Test
    public void insertEcritureTest() throws FunctionalException, NotFoundException {
        
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        Calendar calendar = new GregorianCalendar(2019, 1, 1);
        
        vEcritureComptable.setDate(calendar.getTime());
        vEcritureComptable.setReference("AC-2019/00001");
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
        
                                                                                                                                                        null, new BigDecimal("341"),
        
                                                                                                                                                         null));
        
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
        
                                                                                                                                                        null, null, new BigDecimal("341")));
        
        dao.insertEcritureComptable(vEcritureComptable);
        
        EcritureComptable testEcriture;
        
        testEcriture = dao.getEcritureComptableByRef("AC-2019/00001");
        
        assertNotNull(testEcriture);
        
    }
    
    
    
    //***************************TEST DE LA METHODE DELETE ECRITURE COMPTABLE************************
    
    
    /*
    Nous allons tester la méthode pour supprimer une ecriture comptable
    */
    /*
    @Test
    public void testdeleteEcritureComptableTest() throws NotFoundException {
        
        Integer pId = -1 ;
        EcritureComptable testEcriture;
        testEcriture = dao.getEcritureComptable(pId);
        
        /*
        Bloc Try qui va vérifier si l'écriture à bien été supprimer
        
        try {
            
        testEcriture = dao.getEcritureComptable(pId);
        
        fail("Exception Not Found");
        
        } catch(NotFoundException e) {
            
        assert That(e.getMessage(), is("EcritureComptable non trouvée : id =" + pId));
        
        }
        
        
        
        /*
        On clean la liste des lignes d'écritures
        */
  //      testEcriture.getListLigneEcriture().clear();
        
        /*
        On charge la ligne d'écrite gràâce à la méthode load
        */
     //   dao.loadListLigneEcriture(testEcriture);
        
        /*
        Nous vérifions que les lignes d'écritures ont été suprimées
        */
        //assertTrue(testEcriture.getListLigneEcriture().isEmpty());
    

    
    
}
