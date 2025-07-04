% ==== DECLARACIONES DINÁMICAS ====
:- dynamic tengo/2.
:- dynamic ingrediente/3.
:- dynamic objeto_basico/1.
:- dynamic objeto_compuesto/1.

% ==== OBJETOS BÁSICOS ====
objeto_basico(madera).
objeto_basico(hierro).
objeto_basico(carbon).
objeto_basico(baston).

% ==== OBJETOS COMPUESTOS ====
objeto_compuesto(antorcha).
objeto_compuesto(pico).
objeto_compuesto(espada).

% ==== INVENTARIO ====
tengo(hierro, 6).
tengo(madera, 5).

% ==== RECETARIO ====
% ingrediente(Ingrediente, Producto, CantidadNecesaria)
ingrediente(baston, antorcha, 2).
ingrediente(hierro, pico, 3).
ingrediente(madera, pico, 2).
ingrediente(hierro, espada, 3).
ingrediente(baston, espada, 1).

% ==== REGLAS ====

% puedo_craftear(ObjetoCompuesto)
%   true si existe al menos un ingrediente para Objeto y lo tienes en cantidad suficiente

puedo_craftear(Objeto) :-

    % 1 - reúno todos los pares (Ingred, CantReq) donde el SEGUNDO arg. = Objeto
    findall((Ing, CantReq), ingrediente(Ing,Objeto,CantReq), Ingredientes),
    
    % 2- tiene que haber por lo menos un ingrediente
    Ingredientes \= [],
    
    % y tienen que estar todos
    tengo_ingredientes(ListaIngredientes).

% tengo_ingredientes(ListaIngrediente)

tengo_ingredientes([]).
tengo_ingredientes([(Ing,CantReq)|Resto]) :-
    tengo(Ing,CantTengo),
    CantTengo >= CantReq,
    tengo_ingredientes(Resto).

% objetos_crafteables(-Lista)
objetos_crafteables(Lista) :-
    findall( O, (objeto_compuesto(O), puedo_craftear(O)), Lista).
