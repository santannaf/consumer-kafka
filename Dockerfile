###FROM alpine:3.13
###
###ENV VERSION_GRAALVM=22.3.1
###
###RUN apk --no-cache add ca-certificates wget gcc zlib zlib-dev libc-dev bash
###
###ENV GLIBC_VERSION=2.34-r0
###ENV LD_BIND_NOW=1
###
###RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub \
###    &&  wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-$GLIBC_VERSION.apk" \
###    &&  apk --no-cache add "glibc-$GLIBC_VERSION.apk" \
###    &&  rm "glibc-$GLIBC_VERSION.apk" \
###    &&  wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-bin-$GLIBC_VERSION.apk" \
###    &&  apk --no-cache add "glibc-bin-$GLIBC_VERSION.apk" \
###    &&  rm "glibc-bin-$GLIBC_VERSION.apk" \
###    &&  wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-i18n-$GLIBC_VERSION.apk" \
###    &&  apk --no-cache add "glibc-i18n-$GLIBC_VERSION.apk" \
###    &&  rm "glibc-i18n-$GLIBC_VERSION.apk"
###
###COPY ./build/native/nativeCompile/gvapp-demo .
###
###ENTRYPOINT ["./gvapp-demo"]
##
##FROM centos:7 as builder
##
##MAINTAINER thalessantanna
##
##RUN yum update -y \
##    && yum install -y wget gcc gdb zip unzip libacl-devel jansson-devel \
##    && yum install -y keyutils-libs-devel perl rpcgen systemd-devel zlib-devel bash \
##    && yum install -y glibc-devel build-essential libz-dev zlib1g-dev libstdc++-static \
##    && yum clean all \
##    && rm -Rf /var/cache/yum
##
##ARG GRAALVM_VERSION=22.3.1
##
##RUN wget https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-$GRAALVM_VERSION/graalvm-ce-java17-linux-amd64-$GRAALVM_VERSION.tar.gz \
##    && tar -xvzf graalvm-ce-java17-linux-amd64-$GRAALVM_VERSION.tar.gz \
##    && rm -Rf graalvm-ce-java17-linux-amd64-$GRAALVM_VERSION.tar.gz \
##    && /graalvm-ce-java17-$GRAALVM_VERSION/bin/gu install native-image
##
##ENV JAVA_HOME=/graalvm-ce-java17-$GRAALVM_VERSION
##ENV PATH=$JAVA_HOME/bin:$PATH
##
##WORKDIR /project
##
##ADD build/libs/*.jar cmd.jar
##ADD src/main/resources/META-INF META-INF
##RUN jar -xvf cmd.jar
##RUN cp -R META-INF BOOT-INF/classes
##
###ADD compile.sh .
###RUN chmod 777 compile.sh
##
###RUN native-image -H:Name=app @META-INF/native-image/argfile -cp .:BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`
###RUN native-image -H:Name=app -cp .:BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`
###RUN native-image -H:ConfigurationFileDirectories=@META-INF/native-image -H:Name=app -cp .:BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`
##
##RUN native-image \
##    --no-fallback \
##    -g \
##    -H:+AddAllCharsets \
##    -H:+ReportExceptionStackTraces \
##    --enable-monitoring=heapdump,jvmstat \
##    --enable-http \
##    --enable-https \
##    -H:TraceclassInitialization=true \
##    --initialize-at-build-time=org.sl4j.MDC,ch.qos.logback.core.util.Loader,ch.qos.logback.core.pattern.parser.Parser,ch.qos.logback.core.util.StatusPrinter,org.slf4j.simple.SimpleLogger,org.slf4j.impl.StaticLoggerBinder,org.slf4j.LoggerFactory,ch.qos.logback.classic.Logger,ch.qos.logback.core.spi.AppenderAttachableImpl,ch.qos.logback.core.status.StatusBase,ch.qos.logback.classic.Level,ch.qos.logback.core.status.InfoStatus,ch.qos.logback.classic.PatternLayout,ch.qos.logback.core.CoreConstants \
##    -H:Name=app \
##    -Dspring.native.remove-unused-autoconfig=true \
##    -Dspring.native.remove-yaml-support=true \
##    -cp BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`
##
##FROM oraclelinux:9-slim as execute
##MAINTAINER thalessantanna
##COPY --from=0 "/project/app" gvapp
##CMD [ "sh", "-c", "./gvapp -Dfile.encoding=UTF-8 -Duser.countr=BR -Duser.language=pt -Duser.timezone=America/Sao_Paulo" ]
##
##
###FROM alpine:3.13 as execute
###
###MAINTAINER thalessantanna
###
###ENV VERSION_GRAALVM=22.3.1
###
###RUN apk --no-cache add ca-certificates wget gcc zlib zlib-dev libc-dev bash
###
###ENV GLIBC_VERSION=2.34-r0
###ENV LD_BIND_NOW=1
###
###RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub \
###    &&  wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-$GLIBC_VERSION.apk" \
###    &&  apk --no-cache add "glibc-$GLIBC_VERSION.apk" \
###    &&  rm "glibc-$GLIBC_VERSION.apk" \
###    &&  wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-bin-$GLIBC_VERSION.apk" \
###    &&  apk --no-cache add "glibc-bin-$GLIBC_VERSION.apk" \
###    &&  rm "glibc-bin-$GLIBC_VERSION.apk" \
###    &&  wget "https://github.com/sgerrand/alpine-pkg-glibc/releases/download/$GLIBC_VERSION/glibc-i18n-$GLIBC_VERSION.apk" \
###    &&  apk --no-cache add "glibc-i18n-$GLIBC_VERSION.apk" \
###    &&  rm "glibc-i18n-$GLIBC_VERSION.apk"
###
###COPY --from=0 "/app/build/native/nativeCompile/gvapp-demo" gvapp
###
###CMD [ "sh", "-c", "./gvapp" ]
#
#
##FROM openjdk:17-jdk-alpine as build
##WORKDIR /workspace/app
##
##COPY gradle gradle
##COPY build.gradle.kts settings.gradle.kts gradlew ./
##COPY src src
##
##RUN chmod 777 ./gradlew
##
##RUN ./gradlew clean build
#
#FROM openjdk:17-jdk-alpine
##VOLUME /tmp
#
##ARG DEPENDENCY=/workspace/app/build/libs
#
#COPY gradle gradle
#COPY build.gradle.kts settings.gradle.kts gradlew .
#COPY src src
#
#RUN chmod 777 gradlew
#
#RUN ./gradlew clean build
#
#
##RUN chmod 777 ./build/libs/demo-consumer-kafka-process-0.0.1.jar
##COPY --from=build ${DEPENDENCY}/*.jar /app.jar
#COPY build/libs/demo-consumer-kafka-process-0.0.1.jar /app.jar
#ENTRYPOINT java -jar /app.jar