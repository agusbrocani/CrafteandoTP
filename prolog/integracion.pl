% === Declaraciones dinámicas para uso desde Java ===
:- dynamic tengo/2.
:- dynamic ingrediente/3.
:- dynamic objeto_basico/1.
:- dynamic objeto_compuesto/1.

% === OBJETOS BASICOS ===
objeto_basico(madera).
objeto_basico(hierro).
objeto_basico(carbon).
objeto_basico(baston).

% === OBJETOS COMPUESTOS (CRAFTEABLES) ===
objeto_compuesto(antorcha).
objeto_compuesto(pico).
objeto_compuesto(espada).

% === INVENTARIO DEL JUGADOR (se sobrescribe desde Java) ===
tengo(hierro, 6).
tengo(madera, 5).
tengo(carbon, 2).
tengo(baston, 1).

% === RECETARIO DEL JUGADOR ===
% ingrediente(ObjetoCrafteable, Ingrediente, CantidadRequerida)
ingrediente(antorcha, baston, 2).
ingrediente(pico, hierro, 3).
ingrediente(pico, madera, 2).
ingrediente(espada, hierro, 3).
ingrediente(espada, baston, 1).

% === Lógica para saber si un objeto es crafteable con inventario actual ===

% puedo_craftear(Objeto)
puedo_craftear(Objeto) :-
    findall((Ing, CantReq), ingrediente(Objeto, Ing, CantReq), Ingredientes),
    tengo_ingredientes(Ingredientes).

% tengo_ingredientes(ListaDeIngredientes)
tengo_ingredientes([]).
tengo_ingredientes([(Ing, CantReq) | Resto]) :-
    tengo(Ing, CantTengo),
    CantTengo >= CantReq,
    tengo_ingredientes(Resto).

% objetos_crafteables(Lista)
objetos_crafteables(ListaObjetosCrafteables) :-
    findall(O, (objeto_compuesto(O), puedo_craftear(O)), ListaObjetosCrafteables).