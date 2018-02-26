# Build Web Service:

## Intellij Ultimate:
 
- Install Lombok Plugin:
	-
	- Go to _**Preferences -> Plugins -> Search for "Lombok Plugin"**_
	- Install **Lombok Plugin**
	- **Restart** Intellij IDE

- Generate Sources:
	-
	- Right Click on project's root folder
	- **_Maven -> Generate Sources and Update Folders_**

- Maven Update:
	-
	- Right Click on project's root folder
	- **_Maven -> Reimport_**
		
## Eclipse (Windows)
- Install Lombok Plugin
	-
	- Download from: **https://projectlombok.org/download**
	- Execute the downloaded .jar file (java -jar or double click)
	- **Install** the plugin
	- **Restart** Eclipse IDE
- Eclipse issue on Windows (pom.xml error)
	-
	- Add this code right above -vmargs inside eclipse.ini file:
		```
		-vm
		C:\Program Files\Java\jdk1.6.0_07\bin\javaw.exe
		-vmargs
		```
- Generate Sources:
	-
	- Right click on project root folder
	- _**Run As -> Maven Generate Sources**_
	
- Maven Update:
	-
	- Right click on project's root folder
	- _**Maven -> Update**_
		
## Observations:
- Another way to generate sources:
	```
	$ mvn generate-sources
	```

## Using QueryDSL queries on URL:
- Simple Filter:
	```
	http://example.com/endpoint/search?name=fulano
	```
- Filter Combination:
	```
	http://example.com/endpoint/search?name=fulano&filial.code=BR001
	```
- These filter keys can be binded or excluded inside JPA Repository interfaces (I*Entity*Repository) inside project

## PGSQL:
- This project will attempt to create all necessary extensions and schemas for minimal functionality. If some of extensions did not install, you can try below command(s): 
	```
	CREATE EXTENSION "uuid-ossp"
	```
- You can review database data and schema inside **root/src/main/resources/** folder 