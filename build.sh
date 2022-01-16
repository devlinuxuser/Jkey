#!/bin/bash
ARCH=$(uname -m)
GREEN='\033[0;32m'
NC='\033[0m'
exe() { echo -e "${GREEN}${@/eval/}${NC}" ; "$@" ; }

getArch(){
    if [[ $ARCH == *"64"* ]] && [[ $ARCH != *"arm"* ]]
    then
      ARCH="amd64"
    elif [[ $ARCH == *"32"* ]] || [[ $ARCH == *"86"* ]]
    then
      ARCH="x86"
    fi
}

build(){
    exe cd src
    exe javac -h c -d ../dist io/github/devlinuxuser/JKey.java
    mv c/io_github_devlinuxuser_JKey.h c/JKey.h
    cd c
    exe gcc -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux main.c -o main.o
    exe gcc -shared -fPIC  -o ../../lib/JKey-${ARCH}.so main.o
    cd ../../dist/
#    jar cf ../build/JKey.jar io/github/devlinuxuser/*.class io/github/devlinuxuser/utils/*.class ../lib/*.so
    cp -r ../lib .
    exe zip -r JKey.jar io lib
    rm -r ./lib
    mv ./JKey.jar ../build/JKey.jar

}

clean(){
    exe rm ./src/c/main.o
    exe rm -r ./dist/*
    exe rm -r ./build
    exe rm -r ./lib
    exe mkdir ./lib
    exe mkdir ./build
}
help(){
    echo "Usage : ./build.sh [command]"
    echo ""
    echo "commands:"
    echo "         clean : cleans the build output"
    echo "         build : compiles the source"
}

getArch


if [[ -z $@ ]]
then 
    help 
else
    for cmd in "$@"
    do
        $cmd;
    done
fi


