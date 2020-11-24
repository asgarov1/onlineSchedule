FROM store/oracle/database-enterprise:12.2.0.1-slim

ENV DB_SID=MYORACLE
ENV DB_PDB=MYPDB
ENV DB_MEMORY=1GB
ENV DB_PASSWD=password
