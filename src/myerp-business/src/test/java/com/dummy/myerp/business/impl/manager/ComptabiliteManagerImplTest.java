package com.dummy.myerp.business.impl.manager;



import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;




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
    
    
    //********************************************************************************************/
    
    
    /*
    [Exception attendue]
    Méthode qui sert à générée une exception si l'écriture est vide
    @throws Exception
    */
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {
        
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        
        manager.checkEcritureComptableUnit(vEcritureComptable);
        
         }
    
    
     //********************************************************************************************/
    
    
    /*
    Exception générée si l'écriture n'est pas équilibrée
    @throws Exception
    */
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
    
    
    //********************************************************************************************/
    
    
     /*
    Exception générée si deux ecritures comptables son insérées avec de montant Débit
    @throws Exception
    */
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
    
    
    //********************************************************************************************/
    
    
    
         /*
         Méthode qui va permettre de vérifier un montant négatif sur une écriture compùtable
         */
          @Test
          public void checkEcritureComptableUnitRG4() throws Exception {
              
           EcritureComptable ecritureComptable;
           
           ecritureComptable = new EcritureComptable();
           
           ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
           
           Calendar calendar = new GregorianCalendar(2019, 1, 1);
           
           ecritureComptable.setDate(calendar.getTime());
           
           ecritureComptable.setLibelle("Libelle");
           
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal("-12"),  null));
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal("15"), null));
        
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal("3")));
           
           
           ecritureComptable.setReference("AC-2019/00001");
           
           manager.checkEcritureComptableUnit(ecritureComptable);
           
           }
          
          
          //********************************************************************************************/
          
          
          /*
          Verification qu'une exception est lancée si le journal ne correspond pas à la reference de l'ecriture
          @throws Exception
          */
          @Test(expected = FunctionalException.class)
          public void  checkEcritureComptableUnitRG5Journal() throws Exception {
              
              
           EcritureComptable ecritureComptable;
           
           ecritureComptable = new EcritureComptable();
           ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
           ecritureComptable.setDate(new Date());
           ecritureComptable.setLibelle("Libelle");
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),null, new BigDecimal(123), null));
           
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
           
           ecritureComptable.setReference("BQ-2018/00123");
           
           manager.checkEcritureComptableUnit(ecritureComptable);
                      
                     
          }
          
          
          //********************************************************************************************/
          
          
          
          /*
          Vérification qu'une exception est générée si la date de l'écriture ne correspond pas à la reference
          @throws Exception
          */
          @Test(expected = FunctionalException.class)
          public void checkEcritureComptableRG5Date() throws Exception {
              
           EcritureComptable ecritureComptable;
           ecritureComptable = new EcritureComptable();
           ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
           Calendar calendar = new GregorianCalendar(2019, 1, 1);
           ecritureComptable.setDate(calendar.getTime());
           ecritureComptable.setLibelle("Libelle");
           
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1), null, new BigDecimal(123),null));
           
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal(123)));
           
           ecritureComptable.setReference("AC-2017/001236");
           manager.checkEcritureComptableUnit(ecritureComptable);
           
           }
          
          
           //********************************************************************************************/
          
          
           /*
          Vérification qu'une erreur est générée si la reference n'est pas unique
          @throws Exception
          */
          @Test
          public void checkEcritureComptableCtxtRG6() throws Exception {
              
           //Ecriture que nous allons tester
           EcritureComptable ecritureComptable;
           ecritureComptable = new EcritureComptable();
           
           ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
           Calendar calendar = new GregorianCalendar(2019,1,1);
           ecritureComptable.setDate(calendar.getTime());
           ecritureComptable.setLibelle("Libelle");
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),null, new BigDecimal("123.124"),null));
           
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal("123.124")));
           
           ecritureComptable.setReference("AC-2019/000123");
           
           
           //Ecriture comptable de la Dao que nous allons mocker avec la même reference
           EcritureComptable daoEcritureComptable;
           
           daoEcritureComptable = new EcritureComptable();
           
           daoEcritureComptable.setId(12);
           
           daoEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
           
           Calendar calendar2 = new GregorianCalendar(2019,1,1);
           
           daoEcritureComptable.setDate(calendar2.getTime());
           
           daoEcritureComptable.setLibelle("Libelle");
           
           
           
          daoEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),null, new BigDecimal("123.124"),null));
          
          
           daoEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null,new BigDecimal("123.124")));
           
           
           daoEcritureComptable.setReference("AC-2019/000123");
           
           
           when(this.comptaDaoMocking.getEcritureComptableByRef(ecritureComptable.getReference())).thenReturn(daoEcritureComptable); 
           
           
           //Création d'un bloc try / catch qui va lever une exception si une reference commune est insérer sur une écriture
           // [ID : null]
           try {
               
           manager.checkEcritureComptableContext(ecritureComptable);
           
           fail("Une Exception est attendu !");
           
           } catch (FunctionalException exception) {
               
           assertThat(exception.getMessage(), is("Une autre écriture comptable existe avecc la même référence"));
           
           
           }
           
           
           
           //Functional Exception levée si nous avons la même référence et un Id differents
           ecritureComptable.setId(13);
           try {
               
           manager.checkEcritureComptableContext(ecritureComptable);
           
           fail("Exception attendu !");
           
           } catch(FunctionalException exception) {
               
           assertThat(exception.getMessage(), is("Une autre écriture comptable existe avec la même reference"));
           
           }
           
           
           
           //Nous allons vérifier si il n'y a pas d'erreur si nous avons le même Id
           ecritureComptable.setId(12);
           manager.checkEcritureComptableContext(ecritureComptable);
           
           
           //Vérification de la reference non trouvée 
           daoEcritureComptable = null;
           when(this.comptaDaoMocking.getEcritureComptableByRef(ecritureComptable.getReference())).thenThrow(NotFoundException.class); 
        
           ecritureComptable.setId(0);
           manager.checkEcritureComptableContext(ecritureComptable);
               
               
               
               
           }
          
          
          /*
          Vérification que l'exception est bien générée si nous avons une ligne d'écriture à plus de 2 chiffres apres la virgule
          @throws Exception
          */
          @Test(expected = FunctionalException.class)
          public void checkEcritureComptableUnitRG7() throws FunctionalException {
              
              
           EcritureComptable ecritureComptable;
           ecritureComptable = new EcritureComptable();
           ecritureComptable.setJournal(new JournalComptable("AC", "Achat"));
           Calendar calendar = new GregorianCalendar(2019,1,1);
           
           ecritureComptable.setDate(calendar.getTime());
           ecritureComptable.setLibelle("Libelle");
           
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),null, new BigDecimal("123.124"), null));
           
           
           ecritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2), null, null, new BigDecimal("123.124")));
           
           ecritureComptable.setReference("AC-2019/00123");
           manager.checkEcritureComptableUnit(ecritureComptable);
              
          }
          
        
           
          }
    
    
      

