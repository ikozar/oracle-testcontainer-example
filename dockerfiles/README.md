# build oracle image with precooked DB

We can use in tests oracle image with precooked DB instead of empty DB, it will allow
Pro:
* use DB with prepared data pack
* reduce oracle testcontainer start time
* prepare prod-like DB version and test on start new DB release version upgrade scripts

Procedure to prepare precooked oracle image
* start oracle testcontainer and apply all DB release version scripts
* copy all DB files from docker image /opt/oracle/* and pack into dockerfiles/ora-db.tar.gz
* build image and push to github
* change full image name in testcontainers.properties
