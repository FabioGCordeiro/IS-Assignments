Mudanças:

---------MYSQL--------------

mysql -u root -p

-> Dar permissões a um utilizador 

GRANT ALL PRIVILEGES ON *.* TO 'teuUsername'@'localhost'; (se não existir crias um novo)

FLUSH PRIVILEGES;

exit

-> Reiniciar mysql


---------PERSISTENCE.XML----------

java:jboss/datasources/MySqlDS --> MySqlDS = Nome da DataSource

user = user do mysql (com as permisses referidas em cima) e igual no standalone

password = password do mysql e igual no standalone

jdbc:mysql://127.0.0.1:3306/mydb ---> mydb  o nome da database que tem de ser criada no mysql

driver name = driver name na datasource


--------------DEPLOY-------------

mvn clean install wildfly:deploy -Pwildfly-runtime


-----------USERNAME E PASSWORD DO DEPLOY-----------

Credenciais do Wildfly
