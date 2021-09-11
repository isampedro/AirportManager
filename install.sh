mvn clean install
cd server/target
echo "$PATH"
tar -xzf *-bin.tar.gz
cd rmi-params-server-1.0-SNAPSHOT
chmod u+x run-*
cd ../../client/target
echo "$PATH"
tar -xzf *-bin.tar.gz
cd rmi-params-client-1.0-SNAPSHOT
chmod u+x run-*
cd ../../