# LightsaberApi

### Add maven secrets `\apache-maven-3.9.9\conf\settings.xml`

``` xml
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>USERNAME</username>
      <password>TOKEN</password>
    </server>
  </servers>
  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>
  <profiles>
  	<profile>
  	  <id>github</id>
  	  <repositories>
  		<repository>
  		  <id>central</id>
  		  <url>https://repo1.maven.org/maven2</url>
  		</repository>
  		<repository>
  		  <id>github</id>
  		  <url>https://maven.pkg.github.com/Pandadika/LightsaberShop/</url>
  		  <snapshots>
  			<enabled>true</enabled>
  		  </snapshots>
  		</repository>
  	  </repositories>
  	</profile>
  </profiles>
</settings>
```

### How to add an admin user 
POST to `localhost:8080/user/create`
``` json
{
    "username":"admin2",
    "password": "Passw0rd",
    "isAdmin": true
}
```

