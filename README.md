# PDB project Parkoviste

# To run

```
mvn install exec:java -DskipTests=True
```

or

```
clear & mvn install exec:java -DskipTests=True -Dusername=<xlogin00> -Dpassword=<dbpassword> -Durl=jdbc:oracle:thin:@//gort.fit.vutbr.cz:1521/gort.fit.vutbr.cz

```

# To run default test class only (AppTest.class)

```
mvn install

```


# To run single test class only

```
mvn install -Dtest=**/TableTests

```

or in simila rmanner to run app use -Dpassword, -Duser & -Durl that overrides default values in META-INF/hibernate.cf.xml

```
mvn install -Dtest=**/TableTests -Dpassword=<dbpassword>

```

