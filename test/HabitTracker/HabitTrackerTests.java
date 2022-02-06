package HabitTracker;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import main.Giorno;
import main.Models.habittracker.classes.HabitTracker;
import main.Models.goalmanager.classes.ItemImpl;
import main.Models.habittracker.classes.SessionHabit;
import main.Models.habittracker.classes.SimpleHabit;
import main.Models.habittracker.interfaces.IHabit;
import main.Models.habittracker.interfaces.IHabitTracker;
import main.Models.habittracker.interfaces.ISessionHabit;
import main.Models.habittracker.interfaces.ISimpleHabit;
import main.Models.goalmanager.interfaces.Item;

/**
 * TODO:
 * - testare il salvataggio delle abitudini completare per la dashboard
 *
 */
class HabitTrackerTests {
	
	private void initialize(IHabitTracker t) {
		// add a SimpleHabit
		t.addHabit(new SimpleHabit("Morning routine",
								   "Doing my morning routine",
								   	LocalDate.now(),
								   	new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY))));
		// add a SessionHabit
		t.addHabit(new SessionHabit("Studying",
									"Studying for 2 hours",
									LocalDate.now(),
									new ArrayList<>(List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
															DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY, 
															DayOfWeek.THURSDAY)),
									7200));
	}
	
	/**
	 * Test the info of all the completed habits in a year.
	 */
	@Test
	public void testHabitsRecording() {
		IHabitTracker t = new HabitTracker();
		initialize(t);
		IHabit h1 = t.getHabits().get(0);
		IHabit h2 = t.getHabits().get(1);
		int year = LocalDate.now().getYear();
		int day = LocalDate.now().getDayOfYear();
		
		// complete the first habit
		h1.complete();
		
		// there's one habit completed this year
		Map<Integer, List<IHabit>> yearRecords = t.getYearRecords(year);
		assertEquals("Morning routine", yearRecords.get(day).get(0).getName());
		
		// there's one habit completed this week
		Map<Integer, List<IHabit>> weekRecords = t.getWeekRecords();
		assertEquals("Morning routine", weekRecords.get(day).get(0).getName());
		
		// complete the second habit
		h2.complete();
		
		// there are two habits completed this year
		yearRecords = t.getYearRecords(year);
		assertEquals("Morning routine", yearRecords.get(day).get(0).getName());
		assertEquals("Studying", yearRecords.get(day).get(1).getName());
		
		// there are two habits completed this week
		weekRecords = t.getWeekRecords();
		assertEquals("Morning routine", weekRecords.get(day).get(0).getName());
		assertEquals("Studying", weekRecords.get(day).get(1).getName());
		
	}

	/**
	 * Test if that the habits where added correctly
	 */
	@Test
	public void testHabitPresence() {
		IHabitTracker t = new HabitTracker();
		initialize(t);
		
		// there are two habits
		assertEquals(2, t.getHabits().size());
		
		// first is "Morning Routine", a SimpleHabit
		assertEquals("Morning routine", t.getHabits().get(0).getName());
		assertTrue(t.getHabits().get(0) instanceof SimpleHabit);
		
		// second is "Studying", a SessionHabit
		assertEquals("Studying", t.getHabits().get(1).getName());
		assertTrue(t.getHabits().get(1) instanceof SessionHabit);
	}
	
	/**
	 * Test SimpleHabit
	 */
	@Test
	public void testSimpleHabit() {
		IHabitTracker t = new HabitTracker();
		initialize(t);
		ISimpleHabit simpleHabit = (ISimpleHabit) t.getHabits().get(0);
		
		// add 2 items to first habit
		simpleHabit.addItem(new ItemImpl("Run for a mile"));
		Item item2 = new ItemImpl("Make a cold shower");
		simpleHabit.addItem(item2);
		
		// there are 2 items 
		assertEquals(2, simpleHabit.getItems().size());
		
		// complete first item
		simpleHabit.getItems().get(0).completa();
		
		// first item is completed, second not completed
		assertTrue(simpleHabit.getItems().get(0).getCompletato());
		assertFalse(simpleHabit.getItems().get(1).getCompletato());
		
		// delete second item
		simpleHabit.removeItem(item2.getId());
		
		// there is only one item, "Run for a mile"
		assertEquals(1, simpleHabit.getItems().size());
		assertEquals("Run for a mile", simpleHabit.getItems().get(0).getNome());
		
	}
	
	/**
	 * Test if the habits show up on the correct day
	 */
	@Test
	public void testTodayHabits() {
		IHabitTracker t = new HabitTracker();
		initialize(t);
		
		// add another habit
		t.addHabit(new SimpleHabit("Another habit",
								   "",
								   LocalDate.now(),
								   new ArrayList<>(List.of(DayOfWeek.SUNDAY))));
		
		// get habits for today, Friday 14 January 2022
		List<IHabit> habits = t.calculateTodayHabits(LocalDate.of(2022, Month.JANUARY, 14));
		
		// there are 2 habits in the list, "Morning routine" and "Studying"
		assertEquals(2, habits.size());
		assertEquals("Morning routine", habits.get(0).getName());
		assertEquals("Studying", habits.get(1).getName());
	}
	
	/**
	 * Test if the habits update correctly when completed
	 */
	@Test
	public void testCompletion() {
		IHabitTracker t = new HabitTracker();
		initialize(t);
		
		// get habits for today, Friday 14 January 2022
		List<IHabit> habits = t.calculateTodayHabits(LocalDate.of(2022, Month.JANUARY, 14));
		
		// complete first habit
		habits.get(0).complete();
		
		// first habit is completed
		assertTrue(habits.get(0).isCompleted());
		// current count is 1
		assertEquals(1, habits.get(0).getCount());
		// current record is 1
		assertEquals(1, habits.get(0).getRecord());
	}
	
	/**
	 * Test if the habits' count gets reseted when the habit is not completed in the days before
	 */
	@Test
	public void testReset() {
		IHabitTracker t = new HabitTracker();
		initialize(t);
		
		LocalDate date1 = LocalDate.of(2022, Month.JANUARY, 10); // Last access, Monday
		LocalDate date2 = LocalDate.of(2022, Month.JANUARY, 15); // Today's access, Saturday
		
		// set first habit's count to 5
		t.getHabits().get(0).setCount(5);
		// set second habit's count and record to 10
		t.getHabits().get(1).setCount(10);
		t.getHabits().get(1).setRecord(10);
		
		// first habit was completed last time on 14 January
		t.getHabits().get(0).setDateOfLastCompletion(LocalDate.of(2022, Month.JANUARY, 14));
		
		// second habit was completed last time on 12 January
		t.getHabits().get(1).setDateOfLastCompletion(LocalDate.of(2022, Month.JANUARY, 12));
		
		// calculate the resets
		t.resetHabits(date1, date2);
		
		// first habit's count is 5
		assertEquals(5, t.getHabits().get(0).getCount());
		
		// second habit's count is 0
		assertEquals(0, t.getHabits().get(1).getCount());
		// second habit's record is 10
		assertEquals(10, t.getHabits().get(1).getRecord());
	}
	
//	@Test
//	public void testSessionHabit() {
//		IHabitTracker t = new HabitTracker();
//		initialize(t);
//		ISessionHabit habit = (ISessionHabit) t.getHabits().get(1);
//		
//		// set SessionHabit duration to 2 seconds
//		habit.setDuration(2);
//		
//		// start the session
//		habit.startSession();
//		
//		// wait for 3 seconds
//		try {
//			Thread.sleep(3100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} 
//		
//		// SessionHabit is completed
//		assertTrue(habit.isCompleted());
//		// count and record are equal to 1
//		assertEquals(1, habit.getCount());
//		assertEquals(1, habit.getRecord());
//	}

}
