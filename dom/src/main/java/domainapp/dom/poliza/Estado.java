package domainapp.dom.poliza;

public enum Estado {
	Previgente, 
	VigenteConVencimientoMayor30Dias, 
	VigenteConVencimientoMenor30Dias, 
	VigenteConVencimientoMenor15Dias,
	Vencida,
	Anulada;
}
