package com.kurungkurawal.PopComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Konglie on 1/6/2017.
 * konglie@kurungkurawal.com
 */
public class JTablePopup extends JTable implements PopupComponent {
	private JTablePopup self;
	private PopupCombo theCombo;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;
	private String[] selected = null;
	public JTablePopup(){
		super();
		self = this;
		tableModel = new DefaultTableModel(){
			@Override
			public boolean isCellEditable(int r, int c){
				return false;
			}
		};
		tableModel.setColumnIdentifiers(new String[]{"Name", "Gender", "Email"});
		setModel(tableModel);
		scrollPane = new JScrollPane(self);
		initEvents();
		initData("");
	}

	private void initEvents(){
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable table =(JTable) e.getSource();
				Point p = e.getPoint();
				int row = table.rowAtPoint(p);
				if(row < 0){
					return;
				}

				int cols = getColumnModel().getColumnCount();
				selected = new String[cols];
				for(int i = 0; i < cols; i++){
					selected[i] = getValueAt(row, i).toString();
				}
				theCombo.setText(selected[0]);

				// if double click
				if (e.getClickCount() == 2) {
					theCombo.hidePopup();
				}
			}
		});
	}

	private String[][] sample = new String[][]{
			"Joseph,Male,jford0@jigsy.com".split(","),
			"Lillian,Female,levans1@mit.edu".split(","),
			"Rachel,Female,rstone2@dot.gov".split(","),
			"Ryan,Male,rgeorge3@mashable.com".split(","),
			"Lisa,Female,lperez4@spiegel.de".split(","),
			"Arthur,Male,arose5@photobucket.com".split(","),
			"Kenneth,Male,krichards6@mapy.cz".split(","),
			"Kathryn,Female,kaustin7@discovery.com".split(","),
			"Jack,Male,jlopez8@canalblog.com".split(","),
			"Michelle,Female,mharris9@dagondesign.com".split(","),
			"Louise,Female,lturnera@state.tx.us".split(","),
			"Paul,Male,phansenb@delicious.com".split(","),
			"Marilyn,Female,mramosc@yale.edu".split(","),
			"Betty,Female,bkennedyd@moonfruit.com".split(","),
			"John,Male,jmyerse@craigslist.org".split(","),
			"Ruby,Female,rmurphyf@apple.com".split(","),
			"Amy,Female,amorgang@newsvine.com".split(","),
			"Robert,Male,rfernandezh@buzzfeed.com".split(","),
			"Linda,Female,lstevensi@phpbb.com".split(","),
			"Clarence,Male,crodriguezj@mayoclinic.com".split(","),
			"Todd,Male,tmurrayk@google.fr".split(","),
			"Richard,Male,rchavezl@blogger.com".split(","),
			"Melissa,Female,mdixonm@issuu.com".split(","),
			"Raymond,Male,rnguyenn@exblog.jp".split(","),
			"Andrew,Male,alittleo@marriott.com".split(",")
	};
	private void initData(String filter){
		tableModel.setRowCount(0);
		for(String[] data : sample){
			if(filter.isEmpty() || data[0].toLowerCase().contains(filter.toLowerCase())) {
				tableModel.addRow(data);
			}
		}
	}

	public boolean getScrollableTracksViewportHeight() {
		if(getParent() instanceof JViewport) {
			return getParent().getHeight() > getPreferredSize().height;
		}

		return super.getScrollableTracksViewportHeight();
	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(getRowCount() < 1){
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(
					RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setColor(Color.BLACK);
			g2.drawString("Data tidak ditemukan.", 10, getTableHeader().getHeight());
		}
	}

	@Override
	public JComponent getComponent() {
		return scrollPane;
	}

	@Override
	public void filter(String search) {
		initData(search);
	}

	@Override
	public void showPop() {
		// popup is shown,
	}

	@Override
	public void hidePop() {
		// popup is hidden
	}

	@Override
	public void setPopupCombo(PopupCombo popupCombo) {
		theCombo = popupCombo;
	}

	@Override
	public Object getSelected() {
		return selected;
	}
}
