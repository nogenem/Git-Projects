package Persistencia;

public class ClericDao extends Dao {
	
	public ClericDao(){
		super(new ConexaoBanco(), "ClericSkills");
	}
	public ClericDao(ConexaoBanco c, String tabela) {
		super(c, tabela);
	}
	
}
