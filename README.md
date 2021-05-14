# TmailServer
Simulador de um servidor de emails tolerante a falhas.

# Compilação
1. Baixe o software [netbeans](https://netbeans.apache.org/download/index.html) e instale na sua máquina.
2. Abra o projeto baixado do github no netbeans.
3. Vá até o projeto e clique em ```clean and build```.

# Execução
Para executar é necessário abrir 4 terminais em um sistema ou subsistema linux, sendo um deles referente ao cliente, e os outros são referentes ao servidor e suas réplicas.
## Execução do cliente:
  - `./smartrun.sh tmail.TmailClient [index]` onde `[index]` é um numero de `0..n`
## Execução do Servidor:
  - `./smartrun.sh tmail.TmailServer [index]` onde `[index]` é um numero de `0..2`
