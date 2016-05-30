package main;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class GUI implements ActionListener
{
	
	static Connection con;
	static Statement stmt;
	DatabaseMetaData dbmd;
	ResultSet rs;
	ResultSetMetaData rsmd;
	int colCount,i,j;
	ArrayList<JLabel> list1 = new ArrayList<JLabel>();
	ArrayList<JLabel> list2 = new ArrayList<JLabel>();
	ArrayList<String> values = new ArrayList<String>();
	HashMap<String, String> map = new HashMap<>();

	String sb,s1,s2,filePath;
	String[] ss;
	
	String imagePath = "data/images/image.jpg";
	ImageIcon image;
	
	GridBagLayout g=new GridBagLayout();
	GridBagConstraints c=new GridBagConstraints();
	
	Boolean flag,characterFlag=false;
	JButton b1,b2,ssb1,ssb2,ssb3,slb1,slb2,slb3,slb4,source;
	JButton cropsb,seasonsb,soilsb;
	JButton mainb1 = new JButton("Select by Crops");
	JButton mainb2 = new JButton("Select by Season");
	JButton exit = new JButton("Exit");
	JButton character = new JButton("Show Characteristics");
	
	JFrame f1=new JFrame();
	JFrame f2=new JFrame();
	JFrame f3=new JFrame();
	JFrame f4 = new JFrame();
	JFrame f5 = new JFrame();
	JFrame f6 = new JFrame();
	
	JPanel p1,p2,p3;
	JPanel p4=new JPanel(g);
	JPanel p5=new JPanel(g);
	JPanel p6=new JPanel(g);
	JPanel p7 = new JPanel();
	JPanel p8 = new JPanel(g);
	JPanel p9 = new JPanel();
	JTextField tf1,tf2;
	
	JLabel l1,l2,l3;
	JLabel ssl1,ssl2,ssl3;
	JLabel sll1,sll2,sll3;
	
	JComboBox<String> jcb1,jcb2,jcbss,jcb3,jcb4,jcbsl,jcb5,jcb6,jcb7;
	Vector<String> comboBoxItems1 = new Vector<String>();
	DefaultComboBoxModel<String> cbm1 = new DefaultComboBoxModel<String>(comboBoxItems1);
	
	Vector<String> comboBoxItems2 = new Vector<String>();
	DefaultComboBoxModel<String> cbm2 = new DefaultComboBoxModel<String>(comboBoxItems2);
	
	Vector<String> comboBoxItems3 = new Vector<String>();
	DefaultComboBoxModel<String> cbm3 = new DefaultComboBoxModel<String>(comboBoxItems3);
	
	Vector<String> comboBoxItems4 = new Vector<String>();
	DefaultComboBoxModel<String> cbm4 = new DefaultComboBoxModel<String>(comboBoxItems4);
	
	Vector<String> comboBoxItems5 = new Vector<String>();
	DefaultComboBoxModel<String> cbm5 = new DefaultComboBoxModel<String>(comboBoxItems5);
	
	Vector<String> tables = new Vector<String>();
	
	File file;
	
	private void mainScreenGUI()
	{
		soilsb = new JButton("Soil Selection");
		seasonsb = new JButton("Season Selection");
		cropsb = new JButton("Crop Selection");
		
		soilsb.setFocusable(false);
		seasonsb.setFocusable(false);
		cropsb.setFocusable(false);
		exit.setFocusable(false);
		
		soilsb.addActionListener(this);
		seasonsb.addActionListener(this);
		cropsb.addActionListener(this);
		exit.addActionListener(this);
		
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx=0;
		c.gridy=0;
		p5.add(soilsb,c);
		c.gridx=1;
		p5.add(seasonsb,c);
		c.gridx=0;
		c.gridy=1;
		p5.add(cropsb,c);
		c.gridx=1;
		p5.add(exit,c);
		
		f3.add(p5);
		
		f3.setTitle("Method Selection - Crop Prediction");
		f3.pack();
		f3.setResizable(false);
		f3.setVisible(true);
		f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f3.setLocationRelativeTo(null);
	}
	
	private void soilGUI()
	{
		sll1 = new JLabel("Select Soil");
		sll2 = new JLabel("Select Crop");
		sll3 = new JLabel("Select Type");
		slb1 = new JButton("Next");
		slb2 = new JButton("Next");
		slb3 = new JButton("Generate Report");
		
		slb1.setFocusable(false);
		slb2.setFocusable(false);
		slb3.setFocusable(false);
		
		slb1.addActionListener(this);
		slb2.addActionListener(this);
		slb3.addActionListener(this);
		
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx=0;
		c.gridy=0;
		p8.add(sll1,c);
		c.gridx=1;
		String[] soils = {"Alluvial Soil","Black Soil","Black Sandy Soil","Clay","Drained Soil","Laterite Soil",
				"Loam","Moist Soil","Red Soil","Red Laterite Soil","Red Sandy Soil","Sandy Soil",
				"Soils with high fertility","All except Chavudu Soil","All Soils"};
		jcbsl = new JComboBox<String>(soils);
		p8.add(jcbsl,c);
		c.gridx=0;
		c.gridy=1;
		p8.add(sll2,c);
		c.gridx=1;
		jcb5 = new JComboBox<String>();
		p8.add(jcb5,c);
		c.gridx=0;
		c.gridy=2;
		p8.add(sll3,c);
		c.gridx=1;
		jcb6 = new JComboBox<String>();
		p8.add(jcb6,c);
		
		p9.add(slb1);
		p9.add(slb2);
		p9.add(slb3);
		
		sll2.setVisible(false);
		sll3.setVisible(false);
		
		slb2.setVisible(false);
		slb3.setVisible(false);
		
		jcb5.setVisible(false);
		jcb6.setVisible(false);
		
		f5.add(p8,BorderLayout.NORTH);
		f5.add(p9,BorderLayout.SOUTH);
		f5.setTitle("Soil Selection");
		f5.setVisible(true);
		f5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f5.pack();
		f5.setLocationRelativeTo(null);
	}
	
	private void seasonGUI()
	{	
		ssl1 = new JLabel("Select Season");
		ssl2 = new JLabel("Select Crop");
		ssl3 = new JLabel("Select Type");
		ssb1 = new JButton("Next");
		ssb2 = new JButton("Next");
		ssb3 = new JButton("Generate Report");
		
		ssb1.setFocusable(false);
		ssb2.setFocusable(false);
		ssb3.setFocusable(false);
		
		ssb1.addActionListener(this);
		ssb2.addActionListener(this);
		ssb3.addActionListener(this);
		
		c.gridx=0;
		c.gridy=0;
		c.insets = new Insets(20, 20, 20, 20);
		
		p6.add(ssl1,c);
		c.gridx=1;
		String[] seasons = {"Kharif","Rabi","Summer","Early Kharif","Late Kharif","After Rice","All Seasons"};
		jcbss = new JComboBox<String>(seasons);
		p6.add(jcbss,c);
		c.gridx=0;
		c.gridy=1;
		p6.add(ssl2,c);
		c.gridx=1;
		jcb3 = new JComboBox<String>();
		p6.add(jcb3,c);
		c.gridx=0;
		c.gridy=2;
		p6.add(ssl3,c);
		c.gridx=1;
		jcb4 = new JComboBox<String>();
		p6.add(jcb4,c);
		
		p7.add(ssb1);
		p7.add(ssb2);
		p7.add(ssb3);
		
		ssl2.setVisible(false);
		ssl3.setVisible(false);
		
		ssb2.setVisible(false);
		ssb3.setVisible(false);
		
		jcb3.setVisible(false);
		jcb4.setVisible(false);
		
		f4.add(p6,BorderLayout.NORTH);
		f4.add(p7,BorderLayout.SOUTH);
		f4.setTitle("Season Selection");
		f4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f4.setVisible(true);
		f4.pack();
		f4.setLocationRelativeTo(null);
	}
	
	void cropGUI()
	{
		f1.setTitle("Crop Selection");
		f1.setLayout(new BorderLayout(20,20));
		
		p1=new JPanel(g);
		p2=new JPanel(g);
		p3=new JPanel();
		f1.add(p1,"West");
		f1.add(p2,"East");
		f1.add(p3,"South");
		
		c.insets = new Insets(20, 20, 20, 20);
		c.gridx=0;
		c.gridy=0;
		c.insets=new Insets(10, 10, 10, 10);
		l1=new JLabel("Select crop");
		p1.add(l1,c);
		
		jcb1 = new JComboBox<String>(cbm1);
		try
		{
			dbmd = con.getMetaData();
			rs = dbmd.getTables(null, null, "%", null);
			while(rs.next())
			{
				sb = rs.getString(3);
				if(!sb.startsWith("MSys"))
				{
					jcb1.addItem(sb);
				}
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		c.gridx=1;
		p1.add(jcb1,c);
		
		c.gridx=0;
		c.gridy=1;
		
		l2=new JLabel("Types available");
		l2.setVisible(false);
		p1.add(l2,c);
		
		c.gridx=1;
		
		jcb2 = new JComboBox<String>(cbm2);
		jcb2.setVisible(false);
		p1.add(jcb2,c);
		
		b1=new JButton("Next");
		p3.add(b1,BorderLayout.SOUTH);
		b1.setFocusable(false);
		b1.addActionListener(this);
		
		b2 = new JButton("Generate Report");
		p3.add(b2,BorderLayout.SOUTH);
		b2.setFocusable(false);
		b2.addActionListener(this);
		b2.setVisible(false);
		
		f1.setVisible(true);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.pack();
		f1.setLocationRelativeTo(null);
	}
	
	private void display(int n)
	{
		character.setFocusable(false);
		character.addActionListener(this);
		
		try
		{
			if(n==1)
			{
				s1 = jcb5.getSelectedItem().toString();
				s2 = jcb6.getSelectedItem().toString();
			}
			if(n==2)
			{
				s1 = jcb3.getSelectedItem().toString();
				s2 = jcb4.getSelectedItem().toString();
			}
			if(n==3)
			{
				s1 = jcb1.getSelectedItem().toString();
				s2 = jcb2.getSelectedItem().toString();
			}
			rs = stmt.executeQuery("select * from " + s1 + " where Type='"+ s2 + "'");
			colCount = rs.getMetaData().getColumnCount();
			map.clear();
			while(rs.next())
			{
				for(i=2; i<=colCount; i++)
				{
					values.add(rs.getMetaData().getColumnName(i));
					map.put(values.get(i-2), rs.getString(i));	
				}
				for(i=0; i<values.size(); i++)
				{
					list1.add(new JLabel(values.get(i)));
				}
				if(map.containsKey("Yield"))
				{
					if(s1.equals("Groundnut") |	s1.equals("SeasameOil") | s1.equals("Sunflower"))
					{
						map.put("Yield", map.get("Yield").concat(" Kilos / Acre"));
					}
					else
					{
						map.put("Yield", map.get("Yield").concat(" Quintals / Acre"));
					}
				}
				if(s1.equals("Groundnut"))
				{
					map.put("Seed %", map.get("Seed %").concat(" %"));
					map.put("Oil %", map.get("Oil %").concat(" %"));
				}
				if(s1.equals("SeasameOil"))
				{
					map.put("Oil %", map.get("Oil %").concat(" %"));
				}
				if(!s1.equals("Groundnut")&&map.containsKey("Duration"))
				{
					map.put("Duration", map.get("Duration").concat(" Days"));
				}
				for(i=0; i<list1.size(); i++)
				{
					list2.add(new JLabel(map.get(list1.get(i).getText())));
				}
			}
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		c.insets = new Insets(20, 20, 20, 20);
		c.gridx=0;
		c.gridy=0;
		
		for(i=0; i<list1.size()-1; i++)
		{
			c.gridx=0;
			p4.add(list1.get(i),c);
			c.gridx=1;
			p4.add(list2.get(i),c);
			c.gridy++;
		}
		
		filePath = "C:\\Users\\Dax\\Desktop\\characteristics\\" + s1.toString() + ".txt";
		if(s1.equals("Rice"))
			filePath = "C:\\Users\\Dax\\Desktop\\characteristics\\rice\\" + s1.toString() + ".txt";
		file = new File(filePath);
		if(file.exists())
		{
			characterFlag = true;
			c.gridx=0;
			c.gridy++;
			p4.add(list1.get(list1.size()-1),c);
			c.gridx=1;
			p4.add(character,c);
		}
		else
			characterFlag = false;
		
		f1.setVisible(false);
		f2.setVisible(true);
		f2.setTitle(s1.toString());
		f2.add(p4);
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f2.pack();
		f2.setLocationRelativeTo(null);
	}
	
	private void displayChar()
	{
		JTextArea ch = new JTextArea();
		ch.setEditable(false);
		
		
		if(characterFlag)
		{
			String line;
			BufferedReader br = null;
			try
			{
				br = new BufferedReader(new FileReader(file));
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			try
			{
				line = br.readLine();
				while(line!=null)
				{
					ch.append(line + "\n");
					line = br.readLine();
				}
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			f6.add(ch);
			f6.setTitle("Characteristics");
			f6.pack();
			f6.setLocationRelativeTo(null);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		source = (JButton) e.getSource();
		if(source==character)
		{
			f6.setVisible(true);
			displayChar();
		}
		if(source==exit)
		{
			System.exit(0);
		}
		if(source==soilsb)
		{
			f3.setVisible(false);
			soilGUI();
		}
		if(source==cropsb)
		{
			f3.setVisible(false);
			cropGUI();
		}
		if(source==seasonsb)
		{
			f3.setVisible(false);
			seasonGUI();
		}
		if(source==b1)
		{
			try 
			{
				rs = stmt.executeQuery("select Type from " + jcb1.getSelectedItem().toString());
				jcb2.removeAllItems();
				while(rs.next())
				{
					jcb2.addItem(rs.getString("Type"));
				}
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			l2.setVisible(true);
			jcb2.setVisible(true);

			f1.pack();
			f1.setLocationRelativeTo(null);
			
			b1.setVisible(false);
			b2.setVisible(true);
		}
		if(source==b2)
		{
			display(3);
		}
		if(source==slb1)
		{
			tables.removeAllElements();
			try
			{
				dbmd = con.getMetaData();
				rs = dbmd.getTables(null, null, "%", null);
				while(rs.next())
				{
					sb = rs.getString(3);
					if(!sb.toString().startsWith("MSys"))
					{
						tables.addElement(sb);
					}
				}
				for(int i=0; i<tables.size(); i++)
				{
					rs = stmt.executeQuery("Select * from " + tables.elementAt(i));
					while(rs.next())
					{
						flag = false;
						sb = rs.getString("Soils");
						if(sb.toString().equals("All"))
						{
							sb = "All Soils";
						}
						if(sb.toString().equals(jcbsl.getSelectedItem()))
						{
							jcb5.addItem(tables.elementAt(i));
							break;
						}
						ss = sb.split("/");
						for(j=0; j<ss.length; j++)
						{
							if(ss[j].equals(jcbsl.getSelectedItem()))
							{
								jcb5.addItem(tables.elementAt(i));
								flag = true;
								break;
							}
						}
						if(flag)
						{
							break;
						}
					}
				}
			}
			catch(SQLException e1)
			{
				e1.printStackTrace();
			}
			sll2.setVisible(true);
			jcb5.setVisible(true);
			slb1.setVisible(false);
			slb2.setVisible(true);
			
			f5.pack();
			f5.setLocationRelativeTo(null);
		}
		if(source==slb2)
		{
			tables.removeAllElements();
			jcb6.removeAllItems();
			try
			{
				rs = stmt.executeQuery("select * from " + jcb5.getSelectedItem());
				while(rs.next())
				{
					sb = rs.getString("Soils");
					String s3 = jcbsl.getSelectedItem().toString();
					if(s3.equals("All Soils"))
					{
						s3 = "All";
					}
					if(sb.toString().equals(s3))
					{
						jcb6.addItem(rs.getString("Type"));
						continue;
					}
					ss = sb.split("/");
					for(j=0; j<ss.length; j++)
					{
						if(ss[j].equals(s3))
						{
							jcb6.addItem(rs.getString("Type"));
							break;
						}
					}
				}
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			
			jcb6.setVisible(true);
			sll3.setVisible(true);
			slb2.setVisible(false);
			slb3.setVisible(true);
			
			f5.pack();
			f5.setLocationRelativeTo(null);
		}
		if(source==slb3)
		{
			f5.setVisible(false);
			display(1);
		}
		if(source==ssb1)
		{
			tables.removeAllElements();
			try
			{
				dbmd = con.getMetaData();
				rs = dbmd.getTables(null, null, "%", null);
				while(rs.next())
				{
					sb = rs.getString(3);
					if(!sb.toString().startsWith("MSys"))
					{
						tables.addElement(sb);
					}
				}
				jcb3.removeAllItems();
				for(i=0; i<tables.size(); i++)
				{
					rs = stmt.executeQuery("Select * from " + tables.elementAt(i));
					while(rs.next())
					{
						flag = false;
						map.clear();
						for(j=1; j<=rs.getMetaData().getColumnCount(); j++)
						{
							map.put(rs.getMetaData().getColumnName(j), rs.getString(j));
						}
						if(!map.containsKey("Season"))
							break;
						sb = map.get("Season");
						if(sb.toString().equals("All"))
						{
							sb = "All Seasons";
						}
						if(sb.toString().equals(jcbss.getSelectedItem()))
						{
							jcb3.addItem(tables.elementAt(i));
							break;
						}
						ss = sb.split("[/,]");
						for(j=0; j<ss.length; j++)
						{
							if(ss[j].equals(jcbss.getSelectedItem()))
							{
								jcb3.addItem(tables.elementAt(i));
								flag = true;
								break;
							}
						}
						if(flag)
						{
							break;
						}
					}
				}
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			
			jcb3.setVisible(true);
			ssl2.setVisible(true);
			ssb1.setVisible(false);
			ssb2.setVisible(true);
			
			f4.pack();
			f4.setLocationRelativeTo(null);
		}
		if(source==ssb2)
		{
			tables.removeAllElements();
			jcb4.removeAllItems();
			try
			{
				rs = stmt.executeQuery("select * from " + jcb3.getSelectedItem());
				while(rs.next())
				{
					sb = rs.getString("Season");
					String s3 = jcbss.getSelectedItem().toString();
					if(s3.equals("All Seasons"))
					{
						s3 = "All";
					}
					if(sb.toString().equals(s3))
					{
						jcb4.addItem(rs.getString("Type"));
						continue;
					}
					ss = sb.split("[/,]");
					for(j=0; j<ss.length; j++)
					{
						if(ss[j].equals(s3))
						{
							jcb4.addItem(rs.getString("Type"));
						}
					}
				}
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			
			jcb4.setVisible(true);
			ssl3.setVisible(true);
			ssb2.setVisible(false);
			ssb3.setVisible(true);
			
			f4.pack();
			f4.setLocationRelativeTo(null);
		}
		if(source==ssb3)
		{
			f4.setVisible(false);
			display(2);
		}
	}
	
	void loadFile()
	{
		JFileChooser jfc1=new JFileChooser();
		FileFilter filter=new FileFilter()
		{
			@Override
			public String getDescription()
			{
				return "Select *.mdb or *.accdb files";
			}
			
			@Override
			public boolean accept(File f)
			{
				return f.getName().toLowerCase().endsWith(".mdb") | f.getName().toLowerCase().endsWith(".accdb") | f.isDirectory();
			}
		};
		jfc1.setFileFilter(filter);
		int val;
		while(true)
		{
			val=jfc1.showOpenDialog(jfc1.getParent());
			if(jfc1.getSelectedFile().toString().endsWith(".mdb")|jfc1.getSelectedFile().toString().endsWith(".accdb"))
				break;
			else
				JOptionPane.showMessageDialog(null, "Please select a DataBase file (*.mdb or *.accdb)", "DataBase File Selection Error", 2);
		}
		String file = null;
		if(val==JFileChooser.APPROVE_OPTION)
			try 
			{
				file = jfc1.getSelectedFile().getCanonicalPath();
			}
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		else if(val==JFileChooser.CANCEL_OPTION)
			System.exit(0);
		else if(val==JFileChooser.ERROR_OPTION)
			System.exit(0);
		try
		{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		String database="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ="+file+";";
		try
		{
			con=DriverManager.getConnection(database,"","");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		try
		{
			stmt=con.createStatement();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		mainScreenGUI();
	}
}