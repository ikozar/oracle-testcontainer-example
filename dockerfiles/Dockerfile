FROM gvenzl/oracle-xe:21.3.0

ADD ora-db.tar.gz $ORACLE_BASE/oradata/
RUN rm "${ORACLE_BASE}"/"${ORACLE_SID}".zip
