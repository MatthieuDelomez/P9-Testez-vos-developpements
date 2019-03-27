package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import java.util.Calendar;


/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================


    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }



    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    // TODO tests à compléter
    protected void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                                          new ConstraintViolationException(
                                              "L'écriture comptable ne respecte pas les contraintes de validation",
                                              vViolations));
        }

        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        if (!pEcritureComptable.isEquilibree()) {
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }

        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }
        // On test le nombre de lignes car si l'écriture à une seule ligne
        //      avec un montant au débit et un montant au crédit ce n'est pas valable
        if (pEcritureComptable.getListLigneEcriture().size() < 2
            || vNbrCredit < 1
            || vNbrDebit < 1) {
            throw new FunctionalException(
                "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }
        
        

        // TODO ===== RG_Compta_5 : Format et contenu de la référence
        // vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() == null
                    || !pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }
    
    
    //*****************************************************************************************************************//
    
    
    /*
    Implémentation de la méthode qui servira à récupérer la dernière séquence
    d'une écriture comptable
    @param pEcritureCommptable
    @return
    @throws NotFoundException
    */
    @Override
    public SequenceEcritureComptable getLastSequence(EcritureComptable pEcritureComptable) throws NotFoundException {
        
        return getDaoProxy().getComptabiliteDao().getLastSequence(pEcritureComptable);
    }
    
    
                                 /**
                                 * {@inheritDoc}
     
                                  Le principe :
                                 * 
                                 1.  Remonter depuis la persitance la dernière valeur de la séquence du journal pour l'année de l'écriture
                                                                   (table sequence_ecriture_comptable)
                                 * 
                                 2.  * S'il n'y a aucun enregistrement pour le journal pour l'année concernée :
                                 *
                                 1. Utiliser le numéro 1.
                                 *  
                                 * Sinon :
                                 *
                                 1. Utiliser la dernière valeur + 1
                                 *
                                 3.  Mettre à jour la référence de l'écriture avec la référence calculée (RG_Compta_5)
                                 *
                                 4.  Enregistrer (insert/update) la valeur de la séquence en persitance
                                 * 
                                                                  (table sequence_ecriture_comptable)   
                                 **/
    
    //*************************************** ===== METHODE A TESTER =====*****************************************
    
   @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) {
        
        
                                 //Nous allons récupérer en premier lieu la dernière séquence enregitrée
                                 SequenceEcritureComptable sequenceEcritureComptable;
                                 
                                 
                                 //Séquence que nous allons insérer au sein du Dao / Maj
                                 SequenceEcritureComptable sequenceInsert;
                                 
                                 //Création des objets SequenceEcritureComptable
                                 sequenceEcritureComptable = new SequenceEcritureComptable();
                                 sequenceInsert = new SequenceEcritureComptable();
                                 
                                 
                                 //Nous récupérons la dernière séquence via une variable Integer
                                 int derniereSequence;
                                 
                                 
                                 
                                 //Bloc try / catch pour tenter de récupérer la dernière séquence enregistrée
                                 try {
                                     
                                 sequenceEcritureComptable = getLastSequence(pEcritureComptable);
                                 
                                 //Nous définissons la valeur de la dernière séquence
                                 derniereSequence = sequenceEcritureComptable.getDerniereValeur() + 1;
                                 
                                 
                                 //Exception générée si la dernière séquence est égale à null ou à 1
                                 } catch (NotFoundException exception) {
                                     
                                 derniereSequence = 1;
                                 
                                 sequenceEcritureComptable = null ;
                                     
                                     
                                 }
                                 
                                 
                                 //Référence à intégrer
                                 String reference = "";
                                 
                                 //Integration du code du journal [On assigne la valeur de la réference + ecritureCompta + Journal + Code]
                                 reference +=  pEcritureComptable.getJournal().getCode();
                                 
                                 //Ajout d'un séparateur
                                 reference += "-"; 
                                 
                                 
                                 //Init de la date via un Calendar
                                 Calendar calendar = Calendar.getInstance();
                                 
                                 //On ajoute la date à l'écriture comptable
                                 calendar.setTime(pEcritureComptable.getDate());
                                 
                                 //Référence doit correspondre à l'année enregistrée
                                 reference += String.valueOf(Calendar.YEAR);
                                 
                                 
                                 //Ajout du séparateur
                                 reference += "/";
                                 
                                 //Et nous ajoutons la dernière séquence dans la variable reference
                                 // %05d = convertion en 0 || Si derniere Sequence = 1 alors en sortie = 00001
                                 //String.format necessaire pour réaliser la conversion
                                 reference += String.format("%05d", derniereSequence);
                                 
                                 
                                 //Mise à jour de la référence
                                 pEcritureComptable.setReference(reference);
                                 
                                 
                                 
                                 
                                 /*
                                 Enregistrement de la séquence au sein de la base de données
                                 Méthodologie : 
                                 *
                                 Si la méthode n'existe pas nous définissions la nouvelle séquence
                                 pour ensuite l'insérer dans la base de données
                                 */
                                 if (sequenceEcritureComptable == null) {
                                     
                                 sequenceEcritureComptable.setAnnee(calendar.get(Calendar.YEAR));
                                 sequenceEcritureComptable.setDerniereValeur(derniereSequence);
                                 
                                 
                                 insertSequence(sequenceInsert, pEcritureComptable.getJournal().getCode());
                                 
                                 }
                                 
                                 
                                 //Sinon on part de la séquence récupérée que nous allons modifier et la mettre à jour
                                 else{
                                     
                                 sequenceInsert = sequenceEcritureComptable;
                                 sequenceInsert.setDerniereValeur(derniereSequence);
                                 
                                 
                                 try {
                                     
                                 updateSequence(sequenceInsert, pEcritureComptable.getJournal().getCode());
                                     
                                 } catch (FunctionalException exception) {
                                     
                                 exception.printStackTrace();
                                     
                                     
                                 }
                                 
                                 
                                 }
                                     
                                     
                                 }
    
    
                                 /*
                                Implementation de la méthode insertSequence
                                @param sequenceInsert
                                @param code
                                */
                                 @Override
                                 public void insertSequence(SequenceEcritureComptable sequenceInsert, String code) {
                                 
                                 //Ensemble d'instruction qui doit être exécutée en même temps
                                 //Définition du Statut de la transaction
                                 TransactionStatus transactionStatus = getTransactionManager().beginTransactionMyERP();
                                 
                                 try {
                                     
                                 getDaoProxy().getComptabiliteDao().insertSequence(sequenceInsert, code);
                                 
                                 
                                 //Auto commit ==> Transaction Sql considérée comme une seule transaction
                                 getTransactionManager().commitMyERP(transactionStatus);
                                 
                                 transactionStatus = null;
                                 
                                 } finally {
                                 
                                 // Appel à la méthode Rollback pour annuler la transaction    
                                 getTransactionManager().rollbackMyERP(transactionStatus);
                                     
                                     
                                 }
                                 
                                 }
                                 
        
                   
        

                                 }
    
    

