#!/bin/bash
# Exemplos de requests
# curl -v -XPOST -H "content-type: application/json" -d '{"apelido" : "xpto", "nome" : "xpto xpto", "nascimento" : "2000-01-01", "stack": null}' "http://localhost:9999/pessoas"
# curl -v -XGET "http://localhost:9999/pessoas/1"
# curl -v -XGET "http://localhost:9999/pessoas?t=xpto"
# curl -v "http://localhost:9999/contagem-pessoas"

GATLING_BIN_DIR=C:/Users/luizr/Documents/Softwares/gatling-3.9.5/bin

WORKSPACE=C:/Users/luizr/Documents/Projetos/Java/rinha-backend-2023-q3-java/stress-test

sh $GATLING_BIN_DIR/gatling.sh -rm local -s RinhaBackendSimulation \
    -rd "DESCRICAO" \
    -rf $WORKSPACE/user-files/results \
    -sf $WORKSPACE/user-files/simulations \
    -rsf $WORKSPACE/user-files/resources \

sleep 3

curl -v "http://localhost:9999/pessoa/contagem-pessoas"