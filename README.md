# 2023-2-proj-final-dietas-app
# DietasApp

## Proposta e Público Alvo

O DietasApp é um aplicativo voltado para pessoas que desejam um lugar para organizar a sua alimentação. Nele os usuários poderão criar novas dietas e consultar os valores nutricionais contidos em cada uma delas.

Para cada dieta, o usuário poderá adicionar uma coletânea de refeições e personalizar os alimentos que serão consumidos em cada uma das refeições, além de se informar com as quantidades de calorias, gorduras, carboidratos e proteinas de cada **dieta**/**refeição**/**alimento** definido.

## Principais Funcionalidades

### LogIn e SignUp

O usuário possui a capacidade de criar uma nova conta onde serão armazenadas as suas dietas. Para realizar o cadastro, basta clicar no botão **Cadastre-se** e preencher os campos de **Nome**, **Email**, **Telefone** e **Senha**.

Ao final, caso todos os dados forem válidos, uma nova conta será criada e o cliente poderá realizar o login utilizando do **email** e **senha** registrados, sendo redirecionado para a home do app onde são exibidas as dietas desse cliente.

### Funcionalidade de Dietas

Na tela de Dietas, são exibidas todas as dietas relacionadas ao usuário logado. Em cada card é exibido o nome, uma descrição e os valores nutricionais dessa dieta.

Para criar uma nova dieta, basta ao usuário clicar no botão de '**+**' presente no canto inferior da tela. Será exigido um nome e uma descrição para o registro do novo card.

Além de criar, é dado ao cliente a possibilidade de excluir ou editar(nome/descrição) uma dieta ao interagir com os respectivos ícones presentes dentro do card. Além de existir a possibilidade de favoritar uma dieta clicando no ícone de estrela (apenas 1 dieta pode ser a favorita).

Ao interagir com uma dieta, o cliente será redirecionado para a tela de refeições, onde será exibido todas as refeições associadas aquela dieta.

### Funcionalidade de Refeições

Para a tela de refeições, são exibidos os card referentes a dieta escolhida. Cada card apresenta um título e os valores nutricionais contidos nele, além de um horário pré estabelecido pelo usuário.

Da mesma forma que criamos/editamos/excluimos dietas, podemos interagir com refeições, porém agora torna-se necessário adicionar um horário para cada novo card que o usuário deseja registrar.

Ao interagir com uma refeição, o cliente será redirecionado para a tela que contém os alimentos presentes nela.

### Funcionalidades de Alimentos

Cada refeição é constituida por um conjunto de alimentos que poderão ser visualizados na tela de alimentos. Exibi-se o nome do alimento, seus valores nutricionais, calculados a partir do peso registrado, e uma descrição adiciona pelo usuário para facilitar a sua rotina.

Podemos adicionar novos alimentos em uma refeição ao clicarmos no ícone de soma. Em sequência, o usuário poderá pesquisar pelo nome do alimento de interesse, adicionando uma quantidade em gramas e escrever alguma descrição.

Bem como adicionar, o cliente também pode excluir ou editar as informações presentes em cada alimento.

### Consulta aos Dados Perfil

Além da interação com as dietas e seus atribudos, o usuário pode acessar o seu perfil ao clicar no ícone presente na barra de tarefas superior. 

Caso assim o faça, o cliente será redirecionado para uma tela de perfil na qual irá visualizar seus dados registrado, podendo atualiza-los caso assim deseje.

Além de atualizar os dados, essa tela possibilida ao cliente a funcionalidade de sair da sua conta, sendo redirecionado para a tela de Login novamente.

### Vídeo com Funcionalidades

- [Youtube Video]([https://youtu.be/7jYNbKLVgSg])

## Funcionalidades Futuras

Para atualizações futuras do projeto, seria interessante a construção de uma ferramente de disparo de notificação para sinalizar ao usuário caso o horário definido de alguma das suas refeições contidas em uma dieta favoritada chegue. 

Nesse evento, o cliente poderá ser avisado e garantido que não irá esquecer de realizar alguma refeição no horário certo.
