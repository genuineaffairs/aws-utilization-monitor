FROM zalando/openjdk:8u45-b14-6

MAINTAINER Jan Löffler <jan.loeffler@zalando.de>

RUN mkdir -p /newrelic

ADD scm-source.json /scm-source.json
ADD newrelic/newrelic.jar /newrelic/newrelic.jar
ADD newrelic/newrelic.yml /newrelic/newrelic.yml
ADD target/aws-utilization-monitor.jar /aws-utilization-monitor.jar

EXPOSE 8080

CMD java $(java-dynamic-memory-opts) -javaagent:/newrelic/newrelic.jar -jar /aws-utilization-monitor.jar
