package com.mkyong.client;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fighter {

	@SerializedName("fighterClass")
	@Expose
	private String fighterClass;
	@SerializedName("orderNumberInTeam")
	@Expose
	private Integer orderNumberInTeam;
	@SerializedName("isDead")
	@Expose
	private Boolean isDead;
	@SerializedName("maxAvailableMana")
	@Expose
	private Integer maxAvailableMana;
	@SerializedName("maxAvailableLife")
	@Expose
	private Integer maxAvailableLife;
	@SerializedName("currentMana")
	@Expose
	private Integer currentMana;
	@SerializedName("currentLife")
	@Expose
	private Integer currentLife;
	@SerializedName("states")
	@Expose
	private Object states;
	@SerializedName("action")
	@Expose
	private Object action;
	@SerializedName("fighterID")
	@Expose
	private String fighterID;
	@SerializedName("receivedAttacks")
	@Expose
	private Object receivedAttacks;
	@SerializedName("diffMana")
	@Expose
	private Object diffMana;
	@SerializedName("diffLife")
	@Expose
	private Object diffLife;
	
	private int mana = 0;
	
	public int getMana()
	{
		return mana;
	}
	
	public void SetMana(int mana)
	{
		this.mana = mana;
	}
	
	
	public void addMana()
	{
		this.mana = this.mana + 1;
	}
	
	public String getFighterClass() {
	return fighterClass;
	}
	
	public void setFighterClass(String fighterClass) {
	this.fighterClass = fighterClass;
	}
	
	public Integer getOrderNumberInTeam() {
	return orderNumberInTeam;
	}
	
	public void setOrderNumberInTeam(Integer orderNumberInTeam) {
	this.orderNumberInTeam = orderNumberInTeam;
	}
	
	public Boolean getIsDead() {
	return isDead;
	}
	
	public void setIsDead(Boolean isDead) {
	this.isDead = isDead;
	}
	
	public Integer getMaxAvailableMana() {
	return maxAvailableMana;
	}
	
	public void setMaxAvailableMana(Integer maxAvailableMana) {
	this.maxAvailableMana = maxAvailableMana;
	}
	
	public Integer getMaxAvailableLife() {
	return maxAvailableLife;
	}
	
	public void setMaxAvailableLife(Integer maxAvailableLife) {
	this.maxAvailableLife = maxAvailableLife;
	}
	
	public Integer getCurrentMana() {
	return currentMana;
	}
	
	public void setCurrentMana(Integer currentMana) {
	this.currentMana = currentMana;
	}
	
	public Integer getCurrentLife() {
	return currentLife;
	}
	
	public void setCurrentLife(Integer currentLife) {
	this.currentLife = currentLife;
	}
	
	public Object getStates() {
	return states;
	}
	
	public void setStates(Object states) {
	this.states = states;
	}
	
	public Object getAction() {
	return action;
	}
	
	public void setAction(Object action) {
	this.action = action;
	}
	
	public String getFighterID() {
	return fighterID;
	}
	
	public void setFighterID(String fighterID) {
	this.fighterID = fighterID;
	}
	
	public Object getReceivedAttacks() {
	return receivedAttacks;
	}
	
	public void setReceivedAttacks(Object receivedAttacks) {
	this.receivedAttacks = receivedAttacks;
	}
	
	public Object getDiffMana() {
	return diffMana;
	}
	
	public void setDiffMana(Object diffMana) {
	this.diffMana = diffMana;
	}
	
	public Object getDiffLife() {
	return diffLife;
	}
	
	public void setDiffLife(Object diffLife) {
	this.diffLife = diffLife;
	}
}
