package main.views.dashboard.interfacce;

import java.time.LocalDate;
import java.util.List;

import main.model.habittracker.interfacce.IAbitudine;

public interface ReportAbitudiniView {
	public void visualizzaDiagrammaAnnuale(int anno);
	
	public void visualizzaDatiUltimaSettimana();
	
	public void visualizzaDatiGiorno(LocalDate data, List<IAbitudine> abitudini);
}
