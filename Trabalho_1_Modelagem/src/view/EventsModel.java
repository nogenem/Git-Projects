package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.events.Event;

/**
 * Classe que estende um modelo de tabelas abstrato.
 * 
 * @author Gilney e Eduardo
 *
 */
@SuppressWarnings("serial")
public class EventsModel extends AbstractTableModel {
	
	private static final int COL_ID = 0;
	private static final int COL_TYPE_EVENT = 1;
	private static final int COL_TC = 2;
	
	/**
	 * Linhas da tabela da interface.
	 */
	private List<Event> lines;
	/**
	 * Nomes das colunas da tabela da interface.
	 */
	private String[] colNames;
	
	/**
	 * Construtor da classe EventModel.
	 */
	public EventsModel() {
		this.lines = new ArrayList<>();
		this.colNames = new String[]{"ID", "Tipo de Evento", "TC"};
	}
	
	/**
	 * Sobrescreve o método da super classe que retorna o nome 
	 *  de uma coluna da tabela.
	 *  
	 *  @param columnIndex		Índice da coluna que se quer saber o nome.
	 *  
	 *  @return			Nome da coluna com índice 'columnIndex'.
	 */
	public String getColumnName(int columnIndex) { 
		return colNames[columnIndex]; 
	}
	
	/**
	 * Sobrescreve o método da super classe que retorna a quantidade
	 *  de colunas na tabela.
	 *  
	 *  @return			O numero de colunas da tabela.
	 */
	@Override
	public int getColumnCount() {
		return colNames.length;
	}
	
	/**
	 * Sobrescreve o método da super classe que retorna a classe 
	 *  de uma coluna da tabela.
	 *  
	 *  @param columnIndex		Índice da coluna que se quer saber a classe.
	 *  
	 *  @return		A classe da coluna com índice 'columnIndex'.
	 */
	public Class getColumnClass(int columnIndex) {
		return String.class;
	}
	
	/**
	 * Sobrescreve o método da super classe que retorna a quantidade
	 *  de linhas da tabela.
	 *  
	 * @return			A quantidade de linhas da tabela.
	 */
	@Override
	public int getRowCount() {
		return lines.size();
	}
	
	/**
	 * Sobrescreve o método da super classe que retorna o valor
	 *  contido em uma célula especifica da tabela.
	 *  
	 *  @param rowIndex			Índice da linha da célula que se quer
	 * 							 o valor.
	 *  @param columnIndex		Índice da coluna da célula que se quer
	 * 							 o valor.
	 *  
	 *  @return			O valor contido no índice 'rowIndex' e 'columnIndex' 
	 *  				 da tabela.
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Event evt = lines.get(rowIndex);
		switch(columnIndex){
		case COL_ID:
			return ""+evt.getId();
		case COL_TYPE_EVENT:
			return evt.getEventType();
		case COL_TC:
			return String.format("%.3f", evt.getStartTime());
		}
		return "";
	}
	
	/**
	 * Adiciona um evento na tabela.
	 * 
	 * @param evt		Evento a ser adicionado.
	 */
	public void addEvent(Event evt){
		int index = -1;
		for(int i = 0; i<lines.size(); i++){
			if(evt.getStartTime() < lines.get(i).getStartTime()){
				index = i;
				break;
			}
		}
		if(index == -1){
			lines.add(evt);
			index = lines.size() - 1;//getRowCount()
		}else{
			lines.add(index, evt);
		}
		fireTableRowsInserted(index, index);
	}
	
	/**
	 * Sobrescreve o método da super classe que retorna se uma certa
	 *  célula da tabela é editavel ou não.
	 *  
	 * @param rowIndex			Índice da linha da célula que se quer
	 * 							 saber se é editável.
	 * @param columnIndex		Índice da coluna da célula que se quer
	 * 							 saber se é editável.
	 * 
	 * @return			TRUE se a célula é editável,
	 * 					FALSE caso contrario.
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false; 
	}
	
	/**
	 * Remove todos os eventos da tabela.
	 */
	public void resetTable(){
		int lastIndex = lines.size()-1;
		lastIndex = lastIndex < 0 ? 0 : lastIndex;
		
		lines.clear();
		fireTableRowsDeleted(0,lastIndex);
	}
}
