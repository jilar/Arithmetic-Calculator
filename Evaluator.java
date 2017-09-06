package evaluatortestgui;

import java.util.*;

 //number storage class
class Operand{                                      
	int val;
	//class constructors
	Operand(String tok){                                 
		val= Integer.parseInt(tok);
	}
	Operand(int value){                          
		val=value;
	}
        //getter
	int getValue(){
		return val;
	}
        //checks if token is an operand
	static boolean check(String tok){
		try{
			int a=Integer.parseInt(tok);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}
}

//Operator superclass uses hashmap to construct subclasses accorsing to operator
abstract class Operator {
	static HashMap <String, Operator> operators= new HashMap<String, Operator>();
	static{
		operators.put("#", new BeginOperator());	
		operators.put("!", new TerminationOperator());	
		operators.put("+", new AdditionOperator());	
		operators.put("-", new SubtractionOperator());	
		operators.put("*", new MultiplicationOperator());	
		operators.put("/", new DivisionOperator());	
		operators.put("^", new PowerOperator());
		operators.put("(", new OpenOperator());
		operators.put(")", new CloseOperator());
	}
	
        //check if token is a verified operand
	static boolean check(String tok){
		return operators.containsKey(tok);
	}
	
        //int priority of each subclass returns its priority number (see documentation)
	abstract int priority();
        //Operand Execute of each subclass does respective operation and returns another operand
	abstract Operand execute(Operand op1, Operand op2);
	
}
//# subclass
class BeginOperator extends Operator{
	int priority(){
		return 0;
	}
	Operand execute(Operand op1, Operand op2){           //this should never happen
		return null;
	}
}
//! subclass
class TerminationOperator extends Operator{
	int priority(){
		return 1;
	}
	Operand execute(Operand op1, Operand op2){            //this should never happen
		 return null;
	}
}
//addition subclass
class AdditionOperator extends Operator{
	 int priority(){
		 return 4;
	 }
	 
	 Operand execute(Operand op1, Operand op2){
		 return new Operand(op1.getValue()+op2.getValue());
	 }
}
//subtraction subclass
class SubtractionOperator extends Operator{
	 int priority(){
		 return 4;
	 }
	 Operand execute(Operand op1, Operand op2){
		 return new Operand(op1.getValue()-op2.getValue());
	 }
}
//multiplication subclass
class MultiplicationOperator extends Operator{
	int priority(){
		 return 5;
	 }
	 Operand execute(Operand op1, Operand op2){
		 return new Operand(op1.getValue()*op2.getValue());
	 }
}
// division subclass
class DivisionOperator extends Operator{
	int priority(){
		 return 5;
	 }
	 Operand execute(Operand op1, Operand op2){
		 return new Operand(op1.getValue()/op2.getValue());
	 }
}

//power subclass
class PowerOperator extends Operator{	
	int priority(){
		return 6;
	}
	 Operand execute(Operand op1, Operand op2){
		 if (op2.getValue()==0){
			 return new Operand(1);
		 }
		 int product=op1.getValue();
		 for(int i=1; i<op2.getValue(); i++){
			 product= product*op1.getValue();
		 }
		 return new Operand(product);
	 }
}
//left parenthesis subclass
class OpenOperator extends Operator{
	int priority(){
		return 3;
	}
	 Operand execute(Operand op1, Operand op2){                //this should never happen
		return null;
	 }
}
//right parenthesis subclass
class CloseOperator extends Operator{
	 int priority(){
		 return 2;
	 }
	 Operand execute(Operand op1, Operand op2){                //this should never happen
		return null; 
	 }
}

/**
 * The Class Evaluator.
 */
public class Evaluator {
	
	/** The opd stack. */
	private Stack<Operand> opdStack;
	
	/** The opr stack. */
	private Stack<Operator> oprStack;
	
	/**
	 * Instantiates a new evaluator.
	 */
	public Evaluator() {
		opdStack = new Stack<Operand>();
		oprStack = new Stack<Operator>();
	}
	
	/**
	 * Eval.
	 *
	 * @param expr the expr
	 * @return the int
	 */
	public int eval(String expr) {
		String tok;
		expr=expr.concat("!");                       //adds ! to the end of the sting, acts as a termination operater
		oprStack.push(Operator.operators.get("#"));
		String delimiters = "+-*^/#!() ";
 
		StringTokenizer st = new StringTokenizer(expr,delimiters,true);
 
		while (st.hasMoreTokens()) {
			if ( !(tok = st.nextToken()).equals(" ")) { 
				if (Operand.check(tok)) { 
					opdStack.push(new Operand(tok));
				} else {
					if (!Operator.check(tok)) {
						System.out.println("*****invalid token******");
						System.exit(1);
					}	
 
					Operator newOpr = Operator.operators.get(tok); // POINT 1
					
 
					while (((Operator)oprStack.peek()).priority() >= newOpr.priority()&& newOpr.priority()!=3 && newOpr.priority()!=6){     //see documentation for explanation of while loop
					
					Operator oldOpr = ((Operator)oprStack.pop());
					
					if (oldOpr.priority()==3)
						break;

					Operand op2 = (Operand)opdStack.pop();
					Operand op1 = (Operand)opdStack.pop();
					opdStack.push(oldOpr.execute(op1,op2));
					}
				if(newOpr.priority()!=2){                                     //close parenthesis never placed onto stack
					oprStack.push(newOpr);
				}	
			   }
			}
		}
		return eval(opdStack, oprStack);
	}
	
	int eval(Stack<Operand> opd, Stack<Operator> opr){                      //removes remaning operaraters from stack that arent # or ! while doing respective operation
		while(opr.peek().priority()>1){                                 //then later clears the operator stack and returns remaining operand of operand stack
			Operator oldOpr = ((Operator)oprStack.pop());
			Operand op2 = (Operand)opd.pop();
			Operand op1 = (Operand)opd.pop();
			opdStack.push(oldOpr.execute(op1,op2));
		}
		opr.pop();
		opr.pop();
		return opd.pop().getValue();
	}
}