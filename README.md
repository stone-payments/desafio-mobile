
# Stonesafio

App para a loja fictícia Stonesafio. Solução desenvolvida por Allan Yoshio Hasegawa para o
Desafio Stone (README original abaixo).

Demo time:

[![Demo](https://img.youtube.com/vi/zVDrvTovriw/0.jpg)](https://www.youtube.com/watch?v=zVDrvTovriw)

## Code Walkthrough


O código está organizado usando a Clean Architecture. A implementação foi fortemente baseada
nos artigos e projetos do Fernando Cejas:

 * Artigo: https://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/
 * Projeto: https://github.com/android10/Android-CleanArchitecture
 
Esse projeto esta dividido em três *layers* e quatro módulos:

 * Outer layer: contém os módulos `app` e `data`
 * Mid layer: contém o módulo `presentation`
 * Inner layer: contém o módulo `domain`


As mesmas regras da *Clean Architecture* são aplicadas para essas *layers*.
As dependências seguem apenas uma direção, da *outer layer* para a *inner layer*.

### Módulos

Uma breve descrição:

 * `domain`: Regras de negócios e as interfaces de como interagir com serviços e repositórios.
 * `presentation`: Lógica que conecta as regras de negócios com a UI. Manipulação dos dados para
 visualização.
 * `data`: Implementações dos serviços e repositórios.
 * `app`: Implementações das "Views" do MVI e dos `Navigator`s. Implementação específica para a
 plataforma Android que não se encaixam no módulo `data` (por exemplo, *logging*).

#### domain

O módulo `domain` está organizado por *features*. Uma *feature* pode conter:

 * *Interfaces* para serviços e repositórios
 * *Models*, que são *entities* usadas para transferência de dados; sem lógica
 * *UseCases* que fazem a implementação das regras de negócio

O módulo `domain` não depende de nenhum outro módulo.

#### presentation

Depende apenas do módulo `domain`. É organizado por telas, ou *screens*. As telas usam o padrão
de arquitetura MVI (*Model-View-Intents*). A implementação foi baseada na série fantástica
de Hannes Dorfmann: http://hannesdorfmann.com/android/mosby3-mvi-1

Cada tela tem um contrato, ou *contract*, com os elementos:

 * `View`: *interface* que define os *intents* da `View`, como *clicks* em botões ou *scrolls* da
 tela. A `View` é passiva e não contém lógica.
 * `ViewState`: Descreve os possíveis estados em que uma `View` pode se encontrar.
 * `StateEvent`: Eventos que podem alterar o `ViewState`.
 * `Navigator`: Opções de navegação da tela.

Além do contrato, cada tela implementa o `Presenter`. O `Presenter` é quem orchestra os *intents* da
`View` com os *UseCases* do módulo `domain` para construir o `ViewState`. O `ViewState` é construído
usando `StateEvent`s e o padrão do [state reducer](http://redux.js.org/docs/basics/Reducers.html).

Como a lógica do Presenter pode ficar muito grande, ele delega parte de sua lógica para *UseCases*.
Diferentemente dos *UseCases* do `domain`, esses *UseCases* retornam apenas `StateEvent`'s.
Assim, esses *UseCases* acabam servindo também como *data mappers* encontrado em algumas
implementações de Clean Architecture.

#### data

O módulo `data` faz a implementação dos serviços e repositórios definidos no módulo `domain`.
A organização é feita por *feature*, assim como no módulo `domain`.


#### app

O módulo `app` está organizdo por telas e *features*. Sua principal função é a implementação da
`View` do MVI. Ou seja, é nela onde a maior parte da interação do app com a Android Framework
acontece. Assim, essa *layer* lida com a `Activity`, `RecyclerView`, etc, coisas que as outras
*layers* nem sabem que existem.

Cada tela tem um `Controller` e um `Navigator`. O navigator implementa a lógica para a navegação
entre as telas. O `Controller` é algo parecido com um `Fragment`, porém usando a biblioteca
[Conductor](https://github.com/bluelinelabs/Conductor). O `Controller` faz a implementação da
`View` do MVI.

Além disso, o `Controller` faz a criação do `Presenter`. *Dependency Injection* é usado para
facilitar a gerência das dependências.

## Decisões

### Por que MVI?

Uma alternativa famosa é a MVP. O problema da MVP é que é difícil gerenciar o estado da `View`.
Essa problema é ainda mais grave em uma arquitetura completamente reativa, onde eventos podem
ser emitidos a qualquer momento e em ordens inesperadas. Com isso, a `View` pode entrar em um
estado inválido e difícil de ser replicado.

Esse problema é mais fácil de resolver com a MVI, onde o estado da `View` é descrito e bem
definido. As ações que mudam o estado da `View` podem ser salvas (e até mesmo enviadas junto com
o crash report) e repetidas para replicar uma falha.

Além disso, a MVI facilita a recuperação do estado da `View` quando se tem uma
"configuration change".


### Por que Conductor?

Esse blog post resume bem:
https://academy.realm.io/posts/michael-yotive-state-of-fragments-2017/?

Conductor é uma alternativa para a bagunça que são os Fragments.
Lançado em uma época onde todo mundo odiava os Fragments, o Conductor chegou como um salvador.
Hoje eu gosto de usar ele pela sua simplicidade, e até agora não senti falta dos Fragments.

Porém, hoje em dia Fragments não são tão ruins, só tem que usar com cuidado.


### Por que Mosby?

Aproveitei esse projeto para testar a MVI. Eu já conhecia a teoria por meio dos blog posts do 
Hannes Dorfmann, logo, optei por usar a biblioteca dela, a Mosby.

### Por que organizar o código por features?

Acredito que essa organização é a primeira coisa que a gente ve em um projeto.
A organização por *features* é simples de entender, e até uma pessoa que não conhece o projeto
consegue navegar nele.

### Por que separação via módulos (domain, presentation, app, data)?

Essa é a separação proposta por Fernando Cejas em seu projeto demonstrando Clean Architecture: 
https://github.com/android10/Android-CleanArchitecture

Essa separação permite melhor organização das dependências.
Por exemplo, o módulo `domain` é um modulo Kotlin normal, sem dependência com a Android Framework.
Além disso, ele só importa as dependências necessárias.
Isso evita que a parte do `domain` acabe usando dependências erradas
(como algo da Android Framework, ou algum objeto do `presentation`).


### Por que um model para cada layer?

ou, "Não seria mais fácil reutilizar um único *model* em todas as layers?"

O problema é quando a `View` acaba dependendo de um *model* do `domain`. Esse *model* pode acabar
sendo usado em diversas partes da UI. Caso precise ser feito algum refatoramento nesse *model*,
todas as layers acabam sendo impactadas, principalmente a de UI. Com uma separação, a mudança é
feita em apenas um lugar.


## Problemas

### Pagamento é feito mas transação não é salva

 > "Allan, confirmei meu pagamento, o app mandou POST pra backend, porém, antes de salvar a transação, eu cliquei para voltar e a transação não foi salva. E agora?"

O problema é que o lifecycle da stream que faz essa operação está associada ao lifecycle da tela
de pagamento. Ou seja, quando a tela de pagamento é fechada (o user clicka back button),
a stream é unsubscribed.

Uma solução seria apenas aumentar o lifecycle da stream, deixando ela sobreviver a destruição
da tela. Outra solução seria criar uma stream "singleton" com o mesmo lifecycle do app.

### Você não gosta de testes?

O `ListingPresenter` teve a sorte de ganhar testes. Os outros não receberam por falta de tempo.
Como esse foi um projeto pequeno em escopo, e não pretendo refatorar ele, não me preocupei muito.

Mas testes são importantes o7

---
 
*ReadMe Original abaixo.*


![StoneSDK](https://cloud.githubusercontent.com/assets/2567823/11539067/6300c838-990c-11e5-9831-4f8ce691859e.png)

# Desafio Mobile

O desafio consiste em criar uma loja de itens de Star Wars que o usuário é capaz de adicionar os itens desejados em um carrinho de compras e finalizar a compra com uma simulação de transação e-commerce.

O candidato deve dar **fork** neste repositório e após o termino do desenvolvimento, realizar um **pull request** para análise do time.

Para obter os itens da loja, sua aplicação deverá realizar uma chamada `GET` na URL https://raw.githubusercontent.com/stone-pagamentos/desafio-mobile/master/products.json

A lista de itens deve exibir as seguintes informações:
+ Nome [title]
+ Preço [price]
+ Vendedor [seller]
+ Foto do item [thumbnailHd]

Após o usuário adicionar todos os itens no carrinho, ele deverá finalizar a compra.
Para finalizar a compra, aconselhamos que use o [Apiary](https://apiary.io) como API nessa etapa.

###### Simulação E-commerce

Sua aplicação deve realizar um `POST` com os seguintes atributos:
+ Número do cartão (máximo de 16 números - XXXX XXXX XXXX XXXX)
+ Nome do portador do cartão
+ Vencimento do cartão (MM/yy)
+ CVV (código encontrado na parte traseira do cartão)
+ Valor da transação (total dos itens no carrinho)

``` json
{  
   "card_number":"1234123412341234",
   "value":7990,
   "cvv":789,
   "card_holder_name":"Luke Skywalker",
   "exp_date":"12/24"
}
```

###### Banco de dados
Todas as transações realizadas devem ser salvas em um banco interno com os seguintes campos:


+ Valor
+ Data e hora
+ Últimos 4 dígitos do cartão
+ Nome do portador do cartão

###### Lista de Transações
A aplicação deverá conter uma tela para exibir as transações que foram salvas em seu banco de dados.
 
---
#### LICENSE
```
MIT License

Copyright (c) 2016 Stone Pagamentos

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
