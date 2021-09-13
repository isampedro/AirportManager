AirportManager
## Instrucciones de uso:
En el directorio root, ejecutar:
```
./install.sh
```

### 1. CORRER REGISTRY:
Corre en el puerto 1099
En el directorio server/target/tpe1-g14-server-1.0-SNAPSHOT:
```
./run-registry.sh
```

### 2. CORRER SERVER:
En el directorio server/target/tpe1-g14-server-1.0-SNAPSHOT:
```
./run-server.sh
```

### 3. CORRER CLIENTES:
En el directorio client/target/tpe1-g14-client-1.0-SNAPSHOT:

```
Para correr Cliente de Administración:
./run-management -DserverAddress=xx.xx.xx.xx:yyyy -Daction=actionName [ -Drunway=runwayName | -Dcategory=minCategory ]

Para correr Cliente de Seguimiento de Vuelo:
./run-airline -DserverAddress=xx.xx.xx.xx:yyyy -Dairline=airlineName -DflightCode=flightCode

Para correr Cliente de Solicitud de Pista:
./run-runway.sh -DserverAddress=xx.xx.xx.xx:yyyy -Did=pollingPlaceNumber -Dparty=partyName

Para correr Cliente de Consulta:
./run-query.sh -DserverAddress=xx.xx.xx.xx:yyyy [ -Dstate=stateName | -Did=pollingPlaceNumber ] -DoutPath=​fileName
```