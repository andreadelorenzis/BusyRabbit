package main.model.accountmanager.classi;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import main.model.goalmanager.classi.AzioneScomponibile;
import main.model.goalmanager.classi.AzioneSessione;
import main.model.goalmanager.classi.ObiettivoAzione;
import main.model.goalmanager.classi.ObiettivoScomponibile;
import main.model.goalmanager.interfacce.IAzione;
import main.model.goalmanager.interfacce.IAzioneScomponibile;
import main.model.goalmanager.interfacce.IAzioneSessione;
import main.model.goalmanager.interfacce.IGoalManager;
import main.model.goalmanager.interfacce.IObiettivo;
import main.model.goalmanager.interfacce.IObiettivoAzione;
import main.model.goalmanager.interfacce.IObiettivoScomponibile;
import main.model.goalmanager.interfacce.IItem;
import main.model.habittracker.classi.AbitudineScomponibile;
import main.model.habittracker.classi.AbitudineSessione;
import main.model.habittracker.interfacce.IAbitudine;
import main.model.habittracker.interfacce.IAbitudineScomponibile;
import main.model.habittracker.interfacce.IAbitudineSessione;
import main.model.habittracker.interfacce.IHabitTracker;
import main.model.timetracker.interfacce.IAttività;
import main.model.timetracker.interfacce.IProgetto;
import main.model.timetracker.interfacce.ITimeTracker;

/**
 * Classe per scrivere i dati sul database
 */
public class AccountWriter {
	
	//---------------------------- METODI PRIVATI ------------------------------
	/**
	 * Scrive un ObiettivoScomponibile
	 */
	private void scriviObiettivoScomponibile(BufferedWriter writer, IObiettivo o) throws IOException {
		IObiettivoScomponibile os = (IObiettivoScomponibile) o;
		String stringaObiettivo = "";
		
		// se il seguente obiettivo è un sotto-obiettivo
		if(o.getObiettivoPadre() != null) {
			String idPadre = o.getObiettivoPadre().getId();
			stringaObiettivo = "obiettivo-scomponibile" + "," + true + "," + idPadre
			+ "," + os.getNome() + "," + os.getDescrizione() + "," + os.getData().getDayOfMonth() 
			+ "," + os.getData().getMonthValue() + "," + os.getData().getYear() + "," + os.getId() + "\n";
		} else {
			stringaObiettivo = "obiettivo-scomponibile" + "," + false + "," + os.getNome() 
			+ "," + os.getDescrizione() + "," + os.getData().getDayOfMonth() + "," + os.getData().getMonthValue() 
			+ "," + os.getData().getYear() + "," + os.getId() + "\n";
		}
		
		writer.write(stringaObiettivo);
		
		// scrivo i sotto-obiettivi del seguente ObiettivoScomponibile
		scriviObiettivi(writer, os.getSottoObiettivi());
	}
	
	/**
	 * Scrive un ObiettivoAzione
	 */
	private void scriviObiettivoAzione(BufferedWriter writer, IObiettivo o) throws IOException {
		IObiettivoAzione oa = (IObiettivoAzione) o;
		String stringaObiettivo = "";
		
		// se il seguente obiettivo è un sotto-obiettivo
		if(o.getObiettivoPadre() != null) {
			String idPadre =  o.getObiettivoPadre().getId();
			stringaObiettivo = "obiettivo-azione" + "," + true + "," + idPadre
			+ "," + oa.getNome() + "," + oa.getDescrizione() + "," + oa.getData().getDayOfMonth()
			+ "," + oa.getData().getMonthValue() + "," + oa.getData().getYear() 
			+ "," + oa.getValoreTotale() + "," + oa.getUnità() + "," + oa.getId() + "\n";
		} else {
			stringaObiettivo = "obiettivo-azione" + "," + false + "," + oa.getNome() + 
					"," + oa.getDescrizione() + "," + oa.getData().getDayOfMonth()
					+ "," + oa.getData().getMonthValue() + "," + oa.getData().getYear() 
					+ "," + oa.getValoreTotale() + "," + oa.getUnità() + "," + oa.getId() + "\n";
		}
		
		writer.write(stringaObiettivo);
		
		// scrivo le azioni del seguente ObiettivoAzione
		for(IAzione a : oa.getAzioni()) {
			String stringaGiorni = "";
			int i = 0;
			for(DayOfWeek g : a.getGiorniRipetizione()) {
				if(i == a.getGiorniRipetizione().size() - 1) {
					stringaGiorni += g;
				} else {
					stringaGiorni += g + "-";
				}
				i++;
			}
			if(a instanceof AzioneScomponibile) {
				this.scriviAzioneScomponibile(writer, a, stringaGiorni);
			} else if(a instanceof AzioneSessione) {
				this.scriviAzioneSessione(writer, a, stringaGiorni);
			}
		}
	}
	
	/**
	 * Scrive un AzioneScomponibile
	 */
	private void scriviAzioneScomponibile(BufferedWriter writer, IAzione a, String stringaGiorni) throws IOException {
		String stringaAzione = "";
		
		IAzioneScomponibile as = (IAzioneScomponibile) a;
		stringaAzione = "azione-scomponibile" + "," + as.getObiettivo().getId() 
		+ "," + as.getNome() + "," + as.getIncremento() + "," + as.getDataInizio().getDayOfMonth()
		+ "," + as.getDataInizio().getMonthValue() + "," + as.getDataInizio().getYear() 
		+ "," + stringaGiorni + "," + as.getId() + "\n";
		writer.write(stringaAzione);
		
		// scrivi gli item della seguente AzioneScomponibile
		for(IItem it : as.getItems()) {
			IAzioneScomponibile padre = (IAzioneScomponibile) it.getPadre();
			String stringaItem = "item-azione" + "," + padre.getId() 
			+ "," + it.getNome() + "," + it.getId() + "\n";
			writer.write(stringaItem);
		}
	}
	
	/**
	 * Scrive un AzioneSessione
	 */
	private void scriviAzioneSessione(BufferedWriter writer, IAzione a, String stringaGiorni) throws IOException {
		String stringaAzione = "";
		IAzioneSessione as = (IAzioneSessione) a;
		stringaAzione = "azione-sessione" + "," + as.getObiettivo().getId() 
				+ "," + as.getNome() + "," + as.getIncremento() + "," + as.getDataInizio().getDayOfMonth()
				+ "," + as.getDataInizio().getMonthValue() + "," + as.getDataInizio().getYear() 
				+ "," + stringaGiorni + "," + as.getDurata() + "," + as.getId() + "\n";
		writer.write(stringaAzione);
	}
	
	/**
	 * Scrive un AbitudineScomponibile
	 */
	private void scriviAbitudineScomponibile(BufferedWriter writer, IAbitudine h, String stringaGiorni) throws IOException {
		String stringaAbitudine = "";
		String stringaItem = "";
		IAbitudineScomponibile s = (IAbitudineScomponibile) h;
		stringaAbitudine = "abitudine-semplice" + "," + s.getName() + "," + s.getDescription()
		+ "," + s.getStartDate().getDayOfMonth() + "," + s.getStartDate().getMonthValue()
		+ "," + s.getStartDate().getYear() + "," + stringaGiorni + "," + s.getId() + "\n";
		writer.write(stringaAbitudine);
		
		// scrive gli item della seguente abitudine semplice
		for(IItem it : s.getItems()) {
			IAbitudineScomponibile padre = (IAbitudineScomponibile) it.getPadre();
			stringaItem = "item-abitudine" + "," + padre.getId() 
					+ "," + it.getNome() + "," + it.getId() + "\n";
			writer.write(stringaItem);
		}
	}
	
	/**
	 * Scrive un AbitudineSessione
	 */
	private void scriviAbitudineSessione(BufferedWriter writer, IAbitudine h, String stringaGiorni) throws IOException {
		String stringaAbitudine = "";
		IAbitudineSessione s = (IAbitudineSessione) h;
		stringaAbitudine = "abitudine-sessione" + "," + s.getName() + "," + s.getDescription()
		+ "," + s.getStartDate().getDayOfMonth() + "," + s.getStartDate().getMonthValue()
		+ "," + s.getStartDate().getYear() + "," + stringaGiorni 
		+ "," + s.getDuration() + "," + s.getId() + "\n"; 
		writer.write(stringaAbitudine);
	}
	
	//--------------------------- METODI PUBBLICI ------------------------------
	/**
	 * Scrive tutti i progetti sul file di testo
	 */
    public void scriviProgetti(BufferedWriter writer, ITimeTracker tt) {
		try {
			int i = 0;
			for(IProgetto p : tt.getProgetti()) {
				// se p non è il progetto di default
				if(i > 0) {
					String progetto = p.getNome() + "," + p.getColore() + "," + p.getId() + "\n";
					writer.write(progetto);
				}
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
	 * Scrive tutte le attività sul file di testo
	 */
    public void scriviAttività(BufferedWriter writer, ITimeTracker tt) {
		try {
			for(IAttività a : tt.getAttività()) {
				String idProgetto = "null";
				if(!(a.getProgetto().getId().equals(tt.getProgetti().get(0).getId()))) {
					idProgetto = a.getProgetto().getId();
				} 
				String Attività = a.getNome() + "," + a.getData().getDayOfMonth() + "," + a.getData().getMonthValue()
								  + "," + a.getData().getYear() + "," + a.getOraInizio().getHour() 
								  + "," + a.getOraInizio().getMinute() + "," + a.getDurata() + "," + idProgetto + "," 
								  + a.getId() + "\n";
				writer.write(Attività);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**
	 * Scrive tutti gli obiettivi sul file di testo
	 */
	public void scriviObiettivi(BufferedWriter writer, List<IObiettivo> obiettivi) {
		try {
			if(!obiettivi.isEmpty()) {
				for(IObiettivo o : obiettivi) {
					if(o instanceof ObiettivoScomponibile) {
						this.scriviObiettivoScomponibile(writer, o);
					} else if (o instanceof ObiettivoAzione) {
						this.scriviObiettivoAzione(writer, o);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Scrive tutte le abitudini sul file di testo
	 */
	public void scriviAbitudini(BufferedWriter writer, IHabitTracker ht) {
		try {
			for(IAbitudine h : ht.getHabits()) {
				String stringaGiorni = "";
				int i = 0;
				for(DayOfWeek g : h.getDays()) {
					if(i == h.getDays().size() - 1) {
						stringaGiorni += g;
					} else {
						stringaGiorni += g + "-";
					}
					i++;
				}
				if(h instanceof AbitudineScomponibile) {
					this.scriviAbitudineScomponibile(writer, h, stringaGiorni);
				} else if (h instanceof AbitudineSessione) {
					this.scriviAbitudineSessione(writer, h, stringaGiorni);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Scrive tutte le informazioni di completamento storiche delle abitudini sul file di testo
	 */
	public void scriviStoricoAbitudini(BufferedWriter writer, IHabitTracker ht) {
		try {
			for(IAbitudine h : ht.getHabits()) {
				String stringaAbitudine = "";
				String stringaAnno = "";
				stringaAbitudine += h.getId() + ",";
				Map<Integer, List<Integer>> yearRecords = h.getYearData();
				int i = 0;
				for(int anno : yearRecords.keySet()) {
					stringaAnno += anno + "-";
					List<Integer> giorni = yearRecords.get(anno);
					int j = 0;
					for(int giorno : giorni) {
						if(j != giorni.size() - 1) {
							stringaAnno += giorno + "-";
						} else {
							stringaAnno += giorno;
						}
						j++;
					}
					if(i != yearRecords.keySet().size() - 1) {
						stringaAnno += ",";
					}
					i++;
				}
				stringaAbitudine += stringaAnno + "\n";
				writer.write(stringaAbitudine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}
