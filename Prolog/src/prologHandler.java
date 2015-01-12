import java.util.Hashtable;
import javax.swing.JOptionPane;
import jpl.Atom;
import jpl.Query;
import jpl.Term;
import jpl.Variable;

public class prologHandler {

	public prologHandler(String plFile, String bdFile){
		Query query = new Query("consult('"+plFile+"')");
		if (!query.hasSolution()){
			System.err.println("consult('"+plFile+"') failed");
			System.exit(0);
		}

		query = new Query("carregaArq('"+bdFile+"')");
		if (!query.hasSolution()){
			System.err.println("carregaArq('"+plFile+"') failed");
			System.exit(0);
		}
	}

	/* r = reposta do usuario, 's' pra sim e 'n' pra nao
	   pAtual = pergunta atual do sistema */
	public String getNextQuestion(String r, String pAtual){
		Query query = null;
		Variable nextQuestion = new Variable("NQ"); //vai receber a proxima pergunta
		String NQ = "";

		if(r.equals("s")){
			query = new Query("getNextQuestionForYes",
							   new Term[] {
									new Atom(pAtual),
									nextQuestion
							  });
			if (!query.hasSolution()){
				JOptionPane.showMessageDialog(null, "Ocorreu um erro durante o processamento.");
				System.err.println("getNextQuestionForYes(CQ, NQ) failed");
				System.exit(0);
			}
		}else{
			query = new Query("getNextQuestionForNo",
							   new Term[] {
									new Atom(pAtual),
									nextQuestion
							 });
		}

		Hashtable solution = query.oneSolution();
			NQ = solution.get("NQ").toString();
			NQ = NQ.replaceAll("'", "");
		return NQ;
	}
}
