FROM ubuntu:latest

LABEL maintainer="Luiz Henrique Rossetti <luiz.rossetti@gmail.com>"


# docker build -t ro7luiz/compilers:latest .
# or
# docker tag <image_id> ro7luiz/compilers:latest
# docker image build . -t compilers


# docker push ro7luiz/compilers
# docker run -it compilers

ARG JAVA_VERSION="17.0.8-tem"
ARG MAVEN_VERSION="3.6.3"

RUN echo -----------------------------------------------INSTALLING DEPENDENCIES--------------------------------------------

RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y build-essential && \
    apt-get install -y software-properties-common && \
    apt-get install -y sudo curl git htop man zip unzip vim wget dialog apt-utils&& \
    rm -rf /var/lib/apt/lists/*

RUN echo -------------------------------------------------INSTALLING JAVA--------------------------------------------------

RUN \
    curl -s "https://get.sdkman.io" | bash && \
    bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && \
    yes | sdk install java ${JAVA_VERSION} && \
    yes | sdk install maven ${MAVEN_VERSION}"

ENV MAVEN_HOME="/root/.sdkman/candidates/maven/${MAVEN_VERSION}"
ENV JAVA_HOME="/root/.sdkman/candidates/java/${JAVA_VERSION}"
ENV PATH="$MAVEN_HOME/bin:$JAVA_HOME/bin:$PATH"

# Define working directory.
WORKDIR /app

# Define default command.
CMD ["bash"]