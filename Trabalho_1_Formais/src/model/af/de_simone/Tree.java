package model.af.de_simone;

import java.util.ArrayList;
import java.util.Stack;

import model.er.RegExpressionCtrl;

public class Tree {
	private Node root;
	private ArrayList<Node> listLeaves;
	
	public Tree(String regEx) {
		root = createSubTrees(null, regEx);
		listLeaves = new ArrayList<>();
		addLeavesInOrder();
		costuraEmOrderRec(root);
		
		//System.out.println(listLeaves);
		//printPreOrderRec(root);
	}
	
	public Node getRoot(){
		return this.root;
	}
	
	public ArrayList<Node> getListLeaves(){
		return this.listLeaves;
	}

	private Node createSubTrees(Node n, String regEx) {
		Node newNodo = new Node();
		SubTree sub = SubTree.getInstance();
		
		regEx = removeExternalParentheses(regEx);
		int raiz = sub.positionOfRoot(regEx);
		
		if(raiz == -1 && regEx.length() > 1)
			return createSubTrees(n, regEx);
		else if(raiz == -1 && regEx.length() == 1)
			return new Node(regEx.charAt(0), n);
		
		newNodo.setC(regEx.charAt(raiz));
		newNodo.setPai(n);
		
		String erEsq = regEx.substring(0, raiz);
		String erDir = regEx.substring(raiz+1, regEx.length());
		
		if(erEsq.length() > 1)
			newNodo.setFilhoEsq(createSubTrees(newNodo, erEsq));
		else if(erEsq.length() == 1)
			newNodo.setFilhoEsq(new Node(erEsq.charAt(0), newNodo));
		
		if(erDir.length() > 1)
			newNodo.setFilhoDir(createSubTrees(newNodo, erDir));
		else if(erDir.length() == 1)
			newNodo.setFilhoDir(new Node(erDir.charAt(0), newNodo));
		
		return newNodo;
	}
	
	private String removeExternalParentheses(String regEx){
		String tmp = regEx;
		Stack<Character> stackParentheses = new Stack<>();
		char cTmp;
		
		if(tmp.charAt(0) == '(' && tmp.charAt(regEx.length()-1) == ')'){
			/*for (int i = 0; i < tmp.length()/2; i++) {
				if(tmp.charAt(i) == '(' && tmp.charAt(tmp.length()-(i+1)) == ')')
					tmp = "[" +tmp.substring((i+1), tmp.length()-(i+1))+ "]";
				else
					break;
			}*/
			if(tmp.charAt(0) == '(' && tmp.charAt(tmp.length()-1) == ')')
				tmp = "[" +tmp.substring((1), tmp.length()-1)+ "]";
				
			for (int i = 0; i < tmp.length(); i++) {
				cTmp = tmp.charAt(i);
				if(cTmp == '[')
					stackParentheses.push('[');
				else if(cTmp == ']' && stackParentheses.peek() == '[')
					stackParentheses.pop();
				else if(cTmp == '(' && 
						(stackParentheses.peek() == '(' || 
						stackParentheses.peek() == '['))
					stackParentheses.push('(');
				else if(cTmp == ')' && stackParentheses.peek() == '(')
					stackParentheses.pop();
			}
			
			if(stackParentheses.isEmpty())
				return tmp.substring(1, tmp.length()-1);
			else
				return regEx;
		}else
			return regEx;
	}
	
	private void costuraEmOrderRec(Node root){
		if(root == null)
			return;
		
		costuraEmOrderRec(root.getFilhoEsq());
		
		if(!RegExpressionCtrl.isBinaryOperator(root.getC())){
			Node pai = root.getPai();
			if(pai != null){
				while(pai.isCosturado()){
					pai = pai.getPai();
					if(pai == null){
						root.setCostura(new Node('$', null));//node lambda
						return;
					}
				}
				root.setCostura(pai);
				pai.setIsCosturado(true);
			}else
				root.setCostura(new Node('$', null));
		}
		
		costuraEmOrderRec(root.getFilhoDir());
	}
	
	private void addLeavesInOrder(){
		Stack<Node> stackNodes = new Stack<>();
		Node n = root;
		int num = 1;

		while(!stackNodes.isEmpty() || n != null){
			if(n != null){
				stackNodes.push(n);
				n = n.getFilhoEsq();
			}else{
				n = stackNodes.pop();
				if(!RegExpressionCtrl.isOperator(n.getC(),false)){
					n.setNumero(num);
					listLeaves.add(n);
					++num;
				}
				n = n.getFilhoDir();
			}
		}
		
	}
	
	public void printPreOrderRec(Node root){
		if(root != null){
			System.out.println("E: "+root.getFilhoEsq()+" - R: "+root+" - D: "+root.getFilhoDir()+" - Cost: "+root.getCostura());
			printPreOrderRec(root.getFilhoEsq());
			printPreOrderRec(root.getFilhoDir());
		}
	}
}
