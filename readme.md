Start Oracle DB
From src/main/resources/docker/oracle-db.yml execute following command:
docker-compose up -d

Default credentials:
sqlplus sys/Oradoc_db1@ORCLCDB as sysdba
jdbc:oracle:thin:@localhost:1521:ORCLCDB