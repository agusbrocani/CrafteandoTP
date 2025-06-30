% RECETARIO DEL JUGADOR
% ingrediente(Objeto, Ing, CantReq)
ingrediente(madera, bastón, 2).
ingrediente(hierro, espada, 3).
ingrediente(bastón, espada, 1).

% OBJETOS BASICOS
objeto_basico(madera).
objeto_basico(hierro).

% OBJETOS COMPUESTOS (CRAFTEABLES)
objeto_compuesto(antorcha).
objeto_compuesto(pico).

% INVENTARIO DEL JUGADOR
tengo(hierro, 6).
tengo(madera, 5).
tengo(carbon, 2).


% Para ver si puedo craftear un Objeto tengo que tener todos los ingredientes en cantidad suficiente
puedo_craftear(Objeto):-
    findall((Ing, CantReq), ingrediente(Objeto, Ing, CantReq), Ingredientes),
    tengo_ingredientes(Ingredientes).

% para chequear si tengo los ingredientes
tengo_ingredientes([]).
tengo_ingredientes([(Ing, CantReq)|T]):-
    tengo(Ing, CantTengo),
    CantTengo >= CantReq,
    tengo_ingredientes(T).

% Crafteables = que se pueden fabricar con el inventario actual
% = lista de todos los productos crafteables
objetos_crafteables(ListaObjetosCrafteables):-
    findall(O, (objeto_compuesto(O), puedo_craftear(O)), ListaObjetosCrafteables).
