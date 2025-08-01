.PHONY: *

# The first command will be invoked with `make` only and should be `all`
all: clean build

build:
	./gradlew build

clean:
	./gradlew clean

compile:
	./gradlew assemble

format:
	./gradlew spotlessApply

publish:
	./gradlew publish
