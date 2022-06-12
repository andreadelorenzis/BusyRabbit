package main.Views.Dashboard.interfacce;

import java.time.LocalDate;
import java.util.List;

import main.Models.habittracker.interfaces.IAbitudine;

public interface ReportAbitudiniView {
	public void visualizzaDiagrammaAnnuale(int anno);
	
	public void visualizzaDatiUltimaSettimana();
	
	public void visualizzaDatiGiorno(LocalDate data, List<IAbitudine> abitudini);
}
