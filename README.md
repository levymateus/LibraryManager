# LibraryManager

# INSTALAÇÃO
--------------------------------------------------------------------------------
 
 - O sistema possui conexão com o SGBD PostgreSQL (recomendado: versão 9.5); 

 - Por default a conexão é definida com usuário "postgres" e senha "postgres", via localhost. Caso seja necessário fazer
 alguma alteração em relação ao mesmo, é possivel fixar novos atributos abrindo o arquivo 'Main' e alterando os parâmetros passados 
 para o método 'initSistema';

 - Para a conexão é necessário incluir o driver 'postgresql-9.4.1208.jre6.jar' (o mesmo está contido no diretório sistema-biblioteca);

 - O esquema do banco de dados está declarado no arquivo 'schema.sql', juntamente com suas funções.

 # OBSERVAÇÕES
 ------------------------------------------------------------------------------

  - As mensagens de erro não são detalhadas nesta versão.

  - Os formulários possibilitam a inserção de qualquer caracter, fica a critério do usuário escolher a melhor maneira de informar os dados.
