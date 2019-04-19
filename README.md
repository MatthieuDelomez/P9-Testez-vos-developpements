# Testez vos développements Java - MyERP

    Parcours: OpenClassrooms - Développeur d'application Java/JEE.
    
    Projet N°9: Testez vos développements Java 
    
    
# Description

    Fonctionalités des tests: 
    
    Test Unitaires - Leurs objectifs est de valider les règles de gestion de chaque 'Composants de l'application'
    Test D'Intégration - Leurs objectifs sont de valider la bonne intéraction entre les composants
    

# Technologies
    
    Java EE
    JUnit
    Mockito
    Jenkins
    Docker
    Maven
    Sonar
    SonarQube

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application



## Environnement de développement

    Ide: Netbeans 10.0
    OS: Linux Ubuntu

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)



### [Docker]
### Lancement

    Placer votre terminal dans le dossier ou ce trouve le fichier 'docker-compose.yml'
    [cd docker/dev]
    Effectuez la commande docker-compose up
    Votre image Docker (PostgreSql) à été crée

### Arrêt

    [cd docker/dev]
    docker-compose stop


### Remise à zero

    [cd docker/dev]
    docker-compose stop
    docker-compose rm -v
    docker-compose up
    
    
### [Jenkins]
### Lancement
    
    Installer Jenkins sur votre machine [Linux]:
    
    Assurez-vous de la version de java installée (Java-8-openjdk) 
    Effectuez la commande 'sudo apt-get install jenkins'
    Configurez son port au 8080
    Lancez jenkins à l'adresse suivante: http://localhost:8080
    Connectez-vous grâce à l'initialAdminPassword (/var/lib/jenkins/secrets/initialAdminPassword)
    Installez les plugins suggérés
    Une fois installés configurez votre utilisateur Jenkins
    Jenkins est prêt à l'emploi
    
    ********************************************************************************************
    
    Installer votre projet sur Jenkins:
    
    Assurez-vous que git est bien installé sur votre machine
    Créez un nouveau projet et 'Construire un projet free-style'
    Dans l'onglet 'Generale' assurez-vous que l'intitulé 'GitHub project' est bien coché
    Ajoutez le lien de votre Repo github dans l'espace URL de l'onglet 'GitHub project'
    Effectuez un Build via Jenkins et votre projet sera cloné dans le chemin: /var/lib/jenkins/workspace
    Ajoutez le Build Maven dans l'onglet 'Environnement de Build'
    Une fois le Build ajouté, allez dans 'Configuration Globale des Outils' puis dans 'Maven'
    Créez lui un nom et paramétrez le chemin de votre dossier maven 
    Ex: /Home/markometrie/maven
    Dans l'onglet 'Configurer' de votre projet ajoutez la version de Maven
    Placez vous à l'aide du terminal dans : /usr/lib/jvm
    Effectuez la commande 'sudo apt-get install openjdk-8-jdk' pour que Jekins puisse compiler votre code
    
    
### Sonar/SonarQube [Linux]

    Télécharger le dossier .zip sur le site de Sonar
    Dézippez le dossier Sonar 
    Vérifiez que PostgreSql / MySQL est bien installé sur votre oridnateur
    Créez une nouvelle base de données intitulée 'sonar' ainsi qu'un nouvel utilisateur 'sonar'
    (Vérifiez que le nouvel utilisateur à bien les droits)
    Editez le fichier sonar.properties situé dans: sonarqube/conf/sonar.properties
    Dans 'DATABASE':
    sonar.jdbc.username=sonar
    sonar.jdbc.password=sonar
    Allez au chemin suivant: sonarqube/bin/linux-x86-64
    Executez la commande: ./sonar.sh start à chaque fois que vous voulez lancer l'analyse via SonarQube
    Dashboard Sonar: http://localhost:9000 [username = admin | password = admin]
    
    *********************************************************************************************
    
    Installer SonarQube avec Jenkins:
    
    Une fois SonarQube installé sur votre machine, allez dans l'onglet 'Congurer Jenkins' et 'Configuration des Plugins'
    Dans 'Disponible' télécharger le plugins SonarQube plugins
    Une fois installé allez dans 'Configuration du Système' puis 'SonarQube Serveur'
    Donnez lui l'Url suivante: http://localhost:9000/sonar
    Sauvegardez puis allez dans 'Configuration Globale des Outils' et dans l'onglet 'SonarQube Scanner' installez de façon auto
    Allez dans la configuration de votre projet et ajoutez le Build SonarQube Scanner et ajoutez le propriétés d'analyse suivante
    
    #Required metadata
    sonar.projectKey=TestUnitaire-P9 
    sonar.projectName=TestUnitaire-P9
    sonar.projectVersion=1.0

    sonar.language=java
    sonar.java.binaries=**/target/classes

    sonar.login=admin
    sonar.password=admin

    #Path to source directory
    sonar.sources=/var/lib/jenkins/workspace/TestUnitaire-P9/src
    
    Le Build Maven ainsi que l'analyse via SonaQube Scanner est disponible
    
    
### Framework

    Spring Framework 4.3.11
    Apache Struts 2.5.14
    JUnit 4.12
    Apache Log4j 2.9.1
    Apache Cxf 3.0.2
    Struts Core 2.5.18
    Apache Maven 3.6.0
    Spring Web 3.0.5
    Mockito Core 2.21
    Sonar Scanner 3.6.0.1398
    Mockito Core 2.21
    Hibernate 4.2
    Maven Compiler 3.6.1
    Maven Resources 3.0.2
    Maven Failsafe 2.22
    Maven Surefire 2.20
    Maven Checkstyle 2.17
    Maven Javadoc 2.10.4

  
  
  ### Base de donnée
  
    PostgreSQL latest
    PGAdmin III
    Image Docker
 

 ### Bugs corrigés

    Dans la méthode: public BigDecimal getTotalCredit(){
    getDebit doit être remplacé par getCredit
    }

    SqlContext: Manquement d'une virgule dans la requête Insert

    Classpath du fichier BoostrapContext à modifier

    Uncessary Boxxing to Integer dans la classe ResultHelper dans la méthode public static Long getLong
 

   # Contact
 <ul>
  <li>
    Site: <a title="Site" href="http://www.matthieudelomez.fr">
     matthieudelomez.fr</a>
  </li>
  <li>
    Linkedin: <a title="Linkedin" href="https://www.linkedin.com/in/matthieu-delomez-8a46b9146/">
     https://www.linkedin.com/in/matthieu-delomez</a>
  </li>
  <li>
    Mail: <a title="mail" href="matthieu.delomez@gmail.com">
     matthieu.delomez@gmail.com</a>
  </li>
  </ul>
  <br>

  
