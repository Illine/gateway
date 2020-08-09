FROM openjdk:11

LABEL Mainteiner="Kovtun Evgeniya, eekovtun@gmail.com"

ARG APP_HOME=/opt/gateway
ARG APP_JAR=gateway.jar

ENV TZ=Europe/Moscow \
    HOME=$APP_HOME \
    JAR=$APP_JAR

WORKDIR $HOME

COPY build/libs/gateway.jar $HOME/$JAR

ENTRYPOINT java $JAVA_OPTS -jar $JAR