
package com.dummy.myerp.consumer.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.Test;


import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/com/dummy/myerp/consumer/applicationContext.xml"})
@ActiveProfiles(profiles="test")
@Transactional
public class TestComptabilite {
    
    
    /*
    Implementation de la classe ComptaDao que nous allons tester
    */
    ComptabiliteDaoImpl dao = ComptabiliteDaoImpl.getInstance();
    
                     @Test
	public void testDaoNull() {
		assertNotNull(dao);

	}
        
        

    
    
}
