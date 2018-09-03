package com.maize;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.Font;

public class MazeGUI extends JFrame implements ActionListener{
	private JScrollPane scrollPane_1;
	private JTextArea txtAreaOutput;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton btnLoadMazeButton;
	private JTextArea txtAreaOriginal;
	public MazeGUI() {
		guiConfig();
	}
	public void guiConfig()
	{
		scrollPane = new JScrollPane();

		btnLoadMazeButton = new JButton("Load Maze File");
		btnLoadMazeButton.addActionListener(this);
		scrollPane_1 = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnLoadMazeButton))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(76)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 901, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1))))
					.addGap(97))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnLoadMazeButton)
					.addGap(7)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);

		txtAreaOutput = new JTextArea();
		txtAreaOutput.setFont(new Font("Monospaced", Font.PLAIN, 20));
		scrollPane_1.setViewportView(txtAreaOutput);
		scrollPane_1.setBorder(BorderFactory.createTitledBorder("Solution"));

		//textArea = BorderFactory.createTitledBorder((Border) null);
		scrollPane.setViewportView(textArea);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Original"));
		
		txtAreaOriginal = new JTextArea();
		txtAreaOriginal.setFont(new Font("Monospaced", Font.PLAIN, 20));
		scrollPane.setViewportView(txtAreaOriginal);
		
		getContentPane().setLayout(groupLayout);
		
		this.setVisible(true);
				

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new MazeGUI();

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = null;
		source=e.getSource();
		
		if (source == btnLoadMazeButton)
		{
			JFileChooser jFC = new JFileChooser();
			int returnVal = jFC.showOpenDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
			
				String map=null;
				
				ArrayList<MazeByDirectionPreference> mazeList = new ArrayList<>(); 
				try {
					System.out.println("file out : " + jFC.getSelectedFile().getPath()) ;
					map =jFC.getSelectedFile().getPath();
					mazeList.add(new MazeByDirectionPreference(map , "NEWS") );
				} catch (IOException e1) {
					System.out.println(e1);
				}

				System.out.println("Original Structure");
				
				txtAreaOriginal.setText(mazeList.get(0).printMaze(mazeList.get(0).getOriginalStructure(),'o'));

				try 
				{
					mazeList.add(new MazeByDirectionPreference(map , "NWES"));
				}
				catch (IOException io)
				{
					System.out.println(io);
				}

				try {
					mazeList.add(new MazeByDirectionPreference(map , "SEWN"));
				} catch (IOException io) 
				{
					System.out.println(io);
				}

				try {
					mazeList.add(new MazeByDirectionPreference(map , "SWEN"));
				} catch (IOException io) {
					System.out.println(io);
				}

				try {
					mazeList.add(new MazeByDirectionPreference(map , "WESN"));
				} catch (IOException io) {
					System.out.println(io);
				}

				try {
					mazeList.add(new MazeByDirectionPreference(map , "WENS"));
				} catch (IOException io) {
					System.out.println(io);
				}

				try {
					mazeList.add(new MazeByDirectionPreference(map , "ENSW"));
				} catch (IOException io) {
					System.out.println(io);
				}

				try {
					mazeList.add(new MazeByDirectionPreference(map , "ESNW"));
				} catch (IOException io) {
					System.out.println(io);
				}

				Collections.sort(mazeList);
				boolean first=true;
				for (MazeByDirectionPreference maze: mazeList)
				{
					//print stars
					if (first)
					{
						System.out.println("Best route using : " + maze.getDirectionPreference());
						txtAreaOutput.setText("Best route using:\n" + maze.printMaze(maze.getMazeStructure(),'o') +"Rest of the routes using\n");
					}
					else
					{
						System.out.println("Rest of routes ");
						txtAreaOutput.append( maze.printMaze(maze.getMazeStructure(),'o') );
					}
					first=false;
				}
			}
			else
			{
				txtAreaOutput.append("errors encountered ");
			}
		}
		
		
	}
}
