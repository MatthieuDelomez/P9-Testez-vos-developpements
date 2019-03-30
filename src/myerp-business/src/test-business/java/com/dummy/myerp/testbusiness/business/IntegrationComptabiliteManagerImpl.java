package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/bootstrapContext.xml"})
@ActiveProfiles(profiles = "test")
@Transactional
public class IntegrationComptabiliteManagerImpl {
    
    
    
    //Import de la classe ComptabiliteManagerImpl
    private ComptabiliteManagerImpl comptaManager = new ComptabiliteManagerImpl();
    
    
    /*
    Nous allons tester la méthode insert qui nous servira à insérer une écriture en base
    @param FunctionalException
    */
    @Test
    public void insertEcritureTest() throws FunctionalException {
        
        //Création de la variable qui va contenir l'écriture
        EcritureComptable ecritureComptable;
        
        ecritureComptable = new EcritureComptable();
        
        //On set le journal comptable à l'écriture concernée
        ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        
        //Création d'un calendar
        Calendar calendar = new GregorianCalendar(2019, 1, 1);
        
        //On set la date d'ajout au calendar pour l'intégrer dans l'écriture compatble
        ecritureComptable.setDate(calendar.getTime());
        
        //On ajout la référence à ajouter
        ecritureComptable.setReference("AC-2019/00001");
        
        //On ajoute le Libelle
        ecritureComptable.setLibelle("Libelle");
        
        //On ajoute le crédit ainsi que le débit sous forme de BigDecimal
        //Fournisseur = 401
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), 
                           
                                                                                                                                                      null, new BigDecimal(125), null));
        
        //banque = 512
        ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512), 
                           
                                                                                                                                                      null, null, new BigDecimal(125)));
        
        
        //Nous allons insérer une écriture comptable en base
        comptaManager.insertEcritureComptable(ecritureComptable);
        
        //Import de la liste d'écriture
        List <EcritureComptable> listeEcritureComptable = comptaManager.getListEcritureComptable();
        
        //Vérification de l'assertion - Nous renverra True si l'assertion est inserée correctement en base
       assertTrue(listeEcritureComptable.toString(), listeEcritureComptable.stream().filter(o -> o.getReference().equals("AC-2019/00001")).findFirst().isPresent());
       
       
       //Insertion d'une écrite fausse
       ecritureComptable.getListLigneEcriture().clear();
       
       ecritureComptable.setReference("AC-2019/00002");
       
       ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401), null, new BigDecimal("123.42"),  null));
       
       ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512), null, null,  new BigDecimal("123.50")));
       
       try{
           
       comptaManager.insertEcritureComptable(ecritureComptable);
       
       fail("Nous attendons une exception !");
       
       } catch (FunctionalException exception) {
           
       assertThat(exception.getMessage(), is("L'écriture comptable n'est pas équilibrée"));
           
       }

        
    }
    
    
}
