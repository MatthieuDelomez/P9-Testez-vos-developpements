package com.dummy.myerp.testbusiness.business;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/boostrapContext.xml"})
@ActiveProfiles(profiles = "test")
public class IntegrationComptabiliteManagerImpl {
    
}
