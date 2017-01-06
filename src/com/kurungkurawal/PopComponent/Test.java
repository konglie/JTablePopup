package com.kurungkurawal.PopComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Konglie on 1/6/2017.
 * konglie@kurungkurawal.com
 */
public class Test {
	public static void main(String[] args){
		final JFrame frame = new JFrame("[ kurungkurawal.com ] JTable Popup Example");
		PopupComponent buttonPopup = new JTablePopup();
		final PopupCombo popupCombo = new PopupCombo(buttonPopup);

		frame.setMinimumSize(new Dimension(640, 360));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new FlowLayout());

		JButton btn = new JButton("Go");
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String[] val = (String[]) popupCombo.getSelected();
				String msg = (val == null) ? "Belum terpilih" : String.format(
					"Name: %s,%nGender: %s,%nEmail: %s",
						val[0], val[1], val[2]
				);
				JOptionPane.showMessageDialog(frame, msg);
			}
		});

		frame.getContentPane().add(popupCombo);
		frame.getContentPane().add(btn);
		btn.requestFocus();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
