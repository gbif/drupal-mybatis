#drupal-mybatis
This library provides a read-only persistence layer to access users and GBIF nodes information stored in the Drupal database.
The elements exposed in this library are basically:
  * ImsNodeMapper: gets the information of a GBIF org.gbif.api.model.registry.Node stored
  * UserService: gets the information of GBIF portal user.


## To build the project
Execute the Maven command:

```
  mvn clean packages verify install -Pdrupal-mybatis
```

The profile `drupal-mybatis` must contain the following settings to access the MySql Drupal data base.

```
<profile>
  <id>drupal-mybatis</id>
  <properties>
    <drupal.db.host>drupaldb_host</drupal.db.host>
    <drupal.db.name>drupal_db</drupal.db.name>
    <drupal.db.username>user</drupal.db.username>
    <drupal.db.password>password</drupal.db.password>
  </properties>
</profile>
```

## How to use this library

The Guice module `org.gbif.drupal.guice.DrupalMyBatisModule` can be used to instantiate instances of the services exposed
by this library, it requires that the settings described in the Maven profile above are passed in a java.util.Properties 
class, the test cases contain several examples of how this library can be used.
