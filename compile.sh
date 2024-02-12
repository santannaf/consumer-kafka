echo "[-->] Expanding the Spring Boot fat jar"

#BINARY=$(./gradlew nameBinary -q)
#MAINCLASS=$(./gradlew resolveMainClassName -q)
#echo "Spring Boot Main class ('start-class') is '$MAINCLASS'"
#
#echo "[-->] Expanding the Spring Boot fat jar"
#JAR=$(./gradlew nameJar -q);
#jar -xvf ./build/libs/"$JAR" >/dev/null 2>&1
#cp -R META-INF BOOT-INF/classes
#
#echo "[-->] Set the classpath to the contents of the fat jar (where the libs contain the Spring Graal AutomaticFeature)"
#LIBPATH=`find BOOT-INF/lib | tr '\n' ':'`
#CP=BOOT-INF/classes:$LIBPATH
#
#GRAALVM_VERSION=$(native-image --version)
#echo "[-->] Compiling Spring Boot App '$BINARY' with $GRAALVM_VERSION"

time native-image \
    --no-fallback \
    -g \
    -H:+AddAllCharsets \
    -H:+ReportExceptionStackTraces \
    --enable-monitoring=heapdump,jvmstat \
    --enable-http \
    --enable-https \
    -H:TraceClassInitialization=true \
    --initialize-at-build-time=org.sl4j.MDC,ch.qos.logback.core.util.Loader,ch.qos.logback.core.pattern.parser.Parser,ch.qos.logback.core.util.StatusPrinter,org.slf4j.simple.SimpleLogger,org.slf4j.impl.StaticLoggerBinder,org.slf4j.LoggerFactory,ch.qos.logback.classic.Logger,ch.qos.logback.core.spi.AppenderAttachableImpl,ch.qos.logback.core.status.StatusBase,ch.qos.logback.classic.Level,ch.qos.logback.core.status.InfoStatus,ch.qos.logback.classic.PatternLayout,ch.qos.logback.core.CoreConstants \
    -H:Name=app \
    -Dspring.native.remove-unused-autoconfig=true \
    -Dspring.native.remove-yaml-support=true \
    -cp BOOT-INF/classes:`find BOOT-INF/lib | tr '\n' ':'`

#rm -rf BOOT-INF META-INF org


#command:
#sh -c ./app -Dfile.encoding=UTF-8 -Duser.countr=BR -Duser.language=pt -Duser.timezone=America/Sao_Paulo