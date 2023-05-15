FROM jboss/wildfly:25.0.0.Final

WORKDIR /usr/src/app

COPY pom.xml ./

COPY . .

USER root

RUN yum install wget -y

RUN wget https://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo --no-check-certificate

RUN sed -i s/\$releasever/6/g /etc/yum.repos.d/epel-apache-maven.repo

RUN yum install -y apache-maven

RUN yum install java-1.8.0-devel -y

RUN mvn clean install

WORKDIR /usr/src/app/target

ADD ./target/iri_file_upload-0.1.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8181

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
