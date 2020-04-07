home=${HOME}
cpu_count=2
gradle=docker run --rm -it \
	--cpus $(cpu_count) \
	-v $(shell pwd):/build_dir \
	-v $(home)/.m2:/root/.m2 \
	-v $(home)/.gradle:/root/.gradle \
	-w /build_dir \
	--entrypoint gradle \
	gradle:5.6.4-jdk11 \
	--no-daemon \
	--parallel \
	--max-workers=$(cpu_count) \
	--stacktrace
java=docker run --rm -it \
	--cpus $(cpu_count) \
	-v $(shell pwd):/build_dir \
	-w /build_dir \
	--entrypoint java \
	openjdk:11.0.6-jre-buster

compile:
	$(gradle) clean testClasses

pack:
	$(gradle) clean shadowJar

becnh: pack
	$(java) -jar build/libs/benchmarks.jar
