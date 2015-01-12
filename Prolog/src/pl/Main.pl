:- dynamic(pais/1).
:- dynamic(regiao_lingua/2).
:- dynamic(lingua_danca/2).
:- dynamic(danca_pais/2).
:- dynamic(perg/2).

/*
Parametros:
	CQ = Current Question = Pergunta Atual do sistema
	NQ = Next Question = Proxima pergunta que sera feita ao usuario
	MCQ = Meaning of the Current Question = 'Significado' da pergunta atual do sistema
	MNQ = Meaning of the Next Question = 'Significado' da proxima pergunta que sera feita ao usuario
	MPQ = Meaning of the Previous Question = 'Significado' da pergunta anterior a pergunta atual
*/

%clausulas usadas quando receber uma resposta positiva do usuario
getNextQuestionForYes(CQ, NQ) :-
	perg(CQ, MCQ),
	getNextQuestionForYes(_, MCQ, NQ).

%caso o 'significado' da pergunta atual seja um pais, entao o programa
%acho o pais que o usuario tava pensando
getNextQuestionForYes(_, MCQ, NQ) :-
	pais(MCQ), NQ = 'Done'.

getNextQuestionForYes(_, MCQ, NQ) :-
	\+pais(MCQ),
	processRespForYes(MCQ, NQ1),
	perg(NQ, NQ1).

processRespForYes(MCQ, MNQ) :-
	regiao_lingua(MCQ, MNQ);
	lingua_danca(MCQ, MNQ);
	danca_pais(MCQ, MNQ).

%clausulas usadas quando receber uma resposta negativa do usuario
getNextQuestionForNo(CQ, NQ) :-
	perg(CQ, MCQ),
	processRespForNo(MCQ, NQ).

processRespForNo(MCQ, NQ) :-
	(
	 regiao_lingua(MCQ, _),
	 retractAll(regiao_lingua(MCQ, _)),
	 regiao_lingua(MNQ, _),
	 perg(NQ, MNQ)
	);
	(
	 regiao_lingua(MPQ, MCQ),
	 lingua_danca(MCQ, _),
	 retractAll(regiao_lingua(_, MCQ)),
	 retractAll(lingua_danca(MCQ,_)),
	 regiao_lingua(MPQ, MNQ),
	 perg(NQ, MNQ)
	);
	(
	 lingua_danca(MPQ, MCQ),
	 danca_pais(MCQ, _),
	 retractAll(lingua_danca(_, MCQ)),
	 retractAll(danca_pais(MCQ,_)),
	 lingua_danca(MPQ, MNQ),
	 perg(NQ, MNQ)
	);
	(
	 danca_pais(MPQ, MCQ),
	 retract(danca_pais(MPQ, MCQ)),
	 danca_pais(MPQ, MNQ),
	 perg(NQ, MNQ)
	);
	(NQ = 'N sei'). 
	%caso n ache o 'significado' da pergunta atual em nenhum bd,
	%quer dizer q o sistema n sabe o pais q o usuario esta pensando


retractAll(C) :-
	retract(C), fail.
retractAll(_).

%carrega as perguntas do arquivo de banco de dados.
carregaArq(File) :-
	open(File, read, ID),
	processArq(ID),
	close(ID).

processArq(ID) :-
	read(ID, Termo), processa(ID, Termo), !.
processa(_, end_of_file) :- !.
processa(ID, Termo) :-
	(findall(_, Termo, Z),
	 length(Z, N), N \= 0, processArq(ID));
    (assertz(Termo), processArq(ID)).
