import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui {

	private JFrame frame;
	JComboBox functionSelecter_comboBox;
    ArrayList<String> functions = new ArrayList<>(Arrays.asList("Average", "Min-Max", "Range", "Standard Deviation", "Count"));
	
    private void loadFunctionsComboBox() {
		for (String function : functions) {
			functionSelecter_comboBox.addItem(function);
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
		loadFunctionsComboBox();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 459, 454);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(0, 0, 443, 415);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hadoop Jobs");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(165, 23, 95, 27);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Jobs.csv");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(42, 89, 61, 27);
		panel.add(lblNewLabel_1);
		
		functionSelecter_comboBox = new JComboBox();
		functionSelecter_comboBox.setBounds(165, 92, 89, 22);
		panel.add(functionSelecter_comboBox);
		
		JButton functionSelect_Button = new JButton("Run");
		 functionSelect_Button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String selectedFunc = functionSelecter_comboBox.getSelectedItem().toString();
	                if (selectedFunc.equals("Average")) {
	                    String input = "hdfs://masterNode:9000/data/jobs.csv";
	                    String output = "hdfs://masterNode:9000/data/average";

	                    try {
	                        //AverageJob.runJob(input, output);
	                        JOptionPane.showMessageDialog(frame, "Job completed successfully!");
	                    } catch (Exception ex) {
	                        System.out.println("Job failed: " + ex.getMessage());
	                    }
	                }
	            }
	        });

		functionSelect_Button.setBounds(303, 92, 121, 23);
		panel.add(functionSelect_Button);
		
		JLabel lblNewLabel_2 = new JLabel("Results");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(42, 161, 61, 14);
		panel.add(lblNewLabel_2);
		
		JLabel results_labelArea = new JLabel("");
		results_labelArea.setBounds(42, 200, 382, 172);
		panel.add(results_labelArea);
	}
}
