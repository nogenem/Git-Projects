package model.af;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class Table extends AbstractTableModel {
	
private Automaton automaton;
	
	public Table(Automaton automaton) {
		super();
		this.automaton = automaton;
	}
	
	@Override
	public int getColumnCount() {
		if(automaton == null)
			return 0;
		return automaton.getAlphabet().size() + 2;
	}

	@Override
	public int getRowCount() {
		if(automaton == null)
			return 0;
		return automaton.getStates().size() + 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(automaton == null)
			return "";
		if(rowIndex == 0 && (columnIndex == 0 || columnIndex == 1)){
			if(columnIndex == 0)
				return "Final/Inicial";
			else if(columnIndex == 1)
				return "Estados";
			else
				return "";
		}else if(rowIndex == 0)
			return automaton.getAlphabet().get(columnIndex-2);
		else if(columnIndex == 0){
			State tmp = (State) this.getValueAt(rowIndex, 1);
			String s = "";
			if(automaton.getStartingState().equals(tmp))
				s += "->";
			if(automaton.isFinalState(tmp))
				s += "*";
			return s;
		}else if(columnIndex == 1){
			return automaton.getStates().get(rowIndex-1);
		}else{
			State tmp = (State) this.getValueAt(rowIndex, 1);
			char trigger = (char) this.getValueAt(0, columnIndex);
			ArrayList<State> nexts = automaton.getNextStates(tmp, trigger);
			return (nexts.isEmpty()?"--":nexts.toString());
		}
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	@Override
    public Class getColumnClass(int columnIndex) {
        if (columnIndex == 0 || columnIndex == 1) {
            return Integer.class;
        }else {
            return String.class;
        }
    }
}
