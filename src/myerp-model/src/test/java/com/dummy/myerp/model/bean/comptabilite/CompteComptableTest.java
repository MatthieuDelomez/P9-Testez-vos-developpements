package com.dummy.myerp.model.bean.comptabilite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;


public class CompteComptableTest {
    
    
    @Test
    public void getNumeroTest() {
        
        //Création d'un compte pour pouvoir le tester
        CompteComptable banque = new CompteComptable(512, "banque");
        CompteComptable client = new CompteComptable(411, "client");
        CompteComptable fournisseur = new CompteComptable(401, "fournisseur");
        
        //Compte que nous allons tester 
        //que nous initialisons à null
        CompteComptable compteTest = null;
        
        //Nous ajoutons les comptes dans une liste
        List<CompteComptable> testList = new ArrayList<CompteComptable>();
        
        testList.addAll(Arrays.asList(banque, client, fournisseur));
        
        
        /*
        Méthode de test
        */
        compteTest = CompteComptable.getByNumero(testList, 512);
        Assert.assertTrue(compteTest.toString(), compteTest.getNumero() == 512 && compteTest.getLibelle().equals("banque"));
    }
    
}