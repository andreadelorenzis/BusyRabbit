package main.Models.accountmanager.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.Colori;
import main.Giorno;
import main.Models.accountmanager.interfaces.IApp;
import main.Models.goalmanager.classes.AzioneScomponibile;
import main.Models.goalmanager.classes.AzioneSessione;
import main.Models.goalmanager.classes.GoalManager;
import main.Models.goalmanager.classes.ItemImpl;
import main.Models.goalmanager.classes.ObiettivoAzione;
import main.Models.goalmanager.classes.ObiettivoScomponibile;
import main.Models.goalmanager.interfaces.IAzione;
import main.Models.goalmanager.interfaces.IAzioneScomponibile;
import main.Models.goalmanager.interfaces.IAzioneSessione;
import main.Models.goalmanager.interfaces.IGoalManager;
import main.Models.goalmanager.interfaces.IObiettivo;
import main.Models.goalmanager.interfaces.IObiettivoAzione;
import main.Models.goalmanager.interfaces.IObiettivoScomponibile;
import main.Models.goalmanager.interfaces.Item;
import main.Models.habittracker.classes.HabitTracker;
import main.Models.habittracker.classes.SessionHabit;
import main.Models.habittracker.classes.SimpleHabit;
import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitTracker;
import main.Models.habittracker.interfaces.ISessionHabit;
import main.Models.habittracker.interfaces.ISimpleHabit;
import main.Models.timetracker.classes.Attività;
import main.Models.timetracker.classes.Progetto;
import main.Models.timetracker.classes.TimeTracker;
import main.Models.timetracker.interfaces.IAttività;
import main.Models.timetracker.interfaces.IProgetto;
import main.Models.timetracker.interfaces.ITimeTracker;

public class AppWriter {
	private ITimeTracker tt;
	private IGoalManager gm;
	private IHabitTracker ht;
	
	public AppWriter(ITimeTracker tt, IGoalManager gm, IHabitTracker ht) {
		this.tt = tt;
		this.gm = gm;
		this.ht = ht;
	}
    
    public void scriviProgetti(BufferedWriter writer) {
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
    
    public void scriviAttività(BufferedWriter writer) {
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
	
	public void scriviObiettivi(BufferedWriter writer, List<IObiettivo> obiettivi) {
		try {
			if(!obiettivi.isEmpty()) {
				for(IObiettivo o : obiettivi) {
					String stringaObiettivo = "";
					if(o instanceof ObiettivoScomponibile) {
						IObiettivoScomponibile os = (IObiettivoScomponibile) o;
						
						// se il seguente obiettivo ï¿½ un sotto-obiettivo
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
						
					} else if (o instanceof ObiettivoAzione) {
						IObiettivoAzione oa = (IObiettivoAzione) o;
						
						// se il seguente obiettivo ï¿½ un sotto-obiettivo
						if(o.getObiettivoPadre() != null) {
							String idPadre =  o.getObiettivoPadre().getId();
							stringaObiettivo = "obiettivo-azione" + "," + true + "," + idPadre
							+ "," + oa.getNome() + "," + oa.getDescrizione() + "," + oa.getData().getDayOfMonth()
							+ "," + oa.getData().getMonthValue() + "," + oa.getData().getYear() 
							+ "," + oa.getValoreTotale() + "," + oa.getUnita() + "," + oa.getId() + "\n";
						} else {
							stringaObiettivo = "obiettivo-azione" + "," + false + "," + oa.getNome() + 
									"," + oa.getDescrizione() + "," + oa.getData().getDayOfMonth()
									+ "," + oa.getData().getMonthValue() + "," + oa.getData().getYear() 
									+ "," + oa.getValoreTotale() + "," + oa.getUnita() + "," + oa.getId() + "\n";
						}
						
						writer.write(stringaObiettivo);
						
						// scrivo le azioni del seguente ObiettivoAzione
						for(IAzione a : oa.getAzioni()) {
							String stringaAzione = "";
							String stringaGiorni = "";
							int i = 0;
							for(Giorno g : a.getGiorniRipetizione()) {
								stringaGiorni += g + "-";
								if(i == a.getGiorniRipetizione().size() - 1) {
									stringaGiorni += g;
								}
								i++;
							}
							if(a instanceof AzioneScomponibile) {
								IAzioneScomponibile as = (IAzioneScomponibile) a;
								stringaAzione = "azione-scomponibile" + "," + as.getObiettivo().getId() 
								+ "," + as.getNome() + "," + as.getIncremento() + "," + as.getDataInizio().getDayOfMonth()
								+ "," + as.getDataInizio().getMonthValue() + "," + as.getDataInizio().getYear() 
								+ "," + stringaGiorni + "," + as.getId() + "\n";
								writer.write(stringaAzione);
								
								// scrivi gli item della seguente AzioneScomponibile
								for(Item it : as.getItems()) {
									IAzioneScomponibile padre = (IAzioneScomponibile) it.getPadre();
									String stringaItem = "item-azione" + "," + padre.getId() 
									+ "," + it.getNome() + "," + it.getId() + "\n";
									writer.write(stringaItem);
								}
							} else if(a instanceof AzioneSessione) {
								IAzioneSessione as = (IAzioneSessione) a;
								stringaAzione = "azione-sessione" + "," + as.getObiettivo().getId() 
										+ "," + as.getNome() + "," + as.getIncremento() + "," + as.getDataInizio().getDayOfMonth()
										+ "," + as.getDataInizio().getMonthValue() + "," + as.getDataInizio().getYear() 
										+ "," + stringaGiorni + "," + as.getDurata() + "," + as.getId() + "\n";
								writer.write(stringaAzione);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void scriviAbitudini(BufferedWriter writer) {
		try {
			for(IHabit h : ht.getHabits()) {
				String stringaAbitudine = "";
				String stringaGiorni = "";
				int i = 0;
				for(DayOfWeek g : h.getDays()) {
					stringaGiorni += g + "-";
					if(i == h.getDays().size() - 1) {
						stringaGiorni += g;
					}
					i++;
				}
				if(h instanceof SimpleHabit) {
					ISimpleHabit s = (ISimpleHabit) h;
					stringaAbitudine = "abitudine-semplice" + "," + s.getName() + "," + s.getDescription()
					+ "," + s.getStartDate().getDayOfMonth() + "," + s.getStartDate().getMonthValue()
					+ "," + s.getStartDate().getYear() + "," + stringaGiorni + "," + s.getId() + "\n"; 
					
					// scrive gli item della seguente abitudine semplice
					for(Item it : s.getItems()) {
						ISimpleHabit padre = (ISimpleHabit) it.getPadre();
						String stringaItem = "item-abitudine" + "," + padre.getId() 
								+ "," + it.getNome() + "," + it.getId() + "\n";
						writer.write(stringaItem);
					}
				} else if (h instanceof SessionHabit) {
					ISessionHabit s = (ISessionHabit) h;
					stringaAbitudine = "abitudine-sessione" + "," + s.getName() + "," + s.getDescription()
					+ "," + s.getStartDate().getDayOfMonth() + "," + s.getStartDate().getMonthValue()
					+ "," + s.getStartDate().getYear() + "," + stringaGiorni 
					+ "," + s.getDuration() + "," + s.getId() + "\n"; 
				}
				writer.write(stringaAbitudine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void scriviStoricoAbitudini(BufferedWriter writer) {
		try {
			for(IHabit h : ht.getHabits()) {
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
