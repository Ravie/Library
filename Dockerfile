FROM tomcat:8.0.43-jre8
ADD /target/Library-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
ADD server-library.xml /usr/local/tomcat/conf/
ADD tomcat-users.xml /usr/local/tomcat/conf/
EXPOSE 8080
CMD chmod +x /usr/share/tomcat8/bin/catalina.sh
CMD ["catalina.sh", "run"]
