package model.er;

import model.Regular;

public class RegExpression extends Regular {
	
	private String regEx;
	
	public RegExpression(String regEx, String titulo) {
		super();
		this.regEx = regEx;
		setTitulo(titulo);
	}
	
	/* RegEx */
	public String getRegEx() {
		return regEx;
	}

	public void setRegEx(String regEx) {
		this.regEx = regEx;
	}
	
	/* Regular */
	@Override
	public boolean isExpression() {
		return true;
	}
	
	@Override
	public boolean isDumbGrEr(){
		return regEx.equals("");
	}
	
	@Override
	public String toString() {
		return regEx;
	}
	
}
