package main.Models.habittracker.classes;

import java.util.UUID;

import main.Models.habittracker.interfaces.*;

public class OggettoImpl implements Item {
	
	private String name;
	private boolean isCompleted = false;
	private String id;
	public ISimpleHabit habit;
	
	/**
	 * 
	 * @param name
	 */
	public OggettoImpl(String name) {
		this.name = name;
		this.id = UUID.randomUUID().toString();
	}
	
	public OggettoImpl(String name, String id) {
		this(name);
		this.id = id;
	}

	@Override
	public void complete() {
		isCompleted = !isCompleted;
	}

	@Override
	public boolean isCompleted() {
		return isCompleted;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setName(String nome) {
		this.name = name;
	}

	@Override
	public ISimpleHabit getHabit() {
		return habit;
	}

	@Override
	public void setHabit(ISimpleHabit habit) {
		this.habit = habit;
	}
	
	@Override
    public boolean equals(Object obj) {
  	      if (obj == this) {
  	         return true;
  	      }
  	      if (!(obj instanceof Item)) {
  	         return false;
  	      }
  	      Item o = (Item) obj;
  	      return this.id.equals(o.getId());
   }
}
