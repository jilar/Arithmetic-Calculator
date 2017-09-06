package evaluatortestgui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EvaluatorTestGui extends JFrame implements ActionListener {
	private TextField txField = new TextField();
	private Panel buttonPanel = new Panel();
	private Button buttons[] = new Button[20];                                         //total 20 buttons on the calculator, numbered from left to right, up to down
       private static final String bText[] = {"7","8","9","+","4","5","6","-",             //bText[] array contains text on corresponding buttons
		"1","2","3","*","0","(",")","/","^","CE","C","="};
	Evaluator e = new Evaluator();                                                     //construct new Evaluator                         
	public static void main(String argv[]) {
		EvaluatorTestGui calc = new EvaluatorTestGui();
	}
	public EvaluatorTestGui(){
		setLayout(new BorderLayout());
		add(txField, BorderLayout.NORTH);
		txField.setEditable(false);
		add(buttonPanel, BorderLayout.CENTER);
		buttonPanel.setLayout(new GridLayout(5,4));
		for (int i=0; i<20; i++) //create 20 buttons with corresponding text i in
					 //bText[] array
			buttons[i] = new Button(bText[i]);
		for (int i=0; i<20; i++) //add buttons to button panel
			buttonPanel.add(buttons[i]);
		for (int i=0; i<20; i++) //set up buttons to listen for mouse input
			buttons[i].addActionListener(this);

		setTitle("Calculator");
		setSize(400, 400);
		setLocationByPlatform(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {               
		if(arg0.getSource()==buttons[0])
			txField.setText(txField.getText()+"7");          //concat 7 to text field
		if(arg0.getSource()==buttons[1])
			txField.setText(txField.getText()+"8");          //concat 8 to text field
		if(arg0.getSource()==buttons[2])
			txField.setText(txField.getText()+"9");          //concat 9 to text field
		if(arg0.getSource()==buttons[3])
			txField.setText(txField.getText()+"+");          //concat + to text field
		if(arg0.getSource()==buttons[4])
			txField.setText(txField.getText()+"4");          //concat 4 to text field
		if(arg0.getSource()==buttons[5])
			txField.setText(txField.getText()+"5");          //concat 5 to text field
		if(arg0.getSource()==buttons[6])
			txField.setText(txField.getText()+"6");          //concat 6 to text field
		if(arg0.getSource()==buttons[7])
			txField.setText(txField.getText()+"-");          //concat - to text field
		if(arg0.getSource()==buttons[8])
			txField.setText(txField.getText()+"1");          //concat 1 to text field
		if(arg0.getSource()==buttons[9])
			txField.setText(txField.getText()+"2");          //concat 2 to text field
		if(arg0.getSource()==buttons[10])
			txField.setText(txField.getText()+"3");          //concat 3 to text field
		if(arg0.getSource()==buttons[11])
			txField.setText(txField.getText()+"*");          //concat * to text field
		if(arg0.getSource()==buttons[12])
			txField.setText(txField.getText()+"0");          //concat 0 to text field
		if(arg0.getSource()==buttons[13])
			txField.setText(txField.getText()+"(");          //concat ( to text field
		if(arg0.getSource()==buttons[14])
			txField.setText(txField.getText()+")");          //concat ) to text field
		if(arg0.getSource()==buttons[15])
			txField.setText(txField.getText()+"/");          //concat / to text field
		if(arg0.getSource()==buttons[16])
			txField.setText(txField.getText()+"^");          //concat ^ to text field
		if(arg0.getSource()==buttons[17])                        
			txField.setText("");                             //clears enitre text field
		if(arg0.getSource()==buttons[18]){                       //removes last character from text field
                        String clear = txField.getText();
                        clear=clear.substring(0,clear.length()-1);
			txField.setText(clear);           
                }                                     
		if(arg0.getSource()==buttons[19]){                       //sends textfield string to evaluator class to be evaluated
			int answer=e.eval(txField.getText());            //text field then shows evaulation
			String toString=String.valueOf(answer);
			txField.setText(toString);
		}
	}
}