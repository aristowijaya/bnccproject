package bnccproject;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

public class Main {
	private JTable table;
	private JTextField tfbid;
	
	public Main() {
		aplikasi();
		Connect();
		table_load();
	}
	
	public void aplikasi() {
		JFrame frame = new JFrame();
		frame.setSize(500,550);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		frame.setTitle("Jual Beli Sepatu");
		
		JLabel labelJudul = new JLabel("Jual Beli Sepatu");
		labelJudul.setBounds(200, 5, 150, 30);
		frame.add(labelJudul);
		
		JLabel labelModel = new JLabel("Model");
		labelModel.setBounds(20, 35, 150, 25);
		frame.add(labelModel);
		
		JTextField tfModel = new JTextField();
		tfModel.setBounds(80, 35, 100, 25);
		frame.add(tfModel);
				
		JLabel labelMerk = new JLabel("Merk");
	    labelMerk.setBounds(22, 75, 150, 25);
	    frame.add(labelMerk);
	    
	    JTextField tfMerk = new JTextField();
		tfMerk.setBounds(80, 75, 100, 25);
		frame.add(tfMerk);
		
		JLabel labelWarna = new JLabel("Warna");
		labelWarna.setBounds(20, 115, 100, 25);
		frame.add(labelWarna);
		
		JTextField tfWarna = new JTextField();
		tfWarna.setBounds(80, 115, 100, 25);
		frame.add(tfWarna);
		
		JLabel labelKuantitas = new JLabel("Kuantitas");
		labelKuantitas.setBounds(20, 155, 150, 25);
		frame.add(labelKuantitas);
		
		JTextField tfKuantitas = new JTextField();
		tfKuantitas.setBounds(80, 155, 100, 25);
		frame.add(tfKuantitas);
		
		JLabel labelHarga = new JLabel("Harga");
		labelHarga.setBounds(200, 35, 150, 25);
		frame.add(labelHarga);
		
		JTextField tfHarga = new JTextField();
		tfHarga.setBounds(260, 35, 100, 25);
		frame.add(tfHarga);
		
		JLabel labelTotalHarga = new JLabel("Total Harga");
		labelTotalHarga.setBounds(390, 5, 100, 25);
		frame.add(labelTotalHarga);
		
		JTextField tfTotalHarga = new JTextField();
		tfTotalHarga.setBounds(380, 35, 100, 25);
		frame.add(tfTotalHarga);
		tfTotalHarga.setEnabled(false);
		
		JLabel labelBayar = new JLabel("Bayar");
		labelBayar.setBounds(200, 75, 150, 25);
		frame.add(labelBayar);
		
		JTextField tfBayar = new JTextField();
		tfBayar.setBounds(260, 75, 100, 25);
		frame.add(tfBayar);
		
		JLabel labelKembalian = new JLabel("Kembalian");
		labelKembalian.setBounds(200, 115, 150, 25);
		frame.add(labelKembalian);
		
		JTextField tfKembalian = new JTextField();
		tfKembalian.setBounds(260, 115, 100, 25);
		frame.add(tfKembalian);
		tfKembalian.setEnabled(false);
		
		//======================== button tambah ============================= 
		
		JButton buttonTambah = new JButton("Tambah");
		buttonTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
					String model, merk, warna, harga, kuantitas, bayar, totalharga;
					
					model = tfModel.getText();
					merk = tfMerk.getText();
					warna = tfWarna.getText();
					harga = tfHarga.getText();
					kuantitas = tfKuantitas.getText();
					bayar = tfBayar.getText();
					
					
					
					try {
						double quantity = Double.parseDouble(kuantitas);
						double price = Double.parseDouble(harga);
	                    double payment = Double.parseDouble(bayar);
	                    
	                    double total = quantity * price;
	                    double returnAmount = payment - (price*quantity);
	                    tfKembalian.setText(String.valueOf(returnAmount));
	                    tfTotalHarga.setText(String.valueOf(total));
						
						pst = con.prepareStatement("insert into sepatu(model, merk, warna, harga, kuantitas, bayar, kembalian) "
								+ "values(?,?,?,?,?,?,?)");
						pst.setString(1, model);
						pst.setString(2, merk);
						pst.setString(3, warna);
						pst.setString(4, harga);
						pst.setString(5, kuantitas);
						pst.setString(6, bayar);
						pst.setString(7, String.valueOf(returnAmount));
						
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Record added!");
						
						table_load();
						
						tfModel.setText("");
						tfMerk.setText("");
						tfWarna.setText("");
						tfHarga.setText("");
						tfKuantitas.setText("");
						tfBayar.setText("");
						tfKembalian.setText("");
						tfTotalHarga.setText("");
						
						tfModel.requestFocus();
						
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				
			}
		});
		buttonTambah.setBounds(200, 155, 80, 25);
		frame.add(buttonTambah);
		
		//======================== button update ============================= 
		
		JButton buttonUpdate = new JButton("Update");
		buttonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
					String model, merk, warna, harga, kuantitas, bayar, kembalian, id;
					
					model = tfModel.getText();
					merk = tfMerk.getText();
					warna = tfWarna.getText();
					harga = tfHarga.getText();
					kuantitas = tfKuantitas.getText();
					bayar = tfBayar.getText();
					kembalian = tfKembalian.getText();
					id = tfbid.getText();
					
					try {
						
						pst = con.prepareStatement("update sepatu set model = ?, merk = ?, warna = ?, harga = ?"
								+ ", kuantitas = ?, bayar = ?, kembalian = ? where id = ? ");
						pst.setString(1, model);
						pst.setString(2, merk);
						pst.setString(3, warna);
						pst.setString(4, harga);
						pst.setString(5, kuantitas);
						pst.setString(6, bayar);
						pst.setString(7, kembalian);
						pst.setString(8, id);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Record updated!");
						
						table_load();
						
						tfModel.setText("");
						tfMerk.setText("");
						tfWarna.setText("");
						tfHarga.setText("");
						tfKuantitas.setText("");
						tfBayar.setText("");
						tfKembalian.setText("");
						tfTotalHarga.setText("");
						
						tfModel.requestFocus();
						
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				
			}
		});
		buttonUpdate.setBounds(290, 155, 80, 25);
		frame.add(buttonUpdate);
		
		//======================== button delete ============================= 
		
		JButton buttonDelete = new JButton("Delete");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
					String id;
					
					id = tfbid.getText();
					
					try {
						
						pst = con.prepareStatement("delete from sepatu where id = ?");
						pst.setString(1, id);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Record deleted!");
						
						table_load();
						
						tfModel.setText("");
						tfMerk.setText("");
						tfWarna.setText("");
						tfHarga.setText("");
						tfKuantitas.setText("");
						tfBayar.setText("");
						tfKembalian.setText("");
						tfTotalHarga.setText("");
						
						tfModel.requestFocus();
						
						
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				
			}
		});
		buttonDelete.setBounds(380, 155, 80, 25);
		frame.add(buttonDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 185, 480, 300);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel labelSearch = new JLabel("Search ID");
		labelSearch.setBounds(5, 485, 100, 25);
		frame.add(labelSearch);
		
		//======================== search id ============================= 
		
		tfbid = new JTextField();
		tfbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				try {
					
					String id = tfbid.getText();
					
					pst = con.prepareStatement("select model,merk,warna,harga,kuantitas,bayar,kembalian from sepatu where id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
					
					if (rs.next()== true) {
						String model = rs.getString(1);
						String merk = rs.getString(2);
						String warna = rs.getString(3);
						String harga = rs.getString(4);
						String kuantitas = rs.getString(5);
						String bayar = rs.getString(6);
						String kembalian = rs.getString(7);
						
						tfModel.setText(model);
						tfMerk.setText(merk);
						tfWarna.setText(warna);
						tfHarga.setText(harga);
						tfKuantitas.setText(kuantitas);
						tfBayar.setText(bayar);
						tfKembalian.setText(kembalian);
					
						
					}
					
					else {
						
						tfModel.setText("");
						tfMerk.setText("");
						tfWarna.setText("");
						tfHarga.setText("");
						tfKuantitas.setText("");
						tfBayar.setText("");
						tfKembalian.setText("");
						
					}
					
				}
				
				catch (SQLException ex) {
					
				}
				
				
			}
		});
		tfbid.setBounds(80, 485, 110, 25);
		frame.add(tfbid);
		
		JButton buttonStruk = new JButton("Struk");
		buttonStruk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JFrame struk = new JFrame("Struk");
                struk.setSize(500, 500);
                struk.setDefaultCloseOperation(struk.DISPOSE_ON_CLOSE);
                struk.setLocationRelativeTo(null);

                table_load();

                JTable strukTable = new JTable();
                strukTable.setModel(table.getModel()); 

                JScrollPane strukScrollPane = new JScrollPane(strukTable);
                struk.getContentPane().add(strukScrollPane);

                struk.setVisible(true);
            }
        });
		buttonStruk.setBounds(380, 485, 80, 25);
		frame.add(buttonStruk);
	
		
		
		
		frame.setVisible(true);
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jualsepatu", "root", "");
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException x) {
			x.printStackTrace();
		}
			
	}
	
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from sepatu");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Main();

	}

}
