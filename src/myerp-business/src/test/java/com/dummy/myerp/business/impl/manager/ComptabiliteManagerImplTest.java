package com.dummy.myerp.business.impl.manager;



import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.Date;

import com.dummy.myerp.business.impl.TransactionManager;

import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;

import com.dummy.myerp.technical.exception.FunctionalException;
import java.util.Calendar;
import java.util.GregorianCalendar;




@RunWith(MockitoJUnitRunner.class)
public class ComptabiliteManagerImplTest {

    // Classe à tester
    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();
    
    //Nous allons mocker le proxy Dao
     private DaoProxy daoProxyMocking = mock(DaoProxy.class);
     
     //Mocking du transaction manager
     private TransactionManager transactionManagerMocking = mock(TransactionManager.class);
    
     //Mocking de la classe ComptaDao
     private ComptabiliteDaoImpl comptaDaoMocking = mock(ComptabiliteDaoImpl.class);
     
     
     
     //********************************************************************************************//
     
     
     /*
     Configuration en Amont du Test via l'annotation Before :
     - Mocking du Dao
     - Mocking de la transactionManager
     */
     @Before
     public void Config() {
         
     ComptabiliteManagerImpl.setDaoProxy(this.daoProxyMocking);
     ComptabiliteManagerImpl.setTransactionManager(this.transactionManagerMocking);
     when(this.daoProxyMocking.getComptabiliteDao()).thenReturn(this.comptaDaoMocking);
     when(this.transactionManagerMocking.beginTransactionMyERP()).thenReturn(null);
         
     }
     
     
     //********************************************************************************************//
     

    /*
     Méthode de test d'une écriture comptable avec sa référence correcte
     @throws Exception
     */
    @Test
    public void checkEcritureComptableUnit() throws Exception {
        
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        
        //Ajout d'un Calendar
        Calendar calendar = new GregorianCalendar(2019, 1, 1);
        
        //On set la date au calendar
        vEcritureComptable.setDate(calendar.getTime());
        
        //On ajoute la référence à l'écriture comptable
        vEcritureComptable.setReference("AC-2019/00001");
        
        vEcritureComptable.setLibelle("Libelle");
        
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),  null, new BigDecimal(123), null));
        
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null,  new BigDecimal(123)));
        
        
        manager.checkEcritureComptableUnit(vEcritureComptable);
        
        }
    
    

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        
        manager.checkEcritureComptableUnit(vEcritureComptable);
        
         }
    
    

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {
        
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
        
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(1234)));
        
        
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {
        
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
        
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123), null));
        
        
        manager.checkEcritureComptableUnit(vEcritureComptable);
        
        
         }

         }
