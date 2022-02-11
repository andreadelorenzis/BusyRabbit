package main.Views.Dashboard.interfaces;

import java.time.LocalDate;
import java.util.List;

import main.Models.habittracker.interfaces.IHabit;

public interface ReportAbitudiniView {
	public void visualizzaDiagrammaAnnuale(int anno);
	
	public void visualizzaDatiUltimaSettimana();
	
	public void visualizzaDatiGiorno(LocalDate data, List<IHabit> abitudini);
}
